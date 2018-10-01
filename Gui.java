import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

public class Gui extends JFrame {
	boolean p1turn = true;
	Player white = new Player();
	Player black = new Player();
	handlerClass handler  = new handlerClass();
	Board gameBoard = new Board(white, black, handler);
	Sound noise = new Sound(gameBoard);
	Component temp;
	MouseEvent e;
	Menu menu;
	boolean newGame;
	public Gui(){
		white.initializePieces(1);
		black.initializePieces(0);
		gameBoard.assignAllImages();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,620);
		setTitle("Ben's Chess Game");
		setLocationRelativeTo(null);
		gameBoard.Draw();
		menu = new Menu(gameBoard);
		addMenu();
		setVisible(true);
		refresh();
	}
	private void addMenu() {
		setJMenuBar(menu.returnMenu());
	}
	public void refresh(){
		gameBoard.clearBoard();
		gameBoard.Draw();
		temp = gameBoard.getBoard();
		temp.addMouseListener(handler);
		add(temp);
		revalidate();
		add(gameBoard.getNewBoard());
		repaint();
		revalidate();
		gameBoard.drawOpitions();
	}
	public void setNewGame(boolean newGames){
		newGame = newGames;
	}
	public boolean getNewGame(){
		return newGame;
	}
	public Board getBoard(){
		return gameBoard;
	}
	public Player getWhite(){
		return white;
	}
	public Player getBlack(){
		return black;
	}
	public Sound getNoise(){
		return noise;
	}
	public void close(){
		this.setVisible(false);	
	}
}	




