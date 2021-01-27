# Simple Fact Checker

Goal : Build a corpus-driven fact-checking engine, which returns a veracity value between -1 (fact is false) and +1 (fact is true) given a fact from DBpedia.

While running this program, be sure to include the provided jar files in classpath. 
If you want, you can change training and test filenames in FactChecker.java

# Role of each file
1. Main.java is the entry point of the program 
2. FactChecker.java makes <Subject, Object, Predicate> triplet and handles the other classes
3. FactSearcher.java takes a triplet, performs web search for the given subject and returns matching predicate for the provided object, if available. 
4. FactClassifier.java assigns a truth-value to a fact by matching provided predicate and web predicate. 
