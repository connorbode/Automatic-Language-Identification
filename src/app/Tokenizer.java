package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Tokenizer {
	
	/* ===============================================
		CLASS VARIABLES
	   =============================================== */
	
	private static String[] PERIOD_EQUIVALENT = {
		".", "--", "!", "?", System.getProperty("line.separator") + System.getProperty("line.separator")
	};
	
	private static String[] REMOVABLE = {
		"-", "'", "\""
	};
	
	private static String[] BREAK = {
		":", ","
	};
	
	private static String[] SPACE_EQUIVALENT = {
		System.getProperty("line.separator")
	};
	
	
	
	/* ===============================================
		METHODS
   	   =============================================== */

	public static String tokenize(String string)
		throws CharacterSetException {
		
		// lowercase the string
		string = string.toLowerCase();
		
		// replace each period equivalent
		for(int i = 0; i < PERIOD_EQUIVALENT.length; i++) {
			string = string.replace(PERIOD_EQUIVALENT[i], " . ");
		}
		
		// remove all removable
		for(int i = 0; i < REMOVABLE.length; i++) {
			string = string.replace(REMOVABLE[i], " ");
		}
		
		// replace all breaks with semicolons
		for(int i = 0; i < BREAK.length; i++) {
			string = string.replace(BREAK[i], " ; ");
		}
		
		// replace all space equivalent with spaces
		for(int i = 0; i < SPACE_EQUIVALENT.length; i++) {
			string = string.replace(SPACE_EQUIVALENT[i],  " ");
		}
		
		// split the string by spaces
		String[] split = string.split(" ");
		
		// create an arraylist from the split
		ArrayList<String> splitList = new ArrayList<String>();
		for(int i = 0; i < split.length; i++) {
			if(!split[i].equals("")) {
				splitList.add(split[i]);
			}
		}
		
		// remove sequential periods
		for(int i = 0; i < splitList.size()-1; i++) {
			if(splitList.get(i).equals(".")) {
				if(splitList.get(i+1).equals(".")) {
					splitList.remove(i);
					i = i-1;
				}
			}
		}
		
		// print
		for(int i = 0; i < splitList.size()-1; i++) {
			
			System.out.println(splitList.get(i));
		}
		
		String formatted = ". " + string;
		
		return formatted;
	}
}
