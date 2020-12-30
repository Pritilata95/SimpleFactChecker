import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FactSearcher {
	
	private URL wikiSearch, wikiPage;
	private BufferedReader in;
	private StringBuilder sb;
	
	public String wikiSearcher(String subject){
		String page_id = null;
		subject = subject.replaceAll("\\s+", "%20");
		try {
			wikiSearch = new URL("https","en.wikipedia.org","/w/api.php?action=query&list=search&srsearch="+subject+"&format=xml");
			in = new BufferedReader(new InputStreamReader(wikiSearch.openStream()));
			String inputLine;
			sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) 
                sb.append(inputLine);
            in.close();
            System.out.println(sb); //Pass This to XML parser and Return page_id
		} catch (MalformedURLException e) {
			System.out.println("URL MALFUNCTION DURING WIKI SEARCH");
		} catch (IOException e) {
			System.out.println("IO EXCEPTION DURING WIKI SEARCH");
		}
		return page_id;
	}
	
	public String pageSearcher(String page_id){
		String wiki_predicate = null;
		try {
			wikiPage = new URL("https","en.wikipedia.org","/w/api.php?action=query&format=xml&prop=revisions&pageids="+page_id+"&rvprop=content&rvslots=*");
			in = new BufferedReader(new InputStreamReader(wikiPage.openStream()));
			String inputLine;
			sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) 
                sb.append(inputLine);
            in.close();
            System.out.println(sb); //Pass This to XML parser and Return predicate
		} catch (MalformedURLException e) {
			System.out.println("URL MALFUNCTION DURING INFORMATION FETCH");
		} catch (IOException e) {
			System.out.println("IO EXCEPTION DURING INFORMATION FETCH");
		}
		return wiki_predicate;
	}
}
