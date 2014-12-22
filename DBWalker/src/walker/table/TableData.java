package walker.table;

import java.sql.SQLException;
import java.util.List;

import walker.engine.model.Table;

public interface TableData {

    public List<List<String>> getTableData(Table table) throws SQLException;
}
