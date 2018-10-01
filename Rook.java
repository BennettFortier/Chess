
public class Rook extends Piece {

	public Rook(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
	}
	private int available [] [] = new int [8][8];
	public boolean iterateThrough;
	//Returns an array of possible Rook Moves
	public int [][] possibleMoves(Board gameboard){ 
		int i = 0;
		int j = 0;
		for(int z = 0; z<4; z++){
			i = xPos;
			j = yPos;
			while(iterateThrough){
				if(z==0){
					i++;		
				}
				else if(z==1){		
					j--;
				}
				else if(z==2){				
					 j++;
				}
				else if(z==3){
					i--;
				}
				if(i > 7 || i<0 || j> 7 || j<0 ){
					iterateThrough = false;
				}

				else if(gameboard.getPiece(i,j) == null|| gameboard.getPiece(i,j).getStillAlive() == false){ 
					available[i][j] = 1;
				}
				else if(gameboard.getPiece(i,j).getPlayerOne() == this.getPlayerOne()){ 
					available[i][j] = 3;
					iterateThrough = false;	
				}
				else if((gameboard.getPiece(i,j).getPlayerOne() != this.getPlayerOne())){
					available [i][j] = 2;
					iterateThrough = false;
				}
			}
			iterateThrough = true;
		}
		return available;
	}
}