import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
 *
 * @author Pritilata
 */

public class Main {

	public static void main(String[] args){
		try 
		{
			FactChecker FC = new FactChecker();
			System.out.println("------- FACTS ----------");
			Set<String> keys = FC.statement_map.keySet();
			for(String key:keys)
				System.out.println("Fact ID:"+key+"\tFact Statement:"+FC.statement_map.get(key)+"\tFact Value:"+FC.statement_value.get(key));
			
//		     String text = "The Demolished Man's author is E. E. Smith";
//		     String text = "Camp Rock stars Brad Pitt.";
//		     System.out.println(Arrays.toString(FC.tokenize(text)));
		     FC.makeTriplet();
//		     System.out.println(FC.findVerb(text));	
		}  
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
