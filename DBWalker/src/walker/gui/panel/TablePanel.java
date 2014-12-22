package walker.gui.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import walker.engine.model.Table;
import walker.table.GenericTableModel;
import walker.table.TableDataDB;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class TablePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private JTable panelTable;
    private GenericTableModel genericTableModel;
    private TableDataDB tableData;
    private List<List<String>> data;
    private List<String> columns;
    private DefaultTableCellRenderer centerRenderer;

    public TablePanel() {
        tableData = new TableDataDB();
        data = new ArrayList<List<String>>();
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

        /*try {
            data = m.getTableData(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        
        List<String> list1 = new ArrayList<String>();
        list1.add("Podaci");
        list1.add("Podaci");
        
        List<String> list2 = new ArrayList<String>();
        list2.add("Podaci");
        list2.add("Podaci");

        data.add(list1);
        data.add(list2);
        genericTableModel = new GenericTableModel(columns, data);
        panelTable.setModel(genericTableModel);
        
    }
    
}
