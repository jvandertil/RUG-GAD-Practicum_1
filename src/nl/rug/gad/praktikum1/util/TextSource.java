package nl.rug.gad.praktikum1.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TextSource {

	private Map<String, List<String>> textFiles;
	
	public TextSource() {
		textFiles = new HashMap<String, List<String>>();
	}
	
	public List<String> getTextFile(String name) {
		if(!textFiles.containsKey(name)) {
			BufferedReader reader = null;
			
			try {
				reader = new BufferedReader(new FileReader(name));
				List<String> list = new LinkedList<String>();
				
				
				String line;
				while((line = reader.readLine()) != null) {
					String[] split = line.split(" ");
					
					Collections.addAll(list, split);
				}
				
				textFiles.put(name, list);
			} catch(IOException ioe) {
				ioe.printStackTrace();
				System.exit(-1);
			} finally {
				try {
					reader.close();
				} catch(Exception e) {/* Ignored, we are destroying anyway */}
			}
		}
		
		return textFiles.get(name);
	}
}
