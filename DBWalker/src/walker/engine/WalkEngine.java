package walker.engine;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import walker.engine.model.Column;
import walker.engine.model.Package;
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
	 * Povezuje tabele a povezuje i one koje su precice
	 * @param node cvor koji se povezuje
	 * @param pack paket u kom se pretrazuje
	 */
	public static void connectTables(Node node, Package pack){
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

							if(deteTabela != null && roditeljTabela != null){
								deteTabela.getParrents().add(roditeljTabela);
								roditeljTabela.getChildren().add(deteTabela);
							}
						}
					}
				}
			}
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
								NodeList nodelist = elem.getChildNodes();
								for(int j=0;j<nodelist.getLength();j++){
									if(nodelist.item(j) instanceof Element){
										Element elm = (Element)nodelist.item(j);
										
										Column col = new Column(elm.getElementsByTagName("a:Name").item(0).getTextContent(), 
													    	    elm.getElementsByTagName("a:Code").item(0).getTextContent(),
													    	    elem.getAttribute("Id"));
										
										t.getCols().put(col.getId(), col);
									}
								}
							//povezujemo kljuceve
							//TODO:ovaj deo je dosta LUD, skontati da li moze bolje i brze!!!!!!!	
							}else if(elem.getTagName().equals("c:Keys")){
								NodeList nodelist = elem.getChildNodes();
								for(int j=0;j<nodelist.getLength();j++){
									if(nodelist.item(j) instanceof Element){
										Element elm = (Element)nodelist.item(j);
										NodeList keyslist = elm.getChildNodes();
										for(int z=0;z<keyslist.getLength();z++){
											if(keyslist.item(z) instanceof Element){
												Element keyelm = (Element)keyslist.item(z);
												if(keyelm.getTagName().equals("c:Key.Columns")){
													NodeList keyNodes = keyelm.getChildNodes();
													for(int y=0;y<keyNodes.getLength();y++){
														if(keyNodes.item(y) instanceof Element){
															String id = ((Element)keyNodes.item(y)).getAttribute("Id");
															for(String colId : t.getCols().keySet()){
																if(id.equals(colId)){
																	t.getKeys().add(t.getCols().get(id));
																}
															}
														}
													}
												}
											}
										}
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
