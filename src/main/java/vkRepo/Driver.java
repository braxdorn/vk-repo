package vkRepo;

import java.io.File;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
	
		//printing title and developer info to console, looks weird here but looks better in the console
		System.out.println(" _      ____   _____ _  ________ _____  __  __ ______\r\n"
				+ "| |    / __ \\ / ____| |/ /  ____|  __ \\|  \\/  |  ____|      by Braxton Dorn\r\n"
				+ "| |   | |  | | |    | ' /| |__  | |  | | \\  / | |__     ___ __   _Jan. 2021\r\n"
				+ "| |   | |  | | |    |  < |  __| | |  | | |\\/| |  __|   / __/ _ \\| '_ `'_ \\ \r\n"
				+ "| |___| |__| | |____| . \\| |____| |__| | |  | | |____ | (_| (_) | | | | | |\r\n"
				+ "|______\\____/ \\_____|_|\\_\\______|_____/|_|  |_|______(_)___\\___/|_| |_| |_|\r\n");

		//create file object with hard-coded destination folder
		File fp = new File("c:/repository/");
		//create directory if it does not exist
		if (!fp.exists()) { 	fp.mkdirs();	}
		
		//open an input scanner
		Scanner s = new Scanner(System.in);
		
		//generate and run the base menu
		BaseMenu bm = new BaseMenu(fp, s);
		while (bm.loopmenu()){ }

		//thanks and goodbye, then close application
		System.out.println("\r\n\r\nThank you for using this application. See you next time!");

		//cleanup
		s.close();
	}
}