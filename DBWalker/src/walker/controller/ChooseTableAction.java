package walker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import walker.main.AppState;

public class ChooseTableAction implements ActionListener
{
	private AppState appState;

	public ChooseTableAction(AppState appState)
	{
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String tableCode = e.getActionCommand();
		
		appState.setCurrentTable(tableCode);
	}
}
