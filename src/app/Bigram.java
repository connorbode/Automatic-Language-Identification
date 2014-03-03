package app;

import lib.FileOps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Bigram {
	
	
	/* ===============================================
		VARIABLES
   	   =============================================== */
	
	// The bigram matrix
	private Hashtable<String, Hashtable<String, Double>> bigrams = new Hashtable<String, Hashtable<String, Double>>();
	
	
	/* ===============================================
		CONSTRUCTORS
   	   =============================================== */
	
	/**
	 * Constructs the bigram from a file
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CharacterSetException
	 */
	public Bigram(File file)
		throws FileNotFoundException, IOException, CharacterSetException {
		
		// Read the text from the file
		String fileContents = FileOps.readTextFromFile(file);
		
		// Parse the file contents
		ArrayList<String> tokens = Tokenizer.tokenize(fileContents);
		
		// Build the Bigram
		for(int i = 0; i < tokens.size()-1; i++) {
			incrementValue(tokens.get(i), tokens.get(i+1));
		}
	}
	
	/**
	 * Constructor with file path parameter
	 * @param filePath the path to the file
	 */
	public Bigram(String filePath)
		throws FileNotFoundException, IOException, CharacterSetException {
		
		// Call the other constructor with a new file
		this(new File(filePath));
	}
	
	
	/* ===============================================
		METHODS
   	   =============================================== */
	
	/**
	 * Retrieves the value of a given bigram
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 * @return null if the bigram does not exist; otherwise the probability of the bigram occurring.
	 */
	public Double getValue(String first, String second) {
		
		Double returnVal = null;
		
		// Try to get the second dimension
		Hashtable<String, Double> secondDimension = bigrams.get(first);
		
		// Try to get the actual value
		if(secondDimension != null) {
			returnVal = secondDimension.get(second);
		}
		
		return returnVal;
	}
	
	/**
	 * Sets the value of the bigram
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 * @param value the value to set the bigram to
	 */
	public void setValue(String first, String second, Double value) {
		
		// Try to get the second dimension
		Hashtable<String, Double> secondDimension = bigrams.get(first);
		
		// If there was no existing second dimension, create it
		if(secondDimension == null) {
			secondDimension = new Hashtable<String, Double>();
			bigrams.put(first, secondDimension);
		}
		
		// Set the value of the second dimension
		secondDimension.put(second, value);
	}
	
	/**
	 * Increments the value of the bigram by one.  If the bigram does not exist,
	 * it is set to one.
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 */
	private void incrementValue(String first, String second) {
		
		// Get the current value of the bigram
		Double value = getValue(first, second);
		
		// Set the value of the bigram
		if(value == null)
			setValue(first, second, 1.0);
		else
			setValue(first, second, value + 1.0);
	}
}
