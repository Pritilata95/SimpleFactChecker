import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;

public class FactChecker {
	
	protected Map<String, String> statement_map = new LinkedHashMap<>();
    protected Map<String, Boolean> statement_value = new LinkedHashMap<>();
    private BufferedReader tsvReader;
    private StanfordCoreNLP pipeline;
    private String subject, object, predicate;
    
    FactChecker() throws IOException{
    	try {
    		tsvReader = new BufferedReader(new FileReader("./SNLP2020_training.tsv"));
            String line = null;
            int itteration = 0;
            while((line = tsvReader.readLine()) != null){
            	if(itteration==0) {
            		itteration++;
            		continue;
            	}
            	
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
    
/*  // Tokenizer 
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
    }//end*/
    
    private String findNER(String text) {
    	String nerstring = "";
    	CoreDocument doc = new CoreDocument(text);
    	pipeline.annotate(doc);
    	for (CoreEntityMention em : doc.entityMentions()) {
    	      System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
    	      	nerstring += em.text()+" ";
    	}
    	return nerstring;
    }
    
    public void makeTriplet() {
    	// set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("ner.fine.regexner.ignorecase", "true");
        // build pipeline
        pipeline = new StanfordCoreNLP(props);
    	Set<String> keys = statement_map.keySet();
	     for(String key : keys) {
	    	 String text = statement_map.get(key);
	    	 subject = "";
    		 object = "";
    		 predicate = text;
	    	 try{
	    		 String verb = findVerb(text);
	    		 String[] textparts =text.split(" " + verb + " ");
	    		 String ner1 = findNER(textparts[1]);
	    		 String ner0 = findNER(textparts[0]);
	    		 //Finding correct subject and object
	    		 
	    		 String[] sov = (subject + object + " "+ verb).split(" ") ;
	    		 for(String i : sov)
	    			 predicate = predicate.replace(i, "");
	    		 predicate = predicate.replaceAll("('s|')", "");
	    		 System.out.println(text);
	    		 System.out.println("Subject:"+subject+"\n"+"Object:"+object+"\n"+"Predicate:"+predicate);
	    		 } 
	    	 catch(ArrayIndexOutOfBoundsException e){
	    		 System.out.println("verb not found");
	    		 List<String> entities = new ArrayList<>();
	    		 CoreDocument doc = new CoreDocument(text);
	    		 pipeline.annotate(doc);
	    		 for (CoreEntityMention em : doc.entityMentions()) {
	    			 System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
	    	    	 entities.add(em.text());
	    		 }
	    		 System.out.println(entities);
	    	 }		    	 
	     }
    	
    }
    
    private String findVerb(String text) {
    	String verb = null;
    	//CODE HERE
    	String[] values = {"VB","VBD","VBZ","VBP"};
        // create a document object
        CoreDocument document = pipeline.processToCoreDocument(text);
        // display tokens
        for (CoreLabel tok : document.tokens()) {
        	if(Arrays.stream(values).anyMatch(tok.tag()::equals)) {
        		verb = tok.word();
        	}
        	//System.out.println(String.format("%s\t%s", tok.word(), tok.tag()));
        }
    	return verb;
    }
}
