package classes;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Button implements ActionListener {

	JButton button;
	
	public Button(String text) {
		button = new JButton(text);
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		button.setLabel("X");
		button.setPreferredSize(new Dimension(5, 5));
	}

	public void addMouseListener(MouseListener l) {
		button.addMouseListener(l);
	}


	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
