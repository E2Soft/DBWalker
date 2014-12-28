package walker.gui.panel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import walker.controller.Controller;
import walker.engine.model.Table;
import walker.table.TableData;

public class ChildTablePanel extends JPanel{

    private static final long serialVersionUID = 1L;
    
    private JButton upBtn;
    private TablePanel tablePanel;

    public ChildTablePanel(Controller controller, TableData tableData) {
        super();
        initComponents(controller, tableData);
    }

    private void initComponents(Controller controller, TableData tableData) {
        setLayout(new BorderLayout(5, 5));
        
        upBtn = new JButton();
        upBtn.setIcon(new ImageIcon("icons/up_16x16.png"));
      
        add(upBtn, BorderLayout.NORTH);
        
        tablePanel = new TablePanel(tableData);
        add(tablePanel, BorderLayout.CENTER);      
    
        upBtn.addActionListener(controller.getChooseTableAction());
    }

    public void updateData(Table table){
        upBtn.setActionCommand(table.getCode());
        tablePanel.updateData(table);      
    }
}
