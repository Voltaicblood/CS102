/***********************************************************************************/
/* Jacob Austin                                                           		   */
/* Login ID: aust8558                                                     		   */ 
/* CS-102, Winter 2017                                                    		   */
/* Programming Assignment 5                                            		       */
/* DatabaseFrame class: displays the results from a search or full database print  */
/***********************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DatabaseFrame {
	//main frame
	JFrame frame;
	//panel to store FM stations
	JPanel FMPanel = new JPanel();
	//panel to store AM stations
	JPanel AMPanel = new JPanel();
	//tabs to store the panels
	JTabbedPane tabs = new JTabbedPane();
	
	//no args constructor
	public DatabaseFrame(){
		//main setup of frame
		frame = new JFrame("Database Results");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setSize(400, 800);
		//box layout to vertically display stations
		FMPanel.setLayout(new BoxLayout(FMPanel, BoxLayout.Y_AXIS));
		AMPanel.setLayout(new BoxLayout(AMPanel, BoxLayout.Y_AXIS));
		//tabs help display both lists
		frame.add(tabs);
		tabs.add("FM Stations", FMPanel);
		tabs.add("AM Stations", AMPanel);
	}
}
