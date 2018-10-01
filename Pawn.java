
public class Pawn extends Piece{
	public int startX;
	public int startY;
	private boolean umpesumPossible = false;
	private int available [] [] = new int [8][8];

	public Pawn(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
		startX = xPosition;
		startY = yPosition;
	}
	//Using chess rules, gets basic moves
	public int [][] possibleMoves(Board gameboard){ 
		int x = 0;
		int y = 0;
		if(yPos<8 && yPos>-1){
			if(playerOne == false){
				x=xPos;
				y=yPos+1;
				if((gameboard.getPiece(x, y) == null|| gameboard.getPiece(x,y).getStillAlive() == false) &&y<8 && y>-1 ){ //Space is available
					available [x][y] = 1;
					y=y+1;
					if(startX == xPos && startY == yPos && (gameboard.getPiece(x, y) == null|| gameboard.getPiece(x,y).getStillAlive() == false)){	//if pawn is still in its starting position
						available [x][y] = 1;
					}
				}
				x=xPos+1;
				y=yPos+1;
				if(gameboard.getPiece(x, y)!=null && gameboard.getPiece(x, y).playerOne != this.playerOne){	//If a piece exists that can be taken
					available[x][y] = 2;	
				}
				x=xPos-1;
				if(gameboard.getPiece(x, y)!=null && gameboard.getPiece(x, y).playerOne != this.playerOne){	//If a piece exists that can be taken
					available[x][y] = 2;	
				}
			}
			else if (playerOne){
				x=xPos;
				y=yPos-1;
				if((gameboard.getPiece(x, y) == null|| gameboard.getPiece(x,y).getStillAlive() == false) && y<8 && y>-1 ){ //Space is available
					available [x][y] = 1;
					y=y-1;
					if(startX == xPos && startY == yPos && (gameboard.getPiece(x, y) == null|| gameboard.getPiece(x,y).getStillAlive() == false)){	//if pawn is still in starting position
						available [x][y] = 1;
					}
				}
				x=xPos+1;
				y=yPos-1;
				if(gameboard.getPiece(x, y)!=null && gameboard.getPiece(x, y).playerOne != this.playerOne){	//If a piece exists that can be taken
					available[x][y] = 2;	
				}
				x=xPos-1;
				if(gameboard.getPiece(x, y)!=null && gameboard.getPiece(x, y).playerOne != this.playerOne){	//If a piece exists that can be taken
					available[x][y] = 2;	
				}
			}
		}
		return available;
	}
	public void addPossibleMove(int xPos, int yPos, int moveType){
		//Taking in the xPos and yPos of the piece that can take tempPiece
		available[xPos][yPos] = moveType;
	}
	public int getStartX(){
		return startX;
	}
	public boolean getUmpesum(){
		return umpesumPossible;
	}
	public void setUmpesum(boolean newUmpesum){
		umpesumPossible = newUmpesum;
	}
}
