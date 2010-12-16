package nl.rug.gad.praktikum1.util;

public class Profiler {

	public static final Profiler NULL = new NullProfiler();
	
	private static final class NullProfiler extends Profiler {
		public void incAssignments() { }
		public void addAssignments(long assignments) { }
		public void incComparisons() { }
		public void addComparisons(long comparisons) { }
	}
	
	private long comparisons;
	private long assignments;
	
	public void incAssignments() {
		++assignments;
	}
	
	public void addAssignments(long assignments) {
		this.assignments += assignments;
	}
	
	public void incComparisons() {
		++comparisons;
	}
	
	public void addComparisons(long comparisons) {
		this.comparisons += comparisons;
	}
}
