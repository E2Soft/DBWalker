package walker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import walker.data.model.KeyData;
import walker.engine.model.Column;
import walker.main.AppState;

public class SelectRowAction implements ListSelectionListener
{
	private AppState appState;
	
	public SelectRowAction(AppState appState)
	{
		this.appState = appState;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent selectionEvent)
	{
		if(selectionEvent.getSource() instanceof JTable)
		{
			JTable table = (JTable) selectionEvent.getSource();
			
			List<KeyData> selectedKeyData = new ArrayList<>();
			
			//appState.getCurrentTable().getKeys();
			List<List<Column>> tableKeys = null;
			
			for(List<Column> tableKey : tableKeys)
			{
				KeyData keyValue = new KeyData();
				
				for(Column keyColumn : tableKey)
				{
					String columnName = keyColumn.getName();
					int columnIndex = table.getColumnModel().getColumnIndex(columnName);
					Object value = table.getValueAt(selectionEvent.getFirstIndex(), columnIndex);
					
					keyValue.put(columnName, value);
				}
				
				selectedKeyData.add(keyValue);
			}
			
			appState.setSelectedKeyData(selectedKeyData);
		}
	}
}

