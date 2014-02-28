package classes;

public class Score implements Comparable<Score> {

	private static final int N = 60;
	int totalSecs;
	String name;
	String time;

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

	public String toString() {
		return time + " - " + name;
	}

	@Override
	public int compareTo(Score s) {
		return totalSecs - s.totalSecs;
	}

}
