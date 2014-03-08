package app;

import java.util.ArrayList;

public abstract class Tokenizer {
	
	/* ===============================================
		CLASS VARIABLES
	   =============================================== */

	
	private static String[] REMOVABLE = {
		"-", "'", "\"", ":", ",", ".", "--", "!", "?"
	};
	
	private static String[] SPACE_EQUIVALENT = {
		System.getProperty("line.separator"), "-"
	};
	
	private static char[] ACCEPTABLE_CHARS = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't'
			, 'u', 'v', 'w', 'x', 'y', 'z', ' '
	};
	
	
	
	/* ===============================================
		METHODS
   	   =============================================== */

	public static ArrayList<Character> tokenize(String string)
		throws CharacterSetException {
		
		// lowercase the string
		string = string.toLowerCase();
		
		// remove all removable
		for(int i = 0; i < REMOVABLE.length; i++) {
			string = string.replace(REMOVABLE[i], " ");
		}
		
		// replace all space equivalent with spaces
		for(int i = 0; i < SPACE_EQUIVALENT.length; i++) {
			string = string.replace(SPACE_EQUIVALENT[i],  " ");
		}
		
		ArrayList<Character> acceptable = new ArrayList<Character>();
		for(char acc : ACCEPTABLE_CHARS) {
			acceptable.add(acc);
		}
		
		ArrayList<Character> splitList = new ArrayList<Character>();
		
		for(int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if(acceptable.contains(currentChar))
				splitList.add(currentChar);
		}
		
		return splitList;
	}
}
