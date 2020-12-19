import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactChecker {
	
	protected Map<String, String> statement_map = new LinkedHashMap<>();
    protected Map<String, Boolean> statement_value = new LinkedHashMap<>();
    private BufferedReader tsvReader;
    
    FactChecker() throws IOException{
    	try {
    		tsvReader = new BufferedReader(new FileReader("./SNLP2019_training.tsv"));
            String line = null;
            while((line = tsvReader.readLine()) != null){
                String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]
                statement_map.put(lineItems[0], lineItems[1]);
                statement_value.put(lineItems[0], "1.0".equals(lineItems[2]));
            }
        } 
        catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FILE NOT FOUND");
        } 
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
        	tsvReader.close();
        }
    }
    
    // Tokenizer
    public String[] tokenize(String text){
    	List<String> tokens = new ArrayList<String>(); 
	    String newtext = text.replaceAll("[^a-zA-Z0-9()']"," ");
	    String text_array[] = newtext.split(" ");
	    for(int i = 0; i<text_array.length; i++){
	    	if (text_array[i].isEmpty()) continue;
	        else if (text_array[i].matches("\\s+")) continue;
	        tokens.addAll(Arrays.asList(text_array[i].split("\\s+")));
	            
	        }
    	 return tokens.stream().toArray(String[]::new);
    }
    
    public void makeTriplet() {
    	Set<String> keys = statement_map.keySet();
    }
    
    public String findVerb(String text) {
    	String verb = null;
    	//CODE HERE
    	
    	return verb;
    }
}
