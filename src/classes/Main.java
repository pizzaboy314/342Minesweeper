package classes;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

	public static void main(String[] args) {
		Game game = new Game("TESTING");

		game.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

}
