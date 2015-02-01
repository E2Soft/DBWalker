package walker.gui.workspace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import walker.controller.workspace.WorkspaceTreeController;
import walker.engine.model.Table;
import walker.tree.model.workspace.Project;
import walker.tree.model.workspace.WorkspaceModel;
import walker.view.workspace.WorkspaceTreeCellRendered;



/**
 * 
 * @author Milica Vulic
 *
 */
@SuppressWarnings("serial")
public class WorkspaceTree extends JTree implements MouseListener{

	private JMenuItem itmStart;
	private Table selectedTable;
	private JPopupMenu contextMenu;
	
	public WorkspaceTree() {
		
		addTreeSelectionListener(new WorkspaceTreeController());
	    setCellRenderer(new WorkspaceTreeCellRendered());
	    addMouseListener(this);
	    setEditable(true);
		
	    contextMenu = new JPopupMenu();
		
		itmStart = new JMenuItem("Start");
		contextMenu.add(itmStart);
		
	    itmStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("klik na tabelu = " + selectedTable.getName());
				// nastavite ...
			}
		});
	}

	
	/**
	 * Metoda za dodavanje novog projekta u workspace 
	 * @param project
	 */
	public void addProject(Project project){
		
		((WorkspaceModel)getModel()).addProject(project);
		SwingUtilities.updateComponentTreeUI(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			TreePath selPath =  this.getSelectionPath();
			Object node = selPath.getLastPathComponent(); 
			if(node instanceof Table){
				Table table = (Table)node; 
				selectedTable = table;
				
				contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


	
}
