package classes;
//comment
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Game extends JFrame {

	public Game(String windowLabel) {
		super(windowLabel);

		Container c = getContentPane();
		c.setLayout(new GridLayout(10, 10));

		for (int i = 0; i < 100; i++) {
			Button tmp = new Button("" + (i + 1));
			c.add(tmp.getButton());
		}

		setSize(500, 500);
		show();
	}

}
