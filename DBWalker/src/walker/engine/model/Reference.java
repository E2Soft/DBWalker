package walker.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Reference extends NodeElement {
	
	protected Table parentTable;
	protected Table childTable;
	protected Key parentKey;
	protected List<ReferenceJoin> joins;
	
	public Reference(String name, String code, String id) {
		super(name, code, id);
		joins = new ArrayList<ReferenceJoin>();
	}

	public Table getParentTable() {
		return parentTable;
	}

	public void setParentTable(Table parentTable) {
		this.parentTable = parentTable;
	}

	public Table getChildTable() {
		return childTable;
	}

	public void setChildTable(Table childTable) {
		this.childTable = childTable;
	}

	public Key getParentKey() {
		return parentKey;
	}

	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}

	public List<ReferenceJoin> getJoins() {
		return joins;
	}

}
