package vkRepo;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SubMenu extends PMenu {

	SubMenu(File path, Scanner scan) {
		super(path, scan);
	}

	@Override
	public boolean loopmenu() {
		//start loop by giving options
		boolean loop = true; //default output
		boolean flag = true; //sub-loop flag for repetition
		displayOptions();
		
		//collect user input, first char is unique, switch to handle logic
		switch(getChoice()) {
		case 'a': { //Add file
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
				
				//Handle errors by presenting issue to user
				} catch(FileAlreadyExistsException fae) {
					System.out.println("Unable to copy file: file with the same name already exists in directory.");
				} catch (SecurityException se) {
					System.out.println("Unable to copy file: please check security privileges.");
				} catch(Exception e) {
					System.out.println("An unknown error has occurred, please check the source file address.");
				} 
				//ask to repeat the attempt
				finally { flag = tryAgainPrompt(); 
				}
			} while(flag == true);
			break; //repeat sub-menu
		}
		
		case 'd': { //Delete file
			do {
				//request file name and collect input
				System.out.println("\r\nWhat is the name of the file you wish to delete?");
				String tinput = s.nextLine();
				ArrayList<String> contents = collectContents();
				
				//print searching message
				System.out.println("Locating file...");
				//not a lambda expression, may implement later
				ArrayList<String> matches = new ArrayList<String>();
				for( String f : contents ) {
					if (f.matches(tinput+".*")) { matches.add(f); }
				}
				int results = matches.size();
				
				//check and report results
				if (results == 0) {
					System.out.println("Unable to locate file.");
				} else if (results > 1) {
					System.out.println("Multiple matching file names:");
					for (String f : matches) { System.out.println(f); }
				} else if(results == 1) {
					System.out.println("File " + matches.get(0) + " located. Would you like to delete this file?");
					//see if user has located the file they want, and follow through as expected
					if (getChoice() == 'y') {
						try {
							//attempt to delete file, exception will catch before success response if failed
							File target = new File(fp.toString() +"\\" + matches.get(0));
							Files.delete(target.toPath());
							System.out.println("File deleted successfully!");
						
						//handle errors by presenting issue to user
						} catch (NoSuchFileException nsfe) {
							System.out.println("Unable to delete file: file not found.");
						} catch (SecurityException se) {
							System.out.println("Unable to delete file: please check security privileges.");
						} catch(Exception e) {
							System.out.println("An unknown error has occurred, please try again.");
						}
					}
				}
				flag = tryAgainPrompt();
			} while(flag == true);
			break; //repeat sub-menu
		}
		
		case 's': { //search for file
			do {
				//request file name and collect input
				System.out.println("\r\nWhat is the name of the file you wish to find?");
				String tinput = s.nextLine();
				ArrayList<String> contents = collectContents();
				
				//print searching message
				System.out.println("Attempting to locate file...");
				
				//not a lambda expression, may implement later
				ArrayList<String> matches = new ArrayList<String>();
				for( String f : contents ) {
					if (f.matches(tinput+".*")) { matches.add(f); }
				}
				int results = matches.size();
				
				//check and report results
				if(results == 1) {
					System.out.println("File " + matches.get(0) + " exists in repository!");
				} else if (results == 0){
					System.out.println("Unable to locate file.");
				} else if (results > 1) {
					System.out.println("Multiple matching file names:");
					for (String f : matches) { System.out.println(f); }
				}
				flag = tryAgainPrompt();
			} while(flag == true);
			break; //repeat sub-menu
		}
		
		case 'r': { //return to main menu
			loop = false; //return to main menu
			break;
		}
		
		default: { //invalid entry
			System.out.println("Invalid entry, please try again\r\n");
			//repeat sub-menu
		}}
		
		return loop;
	}

	
	@Override
	public void displayOptions() {
		//display menu
		System.out.println("\r\nPlease type a command option and press Enter:\r\n"
				+ "Add a file\r\n"
				+ "Delete a file\r\n"
				+ "Search for a file\r\n"
				+ "Return to main menu");		
	}
	
	public boolean tryAgainPrompt() {
		System.out.println("Would you like to try again?");
		if( getChoice() != 'y') { return false; }
		return true;
	}
}