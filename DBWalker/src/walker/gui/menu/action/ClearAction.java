package walker.gui.menu.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import walker.gui.panel.TablePanel;

public class ClearAction extends AbstractAction {

	protected TablePanel centralTablePanel;
	
	public ClearAction(TablePanel centralTablePanel) {
		this.centralTablePanel = centralTablePanel;
		putValue(NAME,"Clear filter");
		putValue(SHORT_DESCRIPTION, "Clear current filter");
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
