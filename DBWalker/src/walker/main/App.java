package walker.main;

import walker.controller.Controller;
import walker.gui.form.MainForm;
import walker.table.TableData;

public class App
{
	private MainForm mainForm;
	private AppState appState;
	
	public App(TableData tableData)
	{
		appState = new AppState();

        Controller controller = new Controller(appState);
		mainForm = new MainForm(controller, tableData);
		
		// view prati trenutno stanje aplikacije
		appState.addObserver(mainForm);
		
		// ucitaj inicijalnu semu baze
		controller.getLoadSchemaAction().actionPerformed(null);
	}
	
	public AppState getAppState()
	{
		return appState;
	}
	
	public void run()
	{
		mainForm.setVisible(true);
	}
}
