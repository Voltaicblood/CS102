/**************************************************************************/
/* Jacob Austin                                                           
/* Login ID: aust8558                                                      
/* CS-102, Winter 2017                                                    
/* Programming Assignment 4                                              
/* BSTIterator class: an iterator for binary search tree
/**************************************************************************/

import java.util.Iterator;
import java.util.Stack;

public class BSTIterator<T> implements Iterator<T>{
	Stack<TreeNode<T>> stack;
	 
	public BSTIterator(TreeNode<T> root) {
		stack = new Stack<TreeNode<T>>();
		while (root != null) {
			stack.push(root);
			root = root.getLeft();
		}
	}
	
	/**************************************************************/ 
	/* Method: hasNext								  
	/* Purpose: checks if there is another node		  
	/* Parameters: none									  
	/* Returns: boolean:			if there is another node in the tree							  
	/**************************************************************/
	public boolean hasNext() {
		return !stack.isEmpty();
	}
	
	/**************************************************************/ 
	/* Method: next								  
	/* Purpose: grabs the next datum in the next node of the list	  
	/* Parameters: none									  
	/* Returns: T:				the datum in the node							  
	/**************************************************************/
	public T next() {
		TreeNode<T> node = stack.pop();
		T result = node.getDatum();
		if (node.getRight() != null) {
			node = node.getRight();
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}
		}
		return result;
	}
}
