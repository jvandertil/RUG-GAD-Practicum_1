package nl.rug.gad.praktikum1.avl;

import nl.rug.gad.praktikum1.ITree;
import nl.rug.gad.praktikum1.util.Profiler;

public class AVLTree implements ITree {

	private AVLNode root;
	private AVLNode first; /* Left most node */
	private AVLNode last; /* Right most node */
	private int height;
	
	private Profiler profiler;
	
	public AVLTree() {
		this(Profiler.NULL);
	}
	
	public AVLTree(Profiler p) {
		profiler = p;
	}
	
	@Override
	public AVLNode getNode(String key) {
		return findInsertionPoint(key).node;
	}
	
	private class InsertionInfo {
		public AVLNode node;
		public AVLNode parent;
		public AVLNode unbalanced;
		public boolean isLeft;
	}
	
	private InsertionInfo findInsertionPoint(String key) {
		AVLNode node = root;
		int cmpRes = 0;
		
		profiler.addAssignments(2);
		
		InsertionInfo result = new InsertionInfo();
		
		result.node = null;
		result.unbalanced = node;
		result.isLeft = false;
		result.parent = null;
		
		profiler.addAssignments(4);
		
		while(node != null) {
			if(node.getBalance() != 0)
				result.unbalanced = node;
			
			profiler.incComparisons();
			cmpRes = node.getKey().compareTo(key);
			
			if(cmpRes == 0) { /* Node key is equal to key */
				result.node = node;
				profiler.incAssignments();
				return result;
			}
			
			profiler.incAssignments();
			result.parent = node; /* 
								   * Assignment done here. This is because if the key is found at the first iteration, the key is in the root node. 
								   * Root nodes don't have parents. Saves an assignment.
								   */
			
			if((result.isLeft = cmpRes > 0)) { /* Node is smaller then key, go left. Save this */
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
			
			profiler.incAssignments();
		}
		
		return result;
	}
	
	/* Balancing functions */
	private void rotateLeft(AVLNode node) {
		/*
		 * Let Q be P's right child.
		 * Set Q to be the new root.
		 * Set P's right child to be Q's left child.
		 * Set Q's left child to be P.
		 */
		AVLNode p = node; /* Pivot node */
		AVLNode q = node.getRight();
		AVLNode parent = node.getParent();
		
		profiler.addAssignments(3);
		
		if(!p.isRoot()) {
			if(parent.getLeft() == p)
				parent.setLeft(q);
			else
				parent.setRight(q);
		} else {
			root = q;
		}
		
		profiler.incAssignments();
		
		q.setParent(parent);
		p.setParent(q);
		p.setRight(q.getLeft());
		
		profiler.addAssignments(3);
		
		if(p.hasRight()) {
			p.getRight().setParent(p);
			profiler.incAssignments();
		}
		
		q.setLeft(p);
		
		profiler.incAssignments();
	}
	
	private void rotateRight(AVLNode node) {
		/* 
		 * Let P be Q's left child.
		 * Set P to be the new root.
		 * Set Q's left child to be P's right child.
		 * Set P's right child to be Q.
		 */
		AVLNode q = node; /* Pivot node */
		AVLNode p = node.getLeft();
		AVLNode parent = q.getParent();
		
		profiler.addAssignments(3);
		
		if(!q.isRoot()) {
			if(parent.getLeft() == q)
				parent.setLeft(p);
			else
				parent.setRight(p);
		} else {
			root = p;
		}
		
		profiler.incAssignments();
		
		p.setParent(parent);
		q.setParent(p);
		q.setLeft(p.getRight());
		
		profiler.addAssignments(3);
		
		if(q.hasLeft()) {
			q.getLeft().setParent(q);
			
			profiler.incAssignments();
		}
		
		p.setRight(q);
		
		profiler.incAssignments();
	}

	@Override
	public AVLNode insert(String key) {
		InsertionInfo info = findInsertionPoint(key);
		
		profiler.incAssignments();
		
		if(info.node != null) /* Key already in tree. Return node. */
			return info.node;
		
		AVLNode node = new AVLNode(key);
		
		profiler.incAssignments();
		
		if(info.parent == null) { /* No root */
			root = node;
			first = last = node;
			height++;
			
			profiler.addAssignments(3);
			/*
			 * if height++ should also becounted as an assignment:
			 */
			//profiler.incAssignments();
			return null;
		}
		
		AVLNode parent = info.parent;
		
		profiler.incAssignments();
		
		if(info.isLeft) {
			if(parent == first) {
				first = node;
				profiler.incAssignments();
			}
		} else {
			if(parent == last) {
				last = node;
				profiler.incAssignments();
			}
		}
		
		node.setParent(parent);
		
		if(info.isLeft) {
			parent.setLeft(node);
		} else {
			parent.setRight(node);
		}
		
		AVLNode unbalanced = info.unbalanced;
		
		profiler.addAssignments(3);
		
		for(;;) { /* Update balance values. */
			if(parent.getLeft() == node)
				parent.decBalance();
			else
				parent.incBalance();
			
			/*
			 * if decBalance and incBalance are also assignments
			 */
			//profiler.incAssignments();
			
			if(parent == unbalanced)
				break;
			
			node = parent;
			parent = parent.getParent();
			
			profiler.addAssignments(2);
		}
		
		switch(unbalanced.getBalance()) {
		case 1:
			/* fall through */
		case -1:
			height++; /* Update tree height */
			//profiler.incAssignments();
			/* fall through */
		case 0:
			break;
			
		case 2: /* Tree unbalanced to the right. */
			AVLNode right = unbalanced.getRight();
			
			if(right.getBalance() == 1) {
				unbalanced.setBalance(0);
				right.setBalance(0);
			} else {
				switch(right.getLeft().getBalance()) {
				case 1:
					unbalanced.setBalance(-1);
					right.setBalance(0);
					break;
				case 0:
					unbalanced.setBalance(0);
					right.setBalance(0);
					break;
				case -1:
					unbalanced.setBalance(0);
					right.setBalance(1);
					break;
				}
				
				right.getLeft().setBalance(0);
				
				rotateRight(right);
			}
			rotateLeft(unbalanced);
			break;
			
		case -2: /* Tree unbalanced to the left. */
			AVLNode left = unbalanced.getLeft();
			
			if(left.getBalance() == -1) {
				unbalanced.setBalance(0);
				left.setBalance(0);
			} else {
				switch(left.getRight().getBalance()) {
				case 1:
					unbalanced.setBalance(0);
					left.setBalance(-1);
					break;
				case 0:
					unbalanced.setBalance(0);
					left.setBalance(0);
					break;
				case -1:
					unbalanced.setBalance(1);
					left.setBalance(0);
				}
				left.getRight().setBalance(0);
				
				rotateLeft(left);
			}
			rotateRight(unbalanced);
			break;
		}
		
		return null;
	}
}
