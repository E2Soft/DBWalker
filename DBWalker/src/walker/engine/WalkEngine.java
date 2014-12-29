package walker.engine;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import walker.engine.model.Column;
import walker.engine.model.Key;
import walker.engine.model.Package;
import walker.engine.model.Reference;
import walker.engine.model.ReferenceJoin;
import walker.engine.model.RootElement;
import walker.engine.model.Table;

public class WalkEngine {

	/**
	 * Ucitava XML fajl sa zadate pozicije
	 * @param path putanja do XML fajla
	 * @return vraca objektnu reprezentaciju XML fajla
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Document getDoc(String path) throws ParserConfigurationException, SAXException, IOException{
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;
	}
	
	/**
	 * Vraca konkretnu tabelu po imenu rekurzivno
	 * @param packg paket koji se ispituje
	 * @param tableName ime tabele koja se trazi
	 * @return vraca pronadjenu tabelu ili null
	 */
	private static Table test(Package packg, String tableName){
		for(Table t : packg.getTables().values()){
			if(t.getName().equals(tableName)){
				return t;
			}
		}
		
		for(Package p : packg.getSubpacks()){
			return test(p, tableName);
		}
		return null;
	}
	
	/**
	 * vraca samo ime iz liste precica - rekurzivno
	 * @param pckg paket koji se ispituje
	 * @param key kljuc po kom se trazi
	 * @return pronadjeno ime precice ili null ako nista nije pronadjeno
	 */
	private static String testSchs(Package pckg, String key){
		for(String sch : pckg.getShortcuts().keySet()){
			if(sch.equals(key)){
				return pckg.getShortcuts().get(sch);
			}
		}
		
		for(Package p : pckg.getSubpacks()){
			return testSchs(p, key);
		}
		return null;
	}
	
	/**
	 * pokusaj naci tabelu medju paketima ostalim - rekurzivno
	 * @param pckg paket koji se ispituje
	 * @param key kljuc po kom se trazi
	 * @return vraca pronadjenu tabelu ili null ako nista nije nadjeno
	 */
	private static Table findInterPackageTable(Package pckg, String key){
		for(String id : pckg.getTables().keySet()){
			if(id.equals(key)){
				return pckg.getTables().get(key);
			}
		}
		
		for(Package p : pckg.getSubpacks()){
			return findInterPackageTable(p, key);
		}
		
		return null;
	}
	
	/**
	 * Pronalazi kljuc u tabelama smestenim u razlicitim paketima
	 * @param pckg paket koji se pretrazuje
	 * @param key id kljuca koji se trazi
	 * @return kljuc ako postoji ili null ako nije nadjen
	 */
	private static Key findInterPackageTableKey(Package pckg, String key){
		for(Table table : pckg.getTables().values()){
			for(String id : table.getFullKeys().keySet()){
				if(id.equals(key)){
					return table.getFullKeys().get(key);
				}
			}
		}
		
		for(Package p : pckg.getSubpacks()){
			return findInterPackageTableKey(p, key);
		}
		
		return null;
	}
	
	/**
	 * Pronalazi  kolone u tabelama u razlicitima paketima
	 * @param pckg paket koji se pretrazuje
	 * @param key kljucna koji se trazi tj id colone koja se trazi
	 * @return Kolona ako postoji ili null ako nije nadjena
	 */
	private static Column findInterPackageTableColumn(Package pckg, String key){
		for(Table table : pckg.getTables().values()){
			for(String id : table.getCols().keySet()){
				if(id.equals(key)){
					return table.getCols().get(key);
				}
			}
		}
		
		for(Package p : pckg.getSubpacks()){
			return findInterPackageTableColumn(p, key);
		}
		
		return null;
	}

