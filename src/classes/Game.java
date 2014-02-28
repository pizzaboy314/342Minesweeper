package classes;

// Bryan Spahr & George Maratos
// Contains all GUI items and methods.

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Game implements MouseListener, ActionListener {

	private final int size = 10; // SIZE OF BOARD
	private final int numOfBombs = 3; // NUMBER OF MINES
	private int bombCounter; // Counts down from the number of mines to 0
	private int deadCounter; // Counts up from 0 to 90, as buttons are disabled
	private int endCounter; // sum of deadCounter and the # of mines that have
							// been flagged
	private JFrame mainFrame; // main window
	private JPanel content; // to contain the timer/counter bar and main board
	private JPanel board; // contains grid of JButtons
	private JPanel bar; // contains game timer and number of mines left
	private Button[][] buttonGrid; // grid of JButtons

	private static final int N = 60; // # of secs in a minute
	private int currSecs; // time incrementer for timer, in seconds
	private ClockListener cl; // increments time every second
	private Timer t; // the timer
	private JLabel tLabel; // the gui label for the timer
	private JLabel bLabel; // the gui label for the bomb counter

	// constructor, initializes stuff and creates the top menu
	public Game(String windowLabel) {
		mainFrame = new JFrame(windowLabel);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
		create_menu();
	}

	public void create_menu() { // by George Maratos
		// JMenuBar

		JMenuBar menu = new JMenuBar();

		JMenu game = new JMenu("Game"), Help = new JMenu("Help");

		JMenuItem reset = new JMenuItem("Reset"), topten = new JMenuItem("Top Ten"), eXit = new JMenuItem("eXit"), help = new JMenuItem(
				"Help"), about = new JMenuItem("About"), clearscores = new JMenuItem("Clear Scores");

		game.setMnemonic('G');

		game.add(reset);
		reset.setMnemonic('R');

		game.add(topten);
		topten.setMnemonic('T');

		game.add(clearscores);

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

		topten.addActionListener(new ActionListener() {

			class TopTenWindow {

				// helpWindow constructor
				public TopTenWindow() {
					createScoresFile();

					// create window
					JFrame scores = new JFrame("Top Ten Scores");
					scores.setPreferredSize(new Dimension(200, 225));
					scores.setResizable(true);
					scores.getContentPane().setLayout(new BoxLayout(scores.getContentPane(), BoxLayout.Y_AXIS));

					// arraylist of Score to pull in all entries in file
					List<Score> list = new ArrayList<Score>();
					try {
						BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
						String s;
						while ((s = reader.readLine()) != null) {
							Score score = new Score(s);
							list.add(score);
						}
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					// sort list scores smallest time to largest
					Collections.sort(list);
					int limit = (list.size() < 10) ? list.size() : 10;
					
					// place first ten scores in list into window
					for (int i = 0; i < limit; i++) {
						String s = list.get(i).toString();
						JLabel msg = new JLabel();
						msg.setBorder(new EmptyBorder(3, 10, 0, 0));
						msg.setText(s);
						scores.getContentPane().add(msg);
					}

					// display window
					scores.pack();
					scores.setVisible(true);

				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				TopTenWindow topTen = new TopTenWindow();
			}

		});

		clearscores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// make sure file exists
				createScoresFile();

				// replace file contents with empty string
				PrintWriter out;
				try {
					out = new PrintWriter("scores.txt");
					out.print("");
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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
					msg.setText("Google \"how to play minesweeper\".");
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
					msg.setText("Programmers: George Maratos, Bryan Spahr");
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

	// initialize everything
	public void init() {

		content = new JPanel();
		// box layout to align bar and board vertically
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setPreferredSize(new Dimension(20 * size, (28 - (size / 10)) * size));

		bar = new JPanel();
		bar.setPreferredSize(new Dimension(20 * size, 2 * size));
		bar.setLayout(new FlowLayout(FlowLayout.LEFT));

		cl = new ClockListener();
		t = new Timer(1000, cl);
		t.setInitialDelay(0);
		tLabel = new JLabel();
		tLabel.setText("Time: 0:00");

		bLabel = new JLabel();
		bLabel.setText("Bombs Left: " + numOfBombs + "      ");

		bar.add(bLabel);
		bar.add(tLabel);

		board = new JPanel();
		board.setLayout(new GridLayout(size, size));
		board.setPreferredSize(new Dimension(20 * size, (24 - (size / 10)) * size));

		currSecs = 0;
		bombCounter = numOfBombs;
		deadCounter = 0;
		endCounter = 0;
		buttonGrid = new Button[size][size];

		Random rand = new Random(System.currentTimeMillis());
		List<Point> bombs = new ArrayList<Point>();
		Point tmp;
		
		// generate unique locations for bombs
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

		// initialize all buttons
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				Button btn = new Button(j, k);
				btn.addMouseListener(this);

				btn.setBomb(false);
				buttonGrid[j][k] = btn;
				board.add(btn);
			}
		}

		// set all bombs
		for(Point p : bombs){
			Button b = buttonGrid[p.x][p.y];
			b.setBomb(true);
			b.setVal(11);
		}

		findBombs();
		showBoard(false);
		content.add(bar);
		content.add(board);
		mainFrame.add(content);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	// loop through entire grid. if current button isn't a mines,
	// check the surrounding buttons for mines and count how many.
	// # of mines found is assigned to current button as val.
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

	// is called when a zero is clicked on. searches in all directions,
	// recursively, calling itself when it finds a zero and stopping
	// when it hits an actual number.
	public void clearZeros(int i, int j) {
		deadCounter++;
		updateEndCounter();
		Button b = buttonGrid[i][j];
		b.setHidden(true);
		b.setEnabled(false);
		if (b.isFlagged() == true) {
			b.setToggleState(1);
			bombCounter++;
			updateEndCounter();
			updateBombLabel();
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
						deadCounter++;
						updateEndCounter();
					}
				}

			}
		}
	}

	// shows the whole board when the game is over
	public void showBoard(boolean gameOver) {
		stopTimer();

		// if the game is over and all mines have been found, save score
		if (gameOver == true && bombCounter == 0) {
			try {
				addScore(currSecs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Button b = buttonGrid[i][j];
				if (b.isHidden() == true) {
					b.setHidden(!gameOver);
				}
				if (b.isEnabled() == true) {
					b.setEnabled(!gameOver);
				}
			}
		}
	}

	// updates progress of game as mines are flagged and buttons are cleared
	public void updateEndCounter() {
		endCounter = deadCounter + (numOfBombs - bombCounter);
	}

	// updates the mine counter label everytime a mine is flagged
	public void updateBombLabel() {
		bLabel.setText("Bombs Left: " + bombCounter + "      ");
	}

	// creates score file if it doesn't exist
	public void createScoresFile() {
		File file = new File("scores.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// prompts for a name to save score
	public void addScore(int score) throws IOException {
		createScoresFile();
		
		String s = (String) JOptionPane.showInputDialog(null, "You won!\n" + "Enter your name to save your score:",
				"High Score", JOptionPane.PLAIN_MESSAGE, null, null, "User");

		String line = s + "_" + Integer.toString(score) + "\n";

		Writer output = new BufferedWriter(new FileWriter("scores.txt", true));
		output.append(line);
		output.close();

	}

	// handles all button clicks for button grid
	@Override
	public void mouseClicked(MouseEvent e) {
		Button b = (Button) e.getSource();

		if (t.isRunning() == false) {
			startTimer();
		}
		
		// main left click handler. won't trigger if button is flagged or marked
		if (SwingUtilities.isLeftMouseButton(e) && b.getToggleState() == 1) {
			if (b.isBomb() == true) { // if you click on a mine, game is over
				showBoard(true);
			} else if (b.getVal() > 0) { // if button isn't a zero, show it, etc
				deadCounter++;
				updateEndCounter();
				b.setHidden(false);
				b.setEnabled(false);
			} else {
				clearZeros(b.getI(), b.getJ()); // if button is a zero, clear
												// out other zeros
			}
		}

		// main right click handler. won't trigger if button is already dead
		// toggle state 1 = blank
		// toggle state 2 = flagged
		// toggle state 3 = marked
		if (SwingUtilities.isRightMouseButton(e) && b.isEnabled() == true) {
			if (bombCounter > 0) { // can only set #OFMINES flags
				b.toggleFlags(); // toggle from blank to flagged to marked

				if (b.getToggleState() == 2) {
					bombCounter--;
					updateEndCounter();
					updateBombLabel();
				} else if (b.getToggleState() == 3) {
					bombCounter++;
					updateEndCounter();
					updateBombLabel();
				}
			}

		}
		if (endCounter == size * size) { // if all spaces are cleared or flagged
			showBoard(true);
		}
	}

	// required but unused mouse event functions
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	// for reset button in menu
	@Override
	public void actionPerformed(ActionEvent e) {
		reset();
		findBombs();
		refreshButtons();
	}

	// bug fix for icons that weren't repainting
	public void refreshButtons() {
		for (int j = 0; j < size; j++) {
			for (int k = 0; k < size; k++) {
				buttonGrid[j][k].setHidden(false);
				buttonGrid[j][k].setHidden(true);
			}
		}
	}

	// resets everything
	public void reset() {
		stopTimer();
		tLabel.setText("Time: 0:00");
		currSecs = 0;
		bombCounter = numOfBombs;
		deadCounter = 0;
		endCounter = 0;
		updateBombLabel();

		// determine new bombs
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
				buttonGrid[j][k].reset();
			}
		}
		for (Point p : bombs) {
			Button b = buttonGrid[p.x][p.y];
			b.setBomb(true);
			b.setVal(11);
		}

	}

	// updates timer label for every second of timer
	private class ClockListener implements ActionListener {

		// N = 60
		private int secs = 0;
		private int mins = 0;
		private String label = "Time: %d:%s%d";

		@Override
		public void actionPerformed(ActionEvent e) {
			mins = currSecs / N;
			secs = currSecs % N;
			String text;
			if (secs < 10) {
				text = String.format(label, mins, "0", secs);
			} else {
				text = String.format(label, mins, "", secs);
			}
			tLabel.setText(text);

			currSecs++;
		}
	}

	// starts timer
	public void startTimer() {
		t.start();
	}

	// stops timer
	public void stopTimer() {
		t.stop();
	}

}
