
public class Bishop extends Piece {
	private boolean iterateThrough;
	private int available [] [] = new int [8][8];
	public Bishop(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
	}
	//Returns an array of possible Bishop moves
	public int [][] possibleMoves(Board gameboard){ 		
		iterateThrough = true;	
		int i = 0;
		int j = 0;

		for(int z = 0; z<4; z++){
			i = xPos;
			j = yPos;
			while(iterateThrough){
				if(z==0){
					i++;
					j++;
				}
				else if(z==1){
					i++;
					j--;
				}
				else if(z==2){
					 i--;
					 j++;
				}
				else if(z==3){
					i--;
					j--;
				}
				if(i > 7 || i<0 || j> 7 || j<0 ){
					iterateThrough = false;
				}
				else if(gameboard.getPiece(i,j) == null || gameboard.getPiece(i,j).getStillAlive() == false){ //if no piece is there, square is available to move to
					available[i][j] = 1;
				}
				else if(gameboard.getPiece(i,j).getPlayerOne() == this.getPlayerOne()){ //if piece is there and is same color not good to move to, cant go past it
					available[i][j] = 3;
					iterateThrough = false;	
				}
				else if((gameboard.getPiece(i,j).getPlayerOne() != this.getPlayerOne())){	//if piece of opposite color is there, take it is option. Cant move past it though.
					available [i][j] = 2;
					iterateThrough = false;
				}
			}
			iterateThrough = true;
		}
		return available;
	}
}
