package walker.tree.model.workspace;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Milica Vulic
 
 *klasa predstavlja paket ili potpaket u stablu
 */

public class Package implements TreeNode{
	
	//private ArrayList<LeafTable> tabelePaketa = new ArrayList<LeafTable>();
	private ArrayList<Package> paketiPaketa = new ArrayList<Package>();
	private Package parent;
	
	private String nazivPaketa;
	
	public Package(){}
	
	public Package(Package p){
		this.parent = p;
	}
	
	public void addPackageToPackage(Package p){
		paketiPaketa.add(p);
	}
	
	@Override
	public String toString() {
		return "[ " + nazivPaketa + " ]";
	}

	public String getNazivPaketa() {
		return nazivPaketa;
	}

	public void setNazivPaketa(String nazivPaketa) {
		this.nazivPaketa = nazivPaketa;
	}

	@Override
	public Enumeration children() {
		
		//return (Enumeration<Package>)paketiPaketa;
		return new ChildEnumeration();
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return paketiPaketa.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return paketiPaketa.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return paketiPaketa.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		if(this.parent != null)
			return (TreeNode)this.parent;
		else
			return null;
	}

	@Override
	public boolean isLeaf() {
		if(paketiPaketa.size() == 0)
			return true;
		else
			return false;
	}
	
	private final class ChildEnumeration implements Enumeration {
	     private Iterator iterator;
	 
         ChildEnumeration() {
	       this.iterator = Package.this.paketiPaketa.iterator();
	     }
	 
	 	      public boolean hasMoreElements() {
	       return this.iterator.hasNext();
	     }
			 	      public Object nextElement() {
	       return ((Package)this.iterator.next());
	     }
   }
}
