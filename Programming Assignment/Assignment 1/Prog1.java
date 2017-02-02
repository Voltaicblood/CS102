/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 1                                               */
/* Prog1 class: obtains input file and passes it on to Database class     */
/**************************************************************************/

import java.io.*;
import java.util.*;

public class Prog1 {
	
	/**************************************************************/ 
	/* Method: main									  
	/* Purpose: grabs file from command line argument, then interacts with database
	/* 		to search and print databases based on input
	/* Parameters: 
	/*		String [] args:		arguments from the command line						  
	/* Returns: none							  
	/**************************************************************/
	public static void main(String [] args){
		String inputFileName = args[0]; //name of file from arg[1]
		File inputFile = new File(inputFileName); //file retrieved based on inputFileName
		Database inputDatabase = null; //database created based on inputed file
		try {
			inputDatabase = new Database(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("File not at file name location");
			System.exit(0);
		}
		Scanner in = new Scanner(System.in); //scanner for keyboard input
		String userInput; //string based on input from Scanner in
		//begins printing lines for user input
		System.out.println("Welcome to the CS-102 Radio Station Database Program!");
		while ( true ){
			System.out.println("\nCurrent available commands:\n1 --> Search for a call sign\n"
			+ "2 --> Search for a frequency\n3 --> Search for a format\n"
			+ "4 --> Print all records\n9 --> Exit\n\nYour choice?");
			//checks for user input
			userInput = in.next();
			//depending on input entered, calls method to complete function
			switch (userInput){
			case "1":
				System.out.println("Enter call sign:");
				inputDatabase.callSignSearch(in.next());
			break;
			case "2":
				System.out.println("Enter frequency band:");
				String band = in.next(); //String that holds inputed frequency band
				try{
					//checks band type to determine which type to call for
					if (band.equalsIgnoreCase("FM")){
						System.out.println("Enter frequency:");
						inputDatabase.frequencySearch(in.nextDouble(), "FM");
					}
					else if (band.equalsIgnoreCase("AM")){
						System.out.println("Enter frequency:");
						inputDatabase.frequencySearch(in.nextInt(), "AM");
					} else System.out.println("Invalid band.");
				} catch (InputMismatchException e){
					System.out.println("Incorrect input.");
				}
			break;
			case "3":
				System.out.println("Enter format keyword:");
				inputDatabase.formatSearch(in.next());
			break;
			case "4":
				inputDatabase.printAll();
			break;
			case "9":
				//terminates the program is 9 is entered
				System.exit(0);
			break;
			default:
				//if anything other than the listed commands are entered
				//prints error message and returns to top
				System.out.println("Not a correct command.");
			}
			
		}
	}
}