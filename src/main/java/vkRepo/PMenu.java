package vkRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class PMenu {
	
	File fp;
	Scanner s;
	
	PMenu(File path, Scanner scan){
		fp = path;
		s = scan;
	}
	
	public abstract boolean loopmenu();
	public abstract void displayOptions();
	
	public ArrayList<String> collectContents(){
		//scan directory to array, convert to array list, and sort
		File[] fList = fp.listFiles();
		ArrayList<String> fileNames = new ArrayList<>();
		for (File f : fList) { fileNames.add(f.getName()); }
		return fileNames;
	}
	
	public char getChoice() {
		char selection = '-'; //defaulted in case user hits enter with no input
		String input = s.nextLine().toLowerCase();
		if(input != "") {
			selection = input.charAt(0);
		}
		return selection;
	}
}
	

