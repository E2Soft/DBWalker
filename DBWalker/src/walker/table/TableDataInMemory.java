package walker.table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import walker.data.model.RowData;
import walker.engine.model.Column;
import walker.engine.model.Package;
import walker.engine.model.Table;

public class TableDataInMemory implements TableData
{
	Map<String, List<RowData>> dataStore;

	public TableDataInMemory()
	{
		dataStore = new HashMap<>();
	}

	@Override
	public List<List<String>> getTableData(Table table, RowData foreignKey) throws SQLException
	{
		List<RowData> data  = dataStore.get(table.getCode());
		
		// TODO implementirati referencijalni integritet pri generisanju podataka, pa odkomentarisati filter
		/*if(foreignKey != null && !foreignKey.isEmpty())
		{
			data.removeIf(row->
			{
				for(String keyName : foreignKey.keySet())
				{
					Object keyValue = foreignKey.get(keyName);
					
					if(!row.get(keyName).equals(keyValue))
					{
						System.out.println(row.get(keyName));
						System.out.println(keyValue);
						return true;
					}
				}
				
				return false;
			});
		}*/
		
		List<List<String>> ret = new ArrayList<>();
		
		data.stream().forEach((row)->
		{
			List<String> list = new ArrayList<>();
			row.values().forEach(o->list.add(o.toString()));
			ret.add(list);
		});
		
		return ret;
	}

	public void addGenericData(Table table, int rowNum)
	{
		List<RowData> rows = new ArrayList<>();

		for (int i = 0; i < rowNum; i++)
		{
			RowData row = new RowData();
			
			int j=0;
			for(Column column : table.getCols().values())
			{
				row.put(column.getCode(), table.getName() + "_" + i + "_" + j++);
			}
			
			rows.add(row);
		}

		dataStore.put(table.getCode(), rows);
	}
	
	public void generateDataForPackage(Package root, int rowNum)
	{
		for(Package subPack : root.getSubpacks())
		{
			generateDataForPackage(subPack, rowNum);
		}
		
		for(Table table : root.getTables().values())
		{
			addGenericData(table, rowNum);
		}
	}
}
