
public class Queen extends Piece {

	public Queen(int xPosition, int yPosition, boolean stillAlives, boolean PlayerOne, int pieceType) {
		super(xPosition, yPosition, stillAlives, PlayerOne, pieceType);
		// TODO Auto-generated constructor stub
	}
	private int available [] [] = new int [8][8];
	public boolean iterateThrough;
	//Fills a double array with Queen's possible moves
	public int [][] possibleMoves(Board gameboard){ 
		int i = 0;
		int j = 0;
		for(int z = 0; z<8; z++){
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
				if(z==4){
					i++;
					j++;
				}
				else if(z==5){
					i++;
					j--;
				}
				else if(z==6){
					 i--;
					 j++;
				}
				else if(z==7){
					i--;
					j--;
				}
				if(i > 7 || i<0 || j> 7 || j<0 ){
					iterateThrough = false;
				}
				else if(gameboard.getPiece(i,j) == null|| gameboard.getPiece(i,j).getStillAlive() == false){ //if no piece is there, square is available to move to
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