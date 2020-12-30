import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class FactSearcher {
	
	private URL wikiSearch, wikiPage;
	private DocumentBuilder db;
	private DocumentBuilderFactory dbf;
	private Document doc;
	
	FactSearcher(){
		dbf = DocumentBuilderFactory.newInstance();
	}
	
	public String wikiSearcher(String subject){
		String page_id = null;
		subject = subject.replaceAll("\\s+", "%20");
		try {
			wikiSearch = new URL("https","en.wikipedia.org","/w/api.php?action=query&list=search&srsearch="+subject+"&format=xml");
			/*in = new BufferedReader(new InputStreamReader(wikiSearch.openStream()));
			String inputLine;
			sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) 
                sb.append(inputLine);
            in.close();
            System.out.println(sb); //Pass This to XML parser and Return page_id*/
            page_id = findPage(wikiSearch.openStream());
		} catch (MalformedURLException e) {
			System.out.println("URL MALFUNCTION DURING WIKI SEARCH");
		} catch (IOException e) {
			System.out.println("IO EXCEPTION DURING WIKI SEARCH");
		}
		return page_id;
	}
	
	private String findPage(InputStream xml) { 
        String page_id = "";
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(xml);
            NodeList nodelist = doc.getElementsByTagName("p");
            Node currentItem = nodelist.item(0);
            page_id = currentItem.getAttributes().getNamedItem("pageid").getNodeValue();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       return  page_id;
        
	}
	
	public String pageSearcher(String page_id){
		String wiki_predicate = null;
		try {
			wikiPage = new URL("https","en.wikipedia.org","/w/api.php?action=query&format=xml&prop=revisions&pageids="+page_id+"&rvprop=content&rvslots=*");
			/*in = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
			String inputLine;
			sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) 
                sb.append(inputLine);
            in.close();
            System.out.println(sb); //Pass This to XML parser and Return predicate*/
			//Pass wikiPage.openStream()
		} catch (MalformedURLException e) {
			System.out.println("URL MALFUNCTION DURING INFORMATION FETCH");
		} catch (IOException e) {
			System.out.println("IO EXCEPTION DURING INFORMATION FETCH");
		}
		return wiki_predicate;
	}
}
