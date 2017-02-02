/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 1                                               */
/* Database class: reads and manipulates a collection of Station objects  */
/**************************************************************************/

import java.util.*;
import java.io.*;


public class Database implements DatabaseInterface {
	
	private LinkedList fmList = new LinkedList();
	private LinkedList amList = new LinkedList();
	private final int FM_IDENTIFIER = 0;
	private final int AM_IDENTIFIER = 1;
	
	public Database(File input) throws FileNotFoundException{
		Scanner reader = new Scanner(input);
		try{
			if (!reader.hasNextLine())
				throw new IllegalStateException();
		} catch (IllegalStateException caughtException){
			System.out.println("Station file is empty.");
			throw caughtException;
		}
			
		try{
			//creates stations as long as there are more station lines
			while(reader.hasNextLine()){
				String currentLineInput = reader.nextLine(); //current read line in file
				//Scanner used to read each item in a line
				Scanner stationReader = new Scanner(currentLineInput).useDelimiter("/");
				Station tempStation = new Station();
				//temporary call sign holder used to handle bad inputs
				String tempCallSign = stationReader.next();
				//temporary band holder used to handle bad inputs
				String tempBand = stationReader.next();
				//temporary frequency holder used to set type
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
			fmList.alphabeticalSort();
			amList.alphabeticalSort();
		} catch (IllegalArgumentException caughtException){
			System.out.println("Invalid data types in list of stations.");
			throw caughtException;
		}
	}
	
	public void addStation(String callSign, String band, Number frequency, 
							String home, String format) {
		Station tempStation = new Station(callSign, band, frequency, home, format);
		if (band.equals("AM")){
			amList.add(0, tempStation);
			amList.alphabeticalSort();
		} else {
			fmList.add(0, tempStation);
			fmList.alphabeticalSort();
		}
		
	}

	public void callSignSearch(String inputCallSign) {
		boolean found;
		//search through both lists, and print the stations that match
		found = (callSignSearch(inputCallSign, fmList.getHead())
					| callSignSearch(inputCallSign, amList.getHead()));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	private boolean callSignSearch(String inputCallSign, Node current){
		if (current == null)
			return false;
		Station currentStation = (Station) (current.getDatum());
		String currentStationCallSign = currentStation.getCallSign();
		if (currentStationCallSign.equals(inputCallSign)){
			System.out.println(currentStation.toString());
			return true;
		}
		return callSignSearch(inputCallSign, current.getNext());
	}
	
	public boolean checkCallSignDuplicate(String inputCallSign, String band){
		boolean found;
		if (band.equalsIgnoreCase("FM")){
			found = checkCallSignDuplicate(inputCallSign, fmList.getHead());
		}
		else if (band.equalsIgnoreCase("AM")){
			found = checkCallSignDuplicate(inputCallSign, amList.getHead());
		} else {
			System.out.print("Illegal band name reached callsign duplicate method.");
			throw new InputMismatchException();
		}
		return found;
	}
	
	private boolean checkCallSignDuplicate(String inputCallSign, Node current){
		if (current == null)
			return false;
		Station currentStation = (Station) (current.getDatum());
		String currentStationCallSign = currentStation.getCallSign();
		if (currentStationCallSign.equals(inputCallSign)){
			return true;
		}
		return callSignSearch(inputCallSign, current.getNext());
	}

	public void frequencySearch(Number inputFrequency, String band) {
		boolean found;
		//search through both lists, and print the stations that match
		if (band.equals("FM")){
			found = frequencySearch(inputFrequency, fmList.getHead(), FM_IDENTIFIER, false);
		}
		else if (band.equals("AM")){

			found = frequencySearch(inputFrequency, amList.getHead(), AM_IDENTIFIER, false);
		} else {
			System.out.print("Illegal band name reached frequency search method.");
			throw new InputMismatchException();
		}
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	private boolean frequencySearch(Number inputFrequency, Node current, int band, boolean found){
		if (current == null)
			return found;
		Station currentStation = (Station) (current.getDatum());
		if (band == FM_IDENTIFIER){
			double currentFrequency = currentStation.getFrequency().doubleValue();
			if (currentFrequency == inputFrequency.doubleValue()){
				System.out.println(currentStation.toString());
				found = frequencySearch(inputFrequency, current.getNext(), band, true);
			}
		}
		else if (band == AM_IDENTIFIER){
			int currentFrequency = currentStation.getFrequency().intValue();
			if (currentFrequency == inputFrequency.intValue()){
				System.out.println(currentStation.toString());
				found = frequencySearch(inputFrequency, current.getNext(), band, true);
			}
		} else
			found = frequencySearch(inputFrequency, current.getNext(), band, found);
		return found;
	}

	public void formatSearch(String inputFormat) {
		boolean found = false;
		//search through both lists, and print the stations that match
		found = (formatSearch(inputFormat, fmList.getHead(), found) | formatSearch(inputFormat, amList.getHead(), found));
		//if no stations were found, prints message
		if (!found)
			System.out.println("No results found.");
	}
	
	private boolean formatSearch(String inputFormat, Node current, boolean found){
		if (current == null){
			return found;
		}
		Station currentStation = (Station) (current.getDatum());
		String currentStationFormat = currentStation.getFormat();
		if (currentStationFormat.toLowerCase().contains(inputFormat.toLowerCase())){
			System.out.print(currentStation.toString());
			found = formatSearch(inputFormat, current.getNext(), true);
		} else {
			found = formatSearch(inputFormat, current.getNext(), found);
		}
		return found;
	}

	public void printAll() {
		//if no stations exists, prints message
		if (amList.size() + fmList.size() <= 1)
			System.out.println("No stations in database");
		else{
			System.out.println("FM Stations:\n" + printStations(fmList.getHead()));
			System.out.println("\nAM Stations:\n" + printStations(amList.getHead()));
		}
	}
		
	private String printStations(Node current){
		if (current == null)
			return "";
		Station currentStation = (Station) (current.getDatum());
		return currentStation.toString() + "\n" + printStations(current.getNext());
	}
}