/******************************************************************************/
/* Jacob Austin                                                          
/* Login ID: aust8558                                                     
/* CS-102, Winter 2017                                                    
/* Programming Assignment 4                                                  
/* TreeNode class: holds an object and points to it's children in a tree
/******************************************************************************/

public class TreeNode<T> {
	T datum;
	TreeNode<T> left;
	TreeNode<T> right;
	
	public TreeNode(){
		left = null;
		right = null;
	}
	
	public TreeNode(T datum){
		this.datum = datum;
		left = null;
		right = null;
	}
	
	/**************************************************************/ 
	/* Method: get(variable)										 
	/* Purpose: returns the variable of the station				 
	/* Parameters: none											 
	/* Returns: T or TreeNode									 
	/**************************************************************/
	public T getDatum() {
		return datum;
	}
	
	/**************************************************************/ 
	/* Method: set(variable)									  
	/* Purpose: assigns the value of the variable to the inputed value			  
	/* Parameters: 
	/* 		TreeNode/T (variable name):	value to assign										  
	/* Returns: none								  
	/**************************************************************/
	public void setDatum(T datum) {
		this.datum = datum;
	}
	
	public TreeNode<T> getLeft() {
		return left;
	}
	
	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}
	
	public TreeNode<T> getRight() {
		return right;
	}
	
	public void setRight(TreeNode<T> right) {
		this.right = right;
	}
}
