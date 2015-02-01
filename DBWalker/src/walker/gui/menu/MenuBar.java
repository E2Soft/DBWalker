package walker.gui.menu;

import javax.swing.JMenuBar;

import walker.controller.LoadSchemaAction;
import walker.gui.panel.TablePanel;

public class MenuBar extends JMenuBar {
	
	protected ActionMenu menu;
	
	public MenuBar(LoadSchemaAction appState,TablePanel centralTablePanel) {
		super();
		menu = new ActionMenu(appState,centralTablePanel);
		add(menu);
	}
}
