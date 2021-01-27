import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	private String subject, object, predicate, wiki_predicate;
	public int ioe, npe, mfue;
	
	public FactSearcher(){
		dbf = DocumentBuilderFactory.newInstance();
	}
	
	public String[] wikiSearcher(String[] triplet){
		subject = triplet[0].trim();
		object = triplet[1].trim();
		predicate = triplet[2].trim().split("\\s+")[0];
		wiki_predicate = " ";
		String page_id = null;
//		System.out.println("Subject: "+subject);
		String searchSubject = subject.replaceAll("\\s+", "%20");
		try {
			wikiSearch = new URL("https","en.wikipedia.org","/w/api.php?action=query&list=search&srsearch="+searchSubject+"&format=xml");
            page_id = findPage(wikiSearch.openStream());
            wiki_predicate = pageSearcher(page_id).trim().split("\\s+")[0];
		} catch (MalformedURLException e) {
//			System.out.println("URL MALFUNCTION DURING WIKI SEARCH OF "+subject);
			mfue += 1;
//			System.out.println(wiki_predicate);
//			e.printStackTrace();
		} catch (IOException e) {
//			System.out.println("IO EXCEPTION DURING WIKI SEARCH OF "+subject);
			ioe += 1;
			/*System.out.println(subject);
			System.out.println(object);
			System.out.println(predicate);
			System.out.println(wiki_predicate);*/
//			e.printStackTrace();
		} catch (NullPointerException e) {
//			System.out.println("NULL POINTER EXCEPTION DURING WIKI SEARCH OF "+subject);
			npe += 1;
//			System.out.println(wiki_predicate);
//			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			wiki_predicate = " ";
		}
		return new String[] {subject, object, predicate.split("\\s+")[0], wiki_predicate};
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
	
	private String pageSearcher(String page_id){
		String wikipedia_predicate = "";
		try {
			wikiPage = new URL("https","en.wikipedia.org","/w/api.php?action=query&format=xml&prop=revisions&pageids="+page_id+"&rvprop=content&rvslots=*");
			BufferedReader in = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) 
                sb.append(inputLine);
            in.close();
//            System.out.println(sb);
            String regex = "(?U)\\w+\\s+=\\s+\\w*\\s*(\\{{2}[\\w\\s|]*)*(\\**\\s*\\[{2}[\\w\\s-'|\\(\\),]*\\]{2}\\s*)+([\\w\\s|]*\\}{2})*([,\\w\\s]*)*";
            Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS | Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sb);
            while (matcher.find()){
            	String match = matcher.group(0);
//                System.out.println(match);
                if(match.contains(object)) {
                	wikipedia_predicate = match.split("=")[0];
                	wikipedia_predicate = wikipedia_predicate.replaceAll("[^a-zA-Z]", " ").trim();
//                	break;
                }
            }
		} catch (MalformedURLException e) {
//			System.out.println("URL MALFUNCTION DURING INFORMATION FETCH");
		} catch (IOException e) {
//			System.out.println("IO EXCEPTION DURING INFORMATION FETCH");
		}
		return wikipedia_predicate;
	}
}
