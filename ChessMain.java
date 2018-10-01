import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


//This is my main class, it will call upon the other classes for the most part to set up the board and handle all moving functions.

public class ChessMain  extends JFrame {
	static boolean Run = true;
	static boolean p1turn = true;
	static Gui gui = new Gui();
	static Board gameBoard = gui.getBoard();
	static Player white, black;
	static Sound noises = gui.getNoise();
	public ChessMain(){

	}

	public static void main(String[] args) {
		
		while(true){
		//	noises.music();
			gameBoard = gui.getBoard();
			white = gui.getWhite();
			black = gui.getBlack();
			
			if(gameBoard.getNewGame()){
				gui.close();
				gui = new Gui();
				gameBoard.setNewGame(false);
			}

			if(Run){	
				if(gameBoard.getShouldDraw() == true){
					gui.refresh();
					gameBoard.setShouldDraw(false);
				}
				if(p1turn == true){
					if(white.move(gameBoard)){
						p1turn = false;
						gui.refresh();
						if(gameBoard.isCheckSelf(false)){
							gameBoard.isCheckMate(false);
						}
						if(black.getCheckMate()){
							gameBoard.endGame(white);
							Run=false;	
						}
					}	
				}
				if(p1turn == false){
					if(black.move(gameBoard)){
						p1turn = true;
						gui.refresh();
						if(gameBoard.isCheckSelf(true)){
							gameBoard.isCheckMate(true);
						}
						if(white.getCheckMate()){
							gameBoard.endGame(black);
							Run=false;
						}
					}
				}
			}
		}
	}
}