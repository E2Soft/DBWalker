package walker.engine.model;

public class ReferenceJoin {
	
	protected Column object1;
	protected Column object2;
	
	public ReferenceJoin() {
		// TODO Auto-generated constructor stub
	}
	
	public ReferenceJoin(Column object1, Column object2) {
		this.object1 = object1;
		this.object2 = object2;
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
	
}
