package walker.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import walker.controller.LoadSchemaAction;

public class ActionMenu extends JMenu {

	protected JMenuItem actionItem;
	
	public ActionMenu(LoadSchemaAction appState) {
		super("Load");
		init(appState);
	}
	
	protected void init(LoadSchemaAction appState) {
		actionItem = new JMenuItem(new LoadAction(appState));
		add(actionItem);
	}

}
