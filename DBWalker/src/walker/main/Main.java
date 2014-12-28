package walker.main;

import java.io.IOException;

import org.xml.sax.SAXException;

import walker.table.TableDataDB;

public class Main 
{
	public static void main(String[] args) throws SAXException, IOException 
	{
		App app = new App(new TableDataDB());
		app.getAppState().setCurrentTable(app.getAppState().getSchemaModel().getTables().values().iterator().next()); // izbaciti kad se bude moglo selektovati iz stabla
	}
}
