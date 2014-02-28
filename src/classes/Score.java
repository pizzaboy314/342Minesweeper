package classes;

// Bryan Spahr & George Maratos
// Score class to parse lines from scores file.
// Class is sortable so that the top ten can be grabbed from a list of Scores.

public class Score implements Comparable<Score> {

	private static final int N = 60;
	int totalSecs;
	String name;
	String time;

	// constructor. parses name and # of secs from line
	public Score(String line){
		String[] split = line.split("_");

		name = split[0];
		totalSecs = Integer.parseInt(split[1]);

		time = "%d:%s%d";

		int mins = totalSecs / N;
		int secs = totalSecs % N;

		if (secs < 10) {
			time = String.format(time, mins, "0", secs);
		} else {
			time = String.format(time, mins, "", secs);
		}
	}

	// returns a nice string that will go in GUI
	public String toString() {
		return time + " - " + name;
	}

	// for sorting a list of Score by increasing time
	@Override
	public int compareTo(Score s) {
		return totalSecs - s.totalSecs;
	}

}
