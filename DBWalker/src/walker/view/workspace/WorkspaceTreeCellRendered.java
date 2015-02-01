package walker.view.workspace;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import walker.engine.model.Package;
import walker.engine.model.Table;
import walker.tree.model.workspace.Project;
import walker.tree.model.workspace.Workspace;

/**
 * 
 * @author Milica Vulic
 * ova klasa suzi samo za podesavanje izgleda cvorova u stablu u zavisnosti od tipa elementa
 *
 */
@SuppressWarnings("serial")
public class WorkspaceTreeCellRendered extends DefaultTreeCellRenderer{

	public WorkspaceTreeCellRendered() {
		
		// TODO Auto-generated constructor stub
	}

	  public Component getTreeCellRendererComponent(
              JTree tree,
              Object value,
              boolean sel,
              boolean expanded,
              boolean leaf,
              int row,
              boolean hasFocus) {
                  super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
                  
             
             if (value instanceof Package ) {
                 setIcon(new ImageIcon("icons/package_obj.gif"));
 
             } else if (value instanceof Project ) {
            	 setIcon(new ImageIcon("icons/repository.gif"));
                   
            } else if (value instanceof Table ) {
                setIcon(new ImageIcon("icons/table.gif"));
                  
           } else if (value instanceof Workspace ) {
               setIcon(new ImageIcon("icons/submodules.gif"));
               
          } 
             
            return this;
}

	  }  
