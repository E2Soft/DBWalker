package walker.gui.menu.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import walker.gui.form.MainForm;

public class RefreshAction  extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	protected MainForm mainForm;
	
	public RefreshAction(MainForm mainForm) {
		this.mainForm = mainForm;
		putValue(NAME,"Refresh");
		putValue(SHORT_DESCRIPTION, "Refresh table data");
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		mainForm.refreshData();
	}

}
