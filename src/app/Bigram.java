package app;

import lib.FileOps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Bigram {
	
	
	/* ===============================================
		VARIABLES
   	   =============================================== */
	
	// The bigram matrix
	private Double[][] bigrams = new Double[26][26];
	
	// The smoothing level
	private final static double SMOOTHING = 0.5;
	
	// The size of the character set
	private final static int CHAR_SET_SIZE = 26;
	
	
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
		ArrayList<Character> tokens = Tokenizer.tokenize(fileContents);
		
		// Set up the bigram
		for(int i = 0; i < bigrams.length; i++) {
			for(int j = 0; j < bigrams[i].length; j++) {
				bigrams[i][j] = 0.0;
			}
		}
		
		// Record the number of bigrams
		int numBigrams = 0;
		
		// Build the Bigram
		for(int i = 0; i < tokens.size()-1; i++) {
			
			char token1 = tokens.get(i);
			char token2 = tokens.get(i+1);
			
			// Increment the bigram matrix if neither token is a space
			if(token1 != ' ' && token2 != ' ') {
				incrementValue(tokens.get(i), tokens.get(i+1));
				numBigrams++;
			}
		}
		
		// Iterate through all bigrams and modify them to be smoothed probabilities rather than frequencies
		for(int i = 0; i < bigrams.length; i++) {
			for(int j = 0; j < bigrams[i].length; j++) {
				double numerator = bigrams[i][j] + SMOOTHING;
				double denominator = numBigrams + (SMOOTHING * CHAR_SET_SIZE * CHAR_SET_SIZE);
				bigrams[i][j] = numerator / denominator;
			}
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
	 * @param first the first character in the bigram
	 * @param second the second character in the bigram
	 * @return the probability of the bigram occurring
	 */
	public Double getValue(char first, char second) {
		
		return bigrams[getDecimalValue(first)][getDecimalValue(second)];
	}
	
	/**
	 * Sets the value of the bigram
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 * @param value the value to set the bigram to
	 */
	public void setValue(Character first, Character second, Double value) {
		
		bigrams[getDecimalValue(first)][getDecimalValue(second)] = value;
	}
	
	/**
	 * Increments the value of the bigram by one.  If the bigram does not exist,
	 * it is set to one.
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 */
	private void incrementValue(Character first, Character second) {
		
		bigrams[getDecimalValue(first)][getDecimalValue(second)]++;
	}
	
	/**
	 * Converts a character to it's array index
	 * @param character the character to convert
	 * @return the associated array index
	 */
	private int getDecimalValue(char character) {
		return Integer.valueOf(character) - 97;
	}
}
