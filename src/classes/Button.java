package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button implements ActionListener {

	JButton button;
	
	public Button(String text) {
		button = new JButton(text);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
