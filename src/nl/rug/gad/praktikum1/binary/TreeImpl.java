package nl.rug.gad.praktikum1.binary;

import nl.rug.gad.praktikum1.ITree;

public class TreeImpl implements ITree {
	private TreeNode root;

	@Override
	public TreeNode getNode(String key) {
		if (root == null)
			return null;

		TreeNode next = root;

		while (next != null) {
			if (key.equals(next.key))
				return next;
			else if (goesLeft(next, key))
				next = next.left;
			else
				next = next.right;
		}

		return null;
	}

	@Override
	public TreeNode insert(String key) {
		TreeNode node = getNode(key);
		
		if (node != null)
			return node;

		if (root == null) {
			root = new TreeNode(key);
		} else {
			boolean inserted = false;
			TreeNode current = root;

			while (!inserted) {
				if (goesLeft(current, key) && current.left == null) {
					// Smaller, and left child is free. Insert
					current.left = new TreeNode(key);
					current.left.parent = current;
					inserted = true;
				} else if (current.left != null) {
					// Smaller, but left child is not free. Continue.
					current = current.left;
				} else if (current.right == null) {
					// Bigger, and right child is free. Insert
					current.right = current;
					current.right.parent = current;
					inserted = true;
				} else {
					// Bigger, and right child is not free. Continue
					current = current.right;
				}
			}
		}
		
		return null;
	}

	private boolean goesLeft(TreeNode node, String key) {
		return node.key.compareTo(key) < 0;
	}

	public boolean delete(String key) {
		if (root == null)
			return false;

		TreeNode node = (TreeNode) getNode(key);

		if (node == null)
			return false;

		if (node.isLeaf()) { // Easy case, simply remove the reference.
			int comp = node.parent.compareTo(node);

			if (comp < 0) {
				// Verify
				assert (node.parent.left == node);
				node.parent.left = null;
				node.parent = null;
			} else {
				assert (node.parent.right == node);
				node.parent.right = null;
				node.parent = null;
			}

			return true;
		}

		if (node.childCount() == 1) {
			TreeNode child = node.left == null ? node.right : node.left;

			int comp = node.parent.compareTo(node);

			if (comp < 0) {
				assert (node.parent.left == node);
				node.parent.left = child;
				child.parent = node.parent;
				node.parent = null;
			} else {
				assert (node.parent.right == node);
				node.parent.right = child;
				child.parent = node.parent;
				node.parent = null;
			}

			return true;
		}
		
		TreeNode prev = node.parent;
		
		if (node.childCount() == 2) {
			TreeNode temp = inord(node);
			if (node == root) {
				root.key = temp.key;
			} else {
				if (goesLeft(node, prev.key)) {
					prev.left.key = temp.key;
				} else {
					prev.right.key = temp.key;
				}
			}
		}
		
		return true;
	}

	private TreeNode inord(TreeNode a) {

		int t = 0;
		TreeNode ret, prev = a;
		a = a.right;

		while (a.left != null) {
			prev = a;
			a = a.left;
			t = 1;
		}

		ret = a;

		/* ????? */
		if (t == 0) {
			prev.right = null;
		} else {
			prev.left = null;
		}

		a = null;
		return ret;
	}

}
