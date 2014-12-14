package walker.table;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class GenericTableModel implements TableModel{
    
    protected List<String> columns;
    protected List<List<String>> data;
    
    public GenericTableModel(List<String> columns, List<List<String>> data) {
        super();
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
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }

}
