package walker.tree.model.workspace;

import javax.swing.tree.*;

/**
 * 
 * @author Milica Vulic
 *
 */
@SuppressWarnings("serial")
public class WorkspaceModel extends DefaultTreeModel {

	public WorkspaceModel(TreeNode root) {
		super(root);
		// TODO Auto-generated constructor stub
	}

	public WorkspaceModel() {
		super(new Workspace());

	}

	public void addProject(Project project) {
		((Workspace) getRoot()).addProject(project);
	}

	public Object getChild(Object parent, int index) {

		if (parent instanceof LeafTable) {
			return null;
		} else if (parent instanceof Workspace) {
			return ((Workspace) parent).getProject(index);
		} else if (parent instanceof Project) {
			return ((Project) parent).getPackage(index);
		}
		return getRoot();

	}

	public int getChildCount(Object parent) {
		if (parent instanceof LeafTable) {
			return 0;
		} else if (parent instanceof Project) {
			return ((Project) parent).getChildCount();
		} else if (parent instanceof Workspace) {
			return ((Workspace) parent).getProjectsCount();
		}

		return 0;

	}
	
	
	public boolean isLeaf(Object node) {
		return (node instanceof LeafTable);
	}
	
	
	public int getIndexOfChild(Object parent, Object child) {
		if(parent instanceof LeafTable){
		
			return -1;
		
		}else if(parent instanceof Workspace){
			
				if(child instanceof Project)
					return ((Workspace)parent).getProjectIndex((Project) child);
		
		}else if(parent instanceof Project){
			//TODO resi kao za project ili ??
			//return ((Project)parent).getPackage((Package)child);
		}
		return -1;
	}
  

}
