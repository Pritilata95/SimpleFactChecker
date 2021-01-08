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
			System.out.println("------- FACT TRIPLETS----------");
/*			Set<String> keys = FC.test_statement_map.keySet();
			for(String key:keys)
				System.out.println("Fact ID:"+key+"\tFact Statement:"+Arrays.toString(FC.test_statement_map.get(key)));*/
			
//		    String text = "Maria das Neves' award is São Tomé and Príncipe.";
//		    String text = "Stars Nokia, Finland has been Nokiaed Nokia.";
//			String text = "Alan Rickman is Love Actually's better half.";
//			String text = "When Worlds Collide's author is Philip Wylie.";
//			String text = "Stars and Stripes stars Nick Jonas.";
//		    System.out.println(Arrays.toString(FC.tokenize(text)));
		    FC.makeTriplet();
//		    System.out.println(FC.findVerb(text,true));	
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3326237"))+"\t"+FC.training_statement_value.get("3326237"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3620807"))+"\t"+FC.training_statement_value.get("3620807"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3818539"))+"\t"+FC.training_statement_value.get("3818539"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3419884"))+"\t"+FC.training_statement_value.get("3419884"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3658486"))+"\t"+FC.training_statement_value.get("3658486"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("4330918"))+"\t"+FC.training_statement_value.get("4330918"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3871761"))+"\t"+FC.training_statement_value.get("3871761"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3387592"))+"\t"+FC.training_statement_value.get("3387592"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3399684"))+"\t"+FC.training_statement_value.get("3399684"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3813728"))+"\t"+FC.training_statement_value.get("3813728"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3457895"))+"\t"+FC.training_statement_value.get("3457895"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3611275"))+"\t"+FC.training_statement_value.get("3611275"));
		    System.out.println(Arrays.toString(FC.training_statement_map.get("3323850"))+"\t"+FC.training_statement_value.get("3323850"));
		    
		    System.out.println("----------- WIKI -------------");
		    FactSearcher FS = new FactSearcher();
		    System.out.println(Arrays.toString(FS.wikiSearcher(FC.training_statement_map.get("3399684"))));
		    System.out.println(Arrays.toString(FS.wikiSearcher(FC.test_statement_map.get("3856463"))));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
