package org.hermann.model.board;

import org.hermann.model.pieces.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The board is an 8 by 8 array */
	private Piece[][] board = {{new Rook('b'),new Knight('b'),new Bishop('b'),new Queen('b'),new King('b'),new Bishop('b'),new Knight('b'),new Rook('b')},
  							  {new Pawn('b'),new Pawn('b'),new Pawn('b'),new Pawn('b'),new Pawn('b'),new Pawn('b'),new Pawn('b'),new Pawn('b')},
  							  {null,null,null,null,null,null,null,null},
  							  {null,null,null,null,null,null,null,null},
  							  {null,null,null,null,null,null,null,null},
  							  {null,null,null,null,null,null,null,null},
  							  {new Pawn('w'),new Pawn('w'),new Pawn('w'),new Pawn('w'),new Pawn('w'),new Pawn('w'),new Pawn('w'),new Pawn('w')},
  							  {new Rook('w'),new Knight('w'),new  Bishop('w'),new Queen('w'),new King('w'),new Bishop('w'),new Knight('w'),new Rook('w')} };
	
	/** Initializes new chess board (a fresh game) and instances for every piece on the field (the names are put together by their type,
	 *  the starting y-coordinate on the board, the starting x-coordinate on the board, and also their color).<p>
	 *  
	 *  Problem: If I want to set up the board in the constructor as shown below, the x and y coordinates will be swapped
	 *  (board[6][3] would actually address the pawn p63w in the 6th row and the 3rd column (counting the 0th row/column as well)),
	 *  so I'll have to be very careful not to mix up the x and y coordinate in methods which interact with the board.*/
	public Board(){
		
	}
	
	
	/** An additional constructor (will be needed for loading savegames and testing), 
	 * it would also be possible to initialize a fresh chess board and then change it by the setBoard Method */
	public Board(Piece[][] board) {
		super();
		this.board = board;
	}

	
	
	/** Method to show the current state of the board in ASCII (console)
	 *  (mainly for debugging purposes) */
	public void showBoard(){
		
		for(int i = 0; i < 8; i=i+1){
			for(int j=0; j < 8; j=j+1){
				if (this.board[i][j] != null){
					System.out.print(this.board[i][j].getClass().getSimpleName() + " ");
				}else{
					System.out.print("null ");
				}
				if (j == 7){
					System.out.println("");
				};
			}
		}
		
	};
	
	
	/**
	 *  So this method is apparently one of the major issues during any chess game development
	 *  and I was stuck for quite some time figuring out a way to to tell whether the
	 *  current player's King is in check or not.<p>
	 *  First I wanted to integrate the <code>.isKingInCheck</code> method into the <code>.isMoveValid</code>
	 *  methods, which every Piece has (because only a move that leaves your King in an
	 *  "unchecked" situation is a valid move). My idea was to scan the board and use the
	 *  <code>.getValidMoves</code> method (implemented in the abstract Piece class) on every enemy piece
	 *  and then check whether the King is standing on one of those fields or not.<p>
	 *  
	 *  However,
	 *  in the worst case scenario the <code>.isKingInCheck</code> method would have to use <code>getValidMoves</code>
	 *  16 times (for every enemy Piece) and create 16 Arraylists, also comparing them to the
	 *  Kings location.<p>
	 *  
	 *  And even worse,
	 *  since the <code>.getValidMoves</code> method uses the <code>.isMoveValid</code> method (which was meant to contain
	 *  <code>isKingInCheck</code> in the first place) this would have led to an infinite loop<p>
	 *  
	 *  Example: In order to check, whether the white Rook is allowed to move to a certain destination
	 *  		 we have to check if he puts the white King into check. Therefore we create an arraylist
	 *  		 of possible moves for every black Piece. In order to know which of their moves is valid
	 *  		 we have to check whether their moves would put the black King in check.
	 *  		 Again, we would use the isKingInCheck method to see, if the black King would be in check
	 *  		 focusing on all white pieces, creating an infinite amount of arraylists.<p> 
	 *  
	 *  But I think I found a rather acceptable solution:<p>
	 *  
	 *  Instead of implementing isKingInCheck in isMoveValid and/or scanning the whole board for enemy Pieces 
	 *  we simply focus on the Kings location and use all of our isMoveValid methods to see whether there are
	 *  matching enemy Pieces in range<p>
	 *  
	 *  Example: To see if the white King is put in check by a rook we take the Kings current location and
	 *  		 simulate the movement of a Rook standing on the same field as the King (from the Kings viewpoint).
	 *  		 Then we can easily use the getValidMoves method to create an arraylist of all "dangerous" spots.
	 *  		 If there is a black Rook on any of these fields, the white King is in check.<p>
	 *  
	 *  We repeat the procedure for every type of piece in the game. (Optional:To make it more efficient we could first
	 *  check if there are even any Pieces of a certain kind on the board, if there are no black rooks left the white
	 *  King can't be put in check by a rook, so we don't have to bear with an additional arraylist. I don't really
	 *  think it makes that much of a difference though, it's hard for me to decide if the computer is capable of that
	 *  amount of calculation, but if it's able to repaint the OceanLife GUI 24 times in a second it should probably
	 *  be able to do a lot of basic math operations)<p>
	 *  NOTE: For most Pieces their color doesn't affect the movement pattern, for the Pawn it does. I have to watch 
	 *  	  out so the King isn't put into check by an enemy Pawn that is standing behind him. (The King has to 
	 *  	  simulate his own color's Pawn!)<p>
	 *  
	 *  
	 *  Anyway, here comes the weird part about this solution:
	 *  Since we have only one board that knows all the Pieces' locations, we can't simply create a "what if"-scenario 
	 *  without the possible loss of information, unless we clone the board and simulate the move on this "new" board<p>
	 *  
	 *  Example: We want to know if the white Pawn is allowed to capture the black Rook. In order to do that we have
	 *  		 to put the Pawn on the Rook's position and then check if it affects the King. If it does put the white
	 *  		 King in check the move is invalid, the former gamestate has to be restored to resume the game properly.
	 *  		 Unfortunately, this is not possible since we have overwritten the position on the board and no longer
	 *  		 know who's been there before.
	 * 			 NOTE: I just noticed that it would be possible to copy the information on the destination field to
	 * 				   restore the game state afterwards. I'll keep that in mind but it seems kind of unsafe to me.<p>		 
	 * 
	 * I know this problem could probably be solved if every Piece knew its own location, but also letting the Pieces
	 * know their position would be my last resort. I'd like to keep up the separation between the Pieces, which know
	 * their movement patterns and the board, that knows their positions. Redundant positioning information will only
	 * overcomplicate things and will certainly lead to data inconsistency. Also, the {@link ChessGUI} can be based off almost only
	 * the board rather than to cope with every single Piece.<p>
	 * 
	 * Another odd thing is that in the <code>isKingInCheck</code> method I'll have to create "dummy" Pieces to use <code>isMoveValid</code> on
	 * since there is no way to tell if there is a Piece like that on the board and  we need an object to use the 
	 * method on. It would be more convenient to have an <code>isMoveValid</code> method that expects the type of Piece as an 
	 * argument and then tell us if the move is valid for that specific type of Piece, but that would require us to
	 * put all isMoveValid methods into the Board class, which would make it way too big and also break the separation
	 * between Pieces and the Board.<p>
	 * 
	 * Note to myself: Don't ever call this method in <code>.isMoveValid</code> !!! (=> Stackoverflow)
	 *  
	 * @return A boolean that shows if the king is in check or not.
	 * @param player The method needs to know whose players turn it is.
	 */
	
	public boolean isKingInCheck(char player){
		boolean inCheck = false; 
	    int[] kingPos = new int[2]; 
		
	    // Here we scan the board for the King in order to get his position
		for(int i = 0; i < 8; i=i+1){
			for(int j=0; j < 8; j=j+1){
				if (this.board[i][j] != null && this.board[i][j].getClass().getSimpleName().equals("King")  // We check for null first to avoid a nullpointer exception 
					&& this.board[i][j].getColor() == player){
						kingPos[0] = j;	// about 2 hours of debugging and I realize that I mixed up x and y coordinates again...
						kingPos[1] = i;
				};
			}
		}
		
		// Here we create the dummy Pieces that we need to simulate their movement patterns
		King dummyKing = new King(player);
		Rook dummyRook = new Rook(player);
		Bishop dummyBishop = new Bishop(player);
		Knight dummyKnight = new Knight(player);
		Queen dummyQueen = new Queen(player);
		Pawn dummyPawn = new Pawn(player);
		
		//getting all dangerSpots for the King Piece
		ArrayList<int[]> dangerSpots = new ArrayList<int[]>();
		dangerSpots = dummyKing.getValidMoves(this, player, kingPos);
		
		// Here we go through every one of those "dangerSpots" and check if a matching Piece is on any of the fields
		// Again we try to avoid a nullpointer exception in the if statement (maybe it would be a good idea to change
		// the getBoardpos method to return a String "null" in case of an empty field, but this way might become useful later) 
		 for (int i = 0; i < dangerSpots.size(); i++){
			 if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("King")) {
			        inCheck = true;
			        return inCheck;
			    }
		 }
		 
		 
		 // And now for the other Pieces (I don't think "clear" is really necessary here, but I don't trust ArrayLists)
		 // Rook
		 dangerSpots.clear();
		 dangerSpots = dummyRook.getValidMoves(this, player, kingPos);
		 
		 for (int i = 0; i < dangerSpots.size(); i++){
			    if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("Rook")) {
			        inCheck = true;
			        return inCheck;
			    }
		 }
		 
		 //Bishop
		 dangerSpots.clear();
		 dangerSpots = dummyBishop.getValidMoves(this, player, kingPos);
		
		 for (int i = 0; i < dangerSpots.size(); i++)
			    if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("Bishop")) {
			        inCheck = true;
			        return inCheck;
			    }
		 
		 //Knight
		 dangerSpots.clear();
		 dangerSpots = dummyKnight.getValidMoves(this, player, kingPos);
		
		 for (int i = 0; i < dangerSpots.size(); i++)
			    if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("Knight")) {
			        inCheck = true;
			        return inCheck;
			    }
		 
		 //Queen
		 dangerSpots.clear();
		 dangerSpots = dummyQueen.getValidMoves(this, player, kingPos);
		
		 for (int i = 0; i < dangerSpots.size(); i++)
			    if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("Queen")) {
			        inCheck = true;
			        return inCheck;
			    }
		 
		 //Pawn
		 dangerSpots.clear();
		 dangerSpots = dummyPawn.getValidMoves(this, player, kingPos);
		
		 for (int i = 0; i < dangerSpots.size(); i++)
			    if (this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]) != null
			    	&& this.getBoardPos(dangerSpots.get(i)[0],dangerSpots.get(i)[1]).getClass().getSimpleName().equals("Pawn")) {
			        inCheck = true;
			        return inCheck;
			    }
		 
		 
		// If none of the if statements above caused true to be returned, the King is not in check
		return inCheck;
	}
	
	
	
	public Piece[][] getBoard() {
		return board;
	}
	
	/** So I'll have to modify this setter a little due to some reference problems,
	 *  apparently the <code>.clone()</code> method for arrays doesn't copy the <code>Piece[][]</code> array
	 *  for some reason
	 * */
	public void setBoard(Piece[][] newBoard) {
		this.board = newBoard;
	}

	/** Method for setting a specific field rather than updating the whole board */
	public void setBoardPos(int posx, int posy, Piece piece) {
		
		board[posy][posx] = piece;
	
	};
	
	/** Method for getting a single field rather than getting the whole board*/
	public Piece getBoardPos(int posx, int posy){
		
		Piece temp = board[posy][posx];
		
		return temp;
		
	};
	
}