package walker.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import walker.controller.LoadSchemaAction;
import walker.gui.menu.action.ClearAction;
import walker.gui.menu.action.LoadAction;
import walker.gui.panel.TablePanel;

public class ActionMenu extends JMenu {

	protected JMenuItem actionItem;
	protected JMenuItem clearItem;
	
	public ActionMenu(LoadSchemaAction appState, TablePanel centralTablePanel) {
		super("Load");
		init(appState,centralTablePanel);
	}
	
	protected void init(LoadSchemaAction appState,TablePanel centralTablePanel) {
		actionItem = new JMenuItem(new LoadAction(appState));
		clearItem = new JMenuItem(new ClearAction(centralTablePanel));
		
		add(actionItem);
		add(clearItem);
	}

}
