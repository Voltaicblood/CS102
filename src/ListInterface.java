/***************************************************************************************/
/* Jacob Austin                                                           		       */
/* Login ID: aust8558                                                     		       */ 
/* CS-102, Winter 2017                                                    		       */
/* Programming Assignment 1                                               		       */
/* List interface: methods for all list classes                                */
/***************************************************************************************/

public interface ListInterface {
	public boolean isEmpty();
	public int size();
	public Object get(int index);
	public void add(int index, Object datum);
	public Object remove(int index);
	public void removeAll();
	public boolean isSortedAlph();
	public void alphabeticalSort();
}
