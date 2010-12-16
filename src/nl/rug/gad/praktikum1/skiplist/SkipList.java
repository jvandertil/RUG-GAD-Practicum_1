package nl.rug.gad.praktikum1.skiplist;

import nl.rug.gad.praktikum1.ITree;
import nl.rug.gad.praktikum1.ITreeNode;
import nl.rug.gad.praktikum1.util.Profiler;

public class SkipList implements ITree {

	private SkipNode first = new SkipNode("");
	private SkipNode last = new SkipNode("");

	private Profiler profiler;

	public SkipList(Profiler p) {
		first.setIsFirst();
		last.setIsLast();
		first.setAfter(last);
		last.setBefore(last);

		profiler = p;
	}

	public SkipList() {
		this(Profiler.NULL);
	}

	public ITreeNode insert(String text) {
		SkipNode p = getNode(text);
		if (p.compareTo(text) == 0) {
			return p;
		}

		SkipNode q = insertAfterAbove(p, SkipNode.NullNode, text);
		while (Math.random() < 0.5) {
			while (p.above() == SkipNode.NullNode) {
				p = p.before();
				profiler.incAssignments();
			}

			p = p.above();
			q = insertAfterAbove(p, q, text);

			profiler.addAssignments(2);
		}

		return null;
	}

	public void remove(String text) {
		SkipNode node = getNode(text);

		profiler.incAssignments();

		if (node.compareTo(text) == 0) {
			profiler.incComparisons();
			while (!(node == SkipNode.NullNode)) {
				node.before().setAfter(node.after());
				node.after().setBefore(node.before());
				node = node.above();

				profiler.addAssignments(3);
			}
		}
	}

	/*
	 * set new node after p and above q
	 */
	public SkipNode insertAfterAbove(SkipNode p, SkipNode q, String text) {
		SkipNode newNode = new SkipNode(text);
		SkipNode pAfter = p.after();

		profiler.addAssignments(7);

		pAfter.setBefore(newNode);
		newNode.setAfter(pAfter);
		newNode.setBefore(p);
		p.setAfter(newNode);
		q.setAbove(newNode);
		newNode.setBelow(q);
		newNode.setAbove(SkipNode.NullNode);

		profiler.addAssignments(7);

		// Create new empty list on top if there is none (two infinite nodes
		// (min and max))
		if (newNode.before().isFirst()
				&& newNode.before().above() == SkipNode.NullNode) {
			
			SkipNode newFirstNode = new SkipNode("");
			newFirstNode.setIsFirst();
			
			profiler.addAssignments(3);
			
			SkipNode newLastNode = new SkipNode("");
			newLastNode.setIsLast();
			profiler.addAssignments(3);
			
			newFirstNode.setAfter(newLastNode);
			newLastNode.setBefore(newFirstNode);

			newFirstNode.setBelow(newNode.before());
			newNode.before().setAbove(newFirstNode);

			profiler.addAssignments(4);
			
			SkipNode iterator = newNode;
			
			profiler.incAssignments();
			
			while (iterator.after() != SkipNode.NullNode) {
				iterator = iterator.after();
				profiler.incAssignments();
			}
			
			newLastNode.setBelow(iterator);

			first = newFirstNode;
			last = newLastNode;
			
			profiler.addAssignments(3);
		}

		return newNode;
	}

	public SkipNode getNode(String text) {
		SkipNode p = first;
		
		profiler.incAssignments();
		
		while (p.below() != SkipNode.NullNode) {
			p = p.below();
			profiler.incAssignments();
			
			while (p.after().compareTo(text) <= 0) {
				p = p.after();
				profiler.incComparisons()
						.incAssignments();
			}
		}
		return p;
	}
}
