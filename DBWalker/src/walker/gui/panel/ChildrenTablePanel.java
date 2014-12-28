package walker.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import walker.controller.Controller;
import walker.engine.model.Table;
import walker.table.TableData;

public class ChildrenTablePanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";

	private JButton shiftLeftBtn;
	private JButton shiftRightBtn;
	private JPanel panel;
	private ChildTablePanel firstChildTablePanel;
	private ChildTablePanel secondChildTablePanel;
	private JSplitPane splitPane;
	private int deviderLocation;
	private List<Table> childrenTables;
	private int leftIndex = 0;
	private int rightIndex = 1;

	public ChildrenTablePanel(Controller controller, TableData tableData)
	{
		initComponents(controller, tableData);
	}

	private void initComponents(Controller controller, TableData tableData)
	{
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(5, 5));

		ScrollChildrenActionListener scrollActionListener = new ScrollChildrenActionListener();

		shiftLeftBtn = new JButton();
		shiftLeftBtn.setPreferredSize(new Dimension(25, 100));
		shiftLeftBtn.setIcon(new ImageIcon("icons/previous_16x16.png"));
		shiftLeftBtn.addActionListener(scrollActionListener);
		shiftLeftBtn.setActionCommand(LEFT);
		add(shiftLeftBtn, BorderLayout.WEST);

		shiftRightBtn = new JButton();
		shiftRightBtn.setPreferredSize(new Dimension(25, 100));
		shiftRightBtn.setIcon(new ImageIcon("icons/next_16x16.png"));
		shiftRightBtn.addActionListener(scrollActionListener);
		shiftRightBtn.setActionCommand(RIGHT);
		add(shiftRightBtn, BorderLayout.EAST);

		panel = new JPanel(new BorderLayout());
		firstChildTablePanel = new ChildTablePanel(controller, tableData);
		secondChildTablePanel = new ChildTablePanel(controller, tableData);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, firstChildTablePanel, secondChildTablePanel);
		splitPane.setResizeWeight(0.5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setPreferredSize(new Dimension(10, 10));
		splitPane.setBorder(null);

		panel.add(splitPane, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
	}

	public void update(Table table)
	{
		childrenTables = table.getChildren();

		int childrenNumber = childrenTables.size();

		if (childrenNumber != 0)
		{
			shiftLeftBtn.setVisible(childrenNumber > 2);
			shiftRightBtn.setVisible(childrenNumber > 2);
			
			splitPane.updateUI();
			splitPane.doLayout();

			showBothPanels(childrenNumber >= 2);
			
			leftIndex = 0;
			rightIndex = 1;
			updateChildrenData();
			
			revalidate();
			repaint();
		}
	}

	private void updateChildrenData()
	{
		int childrenNumber = childrenTables.size();

		if (childrenNumber == 1)
		{
			firstChildTablePanel.updateData(childrenTables.get(0));
		}
		else
		{
			firstChildTablePanel.updateData(childrenTables.get(leftIndex));
			secondChildTablePanel.updateData(childrenTables.get(rightIndex));
		}
	}
	
	private void showBothPanels(boolean show)
	{
		// zapamti lokaciju devidera ako je secondChildTablePanel bio vidljiv
		if(secondChildTablePanel.isVisible())
		{
			deviderLocation = splitPane.getDividerLocation();
		}
		
		secondChildTablePanel.setVisible(show);
		splitPane.setEnabled(show);
		splitPane.setOneTouchExpandable(show);
		
		if(show)
		{
			if(deviderLocation <= 0)
			{
				splitPane.setDividerLocation(0.5);
				deviderLocation = splitPane.getDividerLocation();
			}
			else
			{
				// vrati devider na prethodnu lokaciju
				splitPane.setDividerLocation(deviderLocation);
			}
		}
		else
		{
			splitPane.setDividerLocation(1.0);
		}
	}

	class ScrollChildrenActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (LEFT.equals(e.getActionCommand()))
			{
				leftIndex--;
				rightIndex--;
			} 
			else if (RIGHT.equals(e.getActionCommand()))
			{
				leftIndex++;
				rightIndex++;
			}
			else
			{
				throw new RuntimeException("Unknown direction in ScrollChildrenActionListener");
			}

			if (leftIndex < 0)
			{
				leftIndex = childrenTables.size() - 1;
			}

			if (rightIndex < 0)
			{
				rightIndex = childrenTables.size() - 1;
			}

			if (leftIndex > childrenTables.size() - 1)
			{
				leftIndex = 0;
			}

			if (rightIndex > childrenTables.size() - 1)
			{
				rightIndex = 0;
			}

			updateChildrenData();
		}
	}
}
