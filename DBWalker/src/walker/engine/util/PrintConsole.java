package walker.engine.util;

import javax.swing.JEditorPane;

import walker.engine.model.Column;
import walker.engine.model.Key;
import walker.engine.model.Package;
import walker.engine.model.Table;

public class PrintConsole {
	
	private static void voidTables(Table table){
		System.out.println("----VEZE----");
		
		System.out.println("Roditelji:");
		for(Table t : table.getParrents()){
			System.out.println("\t"+t.getName());
		}
		System.out.println("Deca:");
		for(Table t : table.getChildren()){
			System.out.println("\t"+t.getName());
		}
		
		System.out.println("------------");
	}
	
	private static void printColumns(Table table){
		if(table.getCols().values().size() > 0){
			System.out.println("----COLUMMN----"+table.getCols().values().size());
			
			for(Column name : table.getCols().values()){
				System.out.println(name.getName());
			}
			
			System.out.println("------------");
		}
	}
	
	//STARA VERZIJA
	/*private static void printKeys(Table table) {
		if(table.getKeys().size() > 0){
			System.out.println("----KEYS----"+table.getKeys().size());
			
			for(Column key : table.getKeys()){
				System.out.println(key.getName());
			}
			
			System.out.println("------------");
		}
	}*/
	
	//NOVA VERZIJA
	private static void printKeysFull(Table table){
		System.out.println("\n\n");
		if(table.getFullKeys().size() > 0){
			System.out.println("----KEYS-FULL----"+table.getFullKeys().size());
			
			for(Key key : table.getFullKeys().values()){
				System.out.println(key.getName()+" "+key.getCode());
				for(Column col : key.getKeyparts()){
					System.out.println(col.getName());
				}
			}
			
			System.out.println("------------\n\n");
		}
	}
	
	private static void printBasicSQL(Table table) {
		if(table.getCols().values().size() > 0){
			System.out.println("----BASIC-QUERY----");
			
			System.out.println(table.getBasicSQLSelectQuery());
			
			System.out.println("-------------------");
		}
	}
	
	public static void print(Package p){
		System.out.println("/-------------------------------------------/");
		System.out.println("PAKET:"+p.getName());
		
		for (Table t : p.getTables().values()) {
			System.out.println("TABLE:"+t.getName());
			
			printColumns(t);
			
			//printKeys(t);
			printKeysFull(t);
			
			voidTables(t);
			
			printBasicSQL(t);
		}
		
		for(Package pt : p.getSubpacks()){
			print(pt);
		}
		
		System.out.println("/-------------------------------------------/");
	}
	
}
