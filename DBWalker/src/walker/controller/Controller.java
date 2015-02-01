package walker.controller;

import walker.main.AppState;


public class Controller
{
	private ChooseTableAction chooseTableAction;
	private LoadSchemaAction loadSchemaAction;
	
	public Controller(AppState appState)
	{
		chooseTableAction = new ChooseTableAction(appState);
		loadSchemaAction = new LoadSchemaAction(appState);
	}
	
	public ChooseTableAction getChooseTableAction()
	{
		return chooseTableAction;
	}
	
	public LoadSchemaAction getLoadSchemaAction()
	{
		return loadSchemaAction;
	}
}
