/***********************************************************************************/
/* Jacob Austin                                                           		   */
/* Login ID: aust8558                                                     		   */ 
/* CS-102, Winter 2017                                                    		   */
/* Programming Assignment 1                                               		   */
/* Database class: reads and manipulates a Linked Lists containing station objects */
/***********************************************************************************/

import java.util.*;
import java.io.*;


public class Database implements DatabaseInterface {
	
	//list of used to store FM stations
	private LinkedList fmList = new LinkedList(); 
	//list used to store AM stations
	private LinkedList amList = new LinkedList();
	//constant identifies FM band
	private final int FM_IDENTIFIER = 0;
	//constant identifies AM band
	private final int AM_IDENTIFIER = 1;
	
	
	//database constructor from file input
	public Database(File input) throws FileNotFoundException{
		//scanner from used to read file lines
		Scanner reader = new Scanner(input);
		try{
			//throws an exception if list is empty
			if (!reader.hasNextLine())
				throw new IllegalStateException();
			//creates stations as long as there are more station lines
			while(reader.hasNextLine()){
				//current read line in file
				String currentLineInput = reader.nextLine(); 
				//Scanner used to read each item in a line
				Scanner stationReader = new Scanner(currentLineInput).useDelimiter("/");
				//station to be added from line
				Station tempStation = new Station();
				//temporary call sign holder
				String tempCallSign = stationReader.next();
				//temporary band holder
				String tempBand = stationReader.next();
				//temporary frequency holder
				Number tempFrequency;
				//checks for bad input, then assigns values to variables of new Station
				if (tempCallSign.length() > 4 || tempCallSign.length() < 3)
					throw new IllegalArgumentException();
				if (!tempBand.equals("AM") && !tempBand.equals("FM"))
					throw new IllegalArgumentException();
				//corrects frequency based on AM or FM
				if (tempBand.equals("AM")){
					tempFrequency = stationReader.nextInt() * 10;
					amList.add(0 , tempStation);
				} else {
					tempFrequency = stationReader.nextDouble() / 10;
					fmList.add(0, tempStation);
				}
				//finishes setting rest of variables
				tempStation.setCallSign(tempCallSign);
				tempStation.setBand(tempBand);
				tempStation.setFrequency(tempFrequency);
				tempStation.setHome(stationReader.next());
				tempStation.setFormat(stationReader.next());
			}
			//sorts lists after adding in all stations
			fmList.alphabeticalSort();
			amList.alphabeticalSort();
		} catch (IllegalArgumentException caughtException){
			//bad data types
			System.out.println("Invalid data types in list of stations. Aborting");
			throw caughtException;
		} catch (IllegalStateException caughtException){
			//empty file, but can still continue
			System.out.println("Station file is empty. Continuing to menu.");
		} catch (NoSuchElementException caughtException){
			//not enough parameters in each line
			System.out.println("Bad formatting in station file. Aborting.");
			throw caughtException;
		}
	}
	
