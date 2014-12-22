package walker.tree.model.workspace;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/** 
 * @author Milica Vulic
 */
public class Workspace  implements TreeNode{

	//kolekcija projekata
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	
	public Workspace() {
		super();
	}

	
	
	public String toString() {
		return "Workspace";
	}

	public TreeNode getChildAt(int arg0) {
		return getProject(arg0);
		
	}

	public int getChildCount() {
		return getProjectsCount();
	}

	public TreeNode getParent() {
		
		return null;
	}

	public int getIndex(TreeNode arg0) {
		return getProjectIndex((Project) arg0);
	}

	public boolean getAllowsChildren() {
		
		return true;
	}

	public boolean isLeaf() {
		
		return false;
	}

	

	public void addProject(Project project){
		projects.add(project);
		project.setName("Project " + projects.size());
	}
	
	public Project getProject(int index) {
		return projects.get(index);
	}	
	
	public int getProjectIndex(Project project) {
		return projects.indexOf(project);
	}
	public int getProjectsCount() {
		return projects.size();
	}

	@Override
	public Enumeration children() {
		// TODO Auto-generated method stub
		return (Enumeration<Project>) projects;
	}
}
