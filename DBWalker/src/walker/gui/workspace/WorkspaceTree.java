package walker.gui.workspace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import walker.controller.Controller;
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
	
	ActionListener chooseTableAction;
	
	public WorkspaceTree(Controller controller) 
	{
		addTreeSelectionListener(new WorkspaceTreeController());
	    setCellRenderer(new WorkspaceTreeCellRendered());
	    addMouseListener(this);
	    setEditable(true);
	    chooseTableAction = controller.getChooseTableAction();
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
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getClickCount() == 2 && !e.isConsumed())
		{
			e.consume();
			TreePath selPath =  this.getSelectionPath();
			Object node = selPath.getLastPathComponent(); 
			
			if(node instanceof Table)
			{
				Table table = (Table)node;
				
			    chooseTableAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, table.getCode()));
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
