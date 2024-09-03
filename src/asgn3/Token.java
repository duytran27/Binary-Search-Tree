package asgn3;
import java.util.Comparator;

/** 
 * COMP 2503 Assignment 3
 * A Token object represents a word (string) and how many times it 
 * has occurred in a given text. 
*/


/**
 * Represents a word (string) and its frequency in a text.
 * Provides methods for comparing tokens based on frequency and length.
 */
public class Token implements Comparable<Token> {

  
   private String word;
  
   private int count; 
   
   /**
    * Constructs a Token object with the given word and initial frequency of 0.
    *
    * @param w The word
    */
   public Token( String w) 
   {
      this.word = w;
      this.count = 0;
   }

   /**
    * Comparator for comparing tokens based on frequency in descending order.
    */
   public static Comparator<Token> compareFreqDesc = new Comparator<Token>() 
   {

      public int compare( Token w1, Token w2) 
      {

         int freq1 = w1.getCount();
         int freq2 = w2.getCount();
         if ( freq2 - freq1 == 0) 
            return w1.compareTo( w2);
         else
            return freq2 - freq1; 
      }
   };

   /**
    * Comparator for comparing tokens based on frequency in ascending order.
    */
   public static Comparator<Token> CompFreqAsc = new Comparator<Token>() 
   {

      public int compare( Token w1, Token w2) 
      {

         int f1 = w1.getCount();
         int f2 = w2.getCount();

         if ( f1 - f2 == 0) 
            return w1.compareTo( w2);
         else
            return f1 - f2; 
      }
   };

   /**
    * Comparator for comparing tokens based on length in descending order.
    */
   public static Comparator<Token> compareLengthDesc = new Comparator<Token>() 
   {

      public int compare( Token w1, Token w2) 
      {
    	  // TODO:
    	  int f1 = w1.getLength();
    	  int f2 = w2.getLength();
    	  
    	  if(f1-f2 == 0) {
    		  return w1.compareTo(w2);
    	  }
    	  return f2-f1;
      }
   };

   /**
    * Gets the word.
    *
    * @return The word
    */
   public String getWord() 
   { 
      return word;
   }

   /**
    * Gets the frequency of the word.
    *
    * @return The frequency of the word
    */
   public int getCount() 
   { 
      return count;
   }

   /**
    * Increments the frequency count of the word.
    */
   public void incrCount() 
   { 
      count++; 
   }

   /**
    * Gets the length of the word.
    *
    * @return The length of the word
    */
   public int getLength() 
   {
      return getWord().length();
   }

   /**
    * Returns a string representation of the token.
    *
    * @return A string representation of the token
    */
   public String toString() 
   { 
      return getWord() + " : " + getLength() + " : " + getCount();
   }
   
   /**
    * Generates a hash code for the token.
    *
    * @return The hash code for the token
    */
   public int hashCode()
   {
      return getWord().hashCode();
   } 

   /**
    * Checks if the token is equal to another object.
    *
    * @param other The object to compare with
    * @return True if the token is equal to the other object, false otherwise
    */
   public boolean equals( Object other) 
   {
      if ( other == this) return true;
      if ( other == null) return false;
      if (this.getClass() != other.getClass()) return false;
      Token p = (Token)other;
      return this.getWord().equals( p.getWord());
   }
   
   /**
    * Compares this token with another token.
    *
    * @param o The other token to compare with
    * @return 0 if the tokens are equal, a positive value if this token is greater, a negative value if this token is smaller
    */
   public int compareTo( Token o) 
   {
      if ( this.equals( o))
         return 0;
      else
         return this.getWord().compareTo( o.getWord()); 
   }
}
