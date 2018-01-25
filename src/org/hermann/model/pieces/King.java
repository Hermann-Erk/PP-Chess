package org.hermann.model.pieces;

import org.hermann.model.board.Board;

@SuppressWarnings("serial")
public class King extends Piece{
	
	public King(char color){
		super(color);
	};

	/** The validity of the kings movement in general should be a little easier to check than with the other Pieces, we 
	 * only have to make sure that he moves exactly one field into any direction (provided that the field is either empty or occupied by another players piece) 
	 * 
	 *  I'm not sure if I'll be able to implement the castling move, which would make everything way more complicated.
	 *  => I did, and it was annoying and complicated, because there are just so many rules to it and there's no
	 *     easy way to generalize the movement pattern while castling */
	
	@Override
	public boolean isMoveValid(Board board,char player, int[] currentpos, int[] destination){
		boolean isValid = false;
		
		int curX = currentpos[0];
		int curY = currentpos[1];
		int desX = destination[0];
		int desY = destination[1];
		
		if (this.isPlayerProperty(player)){		 // Testing, whether the piece is player property or not
			
			if (desY < 8 && desY >= 0 && desX < 8 && desX >= 0){ 	// The destination mustn't exceed the boards borders  
				
				int fieldDistanceX = Math.abs(desX - curX);		// Here we get the absolute value of the distance in x-direction
				int fieldDistanceY = Math.abs(curY - desY);		// Here we get the absolute value of the distance in y-direction
				
				// Normal movement
				if(fieldDistanceX ==  1 && fieldDistanceY == 1 || fieldDistanceX == 1 && fieldDistanceY == 0 || fieldDistanceX == 0 && fieldDistanceY == 1){ 	// As long as the kings destination is a field next to his current position, the move should be valid 
					
					if(board.getBoardPos(desX,desY) == null ||board.getBoardPos(desX,desY).getColor() != player){ 	// the destination field must be either empty or occupied by an enemy piece  
						
						isValid = true;
						
					};
				};
				
				
				//BIG PROBLEM: We can't use .isKingInCheck in here without causing a stackoverflow-error, since
				// .isKingInCheck calls .isMoveValid, they just call each other over and over again...
				// I guess I'll have to split up this part and test in our BoardSquaresMouseListener, if the
				// king runs through an attacked field the BoardSquaresListener checks for invalidity due to pieces
				// attacking the king (we already do that for "normal" movement patterns anyway, so I don't don't think
				// it's too unfitting)
				
				//Castling
				//Castling kingside (white)
				if(player == 'w'){
					if(curX == 4 && curY == 7 && desX == 6 && desY == 7){
						//Castling requires that rook and king haven't been moved yet...
						if(!this.hasMoved() && board.getBoardPos(7, 7) != null && board.getBoardPos(7, 7).getColor() == player && !board.getBoardPos(7,7).hasMoved()){
							//..and that the space between rook and king is empty
							if(board.getBoardPos(5, 7) == null && board.getBoardPos(6, 7) == null){
								isValid = true;
							}
						}
					}
				}
				
				//Castling kingside (black) 
				if(player == 'b'){
					if(curX == 4 && curY == 0 && desX == 6 && desY == 0){
						//Castling requires that rook and king haven't been moved yet...
						if(!this.hasMoved() && board.getBoardPos(7, 0) != null && board.getBoardPos(7, 0).getColor() == player && !board.getBoardPos(7,0).hasMoved()){
							//..and that the space between rook and king is empty
							if(board.getBoardPos(5, 0) == null && board.getBoardPos(6, 0) == null){
								isValid = true;
							}
						}
					}
				}
				
				//Castling queenside (white)
				if(player == 'w'){
					if(curX == 4 && curY == 7 && desX == 2 && desY == 7){
						//Castling requires that rook and king haven't been moved yet...
						if(!this.hasMoved() && board.getBoardPos(0, 7) != null && board.getBoardPos(0, 7).getColor() == player && !board.getBoardPos(0,7).hasMoved()){
							//..and that the space between rook and king is empty
							if(board.getBoardPos(2, 7) == null && board.getBoardPos(3, 7) == null){
								isValid = true;
							}
						}
					}
				}
				
				//Castling queenside (black)
				if(player == 'b'){
					if(curX == 4 && curY == 0 && desX == 2 && desY == 0){
						//Castling requires that rook and king haven't been moved yet...
						if(!this.hasMoved() && board.getBoardPos(0, 0) != null && board.getBoardPos(0, 0).getColor() == player && !board.getBoardPos(0,0).hasMoved()){
							//..and that the space between rook and king is empty
							if(board.getBoardPos(2, 0) == null && board.getBoardPos(3, 0) == null){
								isValid = true;
							}
						}
					}
				}
				
				
				
			};
		};
		
		
		return isValid; // The boolean value will eventually be returned
		
	};	
	
};
