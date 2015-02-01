package walker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.db.DBConnection;
import walker.engine.WalkEngine;
import walker.engine.model.Package;
import walker.main.AppState;

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
		try
		{
			loadDBLoginData();
			loadXml();
		} 
		catch (Exception e1)
		{}
	}

	private void loadXml() throws Exception
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Phisical Data Model (*.pdm)", "pdm"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Phisical Data Model (*.xml)", "xml"));
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selektovaniFajl = fileChooser.getSelectedFile();

			if(!selektovaniFajl.canRead())
			{
				JOptionPane.showMessageDialog(null, "File is not readable.", "File error", JOptionPane.ERROR_MESSAGE);
			}

			try
			{
				Package schemaModel = WalkEngine.getPackage(selektovaniFajl.getPath());

				appState.setSchemaModel(schemaModel);
				
				appState.setCurrentTable(schemaModel.getTables().values().iterator().next());
			}
			catch (ParserConfigurationException | SAXException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while parsing the file.\n"+ex.getMessage(), "Parsing error", JOptionPane.ERROR_MESSAGE);
				throw ex;
			}
			catch(IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while reading the file.\n"+ex.getMessage(), "File error", JOptionPane.ERROR_MESSAGE);
				throw ex;
			}
		}
	}

	private void loadDBLoginData() throws Exception
	{
		Properties props = new Properties();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Database login properties (*.properties)", "properties"));
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selektovaniFajl = fileChooser.getSelectedFile();
			
			if(!selektovaniFajl.canRead())
			{
				JOptionPane.showMessageDialog(null, "File is not readable.", "File error", JOptionPane.ERROR_MESSAGE);
			}
			
			try
			{
				props.load(new FileInputStream(selektovaniFajl));
				
				String driver = props.getProperty("driver"); //Ime parametara
				String url = props.getProperty("url");
				String username = props.getProperty("username");  
				String password = props.getProperty("password");
				
				DBConnection.open(driver, url, username, password);
			} 
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while opening the file.\n"+ex.getMessage(), "Properties file error", JOptionPane.ERROR_MESSAGE);
				throw ex;
			} catch (ClassNotFoundException ex)
			{
				JOptionPane.showMessageDialog(null, "Unsuported database driver.", "Properties file error", JOptionPane.ERROR_MESSAGE);
				throw ex;
			} catch (SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error occured while opening the database.\n"+ex.getMessage(), "Properties file error", JOptionPane.ERROR_MESSAGE);
				throw ex;
			}
		}
	}
}


