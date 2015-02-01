package walker.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import walker.controller.LoadSchemaAction;

public class LoadAction extends AbstractAction {

	protected LoadSchemaAction appState;
	
	public LoadAction(LoadSchemaAction appState) {
		putValue(NAME,"New schema");
		putValue(SHORT_DESCRIPTION, "Load new schema nd present it's data");
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		this.appState = appState;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		appState.actionPerformed(e);
	}

}
