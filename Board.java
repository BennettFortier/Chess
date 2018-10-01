import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

//import Piece.Pieces;
public class Board extends JFrame{
	public boolean mouseIsClicked;
	public boolean mouseIsDragged;
	public Image board,bPawn,wPawn,bRook,wRook,bKnight,wKnight,bBishop,wBishop,bQueen,wQueen,bKing,wKing,square,cartoon;
	public List<Image> Images = new ArrayList<Image>();
	Graphics2D g;
	public Player white,black;
	public int [] xMoves = new int [1000];
	public int [] yMoves = new int [1000];
	public Piece [] PieceMove = new Piece [1000];
	public int [] prexMoves = new int [1000];
	public int [] preyMoves = new int [1000];
	public int counter = 0;
	public int counter2 = 0;
	private handlerClass handlers;
	private Image board2;
	public boolean shouldDraw = false;
	public int [] selectedSquare = new int[2];
	public boolean pawnUpdate = !true;
	public Pawn pawn = null;
	public Piece newPawn = null;
	boolean showMoves = true;
	boolean showPiece = true;
	boolean pieceNoise = true;
	boolean gameNoise = true;
	boolean newGame = false;
	public int gameNoiseVal = 25;


	public Board(Player whites, Player blacks, handlerClass handler) {
		white=whites;
		black = blacks;
		handlers = handler;
		selectedSquare[0] = 100;
		selectedSquare[1] = 100;
	}
	//Returns the piece at a given square
	public Piece getPiece(int xPos, int yPos){		
		List<Piece> tempPieceList = null;
		Piece tempPiece = null;
		Piece realPiece = null;
		for(int j = 0; j<2;j++){
			if(j==0){
				tempPieceList = white.getPiecesList();
			}
			else if(j==1){
				tempPieceList = black.getPiecesList();
			}
			for(int i = 0; i<tempPieceList.size(); i++){
				tempPiece = tempPieceList.get(i);
				if(tempPiece.getX() == xPos && tempPiece.getY() == yPos){
					realPiece = tempPiece;
				}
			}
		}
		return realPiece;
	}
	//This function returns what location on the 8x8 grid of the chessBoard the mouse is currently at, or was.
	public int [] getSpace(){			
		int array [] = new int [2];
		double mouseX = handlers.getMouseXPosition();
		double mouseY = handlers.getMouseYPosition();
		for(double i = 50, counter = 0; counter<= 7;i=i+500/8, counter++){
			for(double j = 50, counter2 = 0; counter2<= 7;j=j+500/8, counter2++){
				if(i<mouseX && i+62.5>mouseX && j<mouseY && j+62.5>mouseY){
					array [0] = (int)counter;
					array [1] = (int)counter2;
				}
			}
		}
		return array;
	}
	//Major function that takes in a possible move for a piece and determines whether that move is a legal chess move
	public int moveAllowed(int xPos, int yPos, int[][] possibleMoves, Piece tempPiece) {
		int saveXpos = tempPiece.getX();
		int saveYpos = tempPiece.getY();
		int moveAllowed = 1;
		boolean state = false;
		int val = possibleMoves[xPos][yPos];
		if(val == 0 || val ==3){	
			moveAllowed= 1;
		}
		else if(val>=1 && val<=4){	
			if(getPiece(xPos, yPos)!=null){
				if(getPiece(xPos, yPos).getStillAlive()){
					getPiece(xPos,yPos).setStillAlive(false);
					state = true;
				}
			}
			tempPiece.updatePosition(xPos, yPos);	
			boolean checkS = isCheckSelf(tempPiece.getPlayerOne());	
			if(checkS == false){
				moveAllowed = val +1;
			}
			else{
				moveAllowed = 4;
			}
			tempPiece.updatePosition(saveXpos, saveYpos);

			if(state){
				state = false;			
				getPiece(xPos,yPos).setStillAlive(true); 	
			}
		}
		if(val == 5){
			moveAllowed=  6;
		}
		if(val == 6){
			moveAllowed=  7;
		}
		return moveAllowed;
	}
	//Function that takes in a piece that was just moved, and checks to see if it would result in putting its own king in check
	public boolean isCheckSelf(Boolean playerOne){
		boolean check = false;
		if(fillArray(playerOne)==2 || fillArray(playerOne)==1){	
			check = true;
		}
		return check;
	}
	//Checks to see if anyone move would result in king not being in check, if not then checkmate
	public void isCheckMate(boolean playerOne){
		boolean checkMate = true;
		Player color = null;
		if(playerOne){
			color = white;
		}
		else{
			color = black;
		}
		for(int i = 0; i<color.pieces.size();i++){
			Piece tempPiece = color.pieces.get(i);
			if(tempPiece.getStillAlive()){
				int [][] array = tempPiece.runPossibleMoves(tempPiece, this);
				for(int a = 0;a<8; a++ ){
					for(int b=0;b<8;b++){
						if(array[a][b]==1 || array[a][b]==2){
							if(moveAllowed(a,b,array,tempPiece)!=4){
								checkMate = false;
							}
						}
					}
				}
			}
		}
		if(checkMate == true){
			if(playerOne){
				white.setCheckMate(true);
			}
			else{
				black.setCheckMate(true);
			}
		}
	}
	//Takes in the piece's color that was temporarily updated, returns filledArray of the same color's king
	public int fillArray(boolean playerOne){	
		List<int [][]> pieceArrays = new ArrayList<int [][]>();
		List<Piece> pieces =  new ArrayList<Piece>();
		int xPos = 0;
		int yPos = 0;
		if(playerOne){	
			pieces = black.getPieces();
			for(int i =0; i<black.getPieces().size();i++){
				if(pieces.get(i).stillAlive){
					pieceArrays.add(black.pieces.get(i).runPossibleMoves(pieces.get(i), this));
				}
			}
			xPos = white.king.getX();
			yPos = white.king.getY();
		}
		else {
			pieces = white.getPieces();
			for(int i =0; i<white.getPieces().size();i++){
				if(pieces.get(i).stillAlive){
					pieceArrays.add(white.pieces.get(i).runPossibleMoves(pieces.get(i), this));
				}			
			}
			xPos = black.king.getX();
			yPos = black.king.getY();
		}
		int [][]tempBoard = returnFilledArray(pieceArrays);
		return tempBoard[xPos][yPos];
	}
	//Fills the board array with squares labeled as their value concerning their availability for a king to move to
	public int[][] returnFilledArray(List<int [][]> pieceArrays){
		int [][] filledArray = new int [8][8];
		for(int a = 0; a<8;a++){		
			for(int b = 0; b<8; b++){
				filledArray [a][b] = 0;
			}
		}
		for(int i=0;i<8;i++){	
			for(int j=0;j<8;j++){
				for(int f=0;f<pieceArrays.size();f++){
					int tempNumb = checkArraySpot(f,i,j,pieceArrays);
					if(tempNumb==2) { 	
						filledArray[i][j] =2;
					}
					if(filledArray[i][j] != 2){
						if(tempNumb==4){	//Umpesum check
							filledArray[i][j] = 4;
						}
						else if(tempNumb==5){	//Castle x8
							filledArray[i][j] = 5;
						}
						else if(tempNumb==6){		
							filledArray[i][j] = 6;
						}
						if(filledArray[i][j]!=4 && filledArray[i][j]!=5 && filledArray[i][j]!=6){
							if(tempNumb==1) { 
								filledArray[i][j] =1;
							}
							else if(tempNumb==3 && filledArray[i][j]!=1){	//If square cannot be taken and is available, then its available from some piece
								filledArray[i][j]=3;
							}
						}
					}

				}
			}
		}	
		return filledArray;
	}
	//Used for other functions as a means of checking a single square on the board array
	public int checkArraySpot(int arrayLocation, int xPos, int yPos, List<int [][]> pieceArrays){	//Takes in a pieces possible moves, an x pos, and a y pos and returns the value of the pieces possibles moves
		int [][] tempArray = pieceArrays.get(arrayLocation);
		return tempArray [xPos][yPos];

	}

