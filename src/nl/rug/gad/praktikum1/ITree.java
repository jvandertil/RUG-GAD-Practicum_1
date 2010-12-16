package nl.rug.gad.praktikum1;

public interface ITree {
	ITreeNode getNode(String key);
	ITreeNode insert(String key);
}