	/**************************************************************/ 
	/* Method: addStation									  
	/* Purpose: adds a station to a linked list 
	/* Parameters: 
	/*		String callSign:		callSign for new station
	/* 		String band:			band for new station
	/*		Number frequency:		frequency for new station
	/*		String home:			home for new station
	/*		String format:			format for new station
	/* Returns: none							  
	/**************************************************************/
	public void addStation(String callSign, String band, Number frequency, 
							String home, String format) {
		//temporary station holder before being added to a list
		Station tempStation = new Station(callSign, band, frequency, home, format);
		//if AM band, add to AM list
		if (band.equalsIgnoreCase("AM")){
			amList.add(0, tempStation);
			//sort after adding
			amList.alphabeticalSort();
		//if FM band, add to FM list
		} 
		else if (band.equalsIgnoreCase("FM")) {
			fmList.add(0, tempStation);
			//sort after adding
			fmList.alphabeticalSort();
		}
	}
	
	
	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches each list for inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/* Returns: none							  
	/**************************************************************/
	public void callSignSearch(String inputCallSign) {
		//keeps track if a station was found
		boolean found;
		//search through both lists, and print the stations that match
		found = (callSignSearch(inputCallSign, fmList.getHead())
					| callSignSearch(inputCallSign, amList.getHead()));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches for and prints	stations with the inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/*		Node current:				current Node to search on 
	/* Returns: boolean:				if a station was found or not							  
	/**************************************************************/
	private boolean callSignSearch(String inputCallSign, Node current){
		//return false if reached the end of the list
		if (current == null)
			return false;
		//current Station being searched
		Station currentStation = (Station) (current.getDatum());
		//call sign of current station
		String currentStationCallSign = currentStation.getCallSign();
		//compares inputed value with the current value
		if (currentStationCallSign.equals(inputCallSign)){
			System.out.println(currentStation.toString());
			return true;
		}
		//otherwise, search the next node
		return callSignSearch(inputCallSign, current.getNext());
	}
	
	/**************************************************************/ 
	/* Method: checkCallSignDuplicate									  
	/* Purpose: checks inputed call sign with a certain list for duplication
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/*		String band:				which list to search 
	/* Returns: boolean:				true if there is a duplicate						  
	/**************************************************************/
	public boolean checkCallSignDuplicate(String inputCallSign, String band){
		//use to keep track if there is a duplicate
		boolean found;
		//if FM band, check FM list
		if (band.equalsIgnoreCase("FM")){
			found = checkCallSignDuplicate(inputCallSign, fmList.getHead());
		}
		//if AM band, check AM list
		else if (band.equalsIgnoreCase("AM")){
			found = checkCallSignDuplicate(inputCallSign, amList.getHead());
		} else {
			//an illegal band should not reach here,
			//but just in case, throws an exception
			System.out.print("Illegal band name reached callsign duplicate method.");
			throw new InputMismatchException();
		}
		return found;
	}
	
	/**************************************************************/ 
	/* Method: checkCallSignDuplicate									  
	/* Purpose: checks list for inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/*		Node current:				current Node being searched
	/* Returns: boolean:				true if there is a duplicate						  
	/**************************************************************/
	private boolean checkCallSignDuplicate(String inputCallSign, Node current){
		//return false at end of list
		if (current == null)
			return false;
		//current station being searched
		Station currentStation = (Station) (current.getDatum());
		//current call sign being checked
		String currentStationCallSign = currentStation.getCallSign();
		//if the current call sign matches inputed call sign,  return true
		if (currentStationCallSign.equals(inputCallSign)){
			return true;
		}
		//otherwise, search the next node
		return callSignSearch(inputCallSign, current.getNext());
	}
	
	/**************************************************************/ 
	/* Method: frequencySearch								  
	/* Purpose: searches one of the lists for inputed frequency
	/* Parameters: 
	/*		Number inputFrequency:		input to search for in the list
	/*		String band:				which list to search
	/* Returns: none						  
	/**************************************************************/
	public void frequencySearch(Number inputFrequency, String band) {
		//keeps track if the frequency was found
		boolean found;
		//if FM band, search FM list
		if (band.equals("FM")){
			found = frequencySearch(inputFrequency, fmList.getHead(), FM_IDENTIFIER, false);
		}
		//if AM band, search AM list
		else if (band.equals("AM")){
			found = frequencySearch(inputFrequency, amList.getHead(), AM_IDENTIFIER, false);
		} else {
			//an illegal band should not reach here,
			//but just in case, throws an exception
			System.out.print("Illegal band name reached frequency search method.");
			throw new InputMismatchException();
		}
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	/**************************************************************/ 
	/* Method: frequencySearch								  
	/* Purpose: searches for an prints the station with matching frequency
	/* Parameters: 
	/*		Number inputFrequency:		input to search for in the list
	/*		Node current:				node being searched
	/* 		int band:					which band is being searched
	/*		boolean found:				if the frequency has been found
	/* Returns: boolean:				if the frequency is ever found						  
	/**************************************************************/
	private boolean frequencySearch(Number inputFrequency, Node current, int band, boolean found){
		//at the end of the list, return found
		if (current == null)
			return found;
		//station being searched
		Station currentStation = (Station) (current.getDatum());
		//if FM band, convert Numbers to double
		if (band == FM_IDENTIFIER){
			//frequency being compared
			double currentFrequency = currentStation.getFrequency().doubleValue();
			//if current matches input, print station
			if (currentFrequency == inputFrequency.doubleValue()){
				System.out.println(currentStation.toString());
				//call next node and set found to true
				found = frequencySearch(inputFrequency, current.getNext(), band, true);
			}
		}
		//if AM band, convert Numbers to int
		else if (band == AM_IDENTIFIER){
			//frequency being compared
			int currentFrequency = currentStation.getFrequency().intValue();
			//if current matches input, print station
			if (currentFrequency == inputFrequency.intValue()){
				System.out.println(currentStation.toString());
				//call next node and set found to true
				found = frequencySearch(inputFrequency, current.getNext(), band, true);
			}
		} else
			//otherwise, just call the next node and keep found the same
			found = frequencySearch(inputFrequency, current.getNext(), band, found);
		return found;
	}
	
	/**************************************************************/ 
	/* Method: formatSearch								  
	/* Purpose: searches each list to for inputed format
	/* Parameters: 
	/*		String inputFormat:		input to search for in the list
	/* Returns: none						  
	/**************************************************************/
	public void formatSearch(String inputFormat) {
		//keeps track if format was ever found
		boolean found = false;
		//search through both lists
		found = (formatSearch(inputFormat, fmList.getHead(), found) | formatSearch(inputFormat, amList.getHead(), found));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	/**************************************************************/ 
	/* Method: formatSearch							  
	/* Purpose: searches list for inputed format
	/* Parameters: 
	/*		String inputFormat:		input to search for in the list
	/*		Node current:			Node being searched
	/*		boolean found:			if the format has been found
	/* Returns: boolean:			if the format is ever found						  
	/**************************************************************/
	private boolean formatSearch(String inputFormat, Node current, boolean found){
		//at end of the list, return found
		if (current == null){
			return found;
		}
		//station being searched
		Station currentStation = (Station) (current.getDatum());
		//format being searched
		String currentStationFormat = currentStation.getFormat();
		//if current format contains input format, prints station
		if (currentStationFormat.toLowerCase().contains(inputFormat.toLowerCase())){
			System.out.println(currentStation.toString());
			//call next node and set found to true
			found = formatSearch(inputFormat, current.getNext(), true);
		} else {
			//otherwise, just call next node using found's current value
			found = formatSearch(inputFormat, current.getNext(), found);
		}
		return found;
	}
	
	/**************************************************************/ 
	/* Method: printAll									  
	/* Purpose: prints all of the stations in the database
	/* Parameters: none							  
	/* Returns: none							  
	/**************************************************************/
	public void printAll() {
		//if no stations exists, prints message
		if (amList.size() + fmList.size() <= 1)
			System.out.println("No stations in database");
		else{
			//prints FM stations
			System.out.println("FM Stations:\n" + printStations(fmList.getHead()));
			//prints AM stations
			System.out.println("AM Stations:\n" + printStations(amList.getHead()));
		}
	}
	
	/**************************************************************/ 
	/* Method: printStations								  
	/* Purpose: returns list of Stations in a list
	/* Parameters: 
	/*		Node current:			current Node added to String				  
	/* Returns: String:				list of Stations in one list							  
	/**************************************************************/
	private String printStations(Node current){
		//return empty at end of list
		if (current == null)
			return "";
		//station being added to String
		Station currentStation = (Station) (current.getDatum());
		//return string of current, and the string of the next Node
		return currentStation.toString() + "\n" + printStations(current.getNext());
	}
}