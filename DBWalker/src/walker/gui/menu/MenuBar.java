package walker.gui.menu;

import javax.swing.JMenuBar;

import walker.controller.LoadSchemaAction;

public class MenuBar extends JMenuBar {
	
	protected ActionMenu menu;
	
	public MenuBar(LoadSchemaAction appState) {
		super();
		menu = new ActionMenu(appState);
		add(menu);
	}
}
