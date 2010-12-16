package nl.rug.gad.praktikum1.binary;

import nl.rug.gad.praktikum1.ITreeNode;

public class TreeNode implements Comparable<TreeNode>, ITreeNode {

	public String key;
	public TreeNode parent;
	public TreeNode left;
	public TreeNode right;
	
	public TreeNode(String key) {
		this.key = key;
	}

	@Override
	public int compareTo(TreeNode t) {
		return this.key.compareTo(t.key);
	}
	
	public boolean isLeaf() {
		return childCount() == 0;
	}
	
	public int childCount() {
		int count = 0;
		
		if(left != null)
			count++;
		
		if(right != null)
			count++;
		
		return count;
	}

	@Override
	public String getKey() {
		return key;
	}
}
