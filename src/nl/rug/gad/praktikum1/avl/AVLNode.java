package nl.rug.gad.praktikum1.avl;

import nl.rug.gad.praktikum1.ITreeNode;

public class AVLNode implements ITreeNode {

	private AVLNode parent;
	private AVLNode left;
	private AVLNode right;

	private String key;

	private int balance; /* Range: -2 -> +2 */

	public AVLNode(String key) {
		this.key = key;
		parent = null;
		left = null;
		right = null;
	}

	/* Start balance methods */
	
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void incBalance() {
		++this.balance;
	}

	public void decBalance() {
		--this.balance;
	}

	/* End balance methods */

	/* Node methods */
	public AVLNode getParent() {
		return parent;
	}

	public void setParent(AVLNode parent) {
		this.parent = parent;
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public AVLNode getLeft() {
		return left;
	}

	public void setLeft(AVLNode left) {
		this.left = left;
	}

	public AVLNode getRight() {
		return right;
	}

	public void setRight(AVLNode right) {
		this.right = right;
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	/* Key methods */

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
