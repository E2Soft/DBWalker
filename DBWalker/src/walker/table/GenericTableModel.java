package walker.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class GenericTableModel extends AbstractTableModel{
    
    private static final long serialVersionUID = 1L;
    
    protected List<String> columns;
    protected List<List<String>> data;
    
    public GenericTableModel()
    {
    	this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
    }
    
    public GenericTableModel(List<String> columns, List<List<String>> data) {
        this.columns = columns;
        this.data = data;
    } 
    
    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<String> rowData = (List<String>) (data.get(rowIndex));
        return rowData.get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }
}
