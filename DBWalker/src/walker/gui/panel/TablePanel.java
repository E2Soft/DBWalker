package walker.gui.panel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import walker.engine.model.Table;
import walker.table.GenericTableModel;
import walker.table.TableData;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private JTable table;
    private GenericTableModel genericTableModel;
    private TableData tableData;
    private List<List<String>> data;
    private List<String> columns;
    
    public TablePanel() {
        tableData = new TableData();
        data = new ArrayList<List<String>>();

        initComponents();
    }
    
    public void initComponents(){
        setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);
        
        Table t = new Table("A","A","o19");
        String name = t.getName();
        String id = t.getId();
        //columns = t.getColumns(id); prijavljuje error

        try {
            data = tableData.getTableData(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        genericTableModel = new GenericTableModel(columns, data);       
        table = new JTable(genericTableModel);
        scrollPane.setViewportView(table);
    }

}
