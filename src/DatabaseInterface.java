
public interface DatabaseInterface {
	public void addStation(String callSign, String band, Number frequency, String home, String format);
	public void callSignSearch(String inputCallSign);
	public void frequencySearch(Number inputFrequency, String band);
	public void formatSearch(String inputFormat);
	public void printAll();	
}
