/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 5                                          	  */
/* Prog5 class: obtains input file and passes it on to Database class     */
/**************************************************************************/
import java.io.*;
import java.util.*;

public class Prog5 {
	
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
		//create menu from which to view and manipulate database
		MenuFrame menu = new MenuFrame(inputDatabase);
	}
}