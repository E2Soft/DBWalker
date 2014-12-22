package walker.tree.model.workspace;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Milica Vulic
 * Klasa predstavlja jedan projekat tj. jednu bazu koja se pretrazuje
 *
 */
public class Project implements TreeNode{
	
	private ArrayList<Package> paketi = new ArrayList<Package>();
	//private ArrayList<LeafTable> tabele = new ArrayList<LeafTable>();
	private String nazivProjekta;


	public Project(String name){
		nazivProjekta = name;
	}
	
	
	public void addPackage(Package p){
		paketi.add(p);
	}
	
	public Package getPackage(int index){
		return paketi.get(index);
	}
	
	public String toString(){
		return nazivProjekta;
	}
	
	@Override
	public Enumeration<Package> children() {
		return (Enumeration<Package>)paketi;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return paketi.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return paketi.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return paketi.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	public void setName(String name) {
		nazivProjekta = name;
	}

}
