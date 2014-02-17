package classes;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Game extends JFrame {

	public Game(String windowLabel) {
		super(windowLabel);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		setSize(200, 100);
		show();
	}

}
