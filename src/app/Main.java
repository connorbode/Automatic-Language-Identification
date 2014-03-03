package app;

import java.io.File;

import lib.FileOps;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			Bigram bigram = new Bigram(new File("files/english.txt"));
			System.out.println();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
}