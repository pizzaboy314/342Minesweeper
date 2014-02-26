package classes;
//comment
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Game implements MouseListener, ActionListener {

	private final int size = 10; // SIZE OF BOARD
	private final int numOfBombs = 10; // NUMBER OF MINES
	private int bombCounter;
	private JFrame mainFrame;
	private Container board;
	private Button[][] buttonGrid;

	public Game(String windowLabel) {
		bombCounter = numOfBombs;

		mainFrame = new JFrame(windowLabel);
		mainFrame.setLayout(new GridLayout(1, 2));
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = mainFrame.getContentPane();
		board.setLayout(new GridLayout(size, size));
		board.setPreferredSize(new Dimension(20 * size, (24 - (size / 10)) * size));
		init();
		create_menu();
	}

	public void create_menu() {
		// JMenuBar

		JMenuBar menu = new JMenuBar();

		JMenu game = new JMenu("Game"), Help = new JMenu("Help");

		JMenuItem reset = new JMenuItem("Reset"), topten = new JMenuItem("Top Ten"), eXit = new JMenuItem("eXit"), help = new JMenuItem(
				"Help"), about = new JMenuItem("About");

		game.setMnemonic('G');

		game.add(reset);
		reset.setMnemonic('R');

		game.add(topten);
		topten.setMnemonic('T');

		game.add(eXit);
		eXit.setMnemonic('X');

		Help.add(help);
		help.setMnemonic('H');

		Help.add(about);
		about.setMnemonic('A');

		menu.add(game);

		menu.add(Help);
		Help.setMnemonic('H');

		/********************** Menu Actions **************************/
		// adding exit menu action
		eXit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		// adding Reset menu action
		reset.addActionListener(this);

		// adding Help menu action
		help.addActionListener(new ActionListener() {

			class helpWindow {

				// helpWindow constructor
				public helpWindow() {
					// create message container
					JLabel msg = new JLabel();

					// help message
					msg.setText("There will be no help");
					msg.setBorder(new EmptyBorder(10, 10, 10, 10));

					// create window
					JFrame about = new JFrame("Help");

					// set window specifics
					about.getContentPane().setLayout(new BorderLayout());

					// add label to window and place center
					about.getContentPane().add(msg, "Center");

					// display window
					about.pack();
					about.setVisible(true);

				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				helpWindow help = new helpWindow();
			}

		});
		// end of help action listener

		// adding about menu action
		about.addActionListener(new ActionListener() {

			// in-line class that displays a small window during an action event
			class aboutWindow {

				// creates ABOUT WINDOW
				public aboutWindow() {

					// create message
					JLabel msg = new JLabel();

					// set actual message
					msg.setText("Programmers: George Maratos  Bryan Spahr");
					msg.setBorder(new EmptyBorder(10, 10, 10, 10));

					// window
					JFrame about = new JFrame("About");

					// set window specifics
					about.getContentPane().setLayout(new BorderLayout());

					// add label to window and place center
					about.getContentPane().add(msg, "Center");

					// display window
					about.pack();
					about.setVisible(true);

				}

			}

			// end of class definition

			// class aboutWindow which displays a window of text is used here in
			// Action
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				aboutWindow about = new aboutWindow();

			}

		});
		// end of about action listener

		mainFrame.setJMenuBar(menu);

		// end of menu stuff

	}

	public void init() {
		board.removeAll();
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
			b.setVal(11);
			// b.setHidden(true);
		}

		findBombs();
		showBoard(false);
		mainFrame.pack();
		mainFrame.setVisible(true);
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
					b.setVal(count);
				}
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
					b.setHidden(!gameOver);
				}
				if (b.isEnabled() == true) {
					b.setEnabled(!gameOver);
				}
			}
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		init();
	}

}
