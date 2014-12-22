package walker.gui.workspace;


import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;

import walker.controller.workspace.WorkspaceTreeController;
import walker.tree.model.workspace.Project;
import walker.tree.model.workspace.WorkspaceModel;
import walker.view.workspace.WorkspaceTreeCellRendered;
/**
 * 
 * @author Milica Vulic
 *
 */
@SuppressWarnings("serial")
public class WorkspaceTree extends JTree {

	public WorkspaceTree() {
		
		addTreeSelectionListener(new WorkspaceTreeController());
	    setCellRenderer(new WorkspaceTreeCellRendered());
	    setEditable(true);
	}

	
	/**
	 * Metoda za dodavanje novog projekta u workspace 
	 * @param project
	 */
	public void addProject(Project project){
		
		((WorkspaceModel)getModel()).addProject(project);
		SwingUtilities.updateComponentTreeUI(this);
	}


	
}
