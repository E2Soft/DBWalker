package walker.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Key extends NodeElement {

	protected List<Column> keyparts;
	protected boolean primaryKey;
	
	public Key(String name, String code, String id) {
		super(name, code, id);
		
		keyparts = new ArrayList<Column>();
		primaryKey = false;
	}
	
	public List<Column> getKeyparts() {
		return keyparts;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

}
