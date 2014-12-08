package walker.engine.model;

public abstract class NodeElement {
	protected String name;
	protected String code;
	protected String id;
	
	public NodeElement(String name, String code, String id) {
		super();
		this.name = name;
		this.code = code;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
