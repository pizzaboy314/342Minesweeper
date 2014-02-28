package classes;

// Bryan Spahr & George Maratos
// Class is essentially a JButton, with additional global variables.
// Contains toggled state from setting flags or question marks.

import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;

	private int i; // row location of this button
	private int j; // column location of this button
	private int val; // represents how many mines are in the surrounding buttons
	private int toggleState; // for toggling from blank to flagged to marked
	private ImageIcon icon; // the icon that is painted on the button
	private boolean isBomb; // is this button a mine?
	private boolean hidden; // should this button be hidden? IE blank icon
	private boolean flagged; // is this button currently flagged?
	
	private ImageIcon[] icons; // contains all the icons for the game

	public Button(int iPos, int jPos) {
		// populate array with image files
		icons = new ImageIcon[13];
		for (int i = 0; i < 13; i++) {
			ImageIcon ico = new ImageIcon("icon" + i + ".jpeg");
			icons[i] = ico;
		}

		val = 0;
		icon = icons[0];
		i = iPos;
		j = jPos;
		toggleState = 1;

		flagged = false;

		setFont(new Font("Arial", Font.BOLD, 12));
		setIcon(icon);
		setMargin(new Insets(0, 0, 0, 0));
		setHidden(true);

	}
	
	// toggles the int state from 1 to 2 to 3, etc
	public void toggleFlags() {
		if (toggleState < 3) {
			setToggleState(toggleState + 1);
		} else {
			setToggleState(1);
		}
	}

	// hides or shows the identity of this button
	// IE, changes the icon that is shown
	public void setHidden(boolean b) {
		hidden = b;
		if (b == true) {
			icon = icons[0]; // default: show blank icon
		} else {
			if (isDebunked() == true) { // if correctly flagged, show disabled
										// mine icon
				icon = icons[12];
				setDisabledIcon(icon);
			} else {
				icon = icons[val]; // val functions as position in array
				setDisabledIcon(icon);
			}
		}
	}


	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public boolean isBomb() {
		return isBomb;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setBomb(boolean bomb) {
		isBomb = bomb;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public int getToggleState() {
		return toggleState;
	}

	// self-explanatory
	public boolean isDebunked() {
		if (flagged == true && isBomb == true) {
			return true;
		} else {
			return false;
		}
	}

	// updates the button's icon when the toggle state changes
	public void setToggleState(int toggleState) {
		this.toggleState = toggleState;

		switch (toggleState) {
		case 1:
			icon = icons[0];
			repaint();
			break;
		case 2:
			icon = icons[9];
			repaint();
			flagged = true;
			break;
		case 3:
			icon = icons[10];
			repaint();
			flagged = false;
			break;
		}
	}

	// for resetting each button in the grid back to its starting state
	public void reset() {
		val = 0;
		toggleState = 1;

		flagged = false;
		setHidden(true);
		setBomb(false);
		setEnabled(true);
	}

}
