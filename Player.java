import java.util.*;

public class Player {
	private boolean pressOnce = false;
	public Bishop bishop1,bishop2;
	public Queen queen;
	public King king;
	public Pawn pawn1,pawn2,pawn3,pawn4,pawn5,pawn6,pawn7,pawn8;
	public Rook rook1,rook2;
	public Knight knight1,knight2;
	private boolean umpesumPossible;
	public boolean castle1Possible = true;
	public boolean castle2Possible = true;
	public int casteMove = 0;
	public Piece tempPiece;
	public int possibleMoves [][] = new int [8][8];
	public List<Piece> pieces = new ArrayList<Piece>();
	public boolean run = false;
	public int array [] = new int [2];
	public int array2[] = new int [2];
	public boolean checkMate = false;
	public String name = null;
	public int [][] dot = new int [8][8];

	//Major function to chess, handles the moves of a player. It will do everything about the move needed.
	public boolean move(Board gameBoard){	//Only move onto next turn if returned true
		int moveAllow = 0;
		boolean nextTurn = false;
		if(pressOnce==false){
		if(run == true){
			pressOnce = true;
		}
		else if(gameBoard.getMouseClicked() == true){
			array = gameBoard.getSpace();	
			gameBoard.setMouseClicked(false);
			tempPiece =	gameBoard.getPiece(array[0],array[1]);
			if(tempPiece!=null && tempPiece.getStillAlive()==false){
				tempPiece = null;
			}
		
			
			if( tempPiece != null && tempPiece.getStillAlive() && (gameBoard.getCounter()%2 == 0 && tempPiece.playerOne || gameBoard.getCounter()%2 == 1 && tempPiece.playerOne==false)){	
				gameBoard.setSelectedSquare(array[0], array[1]);
				gameBoard.setShouldDraw(true);
				possibleMoves = gameBoard.getPiece(array[0], array[1]).runPossibleMoves(tempPiece, gameBoard);	
				if(tempPiece.pieceType==1){
					gameBoard.canUmpesum(tempPiece); 
				}
				if(tempPiece.pieceType == 5){
					gameBoard.addCastle(tempPiece);
				}
				for(int i = 0; i<8;i++){
					for(int j = 0; j<8;j++){
						if(gameBoard.moveAllowed(i, j, possibleMoves, tempPiece)!=1 && gameBoard.moveAllowed(i, j, possibleMoves, tempPiece)!=4){
							dot [i][j]=1;
						}
					}
				}
				run = true;
			}
		}
	}
		if(pressOnce==true){
			if(gameBoard.getMouseClicked() == true){	
				array2  = gameBoard.getSpace();	
//				if(gameBoard.getPiece(array2[0], array2[1])!=null &&gameBoard.getPiece(array2[0], array2[1]).getPlayerOne()==tempPiece.getPlayerOne()){
//					tempPiece = gameBoard.getPiece(array2[0], array2[1]);
//					pressOnce = false;
//					gameBoard.setMouseClicked(false);
//			}
			
				moveAllow = gameBoard.moveAllowed(array2[0],array2[1],possibleMoves,gameBoard.getPiece(array[0], array[1]));//pass into moveAllowed new x pos, new y pos, possible moves, and the actual piece
				if(moveAllow==3){ // piece move is valid and would take a piece
					gameBoard.getPiece(array2[0], array2[1]).setStillAlive(false);	//Still alive of piece taken false
					gameBoard.getPiece(array2[0], array2[1]).updatePosition(100, 100);
				}		
				else if(moveAllow==5){ //Umpesum is allowed, tempPiece can move to that square
					if(tempPiece.playerOne){ //White, moving from y8 to y1
						gameBoard.getPiece(array2[0], array2[1]+1).setStillAlive(false);
					}
					else if(tempPiece.playerOne!=true){
						gameBoard.getPiece(array2[0], array2[1]-1).setStillAlive(false); //Pawn above where they clicked is dead
						gameBoard.getPiece(array2[0], array2[1]-1).updatePosition(100, 100);
					}
				}
				if(moveAllow ==6 || moveAllow == 7){
					if(moveAllow==6){
						rook1.updatePosition(rook1.getX()+2, rook1.getY());
						king.updatePosition(king.getX()-2, king.getY());
					}

					if(moveAllow==7){
						rook2.updatePosition(rook2.getX()-3, rook2.getY());
						king.updatePosition(king.getX()+2, king.getY());

					}
					nextTurn=true;
					gameBoard.setSelectedSquare(king.getX(), king.getY());
					gameBoard.storeMoves(king, king.getX(), king.getY());
				}
				}
			}


			if(moveAllow!=0){
				run = false;
				gameBoard.setShouldDraw(true);

				gameBoard.setSelectedSquare(100, 100);
				gameBoard.setMouseClicked(false);
				pressOnce = false;
				if(moveAllow != 1 && moveAllow != 4 && moveAllow!=6 && moveAllow!=7){
					gameBoard.storeMoves(gameBoard.getPiece(array[0], array[1]), array2[0], array2[1]); //stores the move
					gameBoard.getPiece(array[0], array[1]).updatePosition(array2[0], array2[1]);	//update piece location
					gameBoard.setSelectedSquare(array2[0], array2[1]);
					if(gameBoard.getPiece(array2[0], array2[1]).getPieceType()==1){
						gameBoard.checkPawnState((Pawn) gameBoard.getPiece(array2[0],array2[1]));
					}
					nextTurn = true;
				}
		}
		return nextTurn;
	}
	//Allows the adding of a piece to a player at a location in the pieceArray
	public void addPiece(int location, Piece piece){
		Piece nePiece = piece;
		pieces.add(location, nePiece);
	}
	//Remove a piece from the board, at location in pieceArray
	public void removePiece(int location){
		pieces.get(location).setStillAlive(false);
		pieces.get(location).updatePosition(100, 100);
		pieces.remove(location);
	}