	/**
	 * Povezuje tabele a povezuje i one koje su precice
	 * @param node cvor koji se povezuje
	 * @param pack paket u kom se pretrazuje
	 */
	private static void connectTables(Node node, Package pack){
			if(node instanceof Element){
				Element element = (Element)node;
				//povezivanje tabela
				if(element.getTagName().equals("c:References")){
					NodeList list = element.getChildNodes();
					for(int i=0;i<list.getLength();i++){
						if(list.item(i) instanceof Element){
							Element elem = (Element)list.item(i);//o:Reference
							
							NodeList roditelji = elem.getElementsByTagName("c:ParentTable");
							Element elementRoditelj = (Element) ((Element)roditelji.item(0)).getChildNodes().item(1);//tag roditelja
							
							NodeList deca = elem.getElementsByTagName("c:ChildTable");
							Element elementDete = (Element) ((Element)deca.item(0)).getChildNodes().item(1);//tag dete

							Table roditeljTabela = null;
							Table deteTabela = null;
							
							//posto postoje dve vrste povezivanja skonati koja je
							if(elementRoditelj.getTagName().equals("o:Table")){
								String key = elementRoditelj.getAttribute("Ref");
								roditeljTabela = pack.getTables().get(key);
								
								//ako nisi uspeo naci u tom paketu probaj u povezanim paketima
								if(roditeljTabela == null){
									roditeljTabela = findInterPackageTable(pack,key);
								}
							}else{
								String key = elementRoditelj.getAttribute("Ref");
								String name = testSchs(pack, key);
								roditeljTabela = test(pack, name);
							}
							
							if(elementDete.getTagName().equals("o:Table")){
								String key = elementDete.getAttribute("Ref");
								deteTabela = pack.getTables().get(key);
								
								//ako nije nasao u tom paketu probaj u medjupaketima
								if(deteTabela == null){
									deteTabela = findInterPackageTable(pack, key);
								}
							}else{
								String key = elementDete.getAttribute("Ref");
								String name = testSchs(pack, key);
								deteTabela = test(pack, name);
							}

							//u novoj verziji ne treba
							/*if(deteTabela != null && roditeljTabela != null){
								deteTabela.getParrents().add(roditeljTabela);
								roditeljTabela.getChildren().add(deteTabela);
							}*/
							
							//trazim povezani kljuc
							NodeList parentKeys = elem.getElementsByTagName("c:ParentKey");
							Key key = null;
							
							//proveri da li u opste ima kljuceva tj da li je povezana
							if(parentKeys.getLength() > 0){
								Element parentKey = (Element) ((Element)parentKeys.item(0)).getChildNodes().item(1);//tag kljuca
								
								if(parentKey.getTagName().equals("o:Key")){
									String ref = parentKey.getAttribute("Ref");
									
									//probaj naci u tom paketu nekog od tih tabela
									key = getKeyGromTable(pack.getTables().values(), ref);
									
									if(key == null){
										//ako ne moze vidi u nekom od ostalih paketa
										key = findInterPackageTableKey(pack, ref);
									}
									
									//parent key pa bi trebalo da se dodaje u dete tabelu???
									if(key != null){
										deteTabela.getParentKeys().add(key);
									}
								}
							}
							
							//EKSPERIMENTALNI DEO
							if(deteTabela != null && roditeljTabela != null){
								//probati napraviti referencu sa svim potrebnim stvarima
								String name = elem.getElementsByTagName("a:Name").item(0).getTextContent(); 
								String code = elem.getElementsByTagName("a:Code").item(0).getTextContent();
								String id = elem.getAttribute("Id");
								
								Reference reference = new Reference(name, code, id);
								reference.setParentTable(roditeljTabela);
								reference.setChildTable(deteTabela);
								reference.setParentKey(key);
								
								//nadji kolone
								NodeList jonTag = elem.getElementsByTagName("c:Joins");
								if(jonTag != null && jonTag.getLength() > 0){
									NodeList referenceJoins = jonTag.item(0).getChildNodes();
									for(int j=0;j<referenceJoins.getLength();j++){
										if(referenceJoins.item(j) instanceof Element){
											Element referenceJoin = (Element)referenceJoins.item(j);//o:ReferenceJoin
											String refjoinid = referenceJoin.getAttribute("Id");
											
											NodeList ocolumn = referenceJoin.getElementsByTagName("o:Column");
											String object1Ref = ((Element)ocolumn.item(0)).getAttribute("Ref");
											String object2Ref = ((Element)ocolumn.item(1)).getAttribute("Ref");
											
											Column object1 = null;
											Column object2 = null;
											
											if(roditeljTabela.getCols().containsKey(object1Ref)){
												object1 = roditeljTabela.getCols().get(object1Ref);
											}else{
												object1 = findInterPackageTableColumn(pack, object1Ref);
											}
											
											if(deteTabela.getCols().containsKey(object2Ref)){
												object2 = deteTabela.getCols().get(object2Ref);
											}else{
												object2 = findInterPackageTableColumn(pack, object2Ref);
											}
											
											if(object1 != null && object2 != null){
												ReferenceJoin refJoinobj = new ReferenceJoin(object1, object2, refjoinid);
												reference.getJoins().add(refJoinobj);
											}
											
										}
									}
								}
								
								
								if(!containReference(roditeljTabela.getReferences(), id)){
									roditeljTabela.getReferences().add(reference);//dodaj referencu roditelju
								}
								if(!containReference(deteTabela.getReferences(), id)){
									deteTabela.getReferences().add(reference);//dodaj detetu tabeli
								}
							}
							//EKSPERIMENTALNI DEO
						}
					}
				}
			}
		}
	
