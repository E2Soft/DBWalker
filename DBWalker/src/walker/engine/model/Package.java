package walker.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Package extends NodeElement{

	protected Map<String, Table> tables;
	protected List<Package> subpacks;
	protected Map<String, String> shortcuts;
	
	public Package(String name, String code, String id) {
		super(name, code, id);
		
		tables = new HashMap<String, Table>();
		subpacks = new ArrayList<Package>();
		shortcuts = new HashMap<String, String>();
	}
	
	public Map<String, Table> getTables() {
		return tables;
	}
	
	public List<Package> getSubpacks() {
		return subpacks;
	}
	
	public Map<String, String> getShortcuts() {
		return shortcuts;
	}
	
	public void addSubPackage(Package p){
		subpacks.add(p);
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
}
