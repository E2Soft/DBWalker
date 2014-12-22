package walker.main;

import java.io.IOException;

import org.xml.sax.SAXException;

import walker.controller.Controller;
import walker.engine.model.Column;
import walker.engine.model.Table;
import walker.gui.form.MainForm;

public class Main {
	
	public static void main(String[] args) throws SAXException, IOException 
	{
		AppState appState = new AppState();

        Controller controller = new Controller(appState);
		MainForm mainForm = new MainForm(controller);
		
		// view prati trenutno stanje aplikacije
		appState.addObserver(mainForm);
		
		// TODO dodati akcije kontrolera u mainForm
		
		// ucitaj inicijalnu semu baze
		controller.getLoadSchemaAction().actionPerformed(null);
		
		//PrintConsole.print(appState.getSchemaModel());	
		
		mainForm.setVisible(true);
		
		Table parent = new Table("parent", "parent", "p");
		Table child1 = new Table("child1", "child1", "c1");
		Table child2 = new Table("child2", "child2", "c2");
		Table child3 = new Table("child3", "child3", "c3");
		
		parent.getChildren().add(child1);
		parent.getChildren().add(child2);
		//parent.getChildren().add(child3);
		
		parent.getCols().put("col1", new Column("col1", "col1", "col1"));
        parent.getCols().put("col2", new Column("col2", "col2", "col2"));
        
        child1.getCols().put("a_col1", new Column("a_col1", "a_col1", "a_col1"));
        child1.getCols().put("a_col2", new Column("a_col2", "a_col2", "a_col2"));
        
        child2.getCols().put("b_col1", new Column("b_col1", "b_col1", "b_col1"));
        child2.getCols().put("b_col2", new Column("b_col2", "b_col2", "b_col2"));
        
        child3.getCols().put("c_col1", new Column("c_col1", "c_col1", "c_col1"));
        child3.getCols().put("c_col2", new Column("c_col2", "c_col2", "c_col2"));    
        
        appState.setCurrentTable(parent);
	}

}