	/**
	 * Proverava da li se referenca nalazi u listi referenci
	 * @param references lista refernci koja se pretrazuje
	 * @param id id reference koja se trazi
	 * @return true ako je nadjena ili false ako nije nadjena
	 */
	private static boolean containReference(List<Reference> references, String id) {
		
		for (Reference reference : references) {
			if(reference.getId().equals(id)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Pronalazi kljuc u prosledjenoj kolekciji
	 * @param tables kolekcija tabela u kojoj se trazi kljuc
	 * @param id id kljuca koji se trazi
	 * @return pronadjen kljuc ili null ako nije nadjen
	 */
	private static Key getKeyGromTable(Collection<Table> tables, String id) {
		for (Table table : tables) {
			if(table.getFullKeys().keySet().contains(id)){
				return table.getFullKeys().get(id);
			}
		}
		
		return null;
	}

	/**
	 * Parsira prosledjeni XML node i popunjava podatke o tabelama i podpaketima za prosledjeni paket
	 * Radi rekurzivno da bi mogao da pronadje i podpakete i tabele u pakete i tako u 
	 * proizvolju dubonu
	 * @param node cvor koji se gleda
	 * @param pack paket u koji se smesta
	 */
	private static void parse(Node node, Package pack){
		
		//rekurzivno trazenje tabela
		if(node instanceof Element){
			Element element = (Element)node;
			if(element.getTagName().equals("c:Tables")){
				NodeList list = element.getChildNodes();
				for(int i=0;i<list.getLength();i++){
					if(list.item(i) instanceof Element){
						parse(list.item(i), pack);
					}
				}
			}
			
			//uzimanje vrednosti za konkretnu tabelu
			if(element.getTagName().equals("o:Table")){
				if(element.getElementsByTagName("a:Name").getLength() != 0){
					Table t = new Table(element.getElementsByTagName("a:Name").item(0).getTextContent(), 
										element.getElementsByTagName("a:Code").item(0).getTextContent(), 
										element.getAttribute("Id"));
					
					NodeList list = element.getChildNodes();
					for(int i=0;i<list.getLength();i++){
						if(list.item(i) instanceof Element){
							Element elem = (Element)list.item(i);
							
							//povezujemo kolone
							if(elem.getTagName() == "c:Columns"){
								NodeList nodelist = elem.getChildNodes();//o:Column
								for(int j=0;j<nodelist.getLength();j++){
									if(nodelist.item(j) instanceof Element){
										Element elm = (Element)nodelist.item(j);
										
										Column col = new Column(elm.getElementsByTagName("a:Name").item(0).getTextContent(), 
													    	    elm.getElementsByTagName("a:Code").item(0).getTextContent(),
													    	    elm.getAttribute("Id"));//BIO BUG umesto elm stajao elem koji nije imao ID pa nije prikazivao ispravno kolone

										t.getCols().put(col.getId(), col);
									}
								}
							//povezujemo kljuceve	
							}else if(elem.getTagName().equals("c:Keys")){
								NodeList nodelist = elem.getChildNodes();//o:Key tag
								
								for(int j=0;j<nodelist.getLength();j++){
									if(nodelist.item(j) instanceof Element){
										Element key = (Element)nodelist.item(j);//ovde sada imam svaki o:key posebno
										String name = key.getElementsByTagName("a:Name").item(0).getTextContent();
										String code = key.getElementsByTagName("a:Name").item(0).getTextContent();
										String id =  key.getAttribute("Id");
										Key okey = new Key(name, code, id);
										
										//dobijem kolone
										NodeList collumns = key.getElementsByTagName("c:Key.Columns").item(0).getChildNodes();
										for(int z=0;z<collumns.getLength();z++){
											if(collumns.item(z) instanceof Element){
												Element collumn = (Element)collumns.item(z);
												String ref = collumn.getAttribute("Ref");
												Column col = t.getCols().get(ref);
												
												//popunim kolonama kljuc
												okey.getKeyparts().add(col);
											}
										}
										//za novu verziju
										t.getFullKeys().put(id, okey);
									}
								}
								//nadjimo koji je primarni od svih kljuceva
							}else if(elem.getTagName().equals("c:PrimaryKey")){
								NodeList primaryKesy =  elem.getChildNodes();
								for(int j=0;j<primaryKesy.getLength();j++){
									if(primaryKesy.item(j) instanceof Element){
										Element primkey = (Element)primaryKesy.item(j);
										String ref = primkey.getAttribute("Ref");//nasao samo ref na primarni kljuc
										t.getFullKeys().get(ref).setPrimaryKey(true);
									}
								}
							}
						}
					}
					//dodajemo novu formiranu tabelu
					pack.getTables().put(t.getId(), t);
				}
			}else if(element.getTagName().equals("o:Shortcut")){
				//probaj da nadjes u paketu tabelu koja je precica po imenu
				if(element.getElementsByTagName("a:Name").getLength() != 0){
					pack.getShortcuts().put(element.getAttribute("Id"), element.getElementsByTagName("a:Name").item(0).getTextContent());
				}
			}
			

			if(element.getTagName().equals("o:Package")){
				if(element.getElementsByTagName("a:Name").getLength() != 0){
					Package pckg = new Package(element.getElementsByTagName("a:Name").item(0).getTextContent(), 
											   element.getElementsByTagName("a:Code").item(0).getTextContent(),
											   element.getAttribute("Id"));
					
					pack.getSubpacks().add(pckg);
					
					NodeList list = element.getChildNodes();
					for(int i=0;i<list.getLength();i++){
						if(list.item(i) instanceof Element){
							parse(list.item(i), pckg);
						}
					}	
				}
			}
			
			if(element.getTagName().equals("c:Packages")){
				NodeList list = element.getChildNodes();
				for(int i=0;i<list.getLength();i++){
					if(list.item(i) instanceof Element){
						parse(list.item(i), pack);
					}
				}
			}
		}	
	}
	
	/**
	 * Vraca naziv pocetnog dokumenta
	 * @param doc Dokument cvor koji se ispituje
	 * @return vraca element sa osnovnim informacijama ime, code, id
	 */
	private static RootElement getStartName(Document doc){ 
		NodeList startNodes = doc.getElementsByTagName("o:RootObject");
		Element elem = (Element)startNodes.item(0);
		
		String name = ((Element)elem.getElementsByTagName("a:Name").item(0)).getTextContent();
		String code = ((Element)elem.getElementsByTagName("a:Code").item(0)).getTextContent();
		String id = elem.getAttribute("Id");
		
		RootElement root = new RootElement(name, code, id);
		
		return root;
	}
	
	/**
	 * Vraca povezan paket sa podpaketima i tabelama
	 * @param path putanja sa koje se trazi XML fajl
	 * @return vraca paket koji je povezan i popunjen
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static Package getPackage(String path) throws ParserConfigurationException, SAXException, IOException{
		
		Document doc = getDoc(path);
		
		RootElement root = getStartName(doc);
		Package pack = new Package(root.getName(), root.getCode(), root.getId());
		
		NodeList list = doc.getElementsByTagName("o:Model");
		
		for(int i=0;i<list.getLength();i++){
			
			if(list.item(i) instanceof Element){
				Element element = (Element)list.item(i);
				
				for(int j=0;j<element.getChildNodes().getLength();j++)
				{
					if(element.getChildNodes().item(j) instanceof Element){
						parse(element.getChildNodes().item(j), pack);
					}
				}
			}
		}

		NodeList refs = ((Element)list.item(0)).getElementsByTagName("c:References");
		
		for(int i=0;i<refs.getLength();i++){
			if(refs.item(i) instanceof Element){
				connectTables(refs.item(i), pack);
			}
		}
		
		return pack;
	}
	
}
