package walker.main;

import java.io.IOException;

import org.xml.sax.SAXException;

import walker.table.TableDataDB;

public class Main 
{
	public static void main(String[] args) throws SAXException, IOException 
	{
		new App(new TableDataDB());
	}
}
