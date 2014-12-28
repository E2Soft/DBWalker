package walker.controller.workspace;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * 
 * @author Milica Vulic
 *
 */
public class WorkspaceTreeController implements TreeSelectionListener{

	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		TreePath path = e.getPath();
		for(int i=0; i<path.getPathCount(); i++){
			if(path.getPathComponent(i) instanceof Package){
				
				Package pack = (Package)path.getPathComponent(i);
				
				//selektovan je dijagram u stablu, potreno je pronaci odgovarajuci
				//DiagramView i postaviti ga u fokus
				System.out.println("Selektovan paket:" + pack.toString());	
				
				System.out.println("getPath: "+e.getPath());
				System.out.println("getPath: "+e.getNewLeadSelectionPath());
				break;
			}
		}
	}

}
