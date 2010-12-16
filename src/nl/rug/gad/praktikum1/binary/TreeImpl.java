package nl.rug.gad.praktikum1.binary;

import nl.rug.gad.praktikum1.ITree;
import nl.rug.gad.praktikum1.util.Profiler;

public class TreeImpl implements ITree {
	private TreeNode root;
	
	private Profiler profiler;
	
	public TreeImpl() {
		this(Profiler.NULL);
	}
	
	public TreeImpl(Profiler p) {
		this.profiler = p;
	}

	@Override
	public TreeNode getNode(String key) {
		if (root == null)
			return null;

		TreeNode next = root;
		
		profiler.incAssignments();
		
		int comp;

		while (next != null) {
			comp = key.compareTo(next.key);
			profiler.incComparisons()
					.incAssignments();
			
			if (comp == 0)
				return next;
			else if (comp < 0) {
				next = next.left;
				profiler.incAssignments();
			}
			else {
				next = next.right;
				profiler.incAssignments();
			}
		}

		return null;
	}

	@Override
	public TreeNode insert(String key) {
		TreeNode node = getNode(key);
		
		profiler.incAssignments();
		
		if (node != null)
			return node;

		if (root == null) {
			root = new TreeNode(key);
			profiler.incAssignments();
		} else {
			boolean inserted = false;
			TreeNode current = root;
			
			profiler.addAssignments(2);
			
			int compRes;

			while (!inserted) {
				compRes = key.compareTo(current.key);
				profiler.incComparisons().incAssignments();
				
				if (compRes < 0 && current.left == null) {
					// Smaller, and left child is free. Insert
					current.left = new TreeNode(key);
					current.left.parent = current;
					inserted = true;
					
					profiler.addAssignments(3);
				} else if (compRes < 0 && current.left != null) {
					// Smaller, but left child is not free. Continue.
					current = current.left;
					profiler.incAssignments();
				} else if (compRes > 0 && current.right == null) {
					// Bigger, and right child is free. Insert
					current.right = current;
					current.right.parent = current;
					inserted = true;
					
					profiler.addAssignments(3);
				} else {
					// Bigger, and right child is not free. Continue
					current = current.right;
					
					profiler.incAssignments();
				}
			}
		}
		
		return null;
	}
}
