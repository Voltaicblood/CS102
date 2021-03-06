/***********************************************************************************/
/* Jacob Austin                                                           		   */
/* Login ID: aust8558                                                     		   */ 
/* CS-102, Winter 2017                                                    		   */
/* Programming Assignment 5                                            		       */
/* Database class: reads and manipulates trees containing station objects          */
/***********************************************************************************/

import java.util.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.io.*;


public class Database implements DatabaseInterface {
	
	//tree of used to store FM stations
	private Tree<Station> fmList = new Tree<Station>(); 
	//tree used to store AM stations
	private Tree<Station> amList = new Tree<Station>();
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
				//finishes setting variables
				tempStation.setCallSign(tempCallSign);
				tempStation.setBand(tempBand);
				//corrects frequency based on AM or FM
				if (tempBand.equals("AM")){
					tempFrequency = stationReader.nextInt() * 10;
					amList.add(tempStation);
				} else {
					tempFrequency = stationReader.nextDouble() / 10;
					fmList.add(tempStation);
				}
				tempStation.setFrequency(tempFrequency);
				tempStation.setHome(stationReader.next());
				tempStation.setFormat(stationReader.next());
			}
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
	/* Purpose: adds a station to a tree
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
			amList.add(tempStation);
		//if FM band, add to FM list
		} 
		else if (band.equalsIgnoreCase("FM")) {
			fmList.add(tempStation);
		}
	}
	
	/**************************************************************/ 
	/* Method: removeStation									  
	/* Purpose: removes a station from a specified tree
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the tree
	/* 		String band:				which tree station is being removed from
	/* Returns: none							  
	/**************************************************************/
	public void removeStation(String inputCallSign, String band){
		//holds list being edited
		Tree<Station> currentList;
		//if AM band, remove from AM list
		if (band.equalsIgnoreCase("AM"))
			currentList = amList;
		else if (band.equalsIgnoreCase("FM"))
			currentList = fmList;
		else{
			System.out.println("Incorrect band type reached removeStation method.");
			throw new InputMismatchException();
		}
		for (Station current : currentList){
			//call sign of the current station
			String currentCallSign = current.getCallSign();
			if (currentCallSign.equalsIgnoreCase(inputCallSign)){
				//shows confirmation dialog
				int result = JOptionPane.showConfirmDialog(null, current.toString() +
						"\nDelete this station?", null, JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION){
					//if user confirms deletion
					if (currentList == amList){
						amList.remove(current);
					}
					//if user does not want to delete
					else if (currentList == fmList){
						fmList.remove(current);
					}
					JOptionPane.showMessageDialog(null, "Station deleted");
					return;
				} else { 
					//if user does not confirm deletion
					JOptionPane.showMessageDialog(null, "Station deletion aborted.");
					return;
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Station not found.");
		throw new InputMismatchException();
	}

	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches each tree for inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the tree
	/* Returns: none							  
	/**************************************************************/
	public void callSignSearch(String inputCallSign) {
		//frame to show results of the search
		DatabaseFrame resultFrame = new DatabaseFrame();
		//keeps track if a station was found
		boolean found;
		//search through both lists, and print the stations that match
		found = (callSignSearch(inputCallSign, fmList, resultFrame)
					| callSignSearch(inputCallSign, amList, resultFrame));
		//if no stations were found, prints message
		if (!found){
			JOptionPane.showMessageDialog(null, "No results found.");
			//removes the frame that isn't being used
			resultFrame.frame.dispose();
		}
		else {
			//set the frame to be visible if any results were found
			resultFrame.frame.setVisible(true);
		}
	}
	
	/**************************************************************/ 
	/* Method: callSignSearch									  
	/* Purpose: searches for and prints	stations with the inputed call sign
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the tree
	/*		Tree currentList:			tree to search on
	/* 		DatabaseFrame resultFrame:	frame to put results on
	/* Returns: boolean:				if a station was found or not							  
	/**************************************************************/
	private boolean callSignSearch(String inputCallSign, Tree<Station> currentList,
									DatabaseFrame resultFrame){
		//search through all stations
		for (Station current : currentList){
			//call sign of the current station
			String currentStationCallSign = current.getCallSign();
			//if the call signs match, print and return true
			if (currentStationCallSign.equals(inputCallSign)){
				//add the station to the results frame if it was found
				if (currentList == fmList)
					resultFrame.FMPanel.add(new JLabel(current.toString()));
				else
					resultFrame.AMPanel.add(new JLabel(current.toString()));
				return true;
			}
		}
		return false;
	}
	
	/**************************************************************/ 
	/* Method: checkCallSignDuplicate									  
	/* Purpose: checks inputed call sign with a certain tree for duplication
	/* Parameters: 
	/*		String inputCallSign:		input to search for in the true
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
	/*		String inputCallSign:		input to search for in the tree
	/*		Tree currentList:			current tree being searched
	/* Returns: boolean:				true if there is a duplicate						  
	/**************************************************************/
	private boolean checkCallSignDuplicate(String inputCallSign, Tree<Station> currentList){
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
	/* Purpose: searches one of the trees for inputed frequency
	/* Parameters: 
	/*		Number inputFrequency:		input to search for in the tree
	/*		String band:				which tree to search
	/* Returns: none						  
	/**************************************************************/
	public void frequencySearch(Number inputFrequency, String band) {
		//frame to print results to
		DatabaseFrame resultFrame = new DatabaseFrame();
		//keeps track if the frequency was found
		boolean found;
		//if FM band, search FM list
		if (band.equals("FM")){
			found = frequencySearch(inputFrequency, fmList, FM_IDENTIFIER, resultFrame);
		}
		//if AM band, search AM list
		else if (band.equals("AM")){
			found = frequencySearch(inputFrequency, amList, AM_IDENTIFIER, resultFrame);
		} else {
			//an illegal band should not reach here,
			//but just in case, throws an exception
			System.out.print("Illegal band name reached frequency search method.");
			throw new InputMismatchException();
		}
		//if no stations were found, shows message
		if (!found){
			JOptionPane.showMessageDialog(null, "No results found.");
			//removes the frame that isn't being used
			resultFrame.frame.dispose();
		}
		else {
			//sets the frame as visible if results were found
			resultFrame.frame.setVisible(true);
		}
	}
	
	/**************************************************************/ 
	/* Method: frequencySearch								  
	/* Purpose: searches for an prints the station with matching frequency
	/* Parameters: 
	/*		Number inputFrequency:		input to search for in the tree
	/*		Tree currentList:			tree being searched
	/* 		int band:					which band is being searched
	/* 		DatabaseFrame resultFrame:	frame which results are posted on
	/* Returns: boolean:				if the frequency is ever found						  
	/**************************************************************/
	private boolean frequencySearch(Number inputFrequency, Tree<Station> currentList, int band,
									DatabaseFrame resultFrame){
		//keep track if frequency was found
		boolean found = false;
		//if FM band, convert Numbers to double
		if (band == FM_IDENTIFIER){
			//search through entire list
			for (Station current : currentList){
				//frequency being compared
				double currentFrequency = current.getFrequency().doubleValue();
				//if current matches input, add to results
				if (currentFrequency == inputFrequency.doubleValue()){
					resultFrame.FMPanel.add(new JLabel(current.toString()));
					//removes other tab that will have no results
					resultFrame.tabs.removeTabAt(1);
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
				//if current matches input, add to results
				if (currentFrequency == inputFrequency.intValue()){
					resultFrame.AMPanel.add(new JLabel(current.toString()));
					//removes other tab that will have no results
					resultFrame.tabs.removeTabAt(0);
					//set found to true
					found = true;
				}
			}
		}
		return found;
	}

	/**************************************************************/ 
	/* Method: formatSearch								  
	/* Purpose: searches each tree to for inputed format
	/* Parameters: 
	/*		String inputFormat:		input to search for in the tree
	/* Returns: none						  
	/**************************************************************/
	public void formatSearch(String inputFormat) {
		//frame to put results on
		DatabaseFrame resultFrame = new DatabaseFrame();
		//keeps track if format was ever found
		boolean found = false;
		//search through both lists
		found = (formatSearch(inputFormat, fmList, resultFrame) |
				formatSearch(inputFormat, amList, resultFrame));
		//if no stations were found, prints message
		if (!found){
			JOptionPane.showMessageDialog(null, "No results found.");
			//removes the frame that isn't being used
			resultFrame.frame.dispose();
		}
		else {
			//if a result is found, sets the results frame to visible
			resultFrame.frame.setVisible(true);
		}
	}
	
	/**************************************************************/ 
	/* Method: formatSearch							  
	/* Purpose: searches tree for inputed format
	/* Parameters: 
	/*		String inputFormat:			input to search for in the tree
	/*		Tree currentList:			tree being searched
	/* 		DatabaseFrame resultFrame:	frame to print results to
	/* Returns: boolean:				if the format is ever found						  
	/**************************************************************/
	private boolean formatSearch(String inputFormat, Tree<Station> currentList,
									DatabaseFrame resultFrame){
		//keeps track if format was found
		boolean found = false;
		//search through entire list
		for (Station current : currentList){
			//format being searched
			String currentFormat = current.getFormat();
			//if current format contains input format, add station to results
			if (currentFormat.toLowerCase().contains(inputFormat.toLowerCase())){
				if(currentList == fmList)
					resultFrame.FMPanel.add(new JLabel(current.toString()));
				else
					resultFrame.AMPanel.add(new JLabel(current.toString()));
				//call next node and set found to true
				found = true;
			}
		}
		return found;
	}
	
	/**************************************************************/ 
	/* Method: printAll									  
	/* Purpose: lists all of the stations in the database
	/* Parameters: none							  
	/* Returns: none							  
	/**************************************************************/
	public void printAll() {
		DatabaseFrame resultFrame = new DatabaseFrame();
		//if no stations exists, display message
		if (amList.isEmpty() && fmList.isEmpty() )
			JOptionPane.showMessageDialog(null, "No stations in database");
		else{
			//prints FM stations
			printStations(fmList, FM_IDENTIFIER, resultFrame);
			//prints AM stations
			printStations(amList, AM_IDENTIFIER, resultFrame);
			//show results frame
			resultFrame.frame.setVisible(true);
		}
	}
	/**************************************************************/ 
	/* Method: printStations								  
	/* Purpose: returns string of Stations in a tree
	/* Parameters: 
	/*		Tree currentList:			current tree being turned into a string
	/* 		DatabaseFrame resultFrame:	frame to print results to			  
	/* Returns: String:					tree of Stations in one tree							  
	/**************************************************************/
	private void printStations(Tree<Station> currentList, int identifier,
									DatabaseFrame resultFrame){
		//goes through entire list and add the stations to string
		if(identifier == FM_IDENTIFIER){
			for (Station current : currentList)
				//adds the stations from the fm list to the fm panel
				resultFrame.FMPanel.add(new JLabel(current.toString()));
		}
		else{
			for (Station current : currentList)
				//adds the stations from the am list to the am panel
				resultFrame.AMPanel.add(new JLabel(current.toString()));
		}
	}
	
	/**************************************************************/ 
	/* Method: printToDisk								  
	/* Purpose: writes all the stations to a file 
	/* Parameters: 
	/*		String fileName:		which file to write to		  
	/* Returns: none							  
	/**************************************************************/
	public void printToDisk(String fileName){
		try{
			//writer to print to disk
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			//String made from each list
			String preOrderedString = amList.preOrderString() + fmList.preOrderString();
			//Writes to file
			writer.write(preOrderedString);
			writer.close();
			//confirms database has been saved
			JOptionPane.showMessageDialog(null, "Database saved to disk.");
		} catch (FileNotFoundException caughtException){
			//if filename is incorrect
			JOptionPane.showMessageDialog(null, "File not found at designated location");
		} catch (IOException caughtException){
			//if nothing is entered or dialog is canceled
			JOptionPane.showMessageDialog(null, "File can not be accessed.");
		}
	}
	
}