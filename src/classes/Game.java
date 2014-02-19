package classes;
//comment
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {

	JFrame mainFrame;
	JPanel board;

	public Game(String windowLabel) {
		mainFrame = new JFrame();
		mainFrame.setTitle(windowLabel);
		mainFrame.setSize(600, 600);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new GridLayout(1, 2));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		board = new JPanel();
		board.setLayout(new GridLayout(10, 10));

		for (int i = 0; i < 100; i++) {
			Button tmp = new Button("" + (i + 1));
			board.add(tmp.getButton());
		}

		mainFrame.add(board);
	}

}
