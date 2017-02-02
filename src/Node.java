/**************************************************************************/
/* Jacob Austin                                                           */
/* Login ID: aust8558                                                     */ 
/* CS-102, Winter 2017                                                    */
/* Programming Assignment 1                                               */
/* Node class: holds an object and points to another node for LinkedLists */
/**************************************************************************/

public class Node {
	//next Node in list
	Node next;
	//object being held in node
	Object datum;
	
	//no args constructor
	public Node(){
		next = null;
		datum = null;
	}
	
	//constructor with both next Node and the object
	public Node(Node next, Object datum){
		this.next = next;
		this.datum = datum;
	}
	
	/**************************************************************/ 
	/* Method: get(variable)										 
	/* Purpose: returns the variable of the node			 
	/* Parameters: none											 
	/* Returns: Object or Node									 
	/**************************************************************/
	public Node getNext() {
		return next;
	}
	
	/**************************************************************/ 
	/* Method: set(variable)									  
	/* Purpose: assigns the value of the variable to the inputed value			  
	/* Parameters: 
	/* 		Node/Object (variable name):	value to assign										  
	/* Returns: none								  
	/**************************************************************/
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Object getDatum() {
		return datum;
	}

	public void setDatum(Object datum) {
		this.datum = datum;
	}
	
}