/****************************************************************************/
/* Jacob Austin                                                             */
/* Login ID: aust8558                                                       */ 
/* CS-102, Winter 2017                                                      */
/* Programming Assignment 5                                          	    */
/* AddStationFrame class: frame that collects info to add a station         */
/****************************************************************************/
import java.awt.event.*;
import java.util.InputMismatchException;
import javax.swing.*;

public class AddStationFrame implements ActionListener{
	//main frame
	JFrame frame;
	//database to add station to
	Database inputDatabase;
	//field to grab call sign
	JTextField callSignField = new JTextField();
	//field to grab band
	JTextField bandField = new JTextField();
	//field to grab frequency
	JTextField frequencyField = new JTextField();
	//field to grab home
	JTextField homeField = new JTextField();
	//field to grab format
	JTextField formatField = new JTextField();
	//button when fields are filled to add the station
	JButton doneButton = new JButton("Done");	
	
	//constructor to create a frame using an inputed database
	public AddStationFrame(Database inputDatabase) {
		frame = new JFrame("Add Station");
		this.inputDatabase = inputDatabase;
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new JLabel("Call Sign:"));
		frame.add(callSignField);
		frame.add(new JLabel("Frequency band:"));
		frame.add(bandField);
		frame.add(new JLabel("Frequency:"));
		frame.add(frequencyField);
		frame.add(new JLabel("Home:"));
		frame.add(homeField);
		frame.add(new JLabel("Format:"));
		frame.add(formatField);
		frame.add(doneButton);
		doneButton.addActionListener(this);
		frame.setVisible(true);
	}
	
	/**************************************************************/ 
	/* Method: actionPerformed									  
	/* Purpose: listens to done button and add station when triggered
	/* Parameters: 
	/*		ActionEvent event:			event that triggers the listener
	/* Returns: none							  
	/**************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			//get call sign from field
			String callSign = callSignField.getText();
			//error checking on call sign length
			if (callSign.length() > 4){
				JOptionPane.showMessageDialog(null, "Call sign is too long.");
				throw new InputMismatchException();
			}
			else if (callSign.length() < 3){
				JOptionPane.showMessageDialog(null, "Call sign is too short.");
				throw new InputMismatchException();
			}
			//get band from field
			String band = bandField.getText();
			//error checking to make sure band is FM or AM
			if (!(band.equalsIgnoreCase("FM") ||  band.equalsIgnoreCase("AM"))){
				JOptionPane.showMessageDialog(null, "Invalid band.");
				throw new InputMismatchException();
			} 
			//error checking to make sure the call sign isn't a duplicate
			if (inputDatabase.checkCallSignDuplicate(callSign, band)){
				JOptionPane.showMessageDialog(null, "Duplicate call sign in " +
												band.toUpperCase() + " band.");
				throw new InputMismatchException();
			}
			//frequency used in new station from field
			Number frequency;
			//parses frequency based on FM or AM
			try{
				if (band.equalsIgnoreCase("FM"))
					frequency = Double.parseDouble(frequencyField.getText());
				else
					frequency = Integer.parseInt(frequencyField.getText());
			} catch (NumberFormatException caughtException) {
				//throws an error is frequency is not an int or double
				JOptionPane.showMessageDialog(null, "Invalid frequency format.");
				throw new InputMismatchException();
			} catch (NullPointerException caughtException) {
				//throws an error if the frequency is null
				JOptionPane.showMessageDialog(null, "You must enter a frequency.");
				throw new InputMismatchException();
			}
			//grabs home and format from fields
			String home = homeField.getText();
			String format = formatField.getText();
			//finally adds the station
			inputDatabase.addStation(callSign, band, frequency, home, format);
			//drops the frame after adding
			this.frame.dispose();
			//confirms the station was added
			JOptionPane.showMessageDialog(null, "Station added.");
			
		} catch (InputMismatchException caughtException){
			//any thrown errors drop the frame and return to menu
			this.frame.dispose();
		}
	}
}
