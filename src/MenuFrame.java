/****************************************************************************/
/* Jacob Austin                                                             */
/* Login ID: aust8558                                                       */ 
/* CS-102, Winter 2017                                                      */
/* Programming Assignment 5                                          	    */
/* MenuFrame class: runs the menu that allows database viewing/manipulation */
/****************************************************************************/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import javax.swing.*;

public class MenuFrame implements ActionListener {
	//main frame with rest of components
	JFrame frame;
	//database being manipulated/viewed
	Database inputDatabase;
	//button to search call sign
	JButton searchCallsign = new JButton("Search Call Sign");
	//button to search frequency
	JButton searchFrequency = new JButton("Search Frequency");
	//button to search format
	JButton searchFormat = new JButton("Search Format");
	//button to view all stations in database
	JButton printAll = new JButton("List Database");
	//button to add a station
	JButton addStation = new JButton("Add a Station");
	//button to delete a station
	JButton deleteStation = new JButton("Delete a Station");
	//button to print to a file
	JButton printToDisk = new JButton("Print to File");
	//button to reload the database from a new file
	JButton reloadDatabase = new JButton("Reload Database");
	
	//constructor using inputted database
	public MenuFrame(Database inputDatabase){
		this.inputDatabase = inputDatabase;
		//initial frame setup
		frame = new JFrame("Radio Database");
		frame.setLayout(new GridLayout(0, 2, 5, 5));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//adding all the buttons
		frame.add(searchCallsign);
		frame.add(searchFrequency);
		frame.add(searchFormat);
		frame.add(printAll);
		frame.add(addStation);
		frame.add(deleteStation);
		frame.add(printToDisk);
		frame.add(reloadDatabase);
		//adding action listener to all buttons
		searchCallsign.addActionListener(this);
		searchFrequency.addActionListener(this);
		searchFormat.addActionListener(this);
		printAll.addActionListener(this);
		addStation.addActionListener(this);
		deleteStation.addActionListener(this);
		printToDisk.addActionListener(this);
		reloadDatabase.addActionListener(this);
		//menu should be visible at launch
		frame.setVisible(true);
	}
	
	/**************************************************************/ 
	/* Method: actionPerformed									  
	/* Purpose: listens to menu buttons and performs the actions required
	/* Parameters: 
	/*		ActionEvent event:			event that triggers the listener
	/* Returns: none							  
	/**************************************************************/
	@Override
	public void actionPerformed(ActionEvent event) {
		//call sign search
		if(event.getSource() == searchCallsign){
			//ask for then search the call sign
			String inputCallsign = JOptionPane.showInputDialog("Enter callsign:");
			inputDatabase.callSignSearch(inputCallsign);
		}
		if(event.getSource() == searchFrequency){
			//ask for band
			String band = JOptionPane.showInputDialog("Enter frequency band:");
			try{
				//checks band type to determine which type to call for
				if (band.equalsIgnoreCase("FM")){
					Double inputFrequency = Double.parseDouble(JOptionPane.showInputDialog("Enter frequency:"));
					//search FM list
					inputDatabase.frequencySearch(inputFrequency, "FM");
				}
				else if (band.equalsIgnoreCase("AM")){
					int inputFrequency = Integer.parseInt(JOptionPane.showInputDialog("Enter frequency:"));
					//search AM list
					inputDatabase.frequencySearch(inputFrequency, "AM");
				} else {
					//if it isn't AM or FM
					JOptionPane.showMessageDialog(null, "Invalid band.");
					throw new InputMismatchException();
				}
			} catch (InputMismatchException caughtException){
				//show message and return to menu
				JOptionPane.showMessageDialog(null, "Incorrect input.");
			} catch (NullPointerException caughtException){
				//show message and return to menu
				JOptionPane.showMessageDialog(null, "Incorrect input.");
			}
		}
		//format search
		if(event.getSource() == searchFormat){
			try{
				//grab format and then search for it
				String inputFormat = JOptionPane.showInputDialog("Enter format:");
				inputDatabase.formatSearch(inputFormat);
			} catch (NullPointerException caughtException){
				//if nothing is entered, return to menu
				JOptionPane.showMessageDialog(null, "Invalid input.");
			}
		}
		//list stations in database
		if(event.getSource() == printAll){
			inputDatabase.printAll();
		}
		//add a station using the AddStationFrame
		if(event.getSource() == addStation){
			new AddStationFrame(inputDatabase);
		}
		//delete a station
		if(event.getSource() == deleteStation){
			try{
				//grab callsign from JOptionPane
				String callSign = JOptionPane.showInputDialog("Enter call sign:");
				//error checking on call sign length
				if (callSign.length() > 4){
					JOptionPane.showMessageDialog(null, "Callsign is too long.");
					throw new InputMismatchException();
				}
				else if (callSign.length() < 3){
					JOptionPane.showMessageDialog(null, "Callsign is too short.");
					throw new InputMismatchException();
				}
				//grab band from JOptionPane
				String band = JOptionPane.showInputDialog("Enter frequency band:");
				//error checking to make sure band is FM or AM
				if (!(band.equalsIgnoreCase("FM") ||  band.equalsIgnoreCase("AM"))){
					JOptionPane.showMessageDialog(null, "Invalid band.");
					throw new InputMismatchException();
				}
				//searches and removes the station from the specified band
				inputDatabase.removeStation(callSign, band);
			} catch (InputMismatchException caughtException){
				//return user to menu
				return;
			} catch (NullPointerException caughtException){
				//if nothing is entered, or canceled
				JOptionPane.showMessageDialog(null, "Invalid input.");
			}
		}
		//print to a file
		if(event.getSource() == printToDisk){
			try{
				//grab file name from JOP
				String outputFileName = JOptionPane.showInputDialog("Enter file name to write to:");
				//call database method
				inputDatabase.printToDisk(outputFileName);
			} catch (NoSuchElementException caughtException) {
				//return user to menu
				return;
			} catch (NullPointerException caughtException) {
				//if nothing is entered, or canceled
				JOptionPane.showMessageDialog(null, "Invalid file name.");
			}
		}
		//reload the database from a new file
		if(event.getSource() == reloadDatabase){
			//grab filename from JOP
			String newInputFileName = JOptionPane.showInputDialog("Enter file name of databse:");
			//uses new file to reinitialize the database
			try {
				File newInputFile = new File(newInputFileName);
				inputDatabase = new Database(newInputFile);
				JOptionPane.showMessageDialog(null, "Database reloaded.");
			} catch (FileNotFoundException caughtException) {
				//error on file name
				JOptionPane.showMessageDialog(null, "File not at file name location. Keeping current database.");
			} catch (NullPointerException caughtException) {
				//if nothing entered, or canceled
				JOptionPane.showMessageDialog(null, "Invalid file name.");
			}
		}
	}
}
