package walker.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.engine.WalkEngine;
import walker.engine.model.Package;
import walker.engine.util.PrintConsole;

public class Test {

	public static void main(String[] args) {
		try {
			
			Package pac = WalkEngine.getPackage("C:\\Users\\Milos\\Desktop\\testXML.xml");
			//PrintConsole.print(pac);
			//PrintConsole.printAgainst(pac);
			PrintConsole.name(pac);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
