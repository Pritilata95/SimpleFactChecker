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
		    System.out.println("------------- TRAINING CLASSIFIER --------------");
		    FC.trainClassifier();
		    System.out.println("------------- FACT CHECKING --------------");
		    FC.predictTruth();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
