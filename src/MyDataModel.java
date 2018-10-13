import java.util.List;

public class MyDataModel
        extends MyAbstractTreeTableModel
{
    protected static String[] columnNames = { "List", "Size" };
    protected static Class<?>[] columnTypes = { MyTreeTableModel.class, String.class };

    public MyDataModel(MyDataNode rootNode)
    {
        super(rootNode);
        this.root = rootNode;
    }

    public Object getChild(Object parent, int index)
    {
        return ((MyDataNode)parent).getChildren().get(index);
    }

    public int getChildCount(Object parent)
    {
        return ((MyDataNode)parent).getChildren().size();
    }

    public int getColumnCount()
    {
        return columnNames.length;
    }

    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int column)
    {
        return columnTypes[column];
    }

    public Object getValueAt(Object node, int column)
    {
        switch (column)
        {
            case 0:
                return ((MyDataNode)node).getName();
            case 1:
                return ((MyDataNode)node).getRefinedSize() + " " + ((MyDataNode)node).getUnit();
        }
        return null;
    }

    public boolean isCellEditable(Object node, int column)
    {
        return true;
    }

    public void setValueAt(Object aValue, Object node, int column) {}
}
