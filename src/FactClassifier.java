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
	private Map<String, Integer>  classFrequency;
	private Map<String, Map<String, Integer>> classVocab;
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
		classFrequency = new HashMap<String, Integer>();
        classVocab = new HashMap<String, Map<String, Integer>>();
        Iterator<String> it = classes.iterator();
        while (it.hasNext()) {
            String item = it.next().toString();
            classFrequency.put(item, 0);
            classVocab.put(item, new HashMap<String, Integer>());
        }
	}
	
	private List<String> preprocess(String text) {
        List<String> tokens = new ArrayList<>();
        text = text.replaceAll("[^a-zA-Z0-9]", " "); //replaces all non-alphanumeric character
        text = text.trim().toLowerCase();
//      System.out.println(text);
        tokens.addAll(Arrays.asList(text.split("\\s+"))); //splits on whitespaces
        return tokens;
    }
}
