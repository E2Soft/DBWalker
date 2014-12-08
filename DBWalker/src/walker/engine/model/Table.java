package walker.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table extends NodeElement {
	//name - key code value in db
		protected Map<String, Column> cols;
		protected List<Table> parrents;
		protected List<Table> children;
		protected List<Column> keys;
		
		public Table(String name, String code, String id) {
			super(name, code, id);
			parrents = new ArrayList<Table>();
			children = new ArrayList<Table>();
			keys = new ArrayList<Column>();
			
			cols = new HashMap<String, Column>();
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
		
		public String getBasicSQLSelect(){
			
			return "SELECT * FROM "+name;
		}

}
