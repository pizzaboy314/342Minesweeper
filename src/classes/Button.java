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
			setIcon(icons[0]);
		} else {
			setIcon(icon);
		}
	}


	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
		if (isBomb == false) {
			if (val > 0) {
				icon = icons[val];
			} else {
				icon = icons[0];
			}
		}
	}

	public boolean isBomb() {
		return isBomb;
	}

	public ImageIcon getIcon() {
		return icon;
	}


	public void setBomb(boolean bomb) {
		isBomb = bomb;
		if(bomb == true){
			icon = icons[11];
		}
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

	public void setToggleState(int toggleState) {
		this.toggleState = toggleState;

		switch (toggleState) {
		case 1:
			setIcon(icons[0]);
			break;
		case 2:
			setIcon(icons[9]);
			flagged = true;
			break;
		case 3:
			setIcon(icons[10]);
			flagged = false;
			break;
		default:
			setIcon(icons[0]);
		}
	}
}
