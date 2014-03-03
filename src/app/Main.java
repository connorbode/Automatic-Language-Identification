package app;

import java.io.File;

import lib.FileOps;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			System.out.print(Tokenizer.tokenize(FileOps.readTextFromFile(new File("files/english.txt"))));
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}