package walker.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import walker.engine.model.Table;
import walker.table.GenericTableModel;
import walker.table.TableData;

public class TablePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private JTable panelTable;
    private GenericTableModel genericTableModel;
    private TableData tableData;
    private List<String> columns;
    private DefaultTableCellRenderer centerRenderer;

    public TablePanel(TableData tableData) {
        this.tableData = tableData;
        columns = new ArrayList<String>();
        initComponents();      
    }
    
    public void initComponents(){
        setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(0, 0));
        add(scrollPane);
        panelTable = new JTable();
        panelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        panelTable.setDefaultRenderer(Object.class, centerRenderer);
        scrollPane.setViewportView(panelTable);
    }
        
    public void updateData(Table table){
        
        columns = table.getColumns();
        List<List<String>> data = null;

        try {
            data = tableData.getTableData(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        genericTableModel = new GenericTableModel(columns, data);
        panelTable.setModel(genericTableModel);
        
    }
    
}
