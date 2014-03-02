package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileOps {

	/**
	 * Reads text from a file. Heavily based on code found at http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
	 * @param file the file
	 * @return the String of text in the file
	 */
	public static String readTextFromFile(File file)
		throws FileNotFoundException, IOException {
		
		BufferedReader br  = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		
		while(line != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
			line = br.readLine();
		}
		
		return sb.toString();
	}
}
