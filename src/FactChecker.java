import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;

public class FactChecker {
	
	protected Map<String, String[]> training_statement_map = new LinkedHashMap<>();
	protected Map<String, String[]> test_statement_map = new LinkedHashMap<>();
    protected Map<String, Boolean> training_statement_value = new LinkedHashMap<>();
    private BufferedReader tsvReader, tsvReader1;
    private StanfordCoreNLP pipeline;
    private String subject, object, predicate;
    
    FactChecker() throws IOException{
    	try {
    		tsvReader = new BufferedReader(new FileReader("./SNLP2020_training.tsv"));
            String line = null;
//            int iteration = 0;
            while((line = tsvReader.readLine()) != null){
            	/*if(iteration==0) {
            		iteration++;
            		continue;
            	}*/
            	
                String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]
                training_statement_map.put(lineItems[0], new String[]{lineItems[1]});
                training_statement_value.put(lineItems[0], "1.0".equals(lineItems[2]));
            }
            tsvReader1 = new BufferedReader(new FileReader("./SNLP2020_test.tsv"));
            String line1 = null;
            while((line1 = tsvReader1.readLine()) != null){
                String[] lineItems1 = line1.split("\t"); //splitting the line and adding its items in String[]
                test_statement_map.put(lineItems1[0], new String[]{lineItems1[1]});
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FILES NOT FOUND");
        } catch (IOException ex) {
        	System.out.println("IO EXCEPTION");
        } finally {
        	tsvReader.close();
        	tsvReader1.close();
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
    
    public int[] findSubjectBoundary(String text) {
        Pattern pattern = Pattern.compile("('s|')");
        Matcher matcher = pattern.matcher(text);
        int start_index = -1 , end_index = -1;
        // Check all occurrences
        while (matcher.find()) {
        	start_index = matcher.start();
//            System.out.println("Start index: " + start_index);
            end_index = matcher.end();
//            System.out.println("End index: " + end_index);
//            System.out.println("Found: " + matcher.group());
        }
        return new int[] {start_index, end_index};
    }
    
    public String[] processData(String text){
    	subject = "";
		object = "";
		predicate = "";
		try{
			String verb = findVerb(text, false); //Normal Behaviour- Find VERB POS Tags
   		 	int verbIndex = text.indexOf(" " + verb + " ");
//   		 	System.out.println("Verb "+verb+" starts at "+verbIndex);
   		 	String[] textparts =text.split(" " + verb + " ");
//   		 	System.out.println(Arrays.toString(textparts));
   		 	int[] subjectEndIndices = findSubjectBoundary(text);
//   		 	System.out.println(Arrays.toString(subjectEndIndices));
   		 	if(verbIndex == subjectEndIndices[0]) //both are -1
   		 		throw new ArrayIndexOutOfBoundsException();
   		 	else if(verbIndex > 0 & subjectEndIndices[0] < 0){
   		 		predicate = verb;
   		 		subject = textparts[0];
   		 		object = textparts[1].substring(0,textparts[1].lastIndexOf("."));
   		 		/*System.out.println("Subject:" + subject);
   			 	System.out.println("Object:" + object);
   			 	System.out.println("Predicate:" + predicate);*/
   		 	}
   		 	else if(verbIndex < subjectEndIndices[0]){
   		 		object = textparts[0];
   		 		//Need to adjust search indices after splitting 
   		 		subject = textparts[1].substring(0, subjectEndIndices[0]-object.length()-verb.length()-2);
   		 		predicate = textparts[1].substring(subjectEndIndices[1]-object.length()-verb.length()-2, textparts[1].lastIndexOf("."));
   		 		/*System.out.println("Subject:" + subject);
   			 	System.out.println("Object:" + object);
   			 	System.out.println("Predicate:" + predicate);*/
   		 	}
   		 	else if(verbIndex > subjectEndIndices[0]){
   		 		try{
   		 			object = textparts[1].substring(0,textparts[1].lastIndexOf("."));
   		 		}catch(StringIndexOutOfBoundsException se){
   		 			object = textparts[1];
   		 		}
   		 		subject = textparts[0].substring(0, subjectEndIndices[0]);
   		 		predicate = textparts[0].substring(subjectEndIndices[1]).trim();
   		 		/*System.out.println("Subject:" + subject);
   			 	System.out.println("Object:" + object);
   			 	System.out.println("Predicate:" + predicate);*/
   		 	}
   	 	}catch(ArrayIndexOutOfBoundsException e){
//   	 		System.out.println(text);
   	 		String verb = findVerb(text, true); //Exception- Find NOUN-ly POS Tags
//   	 		int verbIndex = text.indexOf(" " + verb + " ");
//   		 	System.out.println("Verb "+verb+" starts at "+verbIndex);
   	 		String[] textparts =text.split(" " + verb + " ");
   	 		subject = textparts[0];
   	 		try{
	 			object = textparts[1].substring(0,textparts[1].lastIndexOf("."));
	 		}catch(StringIndexOutOfBoundsException se){
	 			object = textparts[1];
	 		}
			predicate = verb;
			/*System.out.println("Subject:" + subject);
			System.out.println("Object:" + object);
			System.out.println("Predicate:" + predicate);*/
   	 	}
		return new String[] {subject, object, predicate};
    }
    
    public void makeTriplet() {
    	// set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        //props.setProperty("ner.fine.regexner.ignorecase", "true");
        // build pipeline
        pipeline = new StanfordCoreNLP(props);
    	Set<String> training_keys = training_statement_map.keySet();
    	Set<String> test_keys = test_statement_map.keySet();
	    for(String key : training_keys)
	    	 training_statement_map.put(key, processData(training_statement_map.get(key)[0]));
	    for(String key : test_keys)
	    	test_statement_map.put(key, processData(test_statement_map.get(key)[0]));
    }
    
    public String findVerb(String text, boolean flag) {
    	String verb = "";
    	int prevIndex = -1;
    	CoreDocument document = pipeline.processToCoreDocument(text); // create a document object
    	//CODE HERE
    	if(!flag){
    		String[] values = {"VB","VBD","VBZ","VBP","VBN"};
    		for (CoreLabel tok : document.tokens()) {
    			if(Arrays.stream(values).anyMatch(tok.tag()::equals) & !Character.isUpperCase(tok.word().charAt(0)) & tok.word().matches("[a-z]{2,}")){
    				if(tok.index() == prevIndex+1){ //consecutive Verb tags
    					verb += tok.word()+" ";
    					prevIndex = tok.index();
    				}
    				else if(tok.index() != prevIndex+1){ //non-consecutive Verb tags
    					verb = tok.word()+" ";
    					prevIndex = tok.index();
    				}
    				else if(prevIndex == -1){ //first occurence of Verb tag
    					verb = tok.word()+" ";
    					prevIndex = tok.index();
    				}
    			}	
//        		System.out.println(String.format("%s\t%s\t%s\t%d", tok.word(), tok.lemma(), tok.tag(), tok.index()));
        		}
    	}
    	if(flag){
    		String[] values = {"VB","VBD","VBZ","VBP","VBN","NNS"};
    		for (CoreLabel tok : document.tokens()) {
    			if(Arrays.stream(values).anyMatch(tok.tag()::equals) & !Character.isUpperCase(tok.word().charAt(0)) & tok.word().matches("[a-z]{2,}")){
    				if(tok.index() == prevIndex+1){ //consecutive Verb tags
    					verb += tok.word()+" "; //add the words
    					prevIndex = tok.index();
    				}
    				else if(tok.index() != prevIndex+1){ //non-consecutive Verb tags
    					verb = tok.word()+" "; //overwrite
    					prevIndex = tok.index();
    				}
    				else if(prevIndex == -1){ //first occurence of Verb tag
    					verb = tok.word()+" "; //overwrite
    					prevIndex = tok.index();
    				}
    			}	
//        		System.out.println(String.format("%s\t%s\t%s\t%d", tok.word(), tok.lemma(), tok.tag(), tok.index()));
        		}
    	}
    	return verb.trim();
    }
}
