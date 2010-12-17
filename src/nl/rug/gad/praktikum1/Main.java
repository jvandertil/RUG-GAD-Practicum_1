package nl.rug.gad.praktikum1;

import nl.rug.gad.praktikum1.avl.AVLTree;
import nl.rug.gad.praktikum1.skiplist.SkipList;
import nl.rug.gad.praktikum1.util.Profiler;
import nl.rug.gad.praktikum1.util.StopWatch;
import nl.rug.gad.praktikum1.util.TextSource;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] textFiles = new String[] { "text1", "text2", "text3", "text4",	"text5" };
		TextSource tx = new TextSource();

		tx.preloadAll();

		Profiler p = new Profiler();

		System.out.println("AVLTree");
		for(String s : textFiles) {
			ITree tree = new AVLTree(p);
			test(tree, p, tx, s);
			p.reset(true);
		}
		
		System.out.println("\nSkipList");
		for(String s : textFiles) {
			ITree tree = new SkipList(p);
			test(tree, p, tx, s);
			p.reset(true);
		}
	}

	private static void test(ITree tree, Profiler p, TextSource tx, String textFile) {
		StopWatch st = new StopWatch();
		String timerName = String.format("Insertion %s", textFile);
		st.start(timerName);

		for (String s : tx.getTextFile(textFile)) {
			tree.insert(s);
		}

		st.stop(timerName);

		st.printTimers();
		p.printInfo();
	}
}
