import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactClassifier {
	
	Set <String> vocabulary = new HashSet<String>();
	private Map<String, Double> trigramFreqTable=new HashMap();
    private Map<String, Double> bigramFreqTable = new HashMap();
    private final Set<String> stopwords = new HashSet<>(Arrays.asList("i","me","my","myself","we","our","ours","ourselves","you","your","yours",
	    "yourself","yourselves","he","him","his","himself","she","her","hers","herself","it","its","itself","they","them",
	    "their","theirs","themselves","what","which","who","whom","this","that","these","those","am","is","are","was",
	    "were","be","been","being","have","has","had","having","do","does","did","doing","a","an","the","and","but","if",
	    "or","because","as","until","while","of","at","by","for","with","about","against","between","into","through",
	    "during","before","after","above","below","to","from","up","down","in","out","on","off","over","under","again",
	    "further","then","once","here","there","when","where","why","how","all","any","both","each","few","more","most",
	    "other","some","such","no","nor","not","only","own","same","so","than","too","very","s","t","can","will","just",
	    "don","should","now"));
	
	private List<String> preprocess(String text) {
		List<String> tokens = new ArrayList<String>(); 
		String newtext = text.replaceAll("[^a-zA-Z0-9]"," ").toLowerCase();
	    //newtext = newtext.replaceAll("[0-9]+", "#DIGIT");
	    String text_array[] = newtext.split("\\s+");
	    tokens.addAll(Arrays.asList(text_array));
	    for(int i=0; i < tokens.size(); i++){
	        if(stopwords.contains(tokens.get(i))){
	            tokens.remove(tokens.get(i));
	        }
	            
	    }
		return tokens; 
	}
	
    protected void learner(String text){
        double val=0.0;
        List<String> tokens = preprocess(text);
        if(tokens.size()>2) {
            String trigramkey = tokens.get(0)+" "+tokens.get(1)+" "+tokens.get(2);
            String bigramkey = tokens.get(0)+" "+tokens.get(1);
            if(trigramFreqTable.containsKey(trigramkey)){
                val=trigramFreqTable.get(trigramkey)+1.0;
                trigramFreqTable.put(trigramkey, val);
            }
            else{
                trigramFreqTable.put(trigramkey, 1.0);
            }
            
            if(bigramFreqTable.containsKey(bigramkey)){
                val=bigramFreqTable.get(bigramkey)+1.0;
                bigramFreqTable.put(bigramkey, val);
            }
            else{
                bigramFreqTable.put(bigramkey, 1.0);
            }
            vocabulary.addAll(tokens);
        }

   }
	
    protected String trigrampredictor(String text){
        //System.out.println(text);
        List<String> tokens = preprocess(text);
        String result= new String();
        Double trueprobab=0.0;
        Double falseprobab=0.0; 
        if(tokens.size()<2){
            result="false";
        }
        else{
            //for(String m : trigramFreqTable.keySet()){
            //System.out.println(m + "\t"+ trigramFreqTable.get(m));}
            String bigramkey = tokens.get(0)+" "+tokens.get(1);
            Double bicount= bigramFreqTable.get(bigramkey);
            Double falsecount= trigramFreqTable.get(bigramkey+" "+"false");
            Double truecount= trigramFreqTable.get(bigramkey+" "+"true");
            if(bicount==null) bicount=0.0;
            if(falsecount==null) falsecount = 0.0;
            if(truecount==null) truecount=0.0;
            falseprobab=( falsecount +1.0)/(bicount+vocabulary.size()); 
            //System.out.println(falseprobab); 
            trueprobab=(truecount+1.0)/(bicount +vocabulary.size()); 
            //System.out.println(trueprobab);
            if(trueprobab>falseprobab){ 
                result="true";
            }
            else{ 
                result="false";
            }
        }
        return result;
    }   
 
    
}
