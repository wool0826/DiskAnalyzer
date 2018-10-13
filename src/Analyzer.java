import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class Analyzer extends JFrame {
    public static Stack<MyDataNode> stack = new Stack();
    public static Loading load;

    public Analyzer(MyDataNode node){
        super("Disk Analyzer");
        setDefaultCloseOperation(3);
        setLayout(new FlowLayout());
        MyAbstractTreeTableModel treeTableModel = new MyDataModel(node);
        MyTreeTable myTreeTable = new MyTreeTable(treeTableModel);
        Container cPane = getContentPane();
        JScrollPane js = new JScrollPane(myTreeTable);
        js.setPreferredSize(new Dimension(800, 500));
        cPane.add(js);

        setSize(1000, 800);
        pack();

        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch ( Exception e){
            e.printStackTrace();
        }

        String result = SelectDrive();
        if(result == null)
            return;

        new Analyzer(execute(result));
    }

    public static String SelectDrive()
    {
        File[] roots = File.listRoots();
        String[] drives = new String[roots.length];
        int count = 0;

        for(int i=0,j=roots.length;i<j;i++){
            File f = roots[i];
            if(f.isDirectory()){
                drives[count++] = f.toString();
            }
        }

        String input = (String)JOptionPane.showInputDialog(null, "Select drive you want to analyze.", "Select Drive", 3, null, drives, drives[0]);

        return input;
    }

    public static MyDataNode execute(String path)
    {
        File root = new File(path);
        File[] list = root.listFiles();

        int length = 0;
        for (int i = 0, j=list.length; i < j; i++)
        {
            File f = list[i];
            if (!f.isHidden()) {
                length++;
            }
        }
        load = new Loading(length);

        MyDataNode rootNode = new MyDataNode(path, 0, null);

        stack.push(rootNode);

        for (int j = 0, k=list.length; j < k; j++)
        {
            File f = list[j];

            if (!f.isHidden())
            {
                load.path = f.getAbsolutePath();
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        Analyzer.load.increase();
                        Analyzer.load.p.setText("처리 중: " + Analyzer.load.path);
                    }
                }).start();

                sizeCheck(f);
            }
        }
        stack.pop();

        rootNode.sort();

        load.complete();

        return rootNode;
    }

    public static long sizeCheck(File dir)
    {
        String unit = "";
        if ((!dir.isDirectory()) || (dir.isFile()))
        {
            long size = dir.length();

            stack.peek().getChildren().add(new MyDataNode(dir.getName(), size, null));

            return size;
        }

        File[] list = dir.listFiles();
        long sum = 0L;
        if (list == null) {
            return 0L;
        }
        MyDataNode pos = new MyDataNode(dir.getName(), 0L, null);

        stack.push(pos);
        for (int i = 0, j=list.length; i < j; i++)
        {
            File f = list[i];
            load.path = f.getAbsolutePath();
            new Thread(new Runnable()
            {
                public void run()
                {
                    Analyzer.load.p.setText("처리 중: " + Analyzer.load.path);
                }
            }).start();
            sum += sizeCheck(f);
        }
        stack.pop();

        pos.setSize(sum);
        pos.sort();

        stack.peek().getChildren().add(pos);

        return sum;
    }
}
