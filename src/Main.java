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
		    System.out.println(FC.predictTruth("subsidiary subsid"));
		    System.out.println(FC.predictTruth("subsidiary  "));
		    System.out.println(FC.predictTruth("better half spouse"));
		    System.out.println(FC.predictTruth("author author"));
		    System.out.println(FC.predictTruth("beter half wife"));
		    System.out.println(FC.predictTruth("stars starring"));
		    System.out.println(FC.predictTruth("birth last"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
