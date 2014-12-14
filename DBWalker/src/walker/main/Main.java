package walker.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.engine.WalkEngine;
import walker.engine.model.Package;
import walker.gui.form.MainForm;

public class Main {

	public static void main(String[] args) throws SAXException, IOException {
		try {
			Package pckg = WalkEngine.getPackage("data/testXML.xml");
			//PrintConsole.print(pckg);	
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		MainForm mainForm = new MainForm();
		mainForm.setVisible(true);
	}

}
