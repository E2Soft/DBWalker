package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.main.App;
import walker.table.TableDataInMemory;


public class MainInMemory
{
	public static TableDataInMemory tableData;
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException 
	{
		tableData = new TableDataInMemory();
		
		App app = new App(tableData);
		
		tableData.generateDataForPackage(app.getAppState().getSchemaModel(), 5);
		
		app.getAppState().setCurrentTable(app.getAppState().getSchemaModel().getTables().values().iterator().next());
		
		app.run();
	}
}
