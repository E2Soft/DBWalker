package walker.view.workspace;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import walker.tree.model.workspace.Project;

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
                 URL imageURL = getClass().getResource("images/package.gif");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
 
             } else if (value instanceof Project ) {
                 URL imageURL = getClass().getResource("images/project.gif");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
                   
            } 
             
            return this;
}

	  }  
