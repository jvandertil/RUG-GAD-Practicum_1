package nl.rug.gad.praktikum1.twofour;

import nl.rug.gad.praktikum1.ITree;
import nl.rug.gad.praktikum1.util.Profiler;

public class TwoFourTree implements ITree {

	private TwoFourNode root;
	private Profiler profiler;
	
	public TwoFourTree(Profiler p){
		root = new TwoFourNode(p);
		TwoFourNode external = new TwoFourNode(p);
		external.setExternal(true);
		external.setParent(root);
		root.addChild(external, 0, false);
		
		profiler = p;
	}
	
	public TwoFourTree() {
		this(Profiler.NULL);
	}
	
	public TwoFourNode insert(String text){
		TwoFourNode node = getNode(text);
		
		profiler.incAssignments();
		
		if(node.contains(text)){
			return node;
		}
		
		
		if(node.isExternal()){
			node = node.getParent();
			int index = node.addValue(text);
			TwoFourNode external = new TwoFourNode(profiler);
			
			external.setExternal(true);
			external.setParent(node);
			
			profiler.addAssignments(3);
			
			node.addChild(external, index + 1, false);
			rebalance(node);
		}
		
		return null;
	}
	
	private void rebalance(TwoFourNode node){
		if(node.getValueCount() > 3){
			TwoFourNode u;
			
			if(node.equals(root)){
				root = new TwoFourNode(profiler);
				u = root;
				
				profiler.addAssignments(2);
			} else {
				u = node.getParent();
				
				profiler.incAssignments();
			}
			
			TwoFourNode node1 = new TwoFourNode(profiler);
			profiler.incAssignments();
			
			node1.addValue(node.getValues().get(0));
			node1.addValue(node.getValues().get(1));
			
			if(node.getChildrenCount() == 0){
				TwoFourNode external1 = new TwoFourNode(profiler);
				profiler.incAssignments();
				
				external1.setExternal(true);
				external1.setParent(node1);
				node1.addChild(external1, 0, false);
				node1.addChild(external1, 1, false);
				node1.addChild(external1, 2, false);
			} else {
				node1.addChild(node.getChild(0), 0, false);
				node1.addChild(node.getChild(1), 1, false);
				node1.addChild(node.getChild(2), 2, false);
				node.getChild(0).setParent(node1);
				node.getChild(1).setParent(node1);
				node.getChild(2).setParent(node1);
			}
			
			TwoFourNode node2 = new TwoFourNode(profiler);
			profiler.incAssignments();
			
			node2.addValue(node.getValues().get(3));
			if(node.getChildrenCount() == 0){
				TwoFourNode external2 = new TwoFourNode(profiler);
				external2.setExternal(true);
				external2.setParent(node2);
				node2.addChild(external2, 0, false);
				node2.addChild(external2, 1, false);
			} else {
				node2.addChild(node.getChild(3), 0, false);
				node2.addChild(node.getChild(4), 1, false);
				node.getChild(3).setParent(node2);
				node.getChild(4).setParent(node2);
			}

			int index = u.addValue(node.getValues().get(2));
			profiler.incAssignments();
			
			u.addChild(node1, index, true);
			node1.setParent(u);
			u.addChild(node2, index + 1, false);
			node2.setParent(u);
			rebalance(u);			
		}
	}
	
	public TwoFourNode getNode(String text){
		TwoFourNode node = root;
		profiler.incAssignments();
		
		while(!node.contains(text) && !node.isExternal()){
			int i = 0;
			profiler.incAssignments();
			
			for(String s : node.getValues()){
				if(s != null){
					if(s.compareTo(text) < 0){
						i++;
						profiler.incAssignments();
					}
					profiler.incComparisons();
				}
			}
			
			node = node.getChild(i);
			profiler.incAssignments();
		}
		
		return node;
	}
}
