package nl.rug.gad.praktikum1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import nl.rug.gad.praktikum1.avl.AVLTree;
import nl.rug.gad.praktikum1.binary.TreeImpl;
import nl.rug.gad.praktikum1.skiplist.SkipList;
import nl.rug.gad.praktikum1.twofour.TwoFourTree;
import nl.rug.gad.praktikum1.util.Profiler;
import nl.rug.gad.praktikum1.util.StopWatch;
import nl.rug.gad.praktikum1.util.TextSource;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] textFiles = new String[] { "text1", "text2", "text3", "text4",
				"text5" };
		TextSource tx = new TextSource();

		tx.preloadAll();
		try {
			PrintStream output = new PrintStream(new FileOutputStream(
					"bench_results.txt"));
			System.setOut(output);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}

		for (int i = 0; i < 10; i++) {
			System.out.printf("Start of run %d....\n", i);
			Profiler p = new Profiler();

			ITree tree;

			System.out.println("\nBinaryTree");
			for (String s : textFiles) {
				tree = new TreeImpl(p);
				test(tree, p, tx, s);
				p.reset(true);
			}

			System.out.println("AVLTree");
			for (String s : textFiles) {
				tree = new AVLTree(p);
				test(tree, p, tx, s);
				p.reset(true);
			}

			System.out.println("\nSkipList");
			for (String s : textFiles) {
				tree = new SkipList(p);
				test(tree, p, tx, s);
				p.reset(true);
			}

			System.out.println("\n2-4 Tree");
			for (String s : textFiles) {
				tree = new TwoFourTree(p);
				test(tree, p, tx, s);
				p.reset(true);
			}

			System.out.println("End of run");
		}
		
		System.out.flush();
		System.out.close();
	}

	private static void test(ITree tree, Profiler p, TextSource tx,
			String textFile) {
		StopWatch st = new StopWatch();
		String insertionTimerName = String.format("Insertion %s", textFile);

		st.start(insertionTimerName);
		for (String s : tx.getTextFile(textFile)) {
			tree.insert(s);
		}
		st.stop(insertionTimerName);

		System.out.println("Insertion");
		p.printInfo();

		p.reset(true);

		String searchTimerName = String.format("Search %s", textFile);

		st.start(searchTimerName);
		for (String s : tx.getTextFile(textFile)) {
			tree.getNode(s);
		}
		st.stop(searchTimerName);

		System.out.println("Search");
		p.printInfo();

		st.printTimers();
	}
}
