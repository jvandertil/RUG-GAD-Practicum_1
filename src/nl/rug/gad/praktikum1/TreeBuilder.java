package nl.rug.gad.praktikum1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import nl.rug.gad.praktikum1.avl.AVLNode;
import nl.rug.gad.praktikum1.avl.AVLTree;

public class TreeBuilder {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("text5.txt"));

			AVLTree tree = new AVLTree();
			
			String line;
			
			long startNanos = System.currentTimeMillis();
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(" ");
				
				for(String s : split)				
					tree.insert(s);
			}
			long stopNanos = System.currentTimeMillis();

			AVLNode node = tree.findKey("a");


			System.out.println(node.getKey());
			System.out.println(stopNanos - startNanos);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
