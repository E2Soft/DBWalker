package walker.engine.util;

import walker.engine.model.Column;
import walker.engine.model.Table;
import walker.engine.model.Package;

public class PrintConsole {
	
	private static void voidTables(Table table){
		System.out.println("Roditelji:");
		for(Table t : table.getParrents()){
			System.out.println("\t"+t.getName());
		}
		System.out.println("Deca:");
		for(Table t : table.getChildren()){
			System.out.println("\t"+t.getName());
		}
	}
	
	public static void print(Package p){
		System.out.println(p.getName());
		
		for (Table t : p.getTables().values()) {
			System.out.println("RABLE:"+t.getName());
			
			if(t.getCols().values().size() > 0){
				System.out.println("----COLUMMN----"+t.getCols().values().size());
				for(Column name : t.getCols().values()){
					System.out.println(name.getName());
				}
				System.out.println("------------");
			}
			
			if(t.getKeys().size() > 0){
				System.out.println("----KEYS----"+t.getKeys().size());
				for(Column key : t.getKeys()){
					System.out.println(key.getName());
				}
				System.out.println("------------");
			}
			
			System.out.println("----VEZE----");
			voidTables(t);
			System.out.println("------------");
		}
		
		for(Package pt : p.getSubpacks()){
			print(pt);
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		}
	}
	
}
