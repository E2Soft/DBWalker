package walker.engine.model;

public class ReferenceJoin {
	
	protected Column object1;
	protected Column object2;
	protected String id;
	
	public ReferenceJoin(Column object1, Column object2, String id) {
		super();
		this.object1 = object1;
		this.object2 = object2;
		this.id = id;
	}

	public Column getObject1() {
		return object1;
	}

	public void setObject1(Column object1) {
		this.object1 = object1;
	}

	public Column getObject2() {
		return object2;
	}

	public void setObject2(Column object2) {
		this.object2 = object2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
