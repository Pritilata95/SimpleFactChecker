import java.io.IOException;

public class Main {

	public static void main(String[] args){
		try 
		{
			long startTime, endTime;
			FactChecker FC = new FactChecker();
			System.out.println("------- FACT TRIPLETS----------");
			startTime = System.currentTimeMillis();
			System.out.println("Triplet Making in Progress......");
		    FC.makeTriplet();	
		    endTime = System.currentTimeMillis();
		    System.out.println("Triplet Making took "+(endTime-startTime)+" milliseconds.");
		    System.out.println("----------- WIKI SEARCH -------------");
		    startTime = System.currentTimeMillis();
		    System.out.println("Wiki Search in Progress......");
		    FC.searchWiki();
		    endTime = System.currentTimeMillis();
		    System.out.println("Wiki Searching took "+(endTime-startTime)+" milliseconds.");
		    System.out.println("------------- TRAINING --------------");
		    startTime = System.currentTimeMillis();
		    System.out.println("Training in Progress......");
		    FC.trainClassifier();
		    endTime = System.currentTimeMillis();
		    System.out.println("Training took "+(endTime-startTime)+" milliseconds.");
		    System.out.println("------------- FACT CHECKING --------------");
		    startTime = System.currentTimeMillis();
		    System.out.println("Testing in Progress......");
		    FC.predictTruth();
		    endTime = System.currentTimeMillis();
		    System.out.println("Testing took "+(endTime-startTime)+" milliseconds.");
		}catch(IOException e) {
			System.out.println("IO error occurred. ");
		}
	}
}
