package walker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.engine.WalkEngine;
import walker.main.AppState;

import walker.engine.model.Package;



public class LoadSchemaAction implements ActionListener
{
	private AppState appState;

	public LoadSchemaAction(AppState appState)
	{
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Phisical Data Model (*.pdm)", "pdm"));
		
		int returnVal = fileChooser.showOpenDialog(null);
		System.out.println(returnVal);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			
			if(!file.canRead())
			{
				JOptionPane.showMessageDialog(null, "File is not readable.", "File error", JOptionPane.ERROR_MESSAGE);
			}
			
			try
			{
				Package schemaModel = WalkEngine.getPackage(file.getPath());
				
				appState.setSchemaModel(schemaModel);
			}
			catch (ParserConfigurationException | SAXException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while parsing the file.\n"+ex.getMessage(), "Parsing error", JOptionPane.ERROR_MESSAGE);
			}
			catch(IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while reading the file.\n"+ex.getMessage(), "File error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
