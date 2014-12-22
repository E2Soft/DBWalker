package walker.main;

import java.io.IOException;

import org.xml.sax.SAXException;

import walker.controller.Controller;
import walker.engine.model.Column;
import walker.engine.model.Package;
import walker.engine.model.Table;
import walker.gui.form.MainForm;
import walker.table.TableDataInMemory;

public class Main {
	
	public static void main(String[] args) throws SAXException, IOException 
	{
		AppState appState = new AppState();

        Controller controller = new Controller(appState);
        TableDataInMemory tableData = new TableDataInMemory();
		MainForm mainForm = new MainForm(controller, tableData);
		
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
		parent.getChildren().add(child3);
		
		parent.getCols().put("parent_col1", new Column("parent_col1", "parent_col1", "parent_col1"));
        parent.getCols().put("parent_col2", new Column("parent_col2", "parent_col2", "parent_col2"));
        
        child1.getCols().put("child1_col1", new Column("child1_col1", "child1_col1", "child1_col1"));
        child1.getCols().put("child1_col2", new Column("child1_col2", "child1_col2", "child1_col2"));
        
        child2.getCols().put("child2_col1", new Column("child2_col1", "child2_col1", "child2_col1"));
        child2.getCols().put("child2_col2", new Column("child2_col2", "child2_col2", "child2_col2"));
        
        child3.getCols().put("child3_col1", new Column("child3_col1", "child3_col1", "child3_col1"));
        child3.getCols().put("child3_col2", new Column("child3_col2", "child3_col2", "child3_col2"));
        
        Package schemaPackage = new Package("root", "root",  "root");
        schemaPackage.getTables().put(parent.getCode(), parent);
        schemaPackage.getTables().put(child1.getCode(), child1);
        schemaPackage.getTables().put(child2.getCode(), child2);
        schemaPackage.getTables().put(child3.getCode(), child3);
        
        tableData.addGenericData("parent", "parent_data", 5, 2);
        tableData.addGenericData("child1", "child1_data", 5, 2);
        tableData.addGenericData("child2", "child2_data", 5, 2);
        tableData.addGenericData("child3", "child3_data", 5, 2);
        
        appState.setSchemaModel(schemaPackage);
        
        appState.setCurrentTable(parent);
	}

}
