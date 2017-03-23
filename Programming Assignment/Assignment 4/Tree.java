/**************************************************************************/
/* Jacob Austin                                                           
/* Login ID: aust8558                                                      
/* CS-102, Winter 2017                                                    
/* Programming Assignment 4                                              
/* Tree class: a collection of items organized in a tree
/**************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tree<T extends Comparable<? super T>> implements Iterable<T>{
	
	TreeNode<T> root;
	public Tree(){
		root = null;
	}
	
	public Iterator<T> iterator(){
		return new BSTIterator<T>(root);
	}
	
	/**************************************************************/
	/* Method: search								  
	/* Purpose: finds a value and returns true if it is there	  
	/* Parameters: T target:	what it is looking for
	/* Returns: boolean:		whether the target exists or not
	/**************************************************************/
	public boolean search(T target){
		TreeNode<T> current = root;
		while(current!= null){
			if (current.getDatum().equals(target))
				return true;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else current = current.getLeft();
		}
		return false;
	}
	
	/**************************************************************/
	/* Method: add								  
	/* Purpose: add a new object to the list  
	/* Parameters: T target:	what it is adding
	/* Returns: none
	/**************************************************************/
	public void add(T target){
		TreeNode<T> current = root;
		TreeNode<T> previous = null;
		while(current != null){
			previous = current;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else current = current.getLeft();
		}
		TreeNode<T> leaf = new TreeNode<T>(target);
		if (previous == null)
			root = leaf;
		else if (previous.getDatum().compareTo(target) < 0)
			previous.setRight(leaf);
		else
			previous.setLeft(leaf);
	}
	
	/**************************************************************/
	/* Method: remove							  
	/* Purpose: removes a object from the list 
	/* Parameters: T target:	what it is removing
	/* Returns: none
	/**************************************************************/
	public void remove(T target){
		TreeNode<T> current = root;
		TreeNode<T> previous = null;
		while((current != null) && !(current.getDatum().equals(target))){
			previous = current;
			if (current.getDatum().compareTo(target) < 0)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		if (current == null)
			throw new NoSuchElementException();
		if (current.getRight() == null){
			if (previous == null)
				root = current.getRight();
			else if (previous.getLeft() == current)
				previous.setLeft(current.getLeft());
			else
				previous.setRight(current.getLeft());
		}
		else if (current.getLeft() == null){
			if (previous == null)
				root = current.getRight();
			else if (previous.getLeft() == current)
				previous.setLeft(current.getRight());
			else
				previous.setRight(current.getRight());
		}
		else{
			previous = current;
			TreeNode<T> heir = current.getLeft();
			while(heir.getRight() != null){
				previous = heir;
				heir = heir.getRight();
			}
			current.setDatum(heir.getDatum());
			if (previous.getLeft() == heir)
				previous.setLeft(heir.getLeft());
			else 
				previous.setLeft(heir.getLeft());
		}
	}
	
	/**************************************************************/
	/* Method: isEmpty							  
	/* Purpose: check if the list is empty
	/* Parameters: none
	/* Returns: boolean:			true if the list is empty
	/**************************************************************/
	public boolean isEmpty(){
		if (root == null)
			return true;
		return false;
	}
	
	/**************************************************************/
	/* Method: preOrderString								  
	/* Purpose: helper; returns a string made from the tree's stations
	/* Parameters: none
	/* Returns: String:			string from the tree's station objects
	/**************************************************************/
	public String preOrderString(){
		return preOrderString(root);	
	}
	
	/**************************************************************/
	/* Method: preOrderString							  
	/* Purpose: recursive; returns a string made from the tree's stations
	/* Parameters: TreeNode<T> current:	which node it is currently parsing
	/* Returns: String:					string from the tree's station objects
	/**************************************************************/
	private String preOrderString(TreeNode<T> current){
		//String being edited
		String onGoing;
		//end of tree branch
		if (current == null)
			return "";
		//current station of current node
		Station currentStation = (Station)current.getDatum();
		//add the current node's string
		onGoing = currentStation.toStringOrig();
		//add the left branches's string
		onGoing += preOrderString(current.getLeft());
		//add the right branches's string
		onGoing += preOrderString(current.getRight());
		return onGoing;
	}
}
