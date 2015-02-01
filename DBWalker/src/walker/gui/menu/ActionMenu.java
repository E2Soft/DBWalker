package walker.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import walker.controller.LoadSchemaAction;
import walker.gui.form.MainForm;
import walker.gui.menu.action.ClearAction;
import walker.gui.menu.action.LoadAction;
import walker.gui.menu.action.RefreshAction;
import walker.gui.panel.TablePanel;

public class ActionMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	protected JMenuItem actionItem;
	protected JMenuItem clearItem;
	protected JMenuItem refreshItem;
	
	public ActionMenu(LoadSchemaAction appState, TablePanel centralTablePanel, MainForm mainForm) {
		super("Load");
		init(appState,centralTablePanel,mainForm);
	}
	
	protected void init(LoadSchemaAction appState,TablePanel centralTablePanel, MainForm mainForm) {
		actionItem = new JMenuItem(new LoadAction(appState));
		clearItem = new JMenuItem(new ClearAction(centralTablePanel));
		refreshItem = new JMenuItem(new RefreshAction(mainForm));
		
		add(actionItem);
		add(clearItem);
		add(refreshItem);
	}

}
