package app;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			Bigram bigram = new Bigram("english", new File("files/english.txt"));
			System.out.println("test");
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
