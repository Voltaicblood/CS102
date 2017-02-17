/***************************************************************************************/
/* Jacob Austin                                                           		       */
/* Login ID: aust8558                                                     		       */ 
/* CS-102, Winter 2017                                                    		       */
/* Programming Assignment 1                                               		       */
/* Database interface: methods for all database classes                                */
/***************************************************************************************/

public interface DatabaseInterface {
	public void addStation(String callSign, String band, Number frequency, String home, String format);
	public void callSignSearch(String inputCallSign);
	public void frequencySearch(Number inputFrequency, String band);
	public void formatSearch(String inputFormat);
	public void printAll();	
}
