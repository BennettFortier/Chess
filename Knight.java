
public class Knight extends Piece {
	private int available [] [] = new int [8][8];
	public Knight(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
		// TODO Auto-generated constructor stub
	}
	//Returns an array of possible moves for knight
	public int [][] possibleMoves(Board gameboard){ 		
		if(xPos+2<=7 && yPos+1<=7){
			available[xPos+2][yPos+1] = knightFunction(xPos+2, yPos+1, gameboard); //case 1
		}
		if(yPos+2<=7 && xPos+1<=7){
			available[xPos+1][yPos+2] = knightFunction(xPos+1, yPos+2, gameboard); //case 2
		}
		if(xPos-1>=0 && yPos+2<=7){
			available[xPos-1][yPos+2] = knightFunction(xPos-1, yPos+2, gameboard); //case 3
		}
		if(xPos-2>=0 && yPos+1<=7){
			available[xPos-2][yPos+1] = knightFunction(xPos-2, yPos+1, gameboard); //case 4
		}
		if(xPos-2>=0 && yPos-1>=0){
			available[xPos-2][yPos-1] = knightFunction(xPos-2, yPos-1, gameboard); //case 5
		}
		if(yPos-2>=0 && xPos-1>=0){
			available[xPos-1][yPos-2] = knightFunction(xPos-1, yPos-2, gameboard); //case 6
		}
		if(xPos+1<=7 && yPos-2>=0){
			available[xPos+1][yPos-2] = knightFunction(xPos+1, yPos-2, gameboard);	//case 7
		}
		if(xPos+2<=7 && yPos-1>=0){
			available[xPos+2][yPos-1] = knightFunction(xPos+2, yPos-1, gameboard); //case 8
		}
		return available;
	}
	//Called upon as a reused method to check if square is available and assign a value to it
	public int knightFunction(int tempX, int tempY, Board gameboard){	//takes in the possible position of future rook move, checks to see what outcome would be
		if(tempX>=0 && tempX<=7 && tempY >=0 && tempY <=7){	//checks to see if the rook move is a valid move on the the 8x8 board
			if(gameboard.getPiece(tempX, tempY)==null|| gameboard.getPiece(tempX,tempY).getStillAlive() == false){
				return 1;
			}
			else if(gameboard.getPiece(tempX, tempY).playerOne != this.playerOne){
				return 2;
			}
			else if(gameboard.getPiece(tempX, tempY).playerOne == this.playerOne){
				return 3;
			}
		}
		return 0;
	}
}
