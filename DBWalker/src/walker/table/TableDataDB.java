package walker.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import walker.data.model.RowData;
import walker.db.DBConnection;
import walker.engine.model.Table;

public class TableDataDB implements TableData
{
    @Override
	public List<List<String>> getTableData(Table table, RowData foreignKey) throws SQLException
	{
		List<List<String>> listOfLists = new ArrayList<List<String>>();
		Statement stmt = null;
		
		Connection conn = DBConnection.getConnection();

		try
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getQuerry(table, foreignKey));
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
	
	private String getQuerry(Table table, RowData foreignKey)
	{
		StringBuilder query = new StringBuilder("SELECT * FROM ").append(table.getCode());
		
		// ako ima filtera
		if(foreignKey != null && !foreignKey.isEmpty())
		{
			// dodaj "WHERE kljuc1 = vrednost1 AND kljuc2 = vrednost2 .."
			query.append(" WHERE ");
			
			List<StringBuilder> clauses = new ArrayList<>();
			
			for(String keyName : foreignKey.keySet())
			{
				Object keyValue = foreignKey.get(keyName);
				StringBuilder clause = new StringBuilder().append(keyName).append(" = ").append(keyValue);
				clauses.add(clause);
			}
			
			query.append(StringUtils.join(clauses, " AND "));
		}
		
		return query.toString();
	}
}
