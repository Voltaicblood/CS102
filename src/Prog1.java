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
		} catch (FileNotFoundException caughtException) {
			System.out.println("File not at file name location");
			System.exit(0);
		}
		Scanner in = new Scanner(System.in); //scanner for keyboard input
		String userInput; //string based on input from Scanner in
		//begins printing lines for user input
		System.out.println("Welcome to the CS-102 Radio Station Database Program!");
		final String SEARCH_CALLSIGN = "1";
		final String SEARCH_FREQUENCY = "2";
		final String SEARCH_FORMAT = "3";
		final String PRINT_ALL = "4";
		final String ADD_STATION = "5";
		final String EXIT = "9";
		String band;  //String that holds inputed frequency band
		while ( true ){
			System.out.println("\nCurrent available commands:\n"
					+ "1 --> Search for a call sign\n"
					+ "2 --> Search for a frequency\n"
					+ "3 --> Search for a format\n"
					+ "4 --> Print all records\n"
					+ "5 --> Add a station\n"
					+ "9 --> Exit\n\nYour choice?");
			//checks for user input
			userInput = in.next();
			//depending on input entered, calls method to complete function
			switch (userInput){
			case SEARCH_CALLSIGN:
				System.out.println("Enter call sign:");
				inputDatabase.callSignSearch(in.next());
			break;
			case SEARCH_FREQUENCY:
				System.out.println("Enter frequency band:");
				band = in.next();
				try{
					//checks band type to determine which type to call for
					if (band.equalsIgnoreCase("FM")){
						System.out.println("Enter frequency:");
						inputDatabase.frequencySearch(in.nextDouble(), "FM");
					}
					else if (band.equalsIgnoreCase("AM")){
						System.out.println("Enter frequency:");
						inputDatabase.frequencySearch(in.nextInt(), "AM");
					} else {
						System.out.println("Invalid band.");
						throw new InputMismatchException();
					}
				} catch (InputMismatchException caughtException){
					System.out.println("Incorrect input.");
				}
			break;
			case SEARCH_FORMAT:
				System.out.println("Enter format keyword:");
				inputDatabase.formatSearch(in.next());
			break;
			case PRINT_ALL:
				inputDatabase.printAll();
			break;
			case ADD_STATION:
				try{
				System.out.println("Enter call sign:");
				String callSign = in.next();
					if (callSign.length() > 4){
						System.out.println("Callsign is too long.");
						throw new InputMismatchException();
					}
					else if (callSign.length() < 3){
						System.out.println("Callsign is too short.");
						throw new InputMismatchException();
					}
					System.out.println("Enter frequency band:");
					band = in.next();
					if (!(band.equalsIgnoreCase("FM") ||  band.equalsIgnoreCase("AM"))){
						System.out.println("Invalid band.");
						throw new InputMismatchException();
					} 
					else if (inputDatabase.checkCallSignDuplicate(callSign, band)){
						System.out.println("Duplicate callsign in " + band.toUpperCase()
												+ " band.");
					}
					System.out.println("Enter frequency:");
					Number frequency;
					try{
						if (band.equalsIgnoreCase("FM"))
							frequency = in.nextDouble();
						else
							frequency = in.nextInt();
					} catch (InputMismatchException caughtException) {
						System.out.println("Invalid frequency format.");
						throw caughtException;
					}
					System.out.println("Enter home:");
					String home = in.next();
					System.out.println("Enter format:");
					String format = in.next();
					inputDatabase.addStation(callSign, band, frequency, home, format);
				} catch (InputMismatchException caughtException){
					System.out.print(" Returning to menu.");
				}
			break;
			case EXIT:
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