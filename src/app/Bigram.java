package app;

import lib.FileOps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Bigram {
	
	
	/* ===============================================
		VARIABLES
   	   =============================================== */
	
	// The size of the character set
	private final static int CHAR_SET_SIZE = Tokenizer.ACCEPTABLE_CHARS.length;
	
	// The bigram matrix
	private Double[][] bigrams = new Double[CHAR_SET_SIZE][CHAR_SET_SIZE];
	
	// Totals for each row of bigrams (e.g. number of bigrams starting with character "s")
	private int[] bigramTotals = new int[CHAR_SET_SIZE];
	
	// The smoothing level
	private final static double SMOOTHING = 0.5;
	
	
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
	protected Bigram(File file)
		throws FileNotFoundException, IOException {
		
		train(file);
	}
	
	/**
	 * Constructor with file path parameter
	 * @param filePath the path to the file
	 */
	protected Bigram(String filePath)
		throws FileNotFoundException, IOException {
		
		// Call the other constructor with a new file
		this(new File(filePath));
	}
	
	
	/* ===============================================
		METHODS
   	   =============================================== */
	
	/**
	 * Trains the bigram
	 * @param file the file to train from
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CharacterSetException If there are unrecognized characters in the file
	 */
	protected void train(File file) throws FileNotFoundException, IOException {
		
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
		
		// Build the Bigram
		for(int i = 0; i < tokens.size()-1; i++) {
			
			char token1 = tokens.get(i);
			char token2 = tokens.get(i+1);
			
			// Increment the bigram matrix if neither token is a space
			if(token1 != ' ' && token2 != ' ') {
				incrementValue(tokens.get(i), tokens.get(i+1));
				bigramTotals[getDecimalValue(token1)]++;
			}
		}
	}
	
	
	/**
	 * Sets the value of the bigram
	 * @param first the first word in the bigram
	 * @param second the second word in the bigram
	 * @param value the value to set the bigram to
	 */
	protected void setValue(Character first, Character second, Double value) {
		
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
	
	/**
	 * Retrieves the probability of the of the bigram occurring
	 * @param val the frequency of the bigram
	 * @return
	 */
	protected Double probability(char char1, char char2) {
		int row = getDecimalValue(char1);
		int col = getDecimalValue(char2);
		double val = bigrams[row][col];
		double numerator = val + SMOOTHING;
		double denominator = bigramTotals[row] + (SMOOTHING * CHAR_SET_SIZE * CHAR_SET_SIZE);
		return numerator / denominator;
	}
}
