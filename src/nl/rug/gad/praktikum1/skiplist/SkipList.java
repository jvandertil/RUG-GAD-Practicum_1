package nl.rug.gad.praktikum1.skiplist;

public class SkipList {
	
	private SkipNode first = new SkipNode("");
	private SkipNode last = new SkipNode("");
	
	public SkipList(){
		first.setIsFirst();
		last.setIsLast();
		first.setAfter(last);
		last.setBefore(last);
	}
	
	public void skipInsert(String text){
		SkipNode p = skipSearch(text);
		if(p.compareTo(text) == 0){
			return;
		}
		SkipNode q = insertAfterAbove(p, SkipNode.NullNode, text);
		while(Math.random() < 0.5){
			while(p.above().equals(SkipNode.NullNode)){
				p = p.before();
			}
			p = p.above();
			q = insertAfterAbove(p, q, text);
		}
	}
	
	public void skipRemove(String text){
		SkipNode node = skipSearch(text);
		if(node.compareTo(text) == 0){
			while(!node.equals(SkipNode.NullNode)){
				node.before().setAfter(node.after());
				node.after().setBefore(node.before());
				node = node.above();
			}
		}
	}
	
	/*
	 * set new node after p and above q
	 */
	public SkipNode insertAfterAbove(SkipNode p, SkipNode q, String text){
		SkipNode newNode = new SkipNode(text);
		SkipNode pAfter = p.after();
		
		pAfter.setBefore(newNode);
		newNode.setAfter(pAfter);
		newNode.setBefore(p);
		p.setAfter(newNode);
		q.setAbove(newNode);
		newNode.setBelow(q);
		newNode.setAbove(SkipNode.NullNode);
		
		//Create new empty list on top if there is none (two infinite nodes (min and max))
		if(newNode.before().isFirst() && newNode.before().above().equals(SkipNode.NullNode) ){
			SkipNode newFirstNode = new SkipNode("");
			newFirstNode.setIsFirst();
			SkipNode newLastNode = new SkipNode("");
			newLastNode.setIsLast();
			newFirstNode.setAfter(newLastNode);
			newLastNode.setBefore(newFirstNode);
			
			newFirstNode.setBelow(newNode.before());
			newNode.before().setAbove(newFirstNode);
			
			SkipNode iterator = newNode;
			while(!iterator.after().equals(SkipNode.NullNode)){
				iterator = iterator.after();
			}
			newLastNode.setBelow(iterator);
			
			first = newFirstNode;
			last = newLastNode;
		}
		
		return newNode;
	}
	
	public SkipNode skipSearch(String text){
		SkipNode p = first;
		while(!p.below().equals(SkipNode.NullNode)){
			p = p.below();
			while(p.after().compareTo(text) <= 0){
				p = p.after();
			}
		}
		return p;
	}
}
