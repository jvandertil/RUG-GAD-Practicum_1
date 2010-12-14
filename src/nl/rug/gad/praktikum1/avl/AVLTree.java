package nl.rug.gad.praktikum1.avl;

public class AVLTree {

	private AVLNode root;
	private AVLNode first; /* Left most node */
	private AVLNode last; /* Right most node */
	private int height;
	
	public AVLNode findKey(String key) {
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
		
		InsertionInfo result = new InsertionInfo();
		
		result.node = null;
		result.unbalanced = node;
		result.isLeft = false;
		result.parent = null;
		
		while(node != null) {
			if(node.getBalance() != 0)
				result.unbalanced = node;
			
			cmpRes = node.getKey().compareTo(key);
			
			if(cmpRes == 0) { /* Node key is equal to key */
				result.node = node;
				return result;
			}
			
			result.parent = node; /* 
								   * Assignment done here. This is because if the key is found at the first iteration, the key is in the root node. 
								   * Root nodes don't have parents. Saves an assignment.
								   */
			
			if((result.isLeft = cmpRes > 0)) { /* Node is smaller then key, go left. Save this */
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
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
		
		if(!p.isRoot()) {
			if(parent.getLeft() == p)
				parent.setLeft(q);
			else
				parent.setRight(q);
		} else {
			root = q;
		}
		
		q.setParent(parent);
		p.setParent(q);
		p.setRight(q.getLeft());
		
		if(p.hasRight())
			p.getRight().setParent(p);
		
		q.setLeft(p);
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
		
		if(!q.isRoot()) {
			if(parent.getLeft() == q)
				parent.setLeft(p);
			else
				parent.setRight(p);
		} else {
			root = p;
		}
		
		p.setParent(parent);
		q.setParent(p);
		
		q.setLeft(p.getRight());
		
		if(q.hasLeft())
			q.getLeft().setParent(q);
		
		p.setRight(q);
	}

	public AVLNode insert(String key) {
		InsertionInfo info = findInsertionPoint(key);
		
		if(info.node != null) /* Key already in tree. Return node. */
			return info.node;
		
		AVLNode node = new AVLNode(key); 
		
		if(info.parent == null) { /* No root */
			root = node;
			first = last = node;
			height++;
			
			return null;
		}
		
		AVLNode parent = info.parent;
		
		if(info.isLeft) {
			if(parent == first)
				first = node;
		} else {
			if(parent == last)
				last = node;
		}
		
		node.setParent(parent);
		if(info.isLeft) {
			parent.setLeft(node);
		} else {
			parent.setRight(node);
		}
		
		AVLNode unbalanced = info.unbalanced;
		
		for(;;) { /* Update balance values. */
			if(parent.getLeft() == node)
				parent.decBalance();
			else
				parent.incBalance();
			
			if(parent == unbalanced)
				break;
			
			node = parent;
			parent = parent.getParent();
		}
		
		switch(unbalanced.getBalance()) {
		case 1:
			/* fall through */
		case -1:
			height++; /* Update tree height */
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
