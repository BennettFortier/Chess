
public class Piece {

	public int xPos;
	public int yPos;
	public boolean stillAlive;
	public boolean playerOne;
	public int pieceType;
	public int available [] [] = new int [8][8];
	public Piece(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceTypes){
		xPos = xPosition;
		yPos = yPosition;
		stillAlive = stillAlives;
		playerOne = PlayerOne;
		pieceType = pieceTypes;
	}
	public int getPieceType(){
		return pieceType;
	}
	public void setPieceType(int type){
		pieceType = type;
	}
	public boolean getPlayerOne(){		
		return playerOne;
	}	
	public int getX(){	
		return xPos;
	}
	public int getY(){ 	
		return yPos;
	}
	//takes in an x and y position, and updates the pieces position.
	public void updatePosition(int newxPos, int newyPos){	
		xPos = newxPos;
		yPos = newyPos;
	}
	public void setStillAlive(boolean newAlive){
		stillAlive = newAlive;
	}
	public boolean getStillAlive(){
		return stillAlive;
	}
	//Adds a possible move to a pieces possibleMoves array
	public void addPossibleMove(int xPos, int yPos, Piece tempPiece,int moveType){
		//Taking in the xPos and yPos of the piece that can take tempPiece
		int tempArray [][] = tempPiece.available;
		tempArray[xPos][yPos] = moveType;
		tempPiece.available = tempArray;
	}
	//Calls on the given piece's possibleMoves
	public int [][] runPossibleMoves(Piece tempPiece, Board gameboard){
		for(int a = 0; a<7;a++){		
			for(int b = 0; b<7; b++){
				available [a][b] = 0;
			}
		}
		if(tempPiece.stillAlive && tempPiece.getX()!=100){
			if(tempPiece.pieceType == 1){
				Pawn tempPawn = (Pawn)tempPiece; 
				tempPiece.available = tempPawn.possibleMoves(gameboard);	
				return available;
			}
			else if(tempPiece.pieceType ==2){	
				Rook tempRook = (Rook)tempPiece;
				tempPiece.available = tempRook.possibleMoves(gameboard);
				return available;
			}
			else if(tempPiece.pieceType ==3){	
				Knight tempKnight = (Knight)tempPiece;
				tempPiece.available = tempKnight.possibleMoves(gameboard);
				return available;
			}
			else if(tempPiece.pieceType ==4){	
				Bishop tempBishop = (Bishop)tempPiece;
				tempPiece.available = tempBishop.possibleMoves(gameboard);
				return available;
			}
			else if(tempPiece.pieceType ==5){	
				King tempKing = (King)tempPiece;
				tempPiece.available = tempKing.possibleMoves(gameboard); 
				return available;
			}
			else if(tempPiece.pieceType ==6){	
				Queen tempQueen = (Queen)tempPiece;
				tempPiece.available = tempQueen.possibleMoves(gameboard);
				return available;
			}
		}
		return null;
	}
}










