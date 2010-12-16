package nl.rug.gad.praktikum1.twofour;

import java.util.LinkedList;

public class TwoFourNode implements ITree {

	private LinkedList<String> valuesList = new LinkedList<String>();
	private LinkedList<TwoFourNode> childrenList = new LinkedList<TwoFourNode>();
	private TwoFourNode parent = null;
	
	private boolean external = false;
	
	public TwoFourNode(){
		
	}
	
	public int addValue(String text){
		int index = 0;
		for(String s : valuesList){
			if(s != null){
				if(s.compareTo(text) <= 0) index++;
			}
		}
		valuesList.add(index, text);
		return index;
	}
	
	public LinkedList<String> getValues() {
		return valuesList;
	}
	
	public int getValueCount(){
		int count = 0;
		for(String s : valuesList){
			if(s != null){
				count++;
			}
		}
		return count;
	}
	
	public void removeValue(String text){
		valuesList.remove(text);
	}
	
	public int getChildrenCount(){
		return childrenList.size();
	}
	
	public boolean contains(String text){
		return valuesList.contains(text);
	}
	
	/*
	 * if replace is true the child will will be replaced by the new child
	 * else the other childs will shift one to the right
	 */
	public void addChild(TwoFourNode child, int number, boolean replace){		
		if(replace && childrenList.size() > 0){
			childrenList.remove(number);
		}
		childrenList.add(number, child);
	}
	
	public TwoFourNode getChild(int number){
		return childrenList.get(number);
	}
	
	public LinkedList<TwoFourNode> getChildren(){
		return childrenList;
	}

	public void setParent(TwoFourNode parent) {
		this.parent = parent;
	}

	public TwoFourNode getParent() {
		return parent;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public boolean isExternal() {
		return external;
	}
}
