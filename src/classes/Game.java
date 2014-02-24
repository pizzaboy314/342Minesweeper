package classes;
//comment
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game implements MouseListener {

	private final int size = 10;
	private final int numOfBombs = 10;
	private int bombCounter;
	private JFrame mainFrame;
	private Container board;
	private Button[][] buttonGrid;

	public Game(String windowLabel) {
		bombCounter = numOfBombs;

		mainFrame = new JFrame(windowLabel);
		mainFrame.setLayout(new GridLayout(1, 2));
		mainFrame.setResizable(false);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		board = mainFrame.getContentPane();
		board.setLayout(new GridLayout(size, size));
		board.setPreferredSize(new Dimension(20 * size, 20 * size));
		init();
	}

	public void init() {
		buttonGrid = new Button[size][size];

		Random rand = new Random(System.currentTimeMillis());
		List<Point> bombs = new ArrayList<Point>();
		Point tmp;
		
		for (int i = 0; i < numOfBombs; i++) {
			tmp = new Point();
			tmp.x = rand.nextInt(size);
			tmp.y = rand.nextInt(size);
			while (bombs.contains(tmp)) {
				tmp.x = rand.nextInt(size);
				tmp.y = rand.nextInt(size);
			}
			bombs.add(tmp);
		}

		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				Button btn = new Button(j, k);
				btn.addMouseListener(this);

				btn.setBomb(false);
				buttonGrid[j][k] = btn;
				board.add(btn);
			}
		}
		for(Point p : bombs){
			Button b = buttonGrid[p.x][p.y];
			b.setBomb(true);
			b.setHidden(false);
		}
		findBombs();
		showBoard(false);
	}

	public void findBombs() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int iStart = (i == 0) ? i : (i - 1);
				int iEnd = (i == size - 1) ? i : (i + 1);
				int jStart = (j == 0) ? j : (j - 1);
				int jEnd = (j == size - 1) ? j : (j + 1);

				Button b = buttonGrid[i][j];
				int count = 0;
				if (b.isBomb() == false) {
					for (int k = iStart; k <= iEnd; k++) {
						for (int m = jStart; m <= jEnd; m++) {
							if (buttonGrid[k][m].isBomb()) {
								count++;
							}
						}
					}
				}

				b.setVal(count);
			}
		}
	}

	public void clearZeros(int i, int j) {
		Button b = buttonGrid[i][j];
		b.setHidden(true);
		b.setEnabled(false);
		if (b.isFlagged() == true) {
			b.setToggleState(1);
			bombCounter++;
		}

		int iStart = (i == 0) ? i : (i - 1);
		int iEnd = (i == size - 1) ? i : (i + 1);
		int jStart = (j == 0) ? j : (j - 1);
		int jEnd = (j == size - 1) ? j : (j + 1);

		for (int k = iStart; k <= iEnd; k++) {
			for (int m = jStart; m <= jEnd; m++) {
				if (buttonGrid[k][m].isEnabled() == true) {
					if (buttonGrid[k][m].getVal() == 0) {
						clearZeros(k, m);
					} else {
						buttonGrid[k][m].setHidden(false);
						buttonGrid[k][m].setEnabled(false);
					}
				}

			}
		}
	}

	public void showBoard(boolean gameOver) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Button b = buttonGrid[i][j];
				if (b.isHidden() == true) { // && b.getVal() != 0
					b.setHidden(false);
				}
				if (b.isEnabled() == true) {
					b.setEnabled(!gameOver);
				}
			}
		}
	}

	public void play() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame.pack();
				mainFrame.setVisible(true);
			}
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Button b = (Button) e.getSource();

		if (SwingUtilities.isLeftMouseButton(e) && b.getToggleState() == 1) {
			if (b.isBomb() == true) {
				showBoard(true);
			} else if (b.getVal() > 0) {
				b.setHidden(false);
				b.setEnabled(false);
			} else {
				clearZeros(b.getI(), b.getJ());
			}
		}
		if (SwingUtilities.isRightMouseButton(e) && b.isEnabled() == true) {
			if (bombCounter > 0) {
				b.toggleFlags();

				if (b.getToggleState() == 2) {
					bombCounter--;
				} else if (b.getToggleState() == 3) {
					bombCounter++;
				}
				System.out.println(bombCounter);
			}

		}

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
