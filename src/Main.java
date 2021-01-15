import java.io.IOException;

public class Main {

	public static void main(String[] args){
		try 
		{
			FactChecker FC = new FactChecker();
			System.out.println("------- FACT TRIPLETS----------");
		    FC.makeTriplet();		    
		    System.out.println("----------- WIKI SEARCH -------------");
		    FC.searchWiki();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
