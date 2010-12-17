package nl.rug.gad.praktikum1.twofour;

import java.util.LinkedList;
import java.util.List;

import nl.rug.gad.praktikum1.ITreeNode;
import nl.rug.gad.praktikum1.util.Profiler;

public class TwoFourNode implements ITreeNode {

	private LinkedList<String> valuesList = new LinkedList<String>();
	private LinkedList<TwoFourNode> childrenList = new LinkedList<TwoFourNode>();
	private TwoFourNode parent = null;
	
	private Profiler profiler;
	
	private boolean external = false;
	
	public TwoFourNode(Profiler p) {
		profiler = p;
	}
	
	public String getKey() {
		throw new IllegalAccessError("Use cast & getValues, damn 2-4 trees...");
	}
	
	public int addValue(String text){
		int index = 0;
		profiler.incAssignments();
		
		for(String s : valuesList){
			if(s != null){
				profiler.incComparisons();
				if(s.compareTo(text) <= 0) {
					index++;
					profiler.incAssignments();
				}
			}
		}
		
		valuesList.add(index, text); 		//Counted as 1 assignment.
		profiler.incAssignments();
		
		return index;
	}
	
	public List<String> getValues() {
		return valuesList;
	}
	
	public int getValueCount(){
		int count = 0;
		for(String s : valuesList){
			if(s != null){
				profiler.incAssignments();
				count++;
			}
		}
		return count;
	}
	
	public void removeValue(String text){
		valuesList.remove(text); //Counted as 1 assignment.
		
		profiler.incAssignments();
	}
	
	public int getChildrenCount(){
		return childrenList.size();
	}
	
	public boolean contains(String text){
		for(String s : valuesList) {
			profiler.incComparisons();
			
			if(s.equals(text))
				return true;
		}
		
		return false;
	}
	
	/*
	 * if replace is true the child will will be replaced by the new child
	 * else the other childs will shift one to the right
	 */
	public void addChild(TwoFourNode child, int number, boolean replace){		
		if(replace && childrenList.size() > 0){
			childrenList.remove(number); //Counted as 1 assignment.
			profiler.incAssignments();
		}
		
		childrenList.add(number, child); //Counted as 1 assignment.
		profiler.incAssignments();
	}
	
	public TwoFourNode getChild(int number){
		return childrenList.get(number);
	}
	
	public List<TwoFourNode> getChildren(){
		return childrenList;
	}

	public void setParent(TwoFourNode parent) {
		this.parent = parent;
		profiler.incAssignments();
	}

	public TwoFourNode getParent() {
		return parent;
	}

	public void setExternal(boolean external) {
		this.external = external;
		profiler.incAssignments();
	}

	public boolean isExternal() {
		return external;
	}
}
