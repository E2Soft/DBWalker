package walker.tree.model.workspace;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Milica Vulic
 * 
 *klasa koja opisuje tabelu u stablu (kad se dodje do tabele to je list stabla) 
 */
public class LeafTable implements TreeNode{
	
	private String nazivTabele;

	@Override
	public Enumeration children() {// treba da ostane 'vako'
		return null;
	}

	@Override
	public boolean getAllowsChildren() {// treba da ostane 'vako'
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) { // treba da ostane 'vako'
			return -1;
	}

	@Override
	public TreeNode getParent() {
				return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

}
