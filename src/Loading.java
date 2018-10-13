import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Loading
        extends JFrame
{
    public JProgressBar pb = new JProgressBar();
    public JLabel p = new JLabel("");
    public int total;
    public int count;
    public String path;

    public Loading(int total)
    {
        this.total = total;

        setLayout(new GridLayout(2, 1, 0, 5));
        setDefaultCloseOperation(3);

        this.pb.setStringPainted(true);

        Container c = getContentPane();
        c.add(this.p);
        c.add(this.pb);

        setResizable(false);
        setSize(350, 120);
        setVisible(true);

        path = "";
    }

    public void increase()
    {
        this.pb.setValue(this.count * 100 / this.total);
        this.pb.repaint();
        this.count += 1;
    }

    public void complete()
    {
        setVisible(false);
    }
}