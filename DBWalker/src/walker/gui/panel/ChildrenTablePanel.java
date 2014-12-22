package walker.gui.panel;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import walker.controller.Controller;
import walker.controller.ScrollChildrenAction;
import walker.engine.model.Table;

import java.awt.Dimension;
import javax.swing.JSplitPane;

public class ChildrenTablePanel extends JPanel{

    private static final long serialVersionUID = 1L;
    
    private JButton shiftLeftBtn;
    private JButton shiftRightBtn;
    private JPanel panel;
    private ChildTablePanel firstChildTablePanel;
    private ChildTablePanel secondChildTablePanel;
    private JSplitPane splitPane;

    public ChildrenTablePanel(Controller controller) {
        super();
        initComponents(controller);
    }
    
    private void initComponents(Controller controller) {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(5, 5));
        
        shiftLeftBtn = new JButton();
        shiftLeftBtn.setPreferredSize(new Dimension(25,100));
        shiftLeftBtn.setIcon(new ImageIcon("icons/previous_16x16.png"));   
        shiftLeftBtn.addActionListener(new ScrollChildrenAction());
        shiftLeftBtn.setActionCommand(ScrollChildrenAction.LEFT);
        add(shiftLeftBtn, BorderLayout.WEST);
        
        shiftRightBtn = new JButton();
        shiftRightBtn.setPreferredSize(new Dimension(25,100));
        shiftRightBtn.setIcon(new ImageIcon("icons/next_16x16.png"));
        shiftRightBtn.addActionListener(new ScrollChildrenAction());
        shiftRightBtn.setActionCommand(ScrollChildrenAction.RIGHT);
        add(shiftRightBtn, BorderLayout.EAST);
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        firstChildTablePanel = new ChildTablePanel(controller);
        secondChildTablePanel = new ChildTablePanel(controller);

        splitPane = new JSplitPane();
        splitPane.setPreferredSize(new Dimension(10, 10));
        splitPane.setBorder(null);
        splitPane.setDividerLocation(340);
        splitPane.setLeftComponent(firstChildTablePanel);
        splitPane.setRightComponent(secondChildTablePanel);
        
        panel.add(splitPane);
        add(panel, BorderLayout.CENTER);
    }
    
    public void update(Table table) {
        
        int childrenNumber = table.getChildren().size();
        
        if(childrenNumber != 0){
            if(childrenNumber == 1) {
               secondChildTablePanel.setVisible(false);           
            }      
            
            if(childrenNumber < 3) {
                shiftLeftBtn.setVisible(false);
                shiftRightBtn.setVisible(false);
            }     
            
            if(childrenNumber == 1) {
                firstChildTablePanel.updateData(table.getChildren().get(0));
            } else {
                firstChildTablePanel.updateData(table.getChildren().get(0));
                secondChildTablePanel.updateData(table.getChildren().get(1));
            }
            
        } else{
            firstChildTablePanel.setVisible(false);
            secondChildTablePanel.setVisible(false);
            shiftLeftBtn.setVisible(false);
            shiftRightBtn.setVisible(false);
        }
    }      
    
}
