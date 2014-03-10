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
	
	// The bigram matrix
	private Double[][] bigrams = new Double[26][26];
	
	// The smoothing level
	private final static double SMOOTHING = 0.5;
	
	// The size of the character set
	private final static int CHAR_SET_SIZE = 26;
	
	// The number of bigrams in the set
	private int numBigrams = 0;
	
	
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
				numBigrams++;
			}
		}
	}
	
	
	/**
	 * Retrieves the value of a given bigram
	 * @param first the first character in the bigram
	 * @param second the second character in the bigram
	 * @return the probability of the bigram occurring
	 */
	protected Double getValue(char first, char second) {
		
		double frequency = bigrams[getDecimalValue(first)][getDecimalValue(second)];
		return smooth(frequency, getDecimalValue(first));
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
	 * Evaluates the probability of the given String belonging to the language
	 * @param s The supplied string
	 * @return 
	 * @throws CharacterSetException If the supplied string contains an invalid character
	 */
	protected Double evaluate(String s) {
		ArrayList<Character> tokens = Tokenizer.tokenize(s);
		
		double probabilitySum = 0.0;
		
		for(int i = 0; i < tokens.size() - 1; i++) {
			
			char token1 = tokens.get(i);
			char token2 = tokens.get(i+1);
			int token1D = getDecimalValue(token1)
				, token2D = getDecimalValue(token2);
			
			
			if(token1 != ' ' && token2 != ' ') {
				
				probabilitySum += getValue(token1, token2);
				System.out.println("BIGRAM: " + token1 + token2);
				System.out.println("P(" + token1 + ", " + token2 + ") = " + probability(bigrams[token1D][token2D], token1D));
				System.out.println("log prob of sequence so far: " + probabilitySum);
				System.out.println();
			}
		}
		
		return probabilitySum;	
	}
	
	/**
	 * Retrieves the probability of the of the bigram occurring
	 * @param val the frequency of the bigram
	 * @return
	 */
	private Double probability(Double val, int row) {

		double numerator = val + SMOOTHING;
		double denominator = getTotal(row) + (SMOOTHING * CHAR_SET_SIZE * CHAR_SET_SIZE);
		return numerator / denominator;
	}
	
	/**
	 * Uses ADD-0.5 smoothing to prevent unseen probabilities from outputting zero & 
	 * computes the base10 logarithm of the output to avoid underflow
	 * @param val the value to smooth
	 * @return the smoothed value
	 */
	private Double smooth(Double val, int row) {
		
		return Math.log10(probability(val, row));
	}
	
	/**
	 * Gets the total frequency for a row of bigrams
	 * @param row 
	 */
	private int getTotal(int row) {
		
		int count = 0;
		for(int i = 0; i < bigrams[row].length; i++) {
			count += bigrams[row][i];
		}
		return count;
	}
}
