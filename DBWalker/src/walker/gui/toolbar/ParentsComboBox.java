package walker.gui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import walker.engine.model.Table;

public class ParentsComboBox extends JComboBox<String>
{
	private static final long serialVersionUID = 1L;
	
	private static final String CHOOSE_A_PARENT = "Choose a parent.";
	private static final String NO_PARENTS = "No parents.";
	
	// posebna lista actionListenera
	private List<ActionListener> actionListeners;
	
	public ParentsComboBox()
	{
		actionListeners = new ArrayList<>();
		
		// dodaj ActionListener koji prosledjuje parentCode kao action command ostalim listenerima
		super.addActionListener((ActionEvent e)->
		{
	        String parentCode = (String) getSelectedItem();
	        
	        // ako nista nije selektovano preskoci event
	        if(parentCode == null || CHOOSE_A_PARENT.equals(parentCode) || NO_PARENTS.equals(parentCode))
	        {
	        	return;
	        }
	        
	        // za svakog actionListenera iz sopstvene liste uradi actionPerformed sa action command kao parentCode
			actionListeners.forEach((ActionListener l)->l.actionPerformed(new ActionEvent(e.getSource(), e.getID(), parentCode)));
		});
	}
	
	@Override
	public void addActionListener(ActionListener l)
	{
		actionListeners.add(l);
	}
	
	public void update(Table table)
	{
		removeAllItems();
		
		if(table.getParrents().isEmpty())
		{
			addItem(NO_PARENTS);
		}
		else
		{
			addItem(CHOOSE_A_PARENT);
		}
		
		for(Table parent : table.getParrents())
		{
			addItem(parent.getCode());
		}
	}
}
