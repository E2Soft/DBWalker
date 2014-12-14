package walker.engine.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import walker.engine.WalkEngine;

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
 
        public List<Table> getTables() {
            return tables;
        }             
        
        public List<String> getColumns(String tableId) {
            List<String> columns = null;
            try {
                Package p = WalkEngine.getPackage("data/testXML.xml");
                for(Table table : p.getTables().values()){
                    if(table.getCols().values().size() > 0){ 
                        Table t = p.getTables().get(tableId);
                        columns = new ArrayList<String>();                       
                        for(Column c : t.getCols().values()){
                            String name = c.getName();
                            columns.add(name);
                        }
                    }
                }          
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
            return columns;
        }

        public String getBasicSQLSelect(){
			return "SELECT * FROM "+name;
		}
        
}
