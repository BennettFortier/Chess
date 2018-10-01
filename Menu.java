import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu extends JFrame{
	JMenuBar menuBar;
	JMenu file;
	JMenuItem exit,options,help,newGame;
	JPopupMenu pMenu;
	JCheckBox blackSquare,yellowCircle,pieceNoise, isGameNoise;
	boolean blackSquareOn,yellowCircleOn, pieceNoiseOn, gameNoiseOn;
	Container visual, sound, button;
	Board gameBoard;
	JLabel text,text2, text3;
	JButton apply, boardType, pieceType;
	GridBagConstraints gbc;
	GridBagLayout layout;
	GridLayout g;
	final int FPS_MIN = 0;
	 final int FPS_MAX = 30;
	 final int FPS_INIT = 15;    //initial frames per second
	 int gameNoiseVal = 0;
	
	JSlider gameNoise = new JSlider(JSlider.HORIZONTAL,
	                                      FPS_MIN, FPS_MAX, FPS_INIT);
	
	public Menu(Board gameboard){
		gameNoise.setName("Game Noise:");
		gbc = new GridBagConstraints();
		layout = new GridBagLayout();
		g = new GridLayout(3,3);
		gameBoard = gameboard;
		visual = new Container();
		sound = new Container();
		button = new Container();
		text = new JLabel("Visuals:    ");
		text2 = new JLabel("Sounds:    ");
		text3 = new JLabel("Volume:  ");
			
		createMenuBar();
		createPop();
	}
	public JMenuBar returnMenu(){
		return menuBar;
	}

	private void createPop(){
		visual.add(text,BorderLayout.SOUTH);
		sound.add(text2,BorderLayout.NORTH);
		button.add(text3);
		visual.setLayout((new BoxLayout(visual, BoxLayout.X_AXIS)));
		sound.setLayout((new BoxLayout(sound, BoxLayout.X_AXIS)));
		button.setLayout((new BoxLayout(button, BoxLayout.X_AXIS)));
		setSize(400,300);
		setTitle("Options");
		setLocationRelativeTo(null);
		setBackground(Color.white);
		setVisible(false);
		popUpScreen();
		setLayout(g);


	}
	
	private void popUpScreen() {
		blackSquare = new JCheckBox("Show possible moves");
		blackSquare.setSelected(true);
		blackSquare.setHorizontalTextPosition( SwingConstants.CENTER );
		blackSquare.setVerticalTextPosition( SwingConstants.TOP );
		blackSquare.addActionListener((ActionEvent event) -> {
			if(blackSquare.isSelected()){
				blackSquareOn = true;
			}
			else if (blackSquare.isSelected() == false){
				blackSquareOn = false;
			}
		});

		yellowCircle = new JCheckBox("Show highlighted piece");
		yellowCircle.setSelected(true);
		yellowCircle.setHorizontalTextPosition( SwingConstants.CENTER );
		yellowCircle.setVerticalTextPosition( SwingConstants.TOP );
		yellowCircle.addActionListener((ActionEvent event) -> {
			if(yellowCircle.isSelected()){
				yellowCircleOn = true;
			}
			else if (yellowCircle.isSelected() == false){
				yellowCircleOn = false;
			}
		});
		visual.add(blackSquare);	
		visual.add(yellowCircle);
		
		gameNoise.setMajorTickSpacing(25);
		gameNoise.setMaximum(100);
		gameNoise.setMinorTickSpacing(10);
		gameNoise.setPaintTicks(true);
		gameNoise.setPaintLabels(true);
		gameNoise.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		        gameNoiseVal = gameNoise.getValue();
		        }
		      });

		pieceNoise = new JCheckBox("Enable Noise for Piece Moving:");
		pieceNoise.setSelected(true);
		pieceNoise.setHorizontalTextPosition( SwingConstants.CENTER );
		pieceNoise.setVerticalTextPosition( SwingConstants.TOP );
		pieceNoise.addActionListener((ActionEvent event) -> {
			if(pieceNoise.isSelected()){
				pieceNoiseOn = true;
			}
			else if (pieceNoise.isSelected() == false){
				pieceNoiseOn = false;
			}
		});

		isGameNoise = new JCheckBox("Music:");
		isGameNoise.setSelected(true);
		isGameNoise.setHorizontalTextPosition( SwingConstants.CENTER );
		isGameNoise.setVerticalTextPosition( SwingConstants.TOP );
		isGameNoise.addActionListener((ActionEvent event) -> {
			if(isGameNoise.isSelected()){
				gameNoiseOn = true;
			}
			else if (isGameNoise.isSelected() == false){
				gameNoiseOn = false;
			}
		});
		sound.add(pieceNoise);	
		sound.add(isGameNoise);

		apply =new JButton("Apply and Exit");  
		//apply.setBackground(Color.yellow);
		apply.addActionListener((ActionEvent event) -> {
			if(apply.isEnabled()){
				this.setVisible(false);
				gameBoard.setShowMoves(blackSquareOn);
				gameBoard.setShowPiece(yellowCircleOn);
				gameBoard.setGameNoiseVal(gameNoiseVal);
				gameBoard.setPieceNoise(pieceNoiseOn);
			}
		});
		button.add(gameNoise);
		//button.add(text3);
		button.add(apply);
		
	
		add(visual,BorderLayout.SOUTH);
		add(sound);
		add(button);
		revalidate();
		repaint();

	}

	private void createMenuBar() {
		menuBar = new JMenuBar();
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("Exit application");
		exit.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		pMenu = new JPopupMenu("Options");

		options = new JMenuItem("Options");
		options.setMnemonic(KeyEvent.VK_E);
		options.setToolTipText("Further customize the game");
		options.addActionListener((ActionEvent event) -> {
			setVisible(true);
		});

		newGame = new JMenuItem("New Game");
		newGame.setMnemonic(KeyEvent.VK_E);
		newGame.setToolTipText("Start a new game");
		newGame.addActionListener((ActionEvent event) -> {
			//New Game
			gameBoard.setNewGame(true);
		});

		help = new JMenuItem("Help");
		help.setMnemonic(KeyEvent.VK_E);
		help.setToolTipText("Don't know how to play chess?");
		help.addActionListener((ActionEvent event) -> {
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("https://www.chess.com/learn-how-to-play-chess");
				  desktop.browse(oURL);
				} catch (Exception e) {
				  e.printStackTrace();
				}		});

		file.add(newGame);
		file.add(options);
		file.add(help);
		file.add(exit);
		menuBar.add(file);
	}

	public boolean getYellow(){
		return yellowCircleOn;
	}
	public boolean getBlack(){
		return blackSquareOn;
	}
}
