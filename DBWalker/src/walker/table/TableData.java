package walker.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import walker.db.DBConnection;
import walker.engine.model.Table;

public class TableData
{
	public List<List<String>> getTableData(String table) throws SQLException
	{
		// return getTableData(table, null);
		// TODO uncoment
		return null;
	}

	public List<List<String>> getTableData(Table table, Map<String, Object> filterKey) throws SQLException
	{
		List<List<String>> listOfLists = new ArrayList<List<String>>();
		Statement stmt = null;
		
		Connection conn = DBConnection.getConnection();

		try
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getQuerry(table, filterKey));
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();

			while (rs.next())
			{
				List<String> list = new ArrayList<String>();

				for (int i = 1; i <= columns; i++)
				{
					list.add(rs.getString(i));
				}

				listOfLists.add(list);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}

		return listOfLists;
	}
	
	private String getQuerry(Table table, Map<String, Object> filterKey)
	{
		StringBuilder query = new StringBuilder("SELECT * FROM ").append(table.getCode());
		
		if(!filterKey.isEmpty())
		{
			query.append(" WHERE ");
			
			int i=0;
			for(String keyName : filterKey.keySet())
			{
				Object keyValue = filterKey.get(keyName);
				
				query.append(keyName).append(" = ").append(keyValue);
				
				if(i < filterKey.keySet().size()-1)
				{
					query.append(" AND ");
				}
				
				i++;
			}
		}
		
		return query.toString();
	}
}
