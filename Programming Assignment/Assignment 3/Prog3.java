/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 3                                             */
/* Prog3 class: obtains input file and passes it on to Database class     */
/**************************************************************************/

import java.io.*;
import java.util.*;

public class Prog3 {
	
	/**************************************************************/ 
	/* Method: main									  
	/* Purpose: grabs file from command line argument, then interacts with database
	/* 		to search and print databases based on input
	/* Parameters: 
	/*		String [] args:		arguments from the command line						  
	/* Returns: none							  
	/**************************************************************/
	public static void main(String [] args){
		//name of file from arg[1]
		String inputFileName = args[0];
		 //file retrieved based on inputFileName
		File inputFile = new File(inputFileName);
		//database created based on inputed file
		Database inputDatabase = null; 
		try {
			inputDatabase = new Database(inputFile);
		} catch (FileNotFoundException caughtException) {
			System.out.println("File not at file name location");
			System.exit(0);
		}
		//scanner for keyboard input
		Scanner in = new Scanner(System.in);
		//string based on input from Scanner in
		String userInput; 
		//begins printing lines for user input
		System.out.println("Welcome to the CS-102 Radio Station Database Program!");
		//constant for case to search the callsign
		final String SEARCH_CALLSIGN = "1";
		//constant for case to search the frequency
		final String SEARCH_FREQUENCY = "2";
		//constant for case to search the format
		final String SEARCH_FORMAT = "3";
		//constant for case to print all stations
		final String PRINT_ALL = "4";
		//constant for case to add a new station
		final String ADD_STATION = "5";
		//constant for case to remove an existing station
		final String DELETE_STATION = "6";
		//constant for case to exit the application
		final String EXIT = "9";
		String band;  //String that holds inputed frequency band
		while ( true ){
			System.out.println("\nCurrent available commands:\n"
					+ "1 --> Search for a call sign\n"
					+ "2 --> Search for a frequency\n"
					+ "3 --> Search for a format\n"
					+ "4 --> Print all records\n"
					+ "5 --> Add a station\n"
					+ "6 --> Remove a station\n"
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
				//callsign used in new station from user input
				String callSign = in.next();
					//error checking on callsign length
					if (callSign.length() > 4){
						System.out.println("Callsign is too long.");
						throw new InputMismatchException();
					}
					else if (callSign.length() < 3){
						System.out.println("Callsign is too short.");
						throw new InputMismatchException();
					}
					System.out.println("Enter frequency band:");
					//band used in new station from user input
					band = in.next();
					//error checking to make sure band is FM or AM
					if (!(band.equalsIgnoreCase("FM") ||  band.equalsIgnoreCase("AM"))){
						System.out.println("Invalid band.");
						throw new InputMismatchException();
					} 
					//error checking to make sure the callsign isn't a duplicate
					else if (inputDatabase.checkCallSignDuplicate(callSign, band)){
						System.out.println("Duplicate callsign in " + band.toUpperCase()
												+ " band.");
						throw new InputMismatchException();
					}
					System.out.println("Enter frequency:");
					//frequency used in new station from user input
					Number frequency;
					//grabs frequency as double or int based on band
					try{
						if (band.equalsIgnoreCase("FM"))
							frequency = in.nextDouble();
						else
							frequency = in.nextInt();
					} catch (InputMismatchException caughtException) {
						//throws an error is frequency is not an int or double
						System.out.println("Invalid frequency format.");
						throw caughtException;
					}
					System.out.println("Enter home:");
					//home used in new station from user input
					String home = in.next();
					System.out.println("Enter format:");
					//format used in new station from user input
					String format = in.next();
					inputDatabase.addStation(callSign, band, frequency, home, format);
				} catch (InputMismatchException caughtException){
					//any thrown errors will return you to the menu
					System.out.print("Returning to menu.");
				}
			break;
			case DELETE_STATION:
				try{
					System.out.println("Enter call sign:");
					//call sign of station to be removed
					String callSign = in.next();
					//error checking on call sign length
					if (callSign.length() > 4){
						System.out.println("Callsign is too long.");
						throw new InputMismatchException();
					}
					else if (callSign.length() < 3){
						System.out.println("Callsign is too short.");
						throw new InputMismatchException();
					}
					System.out.println("Enter frequency band:");
					//band of station to be removed
					band = in.next();
					//error checking to make sure band is FM or AM
					if (!(band.equalsIgnoreCase("FM") ||  band.equalsIgnoreCase("AM"))){
						System.out.println("Invalid band.");
						throw new InputMismatchException();
					}
					//searches and removes the station from the specified band
					inputDatabase.removeStation(callSign, band);
				} catch (InputMismatchException caughtException){
					//any thrown errors will return you to the menu
					System.out.print("Returning to menu.");
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