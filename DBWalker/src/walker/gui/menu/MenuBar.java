package walker.gui.menu;

import javax.swing.JMenuBar;

import walker.controller.LoadSchemaAction;
import walker.gui.form.MainForm;
import walker.gui.panel.TablePanel;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	protected ActionMenu menu;
	
	public MenuBar(LoadSchemaAction appState,TablePanel centralTablePanel,MainForm mainForm) {
		super();
		menu = new ActionMenu(appState,centralTablePanel,mainForm);
		add(menu);
	}
}
