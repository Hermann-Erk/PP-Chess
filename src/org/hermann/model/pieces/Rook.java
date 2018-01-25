package org.hermann.model.pieces;

import org.hermann.model.board.Board;

@SuppressWarnings("serial")
public class Rook extends Piece{
	
	public Rook(char color){
		super(color);
	};
	
	
	/** The rooks movement is pretty straight forward (no pun intended), although it will be annoying to
	 *  test, if the fields he crosses towards his destination are empty
	 * */
	@Override
	public boolean isMoveValid(Board board,char player, int[] currentpos, int[] destination){
		boolean isValid = false;
		
		// makes the code more readable
		int curX = currentpos[0];
		int curY = currentpos[1];
		int desX = destination[0];
		int desY = destination[1];
		
		if (this.isPlayerProperty(player)){		 // Testing, whether the piece is player property or not
			
			if (desY < 8 && desY >= 0 && desX < 8 && desX >= 0){ 	// The destination mustn't exceed the boards borders  
			
				// Block for vertical movement
				if (desX == curX && desY != curY){ // If the rook wants to move vertically (only the positions y-component changes)...
					if (board.getBoardPos(desX,desY) == null || board.getBoardPos(desX, desY).getColor() != player){ 	// ... and the destination field is empty or occupied by another player...
						
						// NullPointerException when using .getColor() on null reference!!!
						// If the "piece" at the given position is an actual piece we want to use the .getColor() method, otherwise we don't!
						
						if(desY < curY){ // ... and if he also moves upwards...
							boolean pathEmpty = true;
							for(int i = 1;curY-i > desY;i++){	// ... we have to check if every field in the rooks way is empty. 
								if(board.getBoardPos(curX,curY-i) != null){
									pathEmpty = false;
								}
							}
							isValid = pathEmpty; // If the path is empty, the move is valid.
						} else {	// ... and if he moves downwards...
							boolean pathEmpty = true;
							for(int i = 1;curY+i < desY;i=i+1){	// ... we have to check if every field in the rooks way is empty. 
								if(board.getBoardPos(curX,curY+i) != null){
									pathEmpty = false;
								}
							}
							isValid = pathEmpty; // If the path is empty, the move is valid.
							
						};
				
					};
				}; 
				
				// Block for horizontal movement
				if (desX != curX && desY == curY){ // If the rook wants to move horizontally (only the positions x-component changes)...
					if (board.getBoardPos(desX,desY) == null || board.getBoardPos(desX, desY).getColor() != player){ 	// ... and the destination field is empty or occupied by another player...
						if(desX > curX){ // ... and if he also moves to the right...
							boolean pathEmpty = true;
							for(int i = 1;i+curX < desX;i++){	// ... we have to check if every field in the rooks way is empty. 
								if(board.getBoardPos(curX+i,curY) != null){
									pathEmpty = false;
								}
							}
							isValid = pathEmpty; // If the path is empty, the move is valid.
						} else {	// ... and if he moves to the left...
							boolean pathEmpty = true;
							for(int i = 1;curX-i > desX;i=i+1){	// ... we have to check if every field in the rooks way is empty. 
								if(board.getBoardPos(curX-i,curY) != null){
									pathEmpty = false;
								}
							}
							isValid = pathEmpty; // If the path is empty, the move is valid.
							
						};
				
					};
				}; 
				
				
			};
		};
		
		return isValid; // The boolean value will eventually be returned
		
	};	
	
};
