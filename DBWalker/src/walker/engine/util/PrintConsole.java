package walker.engine.util;

import javax.swing.JEditorPane;

import walker.engine.model.Column;
import walker.engine.model.Key;
import walker.engine.model.Package;
import walker.engine.model.Reference;
import walker.engine.model.ReferenceJoin;
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

	//NOVA VERZIJA
	private static void printKeysFull(Table table){
		if(table.getFullKeys().size() > 0){
			System.out.println("----KEYS-FULL----"+table.getFullKeys().size());
			
			for(Key key : table.getFullKeys().values()){
				System.out.println(key.getName()+" "+key.getCode()+" PRIM:"+key.isPrimaryKey());
				for(Column col : key.getKeyparts()){
					System.out.println(col.getName());
				}
			}
			
			System.out.println("------------");
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
			
			printKeysFull(t);
			
			//voidTables(t);
			printTables(t);
			
			printBasicSQL(t);
		}
		
		for(Package pt : p.getSubpacks()){
			print(pt);
		}
		
		System.out.println("/-------------------------------------------/");
	}
	
	//NOVA VERZIJA
	private static void printTables(Table table){
		System.out.println("****VEZE****");
		
		System.out.println("Roditelji:");
		for(Table t : table.getParentTables()){
			System.out.println("\t"+t.getName());
		}
		System.out.println("Deca:");
		for(Table t : table.getChildTables()){
			System.out.println("\t"+t.getName());
		}
		
		System.out.println("************");
	}
	
	private static void printOld(Package p){
		System.out.println("/-------------------------------------------/");
		System.out.println("PAKET:"+p.getName());
		
		for (Table t : p.getTables().values()) {
			System.out.println("TABLE:"+t.getName());
			voidTables(t);
		}
		
		for(Package pt : p.getSubpacks()){
			printOld(pt);
		}
		
		System.out.println("/-------------------------------------------/");
	}
	
	private static void printNew(Package p){
		System.out.println("/*******************************************/");
		System.out.println("PAKET:"+p.getName());
		
		for (Table t : p.getTables().values()) {
			System.out.println("TABLE:"+t.getName());
			printTables(t);
		}
		
		for(Package pt : p.getSubpacks()){
			printNew(pt);
		}
		
		System.out.println("/*******************************************/");	
	}
	
	public static void printAgainst(Package p){
		printOld(p);
		
		printNew(p);
	}
	
	public static void name(Package p) {
		for (Table t : p.getTables().values()) {
			System.out.println("TABLE:"+t.getName());
			System.out.println("REFS:");
			for(Reference r : t.getReferences()){
				System.out.println("NAME:"+r.getName());
				System.out.println("PARENT:"+r.getParentTable().getName()+" CHILD:"+r.getChildTable().getName()+" ID:"+r.getId());
				
				System.out.println("JOINS:");
				for(ReferenceJoin join : r.getJoins()){
					System.out.println("ID:"+join.getId()+" OBJECT:"+join.getObject1().getId()+" OBJECT2:"+join.getObject2().getId());
				}
			}
			System.out.println();
		}
		System.out.println();
		
		for(Package pt : p.getSubpacks()){
			name(pt);
		}
	}
	
}
