package walker.table;

import java.sql.SQLException;
import java.util.List;

import walker.data.model.RowData;
import walker.engine.model.Table;


public interface TableData {

	List<List<String>> getTableData(Table table, RowData foreignKey) throws SQLException;
}
