import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 * 
 * @author Gautam Sirdeshmukh
 *
 */
public class WordGram {
	
	private String[] myWords;   
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		myToString = null;
		myHash = 0;

		// TODO: initialize myWords

		int index = 0;
		for (int i = start; i < size + start; i++) {
			myWords[index] = source[i];
			index++;
		}


	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Return the amount of words in the WordGram
	 * @return number of words
	 */
	public int length(){
		// TODO: change this

		return myWords.length;
	}


	/**
	 * Returns true/false based on if two WordGrams are equal
	 * @param o
	 * @return true or false
	 */
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof WordGram) || o == null){
			return false;
		}
		// TODO: Complete this method

		WordGram wg = (WordGram) o;
		if (wg.myWords.length != this.myWords.length) {  // I CHANGED THIS
			return false;
		}
		int length = wg.myWords.length; // I CHANGED THIS
		for (int i = 0; i < length; i++) {
			if (!wg.wordAt(i).equals(this.wordAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the hashCode of the .toString of the words in the WordGram
	 * @return a hashCode
	 */
	@Override
	public int hashCode(){
		// TODO: complete this method

		if (myHash == 0) {
			myHash = this.toString().hashCode();
		}
		return myHash;
	}
	

	/**
	 * Returns an updated WordGram with the first word gone and the new word added
	 * @param last is last String of returned WordGram
	 * @return the shifted WordGram
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords,0,myWords.length);
		// TODO: Complete this method

		for (int i = 0; i < wg.myWords.length - 1; i++) { // I CHANGED THIS
			wg.myWords[i] = wg.myWords[i + 1];
		}
		wg.myWords[myWords.length - 1] = last;
		return wg;
	}


	/**
	 * Returns all of the words in the WordGram in one string separated by spaces
	 * @return the words in the WordGram in a string
	 */
	@Override
	public String toString(){
		// TODO: Complete this method

		if (myToString == null) {
			myToString = String.join(" ", myWords);
		}
		return myToString;
	}
}
