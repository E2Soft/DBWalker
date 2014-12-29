package walker.gui.toolbar;

import javax.swing.JToolBar;

import walker.controller.Controller;
import walker.engine.model.Table;

public class MainToolBar extends JToolBar
{
	private static final long serialVersionUID = 1L;
	
	private ParentsComboBox parentsCombobox;
	
	public MainToolBar(Controller controller)
	{
		setFloatable(false);
		parentsCombobox = new ParentsComboBox();
		parentsCombobox.addActionListener(controller.getChooseTableAction());
		add(parentsCombobox);
	}
	
	public void updateData(Table table)
	{
		parentsCombobox.update(table);
	}
}
