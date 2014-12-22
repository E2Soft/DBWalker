package walker.table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import walker.engine.model.Table;

public class TableDataInMemory implements TableData{ 
    
    public TableDataInMemory() {
        super();
    }

    @Override
    public List<List<String>> getTableData(Table table) throws SQLException {

        List<List<String>> listOfLists = new ArrayList<List<String>>();     
        
        List<String> list1 = new ArrayList<String>();
        list1.add("Petar");
        list1.add("Petrovic");
        
        List<String> list2 = new ArrayList<String>();
        list2.add("Marko");
        list2.add("Markovic");

        listOfLists.add(list1);
        listOfLists.add(list2);
        return listOfLists;
    }

}
