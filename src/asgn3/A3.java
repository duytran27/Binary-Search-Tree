package asgn3; 
import java.util.Scanner;
import java.util.Iterator;

/** 
* COMP 2503 Winter 2024 Assignment 3 
* 
* This program reads a text file and compiles a list of the 
* words together with the frequency of the words.
* Use a BST for storing the words. 
* Words from a standard list of stop words are then deleted.
* 
* BSTs with alternative orderings are constructed to show the
* required output.
* 
* @author Duy Tran
*/

public class A3 
{
   /**
	 * BST for storing words in natural order.
	 */
   private BST<Token> wordsByNaturalOrder = new BST<Token>();
   /**
    * BST for storing words in descending order of frequency.
    */
   private BST<Token> wordsByFreqDesc = new BST<Token>(Token.compareFreqDesc);
   /**
    * BST for storing words in descending order of length.
    */
   private BST<Token> wordsByLength = new BST<Token>(Token.compareLengthDesc);
   
   // there are 103 stopwords in this list
   private String[] stopwords = { 
         "a","about","all","am","an","and","any","are","as","at", 
         "be","been","but","by","can","cannot","could", "did", "do", "does", 
         "else", "for", "from", "get", "got", "had", "has", "have", "he", "her", 
         "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", 
         "its",  "like", "more", "me", "my", "no", "now", "not", "of", "on", 
         "one", "or", "our", "out", "said", "say", "says", "she", "so", "some",
         "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this", 
         "to", "too", "us", "upon", "was", "we", "were", "what", "with", "when", 
         "where", "which", "while", "who", "whom", "why", "will", "you", "your", "up", "down", "left", "right",
         "man", "woman", "would", "should", "dont", "after", "before", "im", "men"
   };

   private int totalWordCount = 0;
   private int stopWordCount = 0;

   private Scanner inp = new Scanner( System.in);
   
   /**
    * Main method to run the program.
    *
    * @param args Command-line arguments
    */
   public static void main( String[ ] args) 
   {	
      A3 a3 = new A3();
      a3.run();
   }

   /**
    * Prints the results of word analysis.
    */
   private void printResults() 
   {
       System.out.println("Total Words: " + totalWordCount);
       System.out.println("Unique Words: " + wordsByNaturalOrder.size()); 
       System.out.println("Stop Words: " + stopWordCount);
       System.out.println();
       System.out.println("10 Most Frequent");
       
       /* TODO:
        * Use an iterator to traverse the wordsByFreqDesc in-order, and print the first 10
        */
       printTopTens(wordsByFreqDesc);

       System.out.println();
       System.out.println("10 Longest");

       /* TODO:
        * Use an iterator to traverse the wordsByLength in-order, and print the first 10
        */
       printTopTens(wordsByLength);
       System.out.println();
       System.out.println("The longest word is " + wordsByLength.min());
       System.out.println("The average word length is " + avgLength());
       System.out.println();        
       System.out.println("All");

       /* TODO:
        * Use an iterator to traverse the wordsByNaturalOrder in-order, and print all elements in the tree
        */
       printAll();
       System.out.println();
       
       System.out.println("Alphabetic Tree: (Optimum Height: " + 
             optHeight(wordsByNaturalOrder.size()) + ") (Actual Height: " 
             + wordsByNaturalOrder.height() + ")");
       System.out.println("Frequency Tree: (Optimum Height: " + 
             optHeight(wordsByFreqDesc.size()) + ") (Actual Height: " 
             + wordsByFreqDesc.height() + ")");
       System.out.println("Length Tree: (Optimum Height: " + 
             optHeight(wordsByLength.size()) + ") (Actual Height: " 
             + wordsByLength.height() + ")");
   }
   
   /**
    * Prints all words in a BST.
    */
   private void printAll() {
	   
	for(Token words: wordsByNaturalOrder) {
		System.out.println(words);
	}
}

   /**
    * Prints the top ten most frequent or longest words.
    *
    * @param listOfWords BST of words
    */
  private void printTopTens(BST<Token> listOfWords) {

	 int amount = Math.min(listOfWords.size(), 10);
	
	 int count = 0;
	
	 for(Token currWord: listOfWords) {
		
		if(count >= amount) {
			count++;
			return;
		 }
		 System.out.println(currWord);
		 count++;
	}
}

  /**
   * Reads input text file and adds words to BST.
   */
   private void readFile() 
   {
	   
      while (inp.hasNext()) 
      {
        
         String word = inp.next().toLowerCase().trim().replaceAll("[^a-z]","");

         if (word.length() > 0) 
         { 
        	 
        	 ifNotExistAddToken(new Token(word));
         }
      }
   }

   /**
    * Adds a word to the BST if it does not exist, otherwise increments its count.
    *
    * @param currToken Token to add
    */
   private void ifNotExistAddToken(Token currToken) {

	Token existsToken = wordsByNaturalOrder.find(currToken);
	
	if(existsToken != null) {
		
		existsToken.incrCount();
	}
	
	else {
		
		wordsByNaturalOrder.add(currToken);
		currToken.incrCount();
	}
	totalWordCount++;
 }

   /**
    * Creates frequency lists based on word counts.
    */
   private void createFreqLists()
   {
	   
       for (Token currToken : wordsByNaturalOrder) {
           
    	   wordsByLength.add(currToken);
           
           if(currToken.getCount() > 2) {
               wordsByFreqDesc.add(currToken);
           }
       }
   }
   
   
   /**
    * Calculates the average length of words in the BST.
    *
    * @return Average length of words
    */
   private int avgLength() 
   {
	
	   int sum = 0;
	   
	   for(Token currWord: wordsByNaturalOrder) {
		   
		   sum+=currWord.getLength();
	   }
	   
	   int avg = 0;
	   
	   if(!wordsByNaturalOrder.isEmpty()) {
		   
		   avg = sum/wordsByNaturalOrder.size();
	   }
	   return avg;
   }

   /**
    * Removes stop words from the BST.
    */
   private void removeStop() 
   {

       for (String stopWord : stopwords) {
           
    	   Token word = new Token(stopWord);
           Token foundWord = wordsByNaturalOrder.find(word);
           
           if (foundWord != null) {
               wordsByNaturalOrder.delete(word);
               stopWordCount++;
           }
       }
   }

   /**
    * Calculates the optimum height of a BST given the number of nodes.
    *
    * @param n Number of nodes
    * @return Optimum height of BST
    */
   private int optHeight(int n) 
   {
	     double h = Math.log(n+1) / Math.log(2) - 1;  
	     
	     if (Math.round(h) < h)
	    	  return (int)Math.round(h) + 1;
	     else
	    	  return (int)Math.round(h);
   }

   /**
    * Executes the program by reading input, processing data, and printing results.
    */
   private void run() 
   {
      readFile();
      removeStop();
      createFreqLists();
      printResults();
   }
}
