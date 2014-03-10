package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

public class LanguageIdentifier {

	/* ===============================================
		VARIABLES
	   =============================================== */

	// The hashtable of bigrams
	private Hashtable<String, Bigram> bigrams;
	
	// The list of hashtable keys
	private ArrayList<String> keys;
	
	/* ===============================================
		CONSTRUCTORS
   	   =============================================== */
	
	public LanguageIdentifier() {
		
		bigrams = new Hashtable<String, Bigram>();
		keys = new ArrayList<String>();
	}
	
	/* ===============================================
		METHODS
	   =============================================== */
	
	/**
	 * Trains one of the language identifiers
	 * @param key the key of the language identifier to train
	 * @param file the file containing the training text
	 * @throws FileNotFoundException if the file specified could not be found
	 * @throws IOException if the file specified was inaccessible
	 * @throws CharacterSetException if the file specified contained unrecognized characters
	 */
	public void train(String key, File file) throws FileNotFoundException, IOException {
		
		// If the bigram doesn't exist, create it
		if(!bigrams.containsKey(key)) {
			bigrams.put(key, new Bigram(file));
			addKey(key);
		}
		
		// If the bigram does exist, update the corpus
		else
			bigrams.get(key).train(file);
	}
	
	/**
	 * Evaluates a string and decides the langauge of the string
	 * @param s the string to be identified
	 * @return the predicted language to which the string belongs
	 * @throws CharacterSetException if there are unrecognized characters in the string
	 */
	public String evaluate(String s) {
		
		// default text if there are no hashtable keys
		String bestKey = "no language identifiers have been trained";
		double bestValue = -Double.MAX_VALUE;
		
		// Iterate all hashtable keys to see which is the greatest
		for(String key : keys) {
			System.out.println("-------------------------------------");
			System.out.println("EVALUATING INPUT AS " + key.toUpperCase());
			System.out.println("-------------------------------------");
			double value = bigrams.get(key).evaluate(s);
			System.out.println("FINAL LOGARITHMIC SUM FOR " + key.toUpperCase() + ": " + value);
			System.out.println();
			if(Double.compare(value, bestValue) > 0) {
				bestKey = key;
				bestValue = value;
			}
		}
		
		return bestKey;
	}
	
	/**
	 * Records a bigram hashtable key for later use
	 * @param key the key to the bigram hashtable
	 */
	private void addKey(String key) {
		keys.add(key);
	}
}
