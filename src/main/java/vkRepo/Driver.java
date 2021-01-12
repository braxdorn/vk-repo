package vkRepo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		//printing title and dev info to console		
		System.out.println(" _      ____   _____ _  ________ _____  __  __ ______\r\n"
				+ "| |    / __ \\ / ____| |/ /  ____|  __ \\|  \\/  |  ____|     by Braxton Dorn\r\n"
				+ "| |   | |  | | |    | ' /| |__  | |  | | \\  / | |__     ___ __  _Jan. 2021\r\n"
				+ "| |   | |  | | |    |  < |  __| | |  | | |\\/| |  __|   / __/ _ \\| '_ ` _ \\ \r\n"
				+ "| |___| |__| | |____| . \\| |____| |__| | |  | | |____ | (_| (_) | | | | | |\r\n"
				+ "|______\\____/ \\_____|_|\\_\\______|_____/|_|  |_|______(_)___\\___/|_| |_| |_|\r\n");

		//create file object with hard-coded destination folder
		File fp = new File("c:/repository/");
		//create directory if it does not exist
		if (!fp.exists()) { 	fp.mkdirs();	}
		
		//open an input scanner
		Scanner s = new Scanner(System.in);
		
		//display the base menu
		boolean loop = true;
		while (loop){ loop = basemenu(fp, s); }

		//thanks and goodbye, then close app
		System.out.println("\r\n\r\nThank you for using this application. See you next time!");

		//cleanup
		s.close();
	}
	
	
	//base menu method 
		//exists within a while loop in main for persistence in case users wish
		//to continue using the application after completing a single task
	public static Boolean basemenu(File fp, Scanner s) {
		
		//display menu
		System.out.println("\r\nPlease type a command option and press Enter:\r\n"
				+ "List Contents\r\n"
				+ "Manage Files\r\n"
				+ "Close Application");
		
		//collect user input, first char is unique
		char selection = '-'; //defaulted in case user hits enter with no input
		String input = s.nextLine();
		if(input != "") {
			selection = input.charAt(0);
		}
		
		boolean loop = true; //loop by default
		
		//response logic
		switch(selection) {
		
		case 'L': case 'l':	{ //list contents
			//scan directory to array, convert to array list, and sort
			File[] fList = fp.listFiles();
			ArrayList<String> fileNames = new ArrayList<>();
			for (File f : fList) { fileNames.add(f.getName()); }
			Collections.sort(fileNames);

			//print list
			System.out.println("\r\nRepository Contents:");
			for (String n : fileNames) { System.out.println(n); }
			System.out.println(); //legibility formatting
			break;
		}
		case 'M': case 'm': {//manage files sub menu
			while(submenu(fp, s)){}
			break;
		}
		case 'C': case 'c': {//close application
			loop = false; //exit to main and close
			break;
		}
		default: {//invalid entry
			System.out.println("Invalid entry, please try again\r\n");
			loop = true;
		}}
		
		return loop;
	}
	
	//file management sub-menu method 
			//exists within a while loop in base menu for persistence in case users wish
			//to continue using file management after completing a single task
	public static Boolean submenu(File fp, Scanner s) {
		
		//display menu
		System.out.println("\r\nPlease type a command option and press Enter:\r\n"
				+ "Add a file\r\n"
				+ "Delete a file\r\n"
				+ "Search for a file\r\n"
				+ "Return to main menu");
		
		//collect user input, first char is unique
		char selection = '-';
		String input = s.nextLine();
		if (input != "") {
			selection = input.charAt(0);
		}
		
		boolean loop = true;
		
		//response logic
		switch(selection) {
		
		case 'A': case 'a': { //Add file
			boolean flag = true; //sub-loop flag
			do {
				//request file and collect input
				System.out.println("\r\nWhat is the address of the file you wish to upload?");
				File source = new File(s.nextLine());
				File destination = new File(fp.toString() +"\\" + source.getName());
				
				//print copying message
				System.out.println("\r\nAttempting to copy file...");
				
				
				try {
					//attempt to copy file, exception will catch before success response if failed
					Files.copy(source.toPath(), destination.toPath());
					System.out.println("File copied sucessfully!");
					flag = false; //do not repeat
					
				} catch(Exception e) { 
					//tell user it failed listing common reasons
					//ask if they would like to try again, collect response, set loop flag accordingly
					System.out.println("Unable to copy file, file not found or already exists.\r\n"
							+ "Would you like to try again?");
					char response = s.nextLine().charAt(0);
					if(response == 'n' || response == 'N') { flag = false; } //no, break loop
				}
			} while(flag == true);
			
			break; //repeat sub-menu
		}
		
		case 'D': case 'd': { //Delete file
			boolean flag = true; //sub-loop flag
			do {
				//request file name and collect input
				System.out.println("\r\nWhat is the name of the file you wish to delete?");
				String tinput = s.nextLine();
				File target = new File(fp.toString() +"\\" + tinput);
				
				//print deleting message
				System.out.println("Attempting to delete file...");
				
				try {
					//attempt to delete file, exception will catch before success response if failed
					Files.delete(target.toPath());
					System.out.println("File deleted successfully!");
					flag = false; //do not repeat
				} catch(Exception e) {
					//tell user it failed listing common reasons
					System.out.println("Unable to delete file, file not found or protected.\r\n"
							+ "Would you like to try again?");
					char response = s.nextLine().charAt(0);
					if(response == 'n' || response == 'N') { flag = false; } //no, break loop
				}
			} while(flag == true);
			
			break; //repeat sub-menu
		}
		case 'S': case 's': { //search for file
			boolean flag = true; //sub-loop flag
			do {
				//request file name and collect input
				System.out.println("\r\nWhat is the name of the file you wish to locate?");
				String tinput = s.nextLine();
				File target = new File(fp.toString() +"\\" + tinput);
				
				//print deleting message
				System.out.println("Attempting to locate file...");

				//check and report results
				if(target.exists()) {
					System.out.println("File " + target.getName() + " exists in repository!");
					flag = false; //do not repeat
				} else {
					System.out.println("Unable to locate file.\r\n"
							+ "Would you like to search again?");
					char response = s.nextLine().charAt(0);
					if(response == 'n' || response == 'N') { flag = false; } //no, break loop
				}
			} while(flag == true);
			
			break; //repeat sub-menu
		}
		case 'R': case 'r': { //return to main menu
			loop = false; //return to main menu
			break;
		}
		default: { //invalid entry
			System.out.println("Invalid entry, please try again\r\n");
			//repeat sub-menu
		}}
		
		return loop;
	}
}
