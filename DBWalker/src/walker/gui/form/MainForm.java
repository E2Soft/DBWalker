package walker.gui.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import walker.controller.Controller;
import walker.gui.panel.ChildrenTablePanel;
import walker.gui.panel.TablePanel;
import walker.gui.toolbar.MainToolBar;
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
    private JPanel tablesPanel;
    private MainToolBar mainToolBar;
    private JScrollPane centralScroll;
    private int horizontalDeviderLocation;
    
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
		centralTablePanel.addListSelectionListener(new SelectRowActionListener());
		
		JScrollPane childrenScroll = new JScrollPane(childrenTablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		childrenScroll.setMinimumSize(new Dimension(0, 0));
		
		centralScroll = new JScrollPane(centralTablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		horizontalSpliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centralScroll, childrenScroll);
		horizontalSpliter.setResizeWeight(0.5);
		horizontalSpliter.setOneTouchExpandable(true);
		
		mainToolBar = new MainToolBar(controller);
		
		tablesPanel = new JPanel(new BorderLayout());
		tablesPanel.add(mainToolBar, BorderLayout.PAGE_START);
		tablesPanel.add(horizontalSpliter, BorderLayout.CENTER);
		
		//tree
		
		JScrollPane treeScroll = new JScrollPane(workspaceTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		//test part
		/*Project project1 = new Project("project1");
		Package p = null;
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Extensible Markup Language File (*.xml)", "xml"));
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
		*/
		
		JPanel treePanel = new JPanel(new BorderLayout());
		treePanel.setMinimumSize(new Dimension(250, 250));
		
		treePanel.add(treeScroll, BorderLayout.CENTER);
		
		//end tree
		
		verticalSpliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, tablesPanel);
		verticalSpliter.setDividerLocation(0.6);
		verticalSpliter.setOneTouchExpandable(true);
		
		getContentPane().add(verticalSpliter);
	}
	
	private void positionAndShow(){
		setLocationRelativeTo(null);
		showChildrenPanel(false);
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
				// ako ima dece prikazi donji panel, inace sakrij
				showChildrenPanel(!appState.getCurrentTable().getChildren().isEmpty());
				
				centralTablePanel.updateData(appState.getCurrentTable(), null);
				childrenTablePanel.update(appState.getCurrentTable(), null);
				mainToolBar.updateData(appState.getCurrentTable());
			}
			else if(AppState.SCHEMA_MODEL_CHANGED.equals(arg))
			{
				// ako je promenjena sema obrisi podatke iz tabela 
				showChildrenPanel(false);
				centralTablePanel.updateData(null, null);
				childrenTablePanel.update(null, null);
				mainToolBar.updateData(null);
				
				// i updateuj stablo
				Project project1 = new Project("project1");
				workspaceModel.clearProjects();
				workspaceModel.addProject(project1);
				project1.addPackage(appState.getSchemaModel());
				SwingUtilities.updateComponentTreeUI(workspaceTree);
			}
		}
	}
	
	private void showChildrenPanel(boolean show)
	{
		// zapamti lokaciju devidera ako je childrenTablePanel bio vidljiv
		if(childrenTablePanel.isVisible())
		{
			horizontalDeviderLocation = horizontalSpliter.getDividerLocation();
		}
		
		childrenTablePanel.setVisible(show);
		horizontalSpliter.setEnabled(show);
		horizontalSpliter.setOneTouchExpandable(show);
		
		if(show)
		{
			horizontalSpliter.setResizeWeight(0.5);
			
			if(horizontalDeviderLocation <= 0)
			{
				horizontalSpliter.setDividerLocation(0.5);
				horizontalDeviderLocation = horizontalSpliter.getDividerLocation();
			}
			else
			{
				// vrati devider na prethodnu lokaciju
				horizontalSpliter.setDividerLocation(horizontalDeviderLocation);
			}
		}
		else
		{
			horizontalSpliter.setResizeWeight(1.0);
			horizontalSpliter.setDividerLocation(1.0);
		}
	}
	
	class SelectRowActionListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent selectionEvent)
		{
			// ignorisi duplo pozivanje
			if(selectionEvent.getValueIsAdjusting())
			{
				return;
			}
			
			childrenTablePanel.update(centralTablePanel.getCurrentTable(), centralTablePanel.getSelectedRowData());
		}
	}
}
