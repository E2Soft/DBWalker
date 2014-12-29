package walker.engine.model;

public class ReferenceJoin {
	
	protected Column parentColumn;
	protected Column childColumn;
	protected String id;
	
	public ReferenceJoin(Column parentColumn, Column childColumn, String id) {
		super();
		this.parentColumn = parentColumn;
		this.childColumn = childColumn;
		this.id = id;
	}

	public Column getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(Column parentColumn) {
		this.parentColumn = parentColumn;
	}

	public Column getChildColumn() {
		return childColumn;
	}

	public void setChlidColumn(Column childColumn) {
		this.childColumn = childColumn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