	//Stores the moves of the piece moved, and checks to see if same color king can castle or not 
	public void storeMoves(Piece piece, int xPosition, int yPosition  ){
		int  prexPosition= piece.getX();
		int  preyPosition = piece.getY();
		PieceMove[counter] = piece;
		xMoves [counter] = xPosition;
		yMoves [counter] = yPosition;
		prexMoves[counter] = prexPosition;
		preyMoves [ counter]= preyPosition;
		Player color = null;
		if(counter%2 ==0){
			color = white;
		}
		else {
			color = black;
		}
		if(PieceMove[counter] != null){
			if(PieceMove[counter].pieceType==5){	
				if(xMoves[counter]-prexMoves[counter]>1){
					color.casteMove = counter;
				}
				color.setCastle1Possible(false);
				color.setCastle2Possible(false);
			}
			if(PieceMove[counter].pieceType == 2){	//Piece is a rook
				if(prexMoves[counter]==7){	//If rook that was moved was located at 8, rook 2 
					color.setCastle2Possible(false);
				}
				if(prexMoves[counter]==0){
					color.setCastle1Possible(false);
				}
			}
		}
		if(counter%2 ==0){
			white = color;
		}
		else {
			black = color;
		}
		counter++;
	}
	public Piece[] getPieceMove(){
		return PieceMove;
	}
	public int[] getPieceMovesX(){
		return xMoves;
	}
	public int[] getPieceMovesY(){
		return yMoves;
	}
	//Checks to see if the pawn attempted to be moved can umpesum, if so it adds it to that pawn's possible moves
	public void canUmpesum(Piece tempPiece) {
		if(counter>0){
			counter = counter -1;
			if(PieceMove[counter]!=null){
				if(PieceMove[counter].pieceType == 1){	//piece last moved is a pawn
					if(yMoves[counter]-preyMoves[counter] == -2 || yMoves[counter]-preyMoves[counter] == 2){
						if(getPiece(xMoves[counter]-1, yMoves[counter])!=null && getPiece(xMoves[counter]-1, yMoves[counter]).pieceType == 1 && getPiece(xMoves[counter]-1, yMoves[counter]).playerOne==tempPiece.playerOne){
							getPiece(xMoves[counter]-1, yMoves[counter]).addPossibleMove(xMoves[counter], yMoves[counter]-(-1+2*(counter%2)), getPiece(xMoves[counter]-1, yMoves[counter]), 4);
						}
						if(getPiece(xMoves[counter]+1, yMoves[counter])!=null && getPiece(xMoves[counter]+1, yMoves[counter]).pieceType == 1 && getPiece(xMoves[counter]+1, yMoves[counter]).playerOne==tempPiece.playerOne){							
							getPiece(xMoves[counter]+1, yMoves[counter]).addPossibleMove(xMoves[counter], yMoves[counter]-(-1+2*(counter%2)),getPiece(xMoves[counter]+1, yMoves[counter]), 4);
						}
					}
				}
			}
			counter++;
		}
	}
	//Checks to see if castle is possible, adds it to the kings possible moves
	public void addCastle(Piece tempPiece){
		Player color = null;

		if(tempPiece.playerOne){
			color = white;
		}
		else{
			color = black;
		}
		int tempKingX = color.king.getX();
		int tempKingY= color.king.getY();
		if(color.isCastle1Possible()==true){
			if(getPiece(tempKingX-1,tempKingY)==null && getPiece(tempKingX-2, tempKingY)==null){
				if(tempPiece.playerOne){
					white.king.updatePosition(tempKingX-1, tempKingY);
				}
				else{
					black.king.updatePosition(tempKingX-1, tempKingY);
				}
				if(isCheckSelf(color.king.getPlayerOne())==false){
					if(tempPiece.playerOne){
						white.king.updatePosition(tempKingX-2, tempKingY);
					}
					else{
						black.king.updatePosition(tempKingX-2, tempKingY);
					}			
					if(isCheckSelf(color.king.getPlayerOne())==false){
						color.king.addPossibleMove(tempKingX-2, tempKingY, color.king, 5);
					}
				}
			}
		}	
		if(color.isCastle2Possible()==true){
			if(getPiece(tempKingX+1, tempKingY)==null && getPiece(tempKingX+2, tempKingY)==null && getPiece(tempKingX+3, tempKingY)==null){
				if(tempPiece.playerOne){
					white.king.updatePosition(tempKingX+1, tempKingY);
				}
				else{
					black.king.updatePosition(tempKingX+1, tempKingY);
				}				
				if(isCheckSelf(color.king.getPlayerOne())==false){
					if(tempPiece.playerOne){
						white.king.updatePosition(tempKingX+2, tempKingY);
					}
					else{
						black.king.updatePosition(tempKingX+2, tempKingY);
					}			
					if(isCheckSelf(color.king.getPlayerOne())==false && getPiece(tempKingX+3,tempKingY)==null){
						color.king.addPossibleMove(tempKingX+2, tempKingY, color.king, 6);
					}
				}
			}
		}
		color.king.updatePosition(tempKingX, tempKingY);
		if(tempPiece.playerOne){
			white = color;
		}
		else{
			black = color;
		}
	}
	//Checks to see if pawn reaches last rank
	public void checkPawnState(Pawn piece) {
		if(piece.getPieceType()==1 && (piece.getY()==7 || piece.getY()==0)){
			pawnUpdate = true;
			pawn = piece;

		}
	}
	//Detects which option was chosen for pawn replacement
	public void chooseOption() {
		while(getMouseClicked()==false){
			System.out.print("");
		}
		int temp [] = getSpace();
		int x = temp[0];
		int y = temp[1];	
		pawnUpdate = false;
		if(x==2 && y ==3 || x==2 && y ==4 ){
			transform(1);
		}
		if(x==3 && y ==3 || x==3 && y ==4){
			transform(2);
		}
		if(x==4 && y ==3 || x==4 && y ==4){
			transform(3);
		}
		if(x==5 && y ==3 || x==5 && y ==4){
			transform(4);
		}
		shouldDraw=true;
	}
	//Used when pawn gets to last rank: removes pawn from game and adds the piece selected at that square
	public void transform(int type){
		int pawnLoc = pawn.getStartX();
		List<Piece> pieceList = black.getPieces();
		boolean player = false;
		if(pawn.getPlayerOne()){
			player = true;
			pieceList = white.getPieces();
		}
		Piece tempPiece = pieceList.get(pawnLoc);
		if(type ==1){
			newPawn = new Queen(tempPiece.getX(),tempPiece.getY(),tempPiece.getStillAlive(),player,6);
		}
		else if(type == 2){
			newPawn = new Rook(tempPiece.getX(),tempPiece.getY(),tempPiece.getStillAlive(),player,2);
		}
		else if(type == 3){
			newPawn = new Knight(tempPiece.getX(),tempPiece.getY(),tempPiece.getStillAlive(),player,3);
		}
		else if(type == 4){
			newPawn = new Bishop(tempPiece.getX(),tempPiece.getY(),tempPiece.getStillAlive(),player,4);
		}
		if(pawn.getPlayerOne()){
			white.removePiece(pawnLoc);
			white.addPiece(pawnLoc, newPawn);
		}
		else{
			black.removePiece(pawnLoc);
			black.addPiece(pawnLoc, newPawn);
		}
	}
	public void setNewGame(boolean newGames){
		//System.out.println(newGames);
	
		newGame = newGames;
	}
	public boolean getNewGame(){
	//	System.out.print(newGame);
		return newGame;
	}
	public int getCounter(){
		return counter;
	}

