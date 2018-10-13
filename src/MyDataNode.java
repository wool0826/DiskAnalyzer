import java.util.ArrayList;
import java.util.List;

public class MyDataNode
{
    private String name;
    private long size;
    private List<MyDataNode> children;
    static final long kb = 1024;
    static final long mb = 1048576; // 1024 ** 2
    static final long gb = 1073741824; // 1024 ** 3



    public MyDataNode(String name, long size, List<MyDataNode> children)
    {
        this.name = name;
        this.size = size;

        this.children = children;
        if (this.children == null) {
            this.children = new ArrayList();
        }
    }

    public String getName()
    {
        return this.name;
    }

    public long getbyteSize()
    {
        return this.size;
    }

    public long getRefinedSize(){
        switch(this.getUnit()){
            case "GB": return this.size/gb;
            case "MB": return this.size/mb;
            case "KB": return this.size/kb;
            default: return this.size;
        }
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getUnit()
    {
        if(size >= gb) return "GB";
        else if(size >= mb) return "MB";
        else if(size >= kb) return "KB";
        else return "B";
    }

    public void setChildren(List<MyDataNode> list)
    {
        this.children = list;
    }

    public List<MyDataNode> getChildren()
    {
        return this.children;
    }

    public void sort()
    {
        this.children.sort(new MyComparator());
    }

    public String toString()
    {
        return this.name;
    }
}
