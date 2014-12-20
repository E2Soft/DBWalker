package walker.controller;

import walker.main.AppState;


public class Controller
{
	private ChooseTableAction chooseTableAction;
	private ScrollChildrenAction scrollChildrenAction;
	private LoadSchemaAction loadSchemaAction;
	
	public Controller(AppState appState)
	{
		chooseTableAction = new ChooseTableAction(appState);
		scrollChildrenAction = new ScrollChildrenAction();
		loadSchemaAction = new LoadSchemaAction(appState);
	}
	
	public ChooseTableAction getChooseTableAction()
	{
		return chooseTableAction;
	}
	
	public ScrollChildrenAction getScrollChildrenAction()
	{
		return scrollChildrenAction;
	}
	
	public LoadSchemaAction getLoadSchemaAction()
	{
		return loadSchemaAction;
	}
}
