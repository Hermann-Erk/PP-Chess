package org.hermann.model.pieces;

import org.hermann.model.board.Board;


public class Knight extends Piece{
	private static final long serialVersionUID = 1L;

	public Knight(char color) {
		super(color);
	}

	/** The Knights movement pattern in general is a little weird, but it should actually be easier to implement since he can just jump over other pieces and
	 *  we don't have to check the fields in his way. */
	
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
				
				if (fieldDistanceX == 1 && fieldDistanceY == 2 || fieldDistanceX == 2 && fieldDistanceY == 1){
					
					if(board.getBoardPos(desX,desY) == null || board.getBoardPos(desX,desY).getColor() != player){
						
						isValid = true;
						
					};
					
				};				
				
			};
		};
			
					
		return isValid; // The boolean value will eventually be returned
		
		}; 
	
	
	
	
};
