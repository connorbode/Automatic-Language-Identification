package drivers;

import java.io.File;
import java.util.Scanner;
import app.LanguageIdentifier;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		LanguageIdentifier li = new LanguageIdentifier();
		
		// TRAIN! (read in the training text and create the bigrams)
		try {
			li.train("english",  new File("files/english.txt"));
			li.train("french", new File("files/french.txt"));
			li.train("german", new File("files/german.txt"));
		} catch(Exception e) {
			System.out.println("Error in training text: " + e);
		}
		
		// PERFORM! (wait for input and decide what language it is)
		try {
			while(true) {
				System.out.println("-------------------------------------");
				System.out.println("Feed me text!!");
				System.out.println("-------------------------------------");
				String input = in.nextLine();
				System.out.println("-------------------------------------");
				String output = li.evaluate(input);
				System.out.println("-------------------------------------");
				System.out.println("Mmmm.. That text was " + output);
				System.out.println("-------------------------------------");
			}
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
