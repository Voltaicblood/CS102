/***********************************************************************************/
/* Jacob Austin                                                           		   */
/* Login ID: aust8558                                                     		   */ 
/* CS-102, Winter 2017                                                    		   */
/* Programming Assignment 3                                               		   */
/* Database class: reads and manipulates a Linked Lists containing station objects */
/***********************************************************************************/

import java.util.*;
import java.io.*;


public class Database implements DatabaseInterface {
	
	//list of used to store FM stations
	private LinkedList<Station> fmList = new LinkedList<Station>(); 
	//list used to store AM stations
	private LinkedList<Station> amList = new LinkedList<Station>();
	//constant identifies FM band
	private final int FM_IDENTIFIER = 0;
	//constant identifies AM band
	private final int AM_IDENTIFIER = 1;
	//comparator to compare the call signs of stations in a list
	private Comparator<Station> callSignComparator = 
			(o1, o2) -> o1.getCallSign().compareTo(o2.getCallSign());

	
	
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
			fmList.sort(callSignComparator);
			amList.sort(callSignComparator);
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
			amList.sort(callSignComparator);
		//if FM band, add to FM list
		} 
		else if (band.equalsIgnoreCase("FM")) {
			fmList.add(0, tempStation);
			//sort after adding
			fmList.sort(callSignComparator);
		}
	}
	
	/**************************************************************/ 
	/* Method: removeStation									  
	/* Purpose: removes a station from a specified list
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/* 		String band:				which list station is being removed from
	/* Returns: none							  
	/**************************************************************/
	public void removeStation(String inputCallSign, String band){
		//scanner for keyboard input
		Scanner in = new Scanner(System.in);
		//holds list being edited
		LinkedList<Station> currentList;
		//if AM band, remove from AM list
		if (band.equalsIgnoreCase("AM"))
			currentList = amList;
		else if (band.equalsIgnoreCase("FM"))
			currentList = fmList;
		else{
			System.out.println("Incorrect band type.");
			throw new InputMismatchException();
		}
		for (Station current : currentList){
			//call sign of the current station
			String currentCallSign = current.getCallSign();
			if (currentCallSign.equalsIgnoreCase(inputCallSign)){
				System.out.println(current.toString() +
						"\nDelete this station? y/n");
				if (in.next().equalsIgnoreCase("y")){
					//if user confirms deletion
					if (currentList == amList){
						amList.remove(current);
						//sort after removing
						amList.sort(callSignComparator);
					}
					else if (currentList == fmList){
						fmList.remove(current);
						//sort after removing
						fmList.sort(callSignComparator);
					}
					System.out.println("Station deleted");
					return;
				} else { 
					//if user does not confirm deletion
					System.out.println("Deletion aborted.");
					return;
				}
			}
		}
		System.out.println("Station not found.");
		throw new InputMismatchException();
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
		found = (callSignSearch(inputCallSign, fmList)
					| callSignSearch(inputCallSign, amList));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}

	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches for and prints	stations with the inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the list
	/*		LinkedList currentList:		list to search on
	/* Returns: boolean:				if a station was found or not							  
	/**************************************************************/
	private boolean callSignSearch(String inputCallSign, LinkedList<Station> currentList){
		//search through all stations
		for (Station current : currentList){
			//call sign of the current station
			String currentStationCallSign = current.getCallSign();
			//if the call signs match, print and return true
			if (currentStationCallSign.equals(inputCallSign)){
				System.out.println(current.toString());
				return true;
			}
		}
		return false;
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
			found = checkCallSignDuplicate(inputCallSign, fmList);
		}
		//if AM band, check AM list
		else if (band.equalsIgnoreCase("AM")){
			found = checkCallSignDuplicate(inputCallSign, amList);
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
	/*		LinkedList currentList:		current list being searched
	/* Returns: boolean:				true if there is a duplicate						  
	/**************************************************************/
	private boolean checkCallSignDuplicate(String inputCallSign, LinkedList<Station> currentList){
		//search through all stations
		for (Station current : currentList){
			//call sign of the current station
			String currentStationCallSign = current.getCallSign();
			//if the call signs match, return true
			if (currentStationCallSign.equals(inputCallSign)){
				return true;
			}
		}
		return false;
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
			found = frequencySearch(inputFrequency, fmList, FM_IDENTIFIER);
		}
		//if AM band, search AM list
		else if (band.equals("AM")){
			found = frequencySearch(inputFrequency, amList, AM_IDENTIFIER);
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
	/*		List currentList:			list being searched
	/* 		int band:					which band is being searched
	/* Returns: boolean:				if the frequency is ever found						  
	/**************************************************************/
	private boolean frequencySearch(Number inputFrequency, LinkedList<Station> currentList, int band){
		//keep track if frequency was found
		boolean found = false;
		//if FM band, convert Numbers to double
		if (band == FM_IDENTIFIER){
			//search through entire list
			for (Station current : currentList){
				//frequency being compared
				double currentFrequency = current.getFrequency().doubleValue();
				//if current matches input, print station
				if (currentFrequency == inputFrequency.doubleValue()){
					System.out.println(current.toString());
					//set found to true
					found = true;
				}
			}
		}
		//if AM band, convert Numbers to int
		else if (band == AM_IDENTIFIER){
			//search through entire list
			for (Station current : currentList){
				//frequency being compared
				int currentFrequency = current.getFrequency().intValue();
				//if current matches input, print station
				if (currentFrequency == inputFrequency.intValue()){
					System.out.println(current.toString());
					//set found to true
					found = true;
				}
			}
		}
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
		found = (formatSearch(inputFormat, fmList) | formatSearch(inputFormat, amList));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	/**************************************************************/ 
	/* Method: formatSearch							  
	/* Purpose: searches list for inputed format
	/* Parameters: 
	/*		String inputFormat:		input to search for in the list
	/*		List currentList:		list being searched
	/* Returns: boolean:			if the format is ever found						  
	/**************************************************************/
	private boolean formatSearch(String inputFormat, LinkedList<Station> currentList){
		//keeps track if format was found
		boolean found = false;
		//search through entire list
		for (Station current : currentList){
			//format being searched
			String currentFormat = current.getFormat();
			//if current format contains input format, prints station
			if (currentFormat.toLowerCase().contains(inputFormat.toLowerCase())){
				System.out.println(current.toString());
				//call next node and set found to true
				found = true;
			}
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
			System.out.println("FM Stations:\n" + printStations(fmList));
			//prints AM stations
			System.out.println("AM Stations:\n" + printStations(amList));
		}
	}
	
	/**************************************************************/ 
	/* Method: printStations								  
	/* Purpose: returns list of Stations in a list
	/* Parameters: 
	/*		List currentList:		current list being turned into a string				  
	/* Returns: String:				list of Stations in one list							  
	/**************************************************************/
	private String printStations(LinkedList<Station> currentList){
		//String of all the stations in the list
		String stationListString = "";
		//goes through entire list and add the stations to string
		for (Station current : currentList){
			stationListString += current.toString() + "\n";
		}
		return stationListString;
	}
}