package classes;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;

	private int i;
	private int j;
	private int val;
	private String label;
	private boolean isBomb;
	private boolean hidden;
	private boolean flagged;
	private boolean unsure;
	
	public Button(int iPos, int jPos) {
		val = 0;
		label = "";
		i = iPos;
		j = jPos;

		flagged = false;
		unsure = false;

		setFont(new Font("Arial", Font.BOLD, 12));
		setText(label);
		setMargin(new Insets(0, 0, 0, 0));
		setHidden(true);
		

	}
	
	public void toggleFlags() {
		if (flagged == false && unsure == false) {
			flagged = true;
			setText("M");
		} else if (flagged == true && unsure == false) {
			flagged = false;
			unsure = true;
			setText("?");
		} else if (flagged == false && unsure == true) {
			unsure = false;
			setText("");
		}
	}

	public void setHidden(boolean b) {
		hidden = b;
		String s = b ? "" : label;
		setText(s);
	}


	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
		if (isBomb == false) {
			if (val > 0) {
				label = "" + val;
			} else {
				label = "";
			}
		}
	}

	public boolean isBomb() {
		return isBomb;
	}

	public String getLabel() {
		return label;
	}


	public void setBomb(boolean bomb) {
		isBomb = bomb;
		label = (bomb == true) ? "B" : "";
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

	public boolean isUnsure() {
		return unsure;
	}
}
