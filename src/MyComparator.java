import java.util.Comparator;

public class MyComparator
        implements Comparator<MyDataNode>
{
    public int compare(MyDataNode arg1, MyDataNode arg2)
    {
        long size1 = arg1.getbyteSize();
        long size2 = arg2.getbyteSize();

        return size1 == size2 ? 0 : size1 > size2 ? -1 : 1;
    }
}
