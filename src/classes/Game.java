package classes;
//comment
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Game {

	JFrame mainFrame;
	Container board;

	public Game(String windowLabel) {
		mainFrame = new JFrame(windowLabel);

		mainFrame.setLayout(new GridLayout(1, 2));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		board = mainFrame.getContentPane();
		board.setLayout(new GridLayout(10, 10));
		board.setPreferredSize(new Dimension(400, 400));

		for (int i = 0; i < 100; i++) {
			Button tmp = new Button("" + (i + 1));
			board.add(tmp.getButton());
		}
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
	}

}