	public boolean getPawnUpdate(){
		return pawnUpdate;

	}
	public void setPawnUpdate(boolean newPawn){
		pawnUpdate = newPawn;
	}
	public int getGameNoiseVal(){
		return gameNoiseVal;
	}
	public void setGameNoiseVal(int val)
	{
		gameNoiseVal = val;
	}
	public void setPieceNoise(boolean pieceNoiseOn) {
		pieceNoise = pieceNoiseOn;
	}
	public boolean getPieceNoise(){
		return pieceNoise;
	}
	public boolean getGameNoise(){
		return gameNoise;
	}
	public void setGameNoise(boolean newnoise){
		gameNoise = newnoise;
	}
	//DRAWING STUFF -------------------------------------------------------------
	public void setShowMoves(boolean bool){
		showMoves = bool;
	}
	public void setShowPiece(boolean bool){
		showPiece = bool;
	}
	public boolean getShowMoves(){
		return showMoves;
	}
	public boolean getShowPiece(){
		return showPiece;
	}
	public boolean getShouldDraw(){
		return shouldDraw;
	}
	public void setShouldDraw(boolean yes){
		shouldDraw = yes;
	}
	public void setSelectedSquare(int xPos, int yPos){
		selectedSquare[0] = xPos;
		selectedSquare [1] = yPos;
	}
	public void clearBoard(){
		g.setBackground(new Color(255, 255, 255, 0));
		g.clearRect(0, 0, board.getWidth(this), board.getHeight(this));
	}
	public Image returnImage(int number){
		return Images.get(number);
	}

