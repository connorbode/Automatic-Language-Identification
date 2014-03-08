package app;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			Bigram english = new Bigram(new File("files/english.txt"));
			Bigram french = new Bigram(new File("files/french.txt"));
			String test = "Le printemps se fait attendre.";
			double probEnglish = english.evaluate(test);
			double probFrench = french.evaluate(test);
			System.out.println("Probability English: " + probEnglish);
			System.out.println("Probability French: " + probFrench);
			System.out.println("The text is " + (probEnglish > probFrench ? "english" : "french"));
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
