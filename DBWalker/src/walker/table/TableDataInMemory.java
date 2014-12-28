package walker.table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import walker.data.model.RowData;
import walker.engine.model.Table;

public class TableDataInMemory implements TableData
{

	Map<String, List<List<String>>> dataStore;

	public TableDataInMemory()
	{
		dataStore = new HashMap<>();
	}

	@Override
	public List<List<String>> getTableData(Table table, RowData foreignKey) throws SQLException
	{
		return dataStore.get(table.getCode());
	}

	public void addGenericData(String tableCode, String dataPrefix, int rowNum, int columnNum)
	{
		List<List<String>> rows = new ArrayList<List<String>>();

		for (int i = 0; i < rowNum; i++)
		{
			List<String> row = new ArrayList<String>();
			for (int j = 0; j < columnNum; j++)
			{
				row.add(dataPrefix + "_" + i + "_" + j);
			}
			
			rows.add(row);
		}

		dataStore.put(tableCode, rows);
	}
}
