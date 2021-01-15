import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactClassifier {
	
	private Set<String> docVocab;
	private Map<String, Integer>  classfrequency;
	private Map<String, Map<String, Integer>> wordfrequency;
	private final Set<String> stopwords = new HashSet<>(Arrays.asList("i","me","my","myself","we","our","ours","ourselves","you","your","yours",
		    "yourself","yourselves","he","him","his","himself","she","her","hers","herself","it","its","itself","they","them",
		    "their","theirs","themselves","what","which","who","whom","this","that","these","those","am","is","are","was",
		    "were","be","been","being","have","has","had","having","do","does","did","doing","a","an","the","and","but","if",
		    "or","because","as","until","while","of","at","by","for","with","about","against","between","into","through",
		    "during","before","after","above","below","to","from","up","down","in","out","on","off","over","under","again",
		    "further","then","once","here","there","when","where","why","how","all","any","both","each","few","more","most",
		    "other","some","such","no","nor","not","only","own","same","so","than","too","very","s","t","can","will","just",
		    "don","should","now","place"));
	
	public FactClassifier (Set<String> classes) {
		classfrequency = new HashMap<String, Integer>();
		wordfrequency = new HashMap<String, Map<String, Integer>>();
		docVocab = new HashSet<String>();
        Iterator<String> it = classes.iterator();
        while (it.hasNext()) {
            String item = it.next().toString();
            classfrequency.put(item, 0);
            wordfrequency.put(item, new HashMap<String, Integer>());
        }
	}
	
	private List<String> preprocess(String text) {
    	List<String> tokens = new ArrayList<String>(); 
    	String newtext = text.replaceAll("[^a-zA-Z0-9]"," ").toLowerCase();
        newtext = newtext.replaceAll("[0-9]+", "#DIGIT");
        String text_array[] = newtext.split("\\s+");
        tokens.addAll(Arrays.asList(text_array));
        for(int i=0; i < tokens.size(); i++){
            if(stopwords.contains(tokens.get(i))){
                tokens.remove(tokens.get(i));
            }
                
        }
    	return tokens; 
    }
	
    public void learnExample(String clazz, String text) {   	
        List<String> words = preprocess(text);
        int val = (classfrequency.get(clazz)) +1;
        classfrequency.put(clazz, val);
        Map <String, Integer> wf = wordfrequency.get(clazz);
        for(String w : words){
            if(!wf.containsKey(w))
                wf.put(w, 1);
            else{
                int valw = (wf.get(w))+1;
                    wf.put(w, valw);
            }
        }
        for(String m : wordfrequency.keySet()){
            docVocab.addAll(wordfrequency.get(m).keySet());
        }
    }
    
    public String classify(String text) {
        String clazz = null;
        // YOUR CODE HERE
        double intprob = Double.NEGATIVE_INFINITY;
//        System.out.println("\t id : "+i);
        List<String> l = preprocess(text);
        Set<String> classez = classfrequency.keySet();
        for(String s : classez){ 
            double likelihood=0L;
            double classprob=0L;
            Map<String, Integer> wf = wordfrequency.get(s);
            int n = wf.values().stream().mapToInt(Integer :: intValue).sum();
            //double nd = n; 
            int nk;
            for(String s1 : l){
                try{
                    nk = wf.get(s1);
//                    System.out.println(s1+" : "+nk);
                }
                catch(NullPointerException e){
                    nk = 0;
//                    System.out.println(s1+" : "+nk);
                }
                
                //double nkd = nk;
                likelihood += Math.log((double)(nk+1)/(n+docVocab.size()));
//                System.out.println("likelihood : " + likelihood);
            }
//              classprob = likelihood;
            classprob = Math.log(0.5)+likelihood;
//            System.out.println(s+" : "+classprob);
            
            if(classprob > intprob){
 //               System.out.println("ba "+clazz+" : "+intprob);
                intprob = classprob; 
                clazz = s;
  //              System.out.println("aa "+clazz+" : "+intprob);

            }
            
        }
 //       i++;
        return clazz;
    }
    
}
