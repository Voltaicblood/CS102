/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 4                                               */
/* Station class: contains objects corresponding to a given radio station */
/**************************************************************************/


public class Station implements Comparable<Station> {

   private String callSign = ""; //call sign of the station
   private String band = ""; //frequency band of station
   private Number frequency = -1.0; //frequency of station
   private String home = ""; //home/place of station
   private String format = ""; //genre format for station
   
   //constructor to create the entire station with direct input
   public Station(String callSign, String band, Number frequency,
                     String home, String format){
      
      this.callSign = callSign;
      this.band = band;
      this.frequency = frequency;
      this.home = home;
      this.format = format;
   }

   //no args constructor
   public Station(){
	   callSign = "";
	   band = "";
	   frequency = -1;
	   home = "";
	   format = "";
   }
   
   /**************************************************************/ 
   /* Method: get(variable)										 
   /* Purpose: returns the variable of the station				 
   /* Parameters: none											 
   /* Returns: String or Number									 
   /**************************************************************/
   
	public Number getFrequency() {
		return frequency;
	}
	
	/**************************************************************/ 
	/* Method: set(variable)									  
	/* Purpose: assigns the value of the variable to the inputed value			  
	/* Parameters: 
	/* 		String/Number (variable name):	value to assign										  
	/* Returns: none								  
	/**************************************************************/
	
	public void setFrequency(Number frequency) {
		this.frequency = frequency;
	}

	public String getCallSign() {
		return callSign;
	}
	
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	
	public String getBand() {
		return band;
	}
	
	public void setBand(String band) {
		this.band = band;
	}
	
	public String getHome() {
		return home;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**************************************************************/ 
	/* Method: toString									  
	/* Purpose: returns a String listing the variables of the station		  
	/* Parameters: none									  
	/* Returns: String:		station variables as a String								  
	/**************************************************************/
	public String toString(){
		String formattedFrequency; //String used to correctly format the frequency
		if (band.equals("AM")){ //if band is AM, uses integer value
			formattedFrequency = Integer.toString(frequency.intValue());
			// if band is FM, uses double value
		} else formattedFrequency = Double.toString(frequency.doubleValue());
		return callSign + ", " +  formattedFrequency + " " + band + ", "
				+ home + ": " + format;
	}
	
	/**************************************************************/ 
	/* Method: toStringOrig								  
	/* Purpose: returns a String listing the variables of the station
	/* 			in a format based on how they were originally received		  
	/* Parameters: none									  
	/* Returns: String:		station variables as a String								  
	/**************************************************************/
	public String toStringOrig(){
		if (band.equalsIgnoreCase("AM"))
			return callSign + "/" + band + "/" +
				((int)frequency / 10) + "/" + home + "/" + format + "\r\n";
		else
			return callSign + "/" + band + "/" +
			(int)((double)frequency * 10) + "/" + home + "/" + format +"\r\n";
	}
	
	/**************************************************************/ 
	/* Method: compareTo									  
	/* Purpose: compares stations based on call sign	  
	/* Parameters: Station other:		other station being compared								  
	/* Returns: int:					based on comparison, 0 if equal							  
	/**************************************************************/
	public int compareTo(Station other){
		return this.callSign.compareTo(other.getCallSign());	
	}

}