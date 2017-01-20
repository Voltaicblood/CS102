/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 1                                               */
/* Database class: reads and manipulates a collection of Station objects  */
/**************************************************************************/

import java.util.*;
import java.io.*;


public class Database {
	
	final private int DATABASE_SIZE = 50; //arbitrary size of database
	static private int last; //location of last station in the database
	private Station [] chronicle; //array used to hold stations
	
	//database constructor from file input
	public Database(File input) throws FileNotFoundException{
		last = -1;
		chronicle = new Station [DATABASE_SIZE];
		Scanner reader = new Scanner(input); //Scanner used to read file lines
		try{
			//creates stations as long as there are more station lines
			while(reader.hasNextLine()){
				last++;
				String currentLineInput = reader.nextLine(); //current read line in file
				//Scanner used to read each item in a line
				Scanner stationReader = new Scanner(currentLineInput).useDelimiter("/");
				chronicle[last] = new Station();//creates new Station object
				//temporary call sign holder used to handle bad inputs
				String tempCallSign = stationReader.next();
				//temporary band holder used to handle bad inputs
				String tempBand = stationReader.next();
				//temporary frequency holder used to set type
				Number tempFrequency;
				//checks for bad input, then assigns values to variables of new Station
				if (tempCallSign.length() > 4 || tempCallSign.length() < 3)
					throw new IllegalArgumentException();
				chronicle[last].setCallSign(tempCallSign);
				if (!tempBand.equals("AM") && !tempBand.equals("FM"))
					throw new IllegalArgumentException();
				chronicle[last].setBand(tempBand);
				//corrects frequency based on AM or FM
				if (tempBand.equals("AM")){
					tempFrequency = stationReader.nextInt() * 10;
				} else {
					tempFrequency = stationReader.nextDouble() / 10;
				}
				//finishes setting rest of variables
				chronicle[last].setFrequency(tempFrequency);
				chronicle[last].setHome(stationReader.next());
				chronicle[last].setFormat(stationReader.next());
			}
		} catch (IllegalArgumentException e){
			System.out.println("Invalid data types in list of stations.");
			throw e;
		}
	}
	
	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches for and prints	stations with the inputed call sign  
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the array							  
	/* Returns: none							  
	/**************************************************************/
	public void callSignSearch(String inputCallSign){
		boolean found = false; //used to see if any stations were found
		for(int i = 0; i <= last; i++){
			if (chronicle[i].getCallSign().equalsIgnoreCase(inputCallSign)){
				found = true;
				System.out.println(chronicle[i].toString());
			}
		}
		//if no stations were found, prints message
		if (!found){
			System.out.println("No results found.");
		}
	}
	
	/**************************************************************/ 
	/* Method: frequencySearch									  
	/* Purpose: searches for and prints	stations with the inputed frequency,
	/*  	based on the band
	/* Parameters: 
	/*		String inputFrequency:		input to search for in the array							  
	/* Returns: none							  
	/**************************************************************/
	public void frequencySearch(Number inputFrequency, String band){
		boolean found = false; //used to see if any stations were found
		for(int i = 0; i <= last; i++){
			if (band.equals("FM")){ //compares using FM double type
				if (chronicle[i].getFrequency().doubleValue() == (inputFrequency.doubleValue())){
					found = true;
					System.out.println(chronicle[i].toString());
				}
			}
			else if (band.equals("AM")){ //compares using AM integer type
				if (chronicle[i].getFrequency().intValue() == (inputFrequency.intValue())){
					found = true;
					System.out.println(chronicle[i].toString());
				}
			}
		}
		//if no stations were found, prints message
		if (!found){
			System.out.println("No results found.");
		}
	}
	
	/**************************************************************/ 
	/* Method: formatSearch									  
	/* Purpose: searches for and prints	stations containing the inputed format keywords 
	/* Parameters: 
	/*		String inputFormat:		input to search for in the array							  
	/* Returns: none							  
	/**************************************************************/
	public void formatSearch(String inputFormat){
		boolean found = false; //used to see if any stations were found
		for(int i = 0; i <= last; i++){
			if (chronicle[i].getFormat().toLowerCase().contains(inputFormat.toLowerCase())){
				found = true;
				System.out.println(chronicle[i].toString());
			}
		}
		//if no stations were found, prints message
		if (!found){
			System.out.println("No results found.");
		}
	}
	
	/**************************************************************/ 
	/* Method: printAll									  
	/* Purpose: prints all of the stations in the database
	/* Parameters: none							  
	/* Returns: none							  
	/**************************************************************/
	public void printAll(){
		//if no stations exists, prints message
		if (last == -1)
			System.out.println("No stations in database");
		else {
			System.out.println("FM Stations:");
			for(int i = 0; i <= last; i++){
				if (chronicle[i].getBand().equals("FM"))
				System.out.println(chronicle[i].toString());
			}
			System.out.println("\nAM Stations:");
			for(int i = 0; i <= last; i++){
				if (chronicle[i].getBand().equals("FM"))
				System.out.println(chronicle[i].toString());
			}
		}
	}
}
