package vkRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BaseMenu extends PMenu {

	BaseMenu(File path, Scanner scan) {
		super(path, scan);
	}

	@Override
	public boolean loopmenu() {
		//start loop by giving options
		boolean loop = true; //default output
		displayOptions();
		
		//collect user input, first char is unique, switch to handle logic
		switch(getChoice()) {
		case 'l': { //list contents
			listContents();
			break;
		}
		case 'm': { //manage files sub menu
			SubMenu sm = new SubMenu(fp, s);
			while(sm.loopmenu()){}
			break;
		}
		case 'c': { //close application
			loop = false; //exit to main and close
			break;
		}
		default: { //invalid entry
			System.out.println("Invalid entry, please try again\r\n");
		}}
		return loop;
	}

	
	@Override
	public void displayOptions() {
		System.out.println("\r\nPlease type a command option and press Enter:\r\n"
				+ "List Contents\r\n"
				+ "Manage Files\r\n"
				+ "Close Application");
	}
	
	public void listContents() {
		//collect contents as array list and sort
		ArrayList<String> fileNames = collectContents();
		Collections.sort(fileNames);

		//print list
		System.out.println("\r\nRepository Contents:");
		for (String n : fileNames) { System.out.println(n); }
	}
	
}
