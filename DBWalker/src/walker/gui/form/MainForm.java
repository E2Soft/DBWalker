package walker.gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.controller.Controller;
import walker.engine.WalkEngine;
import walker.engine.model.Package;
import walker.gui.panel.ChildrenTablePanel;
import walker.gui.panel.TablePanel;
import walker.gui.workspace.WorkspaceTree;
import walker.main.AppState;
import walker.table.TableData;
import walker.tree.model.workspace.Project;
import walker.tree.model.workspace.WorkspaceModel;

public class MainForm extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	
	private WorkspaceModel workspaceModel;
    private WorkspaceTree workspaceTree;
	private ChildrenTablePanel childrenTablePanel;
    private TablePanel centralTablePanel;
    private JSplitPane horizontalSpliter;
    private JSplitPane verticalSpliter;
    private double horizontalDeviderLocation = 0.5;
    
	public MainForm(Controller controller, TableData tableData) {
		initMainFrame();
		
		initTreeWorkspace();
		
		initMainLayout(controller, tableData);
		
		positionAndShow();		
	}
	
	private void initTreeWorkspace(){
		
		workspaceModel = new WorkspaceModel();
		workspaceTree=new WorkspaceTree();
		
//		Project p = new Project("p1");
//		workspaceModel.addProject(p);
//		workspaceTree.addProject(p);
		
		
		workspaceTree.setModel(workspaceModel);
		ToolTipManager.sharedInstance().registerComponent(workspaceTree);
	}
	
	private void initMainFrame(){
		setTitle("JWalker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth()*3/4;
		int height = (int)screenSize.getHeight()*3/4;
		setSize(width, height);
	}
	
	private void initMainLayout(Controller controller, TableData tableData){
		
		childrenTablePanel = new ChildrenTablePanel(controller, tableData);
		centralTablePanel = new TablePanel(tableData);
		
		JScrollPane childrenScroll = new JScrollPane(childrenTablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		childrenScroll.setMinimumSize(new Dimension(250, 250));
		
		JScrollPane centralScroll = new JScrollPane(centralTablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		horizontalSpliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centralScroll, childrenScroll);
		horizontalSpliter.setDividerLocation(horizontalDeviderLocation);
		horizontalSpliter.setOneTouchExpandable(true);
		
		//tree
		
		JPanel treePanel = new JPanel();
		treePanel.setOpaque(true);
		treePanel.setBackground(Color.white);
		
		JScrollPane treeScroll = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		treePanel.setLayout(new BorderLayout(0, 0));
		
		treePanel.add(workspaceTree);//dodaj stablo na panel
		
		
		//test part
		Project project1 = new Project("project1");
		Package p = null;
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			selectedFile = fileChooser.getSelectedFile();
		}
			
			
		try {
			//p = WalkEngine.getPackage(controller.getLoadSchemaAction().getSelectedXmlFile().getAbsolutePath());
			p = WalkEngine.getPackage(selectedFile.getAbsolutePath());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		workspaceModel.addProject(project1);
		project1.addPackage(p);
		
		SwingUtilities.updateComponentTreeUI(workspaceTree);
		
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setMinimumSize(new Dimension(250, 250));
		
		leftPanel.add(treeScroll, BorderLayout.CENTER);
		
		//end tree
		
		verticalSpliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, horizontalSpliter);
		verticalSpliter.setDividerLocation(0.6);
		verticalSpliter.setOneTouchExpandable(true);
		
		getContentPane().add(verticalSpliter);
	}
	
	private void positionAndShow(){
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(o instanceof AppState)
		{
			AppState appState = (AppState)o;
			
			// ako je promenjena tabela updateuj podatke u tabelama
			if(AppState.CURRENT_TABLE_CHANGED.equals(arg))
			{
				// ako nema dece sakri donji panel
				if(appState.getCurrentTable().getChildren().isEmpty())
				{
					verticalSpliter.setRightComponent(centralTablePanel);
					
					// zapamti lokaciju devidera
					horizontalDeviderLocation = horizontalSpliter.getDividerLocation();
				}
				else
				{
					verticalSpliter.setRightComponent(horizontalSpliter);
					// vrati devider na prethodnu lokaciju
					horizontalSpliter.setDividerLocation(horizontalDeviderLocation);
				}
				
				centralTablePanel.updateData(appState.getCurrentTable());
				childrenTablePanel.update(appState.getCurrentTable());
			}
			else if(AppState.SCHEMA_MODEL_CHANGED.equals(arg))
			{
				// TODO SCHEMA_MODEL_CHANGED
				// ako je promenjena sema obrisi podatke iz tabela i updateuj stablo
				//parentPanel.updateData(null);
				//childrenPanel.updateData(null);
				//tree.updateData(appState.getSchemaModel());
			}
		}
	}
}
