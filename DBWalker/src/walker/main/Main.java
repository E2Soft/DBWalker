package walker.main;

import java.io.IOException;

import org.xml.sax.SAXException;

import walker.controller.Controller;
import walker.gui.form.MainForm;

public class Main {
	
	public static void main(String[] args) throws SAXException, IOException 
	{
		AppState appState = new AppState();
		
		MainForm mainForm = new MainForm();
		Controller controller = new Controller(appState);
		
		// view prati trenutno stanje aplikacije
		appState.addObserver(mainForm);
		
		// TODO dodati akcije kontrolera u mainForm
		
		// ucitaj inicijalnu semu baze
		controller.getLoadSchemaAction().actionPerformed(null);
		
		//PrintConsole.print(appState.getSchemaModel());	
		
		mainForm.setVisible(true);
	}

}
