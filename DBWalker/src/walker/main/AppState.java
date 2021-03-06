package walker.main;


import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import walker.engine.model.Package;
import walker.engine.model.Table;

public class AppState extends Observable
{
	public static final String CURRENT_TABLE_CHANGED = "CURRENT_TABLE_CHANGED";
	public static final String SCHEMA_MODEL_CHANGED = "SCHEMA_MODEL_CHANGED";
	
	private Table currentTable;
	private Package schemaModel;
	private Map<String, Table> allTables;
	
	public AppState()
	{
		allTables = new HashMap<>();
	}
		
	public Table getCurrentTable()
	{
		return currentTable;
	}
	
	public void setCurrentTable(Table currentTable)
	{
		this.currentTable = currentTable;
		changeAndNotify(CURRENT_TABLE_CHANGED);
	}
	
	public void setCurrentTable(String code)
	{
		setCurrentTable(getTable(code));
	}
	
	public Package getSchemaModel()
	{
		return schemaModel;
	}
	
	public void setSchemaModel(Package newSchemaModel)
	{
		schemaModel = newSchemaModel;
		
		mapTables(schemaModel);
		
		currentTable = null;
		changeAndNotify(SCHEMA_MODEL_CHANGED);
	}
	
	private void mapTables(Package pack)
	{
		for(Package subPackage : pack.getSubpacks())
		{
			mapTables(subPackage);
		}
		
		for(Table table : pack.getTables().values())
		{
			allTables.put(table.getCode(), table);			
		}
	}

	public Table getTable(String code)
	{
		return allTables.get(code);
	}
	
	private void changeAndNotify(String changeType)
	{
		setChanged();
		notifyObservers(changeType);
	}
}
