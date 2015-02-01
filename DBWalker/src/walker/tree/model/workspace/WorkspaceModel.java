package walker.tree.model.workspace;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

import walker.engine.model.Package;
import walker.engine.model.Table;

/**
 * 
 * @author Milica Vulic
 *
 */
@SuppressWarnings("serial")
public class WorkspaceModel extends DefaultTreeModel {

//	public WorkspaceModel(TreeNode root) {
//		super(root);
//		// TODO Auto-generated constructor stub
//	}

	public WorkspaceModel() {
		super(new Workspace());

	}

	public void addProject(Project project) {
		((Workspace) getRoot()).addProject(project);
	}
	
	public void clearProjects()
	{
		((Workspace) getRoot()).clearProjects();
	}

	public Object getChild(Object parent, int index) {

		if (parent instanceof Table) {
			return null;
		}
		else if(parent instanceof Package){
			Package paketTemp = (Package) parent;
			List<Object> deca = new ArrayList<Object>();
			
			deca.addAll(paketTemp.getSubpacks());
			deca.addAll(paketTemp.getTables().values());
			
			int i = -1;
			for(Object o : deca){
				i++;
				if(i==index){
					if(o instanceof Package){
						return (Package)o;
					}else if(o instanceof Table){
						return (Table)o;
					}
				}
			}
		}
		else if (parent instanceof Project) {
			return ((Project) parent).getPackage(index);
		}
		else if (parent instanceof Workspace) {
			return ((Workspace) parent).getProject(index);
		} 
		return getRoot();

	}

	public int getChildCount(Object parent) {
		if (parent instanceof Table) {
			return 0;
		}
		else if (parent instanceof Package) {
			return ((Package) parent).getSubpacks().size() + ((Package)parent).getTables().size();
		}
		else if (parent instanceof Project) {
			return ((Project) parent).getChildCount();
		}
		else if (parent instanceof Workspace) {
			return ((Workspace) parent).getProjectsCount();
		}

		return 0;

	}
	
	
	public boolean isLeaf(Object node) {
		return (node instanceof Table);
	}
	
	
	public int getIndexOfChild(Object parent, Object child) {
		if(parent instanceof Table){
		
			return -1;
		
		}
		else if(parent instanceof Package){
			if(child instanceof Package){
				return ((Package)parent).getSubpacks().indexOf((Package)child);
			}
			if(child instanceof Table){
				Table temp = (Table)child;
				
				int retIndex = -1;
				for(Table t : ((Package)parent).getTables().values()){
					retIndex++;
					if(t.equals(temp)){
						return retIndex;
					}
				}
				return -1;
			}
		}
		else if(parent instanceof Project){
			if(child instanceof Package)
				return ((Project)parent).getPaketi().indexOf((Package)child);
		}
		else if(parent instanceof Workspace){
			
				if(child instanceof Project)
					return ((Workspace)parent).getProjectIndex((Project) child);
		
		}
		return -1;
	}
  

}
