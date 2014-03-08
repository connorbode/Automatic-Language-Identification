package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class LanguageIdentifier {

	/* ===============================================
		VARIABLES
	   =============================================== */

	private Hashtable<String, Bigram> bigrams;
	
	/* ===============================================
		CONSTRUCTORS
   	   =============================================== */
	
	public LanguageIdentifier() {
		
		bigrams = new Hashtable<String, Bigram>();
	}
	
	/* ===============================================
		METHODS
	   =============================================== */
	
	public void addCorpus(String key, File file) throws FileNotFoundException, IOException, CharacterSetException {
		
		// If the bigram doesn't exist, create it
		if(!bigrams.containsKey(key))
			bigrams.put(key, new Bigram(file));
		
		// If the bigram does exist, update the corpus
		else {}
	}
}
