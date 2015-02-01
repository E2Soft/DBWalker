package walker.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import walker.controller.Controller;
import walker.data.model.RowData;
import walker.engine.model.Reference;
import walker.engine.model.ReferenceJoin;
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
	Map<String, RowData> foreignKeys;
	private int leftIndex = 0;
	private int rightIndex = 1;

	public ChildrenTablePanel(Controller controller, TableData tableData)
	{
		foreignKeys = new HashMap<>();
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

	public void update(Table table, RowData selectedRowData)
	{
		if(table == null)
		{
			return;
		}
		
		childrenTables = table.getChildren();
		
		updateForeignKeys(table, selectedRowData);

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

	private void updateForeignKeys(Table parent, RowData selectedRowData)
	{
		if(selectedRowData == null)
		{
			foreignKeys.clear();
			return;
		}
		
		// za svu decu
		for(Table child : childrenTables)
		{
			// imena kolona i vrednosti stranog kljuca deteta
			RowData foreignKey = new RowData();
			
			// nadji referencu ka ovom roditelju
			for(Reference reference : child.getReferences())
			{
				if(reference.getParentTable().getCode().equals(parent.getCode()))
				{
					// za sve spojeve iz reference
					for(ReferenceJoin join : reference.getJoins())
					{
						// podaci sa imenom kolone deteta i vrednosti iz selektovanog reda u parentu iz odgovarajuce kolone
						foreignKey.put(join.getChildColumn().getCode(), selectedRowData.get(join.getParentColumn().getCode()));
					}
					
					break;
				}
			}
			
			foreignKeys.put(child.getCode(), foreignKey);
		}
	}

	private void updateChildrenData()
	{
		int childrenNumber = childrenTables.size();

		if (childrenNumber == 1)
		{
			Table table = childrenTables.get(0);
			firstChildTablePanel.updateData(table, foreignKeys.get(table.getCode()));
		}
		else
		{
			Table firstTable = childrenTables.get(leftIndex);
			Table secondTable = childrenTables.get(rightIndex);
			firstChildTablePanel.updateData(firstTable, foreignKeys.get(firstTable.getCode()));
			secondChildTablePanel.updateData(secondTable, foreignKeys.get(secondTable.getCode()));
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