	//Function that creates the pieces in Java and adds them to List
	public void initializePieces(int player){ 
		int Pawn = 1+5*player;
		boolean playerOne = false;
		if(player ==1){
			playerOne = true;
		}
		pawn1 = new Pawn(0,Pawn,true,playerOne,1);
		pawn2 = new Pawn(1,Pawn,true,playerOne,1);
		pawn3 = new Pawn(2,Pawn,true,playerOne,1);
		pawn4 = new Pawn(3,Pawn,true,playerOne,1);
		pawn5 = new Pawn(4,Pawn,true,playerOne,1);
		pawn6 = new Pawn(5,Pawn,true,playerOne,1);
		pawn7 = new Pawn(6,Pawn,true,playerOne,1);
		pawn8 = new Pawn(7,Pawn,true,playerOne,1);
		rook1 = new Rook(0,0+7*player,true ,playerOne,2);
		rook2 = new Rook(7,0+7*player,true,playerOne,2);
		knight1 = new Knight(1,0+7*player,true,playerOne,3);
		knight2 = new Knight(6,0+7*player,true,playerOne,3);
		bishop1 = new Bishop(2,0+7*player,true,playerOne,4);
		bishop2 = new Bishop(5,0+7*player,true,playerOne,4);
		queen = new Queen(4,0+7*player,true,playerOne,6);
		king = new King(3,0+7*player,true, playerOne,5);
		pieces.add(pawn1);
		pieces.add(pawn2);
		pieces.add(pawn3);
		pieces.add(pawn4);
		pieces.add(pawn5);
		pieces.add(pawn6);
		pieces.add(pawn7);
		pieces.add(pawn8);
		pieces.add(rook1);
		pieces.add(rook2);
		pieces.add(knight1);
		pieces.add(knight2);
		pieces.add(bishop1);
		pieces.add(bishop2);
		pieces.add(queen);
		pieces.add(king);
	}
	public List<Piece> getPiecesList(){
		return pieces;
	}
	public List<Piece> getPieces(){
		return pieces;
	}
	public boolean umpesonPossibleReturn(){
		return umpesumPossible;
	} 
	public boolean isCastle1Possible() {
		return castle1Possible;
	}
	public void setCastle1Possible(boolean castlePossible) {
		this.castle1Possible = castlePossible;
	}
	public boolean isCastle2Possible() {
		return castle2Possible;
	}
	public void setCastle2Possible(boolean castlePossible) {
		this.castle2Possible = castlePossible;
	}
	public boolean getCheckMate(){
		return checkMate;
	}
	public void setCheckMate(boolean yes){
		checkMate = yes;
	}
	public void setName(String names){
		name = names;
	}
	public String getName(){
		return name;
	}
	public int [][] getDot(){
		return dot;
	}
	public void resetDot(){
		for(int i =0;i<8;i++){
			for(int j =0; j<8;j++){
				dot[i][j]=0;
			}
		}
	}
}
