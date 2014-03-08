package app;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			LanguageIdentifier li = new LanguageIdentifier();
			li.train("english",  new File("files/english.txt"));
			li.train("french", new File("files/french.txt"));
			String best = li.evaluate("Le printemps se fait attendre.");
			System.out.println("the sentence is " + best);
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
