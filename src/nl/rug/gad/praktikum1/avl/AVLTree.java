package nl.rug.gad.praktikum1.avl;

public class AVLTree {

	private AVLNode root;
	private AVLNode first;
	private AVLNode last;
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
			
			if(cmpRes == 0) {
				result.node = node;
				return result;
			}
			
			result.parent = node;
			
			if((result.isLeft = cmpRes > 0)) { /* Node is smaller then key, go right */
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}
		
		return null;
	}
	
	/* Balancing functions */
	private void rotate_left(AVLNode node) {
		AVLNode p = node;
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
	
	private void rotate_right(AVLNode node) {
		AVLNode p = node;
		AVLNode q = node.getLeft();
		AVLNode parent = p.getParent();
		
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
		
		p.setLeft(q.getRight());
		
		if(p.hasLeft())
			p.getLeft().setParent(p);
	}

	public AVLNode insert(String key) {
		InsertionInfo info = findInsertionPoint(key);
		
		if(info.node != null)
			return info.node;
		
		AVLNode node = new AVLNode(key);
		
		if(info.parent == null) {
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
		
		for(;;) {
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
			height++;
			/* fall through */
		case 0:
			break;
			
		case 2:
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
				
				rotate_right(right);
			}
			rotate_left(unbalanced);
			break;
			
		case -2:
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
				
				rotate_left(left);
			}
			rotate_right(unbalanced);
			break;
		}
		return null;
	}
	
}
