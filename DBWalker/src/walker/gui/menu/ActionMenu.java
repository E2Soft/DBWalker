package walker.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import walker.controller.LoadSchemaAction;

public class ActionMenu extends JMenu {

	protected JMenuItem actionItem;
	protected JMenuItem clearItem;
	
	public ActionMenu(LoadSchemaAction appState) {
		super("Load");
		init(appState);
	}
	
	protected void init(LoadSchemaAction appState) {
		actionItem = new JMenuItem(new LoadAction(appState));
		clearItem = new JMenuItem(new ClearAction());
		
		add(actionItem);
		add(clearItem);
	}

}
