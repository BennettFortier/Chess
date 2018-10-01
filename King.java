
public class King extends Piece {

	public King(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
		// TODO Auto-generated constructor stub
	}
	public int x;
	public int y;
	private int available [] [] = new int [8][8];
	//Fills a double array with King's possible moves
	public int [][] possibleMoves(Board gameboard){ 		
		x = xPos + 1;
		y = yPos +1;
		checkKing(gameboard);
		y = yPos-1;
		checkKing(gameboard);
		x = xPos-1;
		y = yPos+1;
		checkKing(gameboard);
		y=yPos-1;
		checkKing(gameboard);
		x=xPos+1;
		y = yPos;
		checkKing(gameboard);
		x=xPos;
		y=yPos+1;
		checkKing(gameboard);
		x=xPos-1;
		y=yPos;
		checkKing(gameboard);
		x=xPos;
		y=yPos-1;
		checkKing(gameboard);
		return available;
	}
	//Used to check if King is in bounds of board
	public void checkKing(Board gameboard){
		if(x<8 && y<8 && x > -1 && y>-1){
			available [x][y] = kingMoves(x, y, gameboard); //++
		}
	}
	//Assigns a value to kings move on the board 
	public int kingMoves(int tempX, int tempY, Board gameboard){
		int moveCount =0;
		if(tempX>=0 && tempX<=7 && tempY >=0 && tempY <=7){	
			if(gameboard.getPiece(tempX, tempY)==null || gameboard.getPiece(tempX,tempY).getStillAlive() == false){
				moveCount = 1;
			}
			else if(gameboard.getPiece(tempX, tempY).playerOne != this.playerOne){
				moveCount = 2;
			}
			else if(gameboard.getPiece(tempX, tempY).playerOne == this.playerOne){
				moveCount = 3;
			}
		}
		return moveCount;
	}
}
