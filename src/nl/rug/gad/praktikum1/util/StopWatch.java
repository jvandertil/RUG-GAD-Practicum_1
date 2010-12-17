package nl.rug.gad.praktikum1.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StopWatch {

	private Map<String, StopWatchInfo> stopWatchMap;
	
	public StopWatch() {
		stopWatchMap = new HashMap<String, StopWatchInfo>();
	}
	
	public void start(String name) {
		StopWatchInfo info = new StopWatchInfo();
		
		stopWatchMap.put(name, info);
		
		info.startTime = System.currentTimeMillis();
	}
	
	public void stop(String name) {
		long stop = System.currentTimeMillis();
		
		StopWatchInfo info = stopWatchMap.get(name);
		
		info.stopTime = stop;
	}
	
	public void printTimers() {
		for(Entry<String, StopWatchInfo> entry : stopWatchMap.entrySet()) {
			System.out.printf("------%s------\n", entry.getKey());
			System.out.printf("-- Run time: %d ms.\n", getTimeMillis(entry.getKey()));
		}
			
	}
	
	public long getTimeMillis(String name) {
		StopWatchInfo info = stopWatchMap.get(name);
		
		if(info.stopTime == 0)
			return System.currentTimeMillis() - info.startTime;
		
		return info.stopTime - info.startTime;
	}
	
	private class StopWatchInfo {
		public long startTime;
		public long stopTime;
	}
}
