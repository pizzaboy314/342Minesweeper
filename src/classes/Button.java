package classes;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;

	private int i;
	private int j;
	private int val;
	private int toggleState;
	private ImageIcon icon;
	private boolean isBomb;
	private boolean hidden;
	private boolean flagged;
	
	private ImageIcon[] icons;

	public Button(int iPos, int jPos) {
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
	
	public void toggleFlags() {
		if (toggleState < 3) {
			setToggleState(toggleState + 1);
		} else {
			setToggleState(1);
		}
	}

	public void setHidden(boolean b) {
		hidden = b;
		if (b == true) {
			icon = icons[0];
		} else {
			if (isDebunked() == true) {
				icon = icons[12];
				setDisabledIcon(icon);
			} else {
				icon = icons[val];
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

	public boolean isDebunked() {
		if (flagged == true && isBomb == true) {
			return true;
		} else {
			return false;
		}
	}

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

	public void reset() {
		val = 0;
		toggleState = 1;

		flagged = false;
		setHidden(true);
		setBomb(false);
		setEnabled(true);
	}

}
