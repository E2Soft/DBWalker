package walker.engine.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table extends NodeElement {
	    //name - key code value in db
		protected Map<String, Column> cols;
        protected List<Table> parrents;
		protected List<Table> children;
		protected List<Column> keys;
		protected List<Table> tables;
		
		public Table(String name, String code, String id) {
			super(name, code, id);
			parrents = new ArrayList<Table>();
			children = new ArrayList<Table>();
			keys = new ArrayList<Column>();
			cols = new LinkedHashMap<String, Column>();
		}
		
		public List<Table> getChildren() {
			return children;
		}
		
		public List<Table> getParrents() {
			return parrents;
		}
		
		public Map<String, Column> getCols() {
			return cols;
		}
		
		public List<Column> getKeys() {
			return keys;
		}
		
        @Override
		public String toString() {
			return name;
		}
        
		public String getBasicSQLSelectStarQuery(){
			
			return "SELECT * FROM "+name;
		}
		
		public String getBasicSQLSelectQuery(){
			
			String select = "SELECT ";
			String table = " FROM " + name;
			String params = "";
			int i = 0;
			
			for (Column column : cols.values()) {
				if(i == 0){
					params+=column.getCode();
				}else if(i <= cols.values().size()-1){
					params+= ","+column.getCode();
				}
				i++;
			}
			
			String query = select + params + table;
			
			return query;
		}
 
        public List<Table> getTables() {
            return tables;
        }             
        
        public List<String> getColumns(){
           List<String> columns = new ArrayList<String>();             
           for(Column column : cols.values()){
               String name = column.getName();
               columns.add(name);
           }            
            return columns;                   
        }

        public String getBasicSQLSelect(){
			return "SELECT * FROM "+name;
		}
        
}