	public int getMouseXPosition(){
		int x = handlers.getMouseXPosition();
		return x;
	}
	public int getMouseYPosition(){
		int x = handlers.getMouseYPosition();
		return x;
	}
	public boolean getMouseClicked(){
		return handlers.getMouseClicked();

	}
	public void setMouseClicked(boolean a){
		handlers.setMouseClicked(a);
	}
	//Assigns images their path
	public Image getImage(String path){		
		Image tempImage = null;
		try{
			tempImage = ImageIO.read(getClass().getResource(path));
		}
		catch(Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}
		return tempImage;

	}
	//Calls for getImage if the image is null and not already set
	public Image assignImage(Image wBishop3, String path){	
		if (wBishop3 == null){
			return getImage(path);
		}
		else
			return wBishop3;
	}
	//A method that assigns all the images their image
	public void assignAllImages(){		
		board = assignImage(board,"Chess Board.png" );
		bPawn = assignImage(bPawn, "Black Pawn.png");
		wPawn = assignImage(wPawn, "White Pawn.png");
		bRook = assignImage(bRook, "Black Rook.png");
		wRook = assignImage(wRook,"White Rook.png");
		bKnight = assignImage(bKnight,"Black Knight.png");
		wKnight = assignImage(wKnight,"White Knight.png");
		bBishop = assignImage(bBishop,"Black Bishop.png");
		wBishop = assignImage(wBishop,"White Bishop.png");
		bKing = assignImage(bKing, "Black King.png");
		wKing = assignImage(wKing, "White King.png");
		bQueen = assignImage(bQueen, "Black Queen.png");
		wQueen = assignImage(wQueen, "White Queen.png");
		square = assignImage(square, "Yellow Circle.png");
		cartoon = assignImage(cartoon, "carton.jpg");
		Images.add(board);
		Images.add(bPawn); 
		Images.add(bRook);		
		Images.add(bKnight);
		Images.add(bBishop);
		Images.add(bKing);
		Images.add(bQueen);
		Images.add(wPawn); 	
		Images.add(wRook);
		Images.add(wKnight);
		Images.add(wBishop);	
		Images.add(wKing);
		Images.add(wQueen);
	}
	//Returns the board
	public Drawing getBoard(){		//Used for basic setup functions, wont be called again
		Drawing d1 = new Drawing(0,0,board,true);
		return d1;
	}
	//Used to refresh board
	public Drawing getNewBoard(){
		board2 = assignImage(board2,"Chess Board.png" );
		Drawing d1 = new Drawing(0,0,board2,true);
		return d1;
	}
	//Handles the drawing of the options for upgrading a pawn
	public void drawOpitions(){
		if(pawnUpdate == true){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(Color.BLACK);
			g2d.fillRect(2*500/8-3, 3*500/8-500/16-3, 4*500/8+6, 3*500/8+6);
			g2d.setPaint(Color.WHITE);
			g2d.fillRect(2*500/8, 3*500/8-500/16, 4*500/8, 3*500/8);
			g2d.setPaint(Color.BLACK);
			g2d.fillRect(2*500/8-3, (3*500/8-500/16)+(3*500/8)/2-5, 4*500/8+8, 8);
			Drawing d2 = new Drawing(2,3,wQueen,false);
			Drawing d3 = new Drawing(2,4,bQueen,false);
			Drawing d4 = new Drawing(3,3,wRook,false);
			Drawing d5 = new Drawing(3,4,bRook,false);
			Drawing d6 = new Drawing(4,3,wKnight,false);
			Drawing d7 = new Drawing(4,4,bKnight,false);
			Drawing d8 = new Drawing(5,3,wBishop,false);
			Drawing d9 = new Drawing(5,4,bBishop,false);
			d2.paint(g2d);	
			d3.paint(g2d);
			d4.paint(g2d);	
			d5.paint(g2d);
			d6.paint(g2d);	
			d7.paint(g2d);
			d8.paint(g2d);	
			d9.paint(g2d);
			chooseOption();
		}
	}
	//Handles the drawing of the board and pieces
	public void Draw(){
		int [][] tempWhite = white.getDot();
		int [][] tempBlack = black.getDot();

		List<Piece> temp = null;
		g = ((BufferedImage) board).createGraphics();
		if(selectedSquare[0]!= 100 && selectedSquare[1]!=100 && showPiece){
			Drawing d2 = new Drawing(selectedSquare[0], selectedSquare[1],square,false);
			d2.paint(g);
		}

		for(int i = 0; i<black.getPieces().size();i++){
			for(int j = 0;j<2;j++){
				if(j==0){
					temp = black.getPieces();
				}
				else if(j==1){
					temp=white.getPieces();
				}
				boolean alive = temp.get(i).getStillAlive();
				if(alive){
					Drawing d2 = new Drawing(temp.get(i).getX(),temp.get(i).getY(),Images.get(temp.get(i).getPieceType()+6*j),false);
					d2.paint(g);
				}
			}
		}
		if(showMoves){
			for(int i = 0; i<8;i++){
				for (int j =0;j<8;j++){
					if(tempWhite[i][j]==1 || tempBlack[i][j] == 1){
						Graphics2D g2d = (Graphics2D) g;
						g2d.setColor(Color.BLACK);
						//g2d.fillRect(i*500/8+1500/64, j*500/8+1500/64, 500/32, 500/32);
						g2d.fillOval(i*500/8 +1500/64, j*500/8+1500/64, 500/32, 500/32);
					}
				}
			}
		}
		white.resetDot();
		black.resetDot();

	}

	//Handles the imaging of when the game is over
	public void endGame(Player player){
		System.out.print(" ");
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0,0,500,500);
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(10,10,480,480);


		String str = null;
		if(player == white){
			str = "Congratulations White Won!";
		}
		else{
			str = "Congratulations Black Won!";
		}


		Font myFont = new Font("Serif",Font.BOLD,26);
		g2d.setFont(myFont);
		g2d.setPaint(Color.BLACK);
		g2d.drawString(str, 85, 145);
		g2d.drawImage(cartoon, 150, 300, 200, 110, this);

	}

}



