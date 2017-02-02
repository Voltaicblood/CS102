/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 1                                               */
/* LinkedList class: points to a head Node object, 						  */
/* 					and allows manipulation of list 					  */
/**************************************************************************/

public class LinkedList implements ListInterface{
	//first node in list
	Node head;
	
	//no args constructor
	public LinkedList(){head = null;}
	
	/**************************************************************/ 
	/* Method: getHead								  
	/* Purpose: retrieves the head node			  
	/* Parameters: none									  
	/* Returns: Node:				the head of the list								  
	/**************************************************************/
	public Node getHead(){
		return head;
	}
	
	/**************************************************************/ 
	/* Method: isEmpty								  
	/* Purpose: checks if the list is empty		  
	/* Parameters: none									  
	/* Returns: boolean:			is the list empty?								  
	/**************************************************************/
	public boolean isEmpty() {
		return (head==null);
	}
	
	/**************************************************************/ 
	/* Method: size							  
	/* Purpose: helper method to find size of list		  
	/* Parameters: none									  
	/* Returns: int:				size of list								  
	/**************************************************************/
	public int size() {
		return size(head);
	}
	
	/**************************************************************/ 
	/* Method: size							  
	/* Purpose: recursive method to find size of list		  
	/* Parameters: 
	/*		Node current:			Node being searched
	/* Returns: int:				size of list								  
	/**************************************************************/
	private int size(Node current){
		//end of the list
		if (current == null) return 0;
		//return 1 + size of the rest of list
		return 1 + size(current.getNext());
	}
	
	/**************************************************************/ 
	/* Method: size							  
	/* Purpose: helper method to grab datum in specified spot of list		  
	/* Parameters:
	/* 		int index:				location of item in list
	/* Returns: Object:				datum of specified Node												  
	/**************************************************************/
	public Object get(int index) {
		//negative index or index over size of list throws exception
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException();
		return get(index, head);
	}
	
	/**************************************************************/ 
	/* Method: size							  
	/* Purpose: recursive method to grab datum in specified spot of list		  
	/* Parameters:
	/* 		int index:				location of item in list
	/* 		Node current:			node being searched
	/* Returns: Object:				datum of specified Node												  
	/**************************************************************/
	private Object get(int index, Node current){
		//if we have reached the node, return the datum
		if(index == 0)
			return current.getDatum();
		//otherwise, search the next node and decrease index
		return get((index - 1), current.getNext());
	}
	
	/**************************************************************/ 
	/* Method: add						  
	/* Purpose: helper method to add a Node to the list	  
	/* Parameters:
	/* 		int index:				location to add Node
	/*		Object datum:			object to add in list
	/* Returns: none											  
	/**************************************************************/
	public void add(int index, Object datum) {
		head = add(index, datum, head);
	}
	
	/**************************************************************/ 
	/* Method: add						  
	/* Purpose: recursive method to add a Node to the list	  
	/* Parameters:
	/* 		int index:				location to add Node
	/*		Object datum:			object to add in list
	/*		Node current:			current Node being parsed
	/* Returns: none											  
	/**************************************************************/
	private Node add(int index, Object datum, Node current){
		//if index is reached, add the new Node
		if (index == 0){
			Node splice = new Node();
			splice.setDatum(datum);
			splice.setNext(current);
			//set head to splice
			return splice;
		}
		//at the end of the list, throw exception
		if (current == null)
			throw new IndexOutOfBoundsException();
		//otherwise, parse the next Node, drop index by 1
		current.setNext(add(index-1, datum, current.getNext()));
		//set head to current
		return current;
	}
	
	/**************************************************************/ 
	/* Method: remove					  
	/* Purpose: helper method to remove a Node from the list  
	/* Parameters:
	/* 		int index:				location to remove Node
	/* Returns: Object:				removed object											  
	/**************************************************************/
	public Object remove(int index) {
		Object removedData = get(index);
		head = remove(index, head);
		return removedData;
	}
	
	/**************************************************************/ 
	/* Method: remove					  
	/* Purpose: recursive method to remove a Node from the list  
	/* Parameters:
	/* 		int index:				location to remove Node
	/*		Node current:			current Node being parsed 
	/* Returns: Object:				removed object											  
	/**************************************************************/
	private Node remove(int index, Node current){
		//add the end of the list, throw exception
		if (current == null)
			throw new IndexOutOfBoundsException();
		//reached the index, drop the item
		if (index == 0)
			return current.getNext();
		current.setNext(remove(index-1, current.getNext()));
		return current;
	}
	
	/**************************************************************/ 
	/* Method: remove					  
	/* Purpose: drops the entire list
	/* Parameters: none
	/* Returns: none											  
	/**************************************************************/
	public void removeAll() {
		head = null;
	}

	/**************************************************************/ 
	/* Method: isSortedAlph					  
	/* Purpose: helper method to check if the list is sorted alphabetically  
	/* Parameters: none
	/* Returns: boolean:			is the list sorted?										  
	/**************************************************************/
	public boolean isSortedAlph(){
		return isSortedAlph(head);
	}
	
	/**************************************************************/ 
	/* Method: isSortedAlph					  
	/* Purpose: recursive method to check if the list is sorted alphabetically  
	/* Parameters:
	/* 		Node current:			current Node being parsed
	/* Returns: boolean:			is the list sorted?										  
	/**************************************************************/
	private boolean isSortedAlph(Node current){
		//at the end of the list, return true
		if (current.getNext() == null) return true;
		//current Node's call sign being compared
		String currentCallsign = ((Station) current.getDatum()).getCallSign();
		//next Node's call sign being compared
		String nextCallsign = ((Station) current.getNext().getDatum()).getCallSign();
		//if they are in incorrect order, return false
		if (currentCallsign.compareTo(nextCallsign) > 0) return false;
		//otherwise, check the new Node
		return isSortedAlph(current.getNext());
	}
	
	/**************************************************************/ 
	/* Method: alphabeticalSort				  
	/* Purpose: helper method to sort the list alphabetically
	/* Parameters: none
	/* Returns: none									  
	/**************************************************************/
	public void alphabeticalSort(){
		//while it is not sorted, sort it more
		while (!isSortedAlph()){
			head = alphabeticalSort(head);
		}
	}
	
	/**************************************************************/ 
	/* Method: alphabeticalSort				  
	/* Purpose: recursive method to sort the list alphabetically 
	/* Parameters:
	/* 		Node current:			current Node being parsed
	/* Returns: Node:				returns a Node to be set as head													  
	/**************************************************************/
	private Node alphabeticalSort(Node current){
		//the node after current
		Node next = current.getNext();
		//if the next node is the end of the list, return current;
		if (next == null) return current;
		//current node's call sign being compared
		String currentCallsign = ((Station) current.getDatum()).getCallSign();
		//next node's call sign being compared
		String nextCallsign = ((Station) next.getDatum()).getCallSign();
		//the comparison
		int comparison = currentCallsign.compareTo(nextCallsign);
		//if they are in the wrong order, reorder them
		if (comparison > 0){
			current.setNext(next.getNext());
			next.setNext(alphabeticalSort(current));
			return next;
		}
		//otherwise, sort the next node
		alphabeticalSort(next);
		return current;
	}
}
