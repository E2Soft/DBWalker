package walker.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import walker.db.DBConnection;

public class TableData {
        
    public TableData(){
        super();
    }
    
   public List<List<String>> getTableData(String tableName) throws SQLException{
       
       List<List<String>> listOfLists = new ArrayList<List<String>>();     
       Statement stmt = null;
       String query = "SELECT * FROM "+tableName;      
       Connection conn = DBConnection.getConnection();
         
           try {
               stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(query);
               ResultSetMetaData metaData = rs.getMetaData();
               int columns = metaData.getColumnCount();             
                   while(rs.next()){
                       List<String> list = new ArrayList<String>();
                       for(int i = 1; i <= columns; i++){
                           list.add(rs.getString(i));
                       }
                       listOfLists.add(list);
                   }              
            } catch (SQLException e) {
                   e.printStackTrace();
            } finally {
                if (stmt != null) { stmt.close(); }
            }
       return listOfLists; 
   }
   
}
