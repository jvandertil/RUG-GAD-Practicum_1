package nl.rug.gad.praktikum1.util;

public class Profiler {

	public static final Profiler NULL = new NullProfiler();
	
	private static final class NullProfiler extends Profiler {
		public Profiler incAssignments() { return this;}
		public Profiler addAssignments(long assignments) { return this;}
		public Profiler incComparisons() {return this; }
		public Profiler addComparisons(long comparisons) {return this; }
	}
	
	private long comparisons;
	private long assignments;
	
	public Profiler incAssignments() {
		++assignments;
		
		return this;
	}
	
	public Profiler addAssignments(long assignments) {
		this.assignments += assignments;
		
		return this;
	}
	
	public Profiler incComparisons() {
		++comparisons;
		
		return this;
	}
	
	public Profiler addComparisons(long comparisons) {
		this.comparisons += comparisons;
		
		return this;
	}
	
	public void reset(boolean reset) {
		if(reset) {
			comparisons = 0;
			assignments = 0;
		}
	}

	public void printInfo() {
		System.out.printf("Comparisons: %d\nAssignments: %d\n", comparisons, assignments);
	}
}
