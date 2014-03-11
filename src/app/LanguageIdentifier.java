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
		
		// create total holders for each language
		double totals[] = new double[keys.size()];
		Bigram bigrams[] = new Bigram[keys.size()];
		for(int i = 0; i < keys.size(); i++) {
			totals[i] = 0;
			bigrams[i] = this.bigrams.get(keys.get(i));
		}
		
		// tokenize the string
		ArrayList<Character> tokens = Tokenizer.tokenize(s);
		
		// iterate through the tokens
		for(int i = 0; i < tokens.size() - 1; i++) {
			
			char char1 = tokens.get(i);
			char char2 = tokens.get(i+1);
			
			if(char1 != ' ' && char2 != ' ') {
			
				System.out.println("BIGRAM: " + char1 + char2);
				
				// parse the bigram using each langauge
				for(int j = 0; j < bigrams.length; j++) {
					double prob = bigrams[j].probability(char1,  char2);
					totals[j] += Math.log10(prob);
					System.out.println(keys.get(j).toUpperCase() + ": P(" + char1 + "," + char2 + ") = " + prob + " ==> log prob of sequence so far: " + totals[j]);
				}
				
				System.out.println();
			}
		}
		
		// choose the outcome
		double max = -Double.MAX_VALUE;
		int maxIndex = -1;
		for(int i = 0; i < totals.length; i++) {
			if(totals[i] > max) {
				max = totals[i];
				maxIndex = i;
			}
		}
		
		return keys.get(maxIndex);
	}
	
	/**
	 * Records a bigram hashtable key for later use
	 * @param key the key to the bigram hashtable
	 */
	private void addKey(String key) {
		keys.add(key);
	}
}
