package walker.gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ToolTipManager;

import walker.controller.Controller;
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
    private TablePanel tablePanel;
    
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
		
		JPanel tree = new JPanel();
		tree.setOpaque(true);
		tree.setBackground(Color.white);

		JPanel parrentPAnel = new JPanel();
		parrentPAnel.setOpaque(true);
		parrentPAnel.setBackground(Color.red);
		
		JPanel childsPanel = new JPanel();
		childsPanel.setOpaque(true);
		childsPanel.setBackground(Color.green);
		
		childrenTablePanel = new ChildrenTablePanel(controller, tableData);
		tablePanel = new TablePanel(tableData);
		JScrollPane HscrollChild = new JScrollPane(childrenTablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		HscrollChild.setMinimumSize(new Dimension(250, 250));
		JScrollPane HscrollParrent = new JScrollPane(tablePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JSplitPane Hspliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HscrollParrent, HscrollChild);
		Hspliter.setDividerLocation(260);
		Hspliter.setOneTouchExpandable(true);
		
		JScrollPane Vscroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tree.setLayout(new BorderLayout(0, 0));
		
		//ja
		//stablo = new JTree();
		
		tree.add(workspaceTree);//dodaj stablo na panel
		
		//test part
		Project project1 = new Project("project1");
		Project project2 = new Project("project2");
		
		walker.tree.model.workspace.Package paket1 = new walker.tree.model.workspace.Package(null);
		walker.tree.model.workspace.Package podPaket1 = new walker.tree.model.workspace.Package(paket1);
		
		paket1.setNazivPaketa("paket1");
		podPaket1.setNazivPaketa("pod paket paketa 1");
		
		//dodela paketa projektu
		project1.addPackage(paket1);
		
		//dodela paketa paketu
		paket1.addPackageToPackage(podPaket1);
		
		//dodela projekata workspace-u
		workspaceTree.addProject(project1);
		workspaceTree.addProject(project2);
		
		
		
		//end ja
		
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setMinimumSize(new Dimension(250, 250));
		
		GridLayout grid = new GridLayout(3, 4);
		grid.setVgap(20);
		grid.setHgap(20);
		JPanel jostick = new JPanel(grid);
		jostick.setOpaque(true);
		jostick.setBackground(Color.YELLOW);
		jostick.setPreferredSize(new Dimension(150, 150));
		
		JButton W = new JButton("W");
		JButton A = new JButton("A");
		JButton S = new JButton("S");
		JButton D = new JButton("D");
		
		JPanel mid = new JPanel();
		mid.setOpaque(false);
		JPanel mid2 = new JPanel();
		mid2.setOpaque(false);
		JPanel mid3 = new JPanel();
		mid3.setOpaque(false);
		JPanel mid4 = new JPanel();
		mid4.setOpaque(false);
		JPanel mid5 = new JPanel();
		mid5.setOpaque(false);
		JPanel mid6 = new JPanel();
		mid6.setOpaque(false);
		
		jostick.add(mid);
		jostick.add(W);
		jostick.add(mid2);
		jostick.add(A);
		jostick.add(mid3);
		jostick.add(S);
		jostick.add(mid4);
		jostick.add(D);
		jostick.add(mid5);
		
		leftPanel.add(Vscroll, BorderLayout.CENTER);
	//	leftPanel.add(jostick, BorderLayout.SOUTH);
		
		JSplitPane Vspliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, Hspliter);
		Vspliter.setDividerLocation(250);
		Vspliter.setOneTouchExpandable(true);
		
		getContentPane().add(Vspliter);
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
			
			if(AppState.CURRENT_TABLE_CHANGED.equals(arg))
			{			    
				// TODO CURRENT_TABLE_CHANGED
				// ako je promenjena tabela updateuj podatke u tabelama
				tablePanel.updateData(appState.getCurrentTable());
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
