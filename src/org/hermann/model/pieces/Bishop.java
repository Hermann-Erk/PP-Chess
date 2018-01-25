package org.hermann.model.pieces;

import org.hermann.model.board.Board;

@SuppressWarnings("serial")
public class Bishop extends Piece {

	public Bishop(char color){
		super(color);
	};

	
	/** The bishops movement is a little tricky, I think the easiest solution would be to take the number of fields 
	* the Piece moves into a direction (in this case the x-component) and compare it to the movement into the y direction
	* they have to match in order to make a valid move.
	* 
	* Afterwards we have to check if the destination field is empty or occupied by an enemy Piece.
	* 
	* Then we have to make sure that the path from the bishops current location to his destination is empty. */
	
	
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
				
				if(fieldDistanceX == fieldDistanceY && fieldDistanceX != 0){		// Now we know, that the movement is carried out in a diagonal direction (and the distance is not zero)  
					
					if (board.getBoardPos(desX,desY) == null || board.getBoardPos(desX, desY).getColor() != player){ 	// In any case the field of destination has to be either empty or occupied by another players piece
						
						boolean isPathEmpty = true;		// We'll need this soon to check if the path towards the destination is free
						
						// First we take care of both upper directions
						if (desY>curY){ 		// The destinations y-component is greater than the current y-component	 
							if(desX>curX){ 		// The destinations x-component is greater than the current x-component => movement to the right
								
								for(int i = 1; i< fieldDistanceX; i++){		// Now we check the fields from the current location+1 (the field, that the bishop is standing on isn't empty of course) to the destination-1 (we already checked the destinations itself)
									if(board.getBoardPos(curX+i,curY+i) != null){
										isPathEmpty = false;
									}
								};
							} else { 	// Movement to the left
								
								for(int i = 1; i< fieldDistanceX; i++){		// The same thing for movement to the upper left, only the fields we observe are mirrored
									if(board.getBoardPos(curX-i,curY+i) != null){
										isPathEmpty = false;
									};
								};
							};
						};
						
						// Now we look at the movement downwards (could also be done with "else", but I guess it's easier to read this way)
						if(desY<curY){
							if(desX>curX){ 		// The destinations x-component is greater than the current x-component => movement to the right
								
								for(int i = 1; i< fieldDistanceX; i++){		// The same thing for movement to the down right side, only the fields we observe are mirrored
									if(board.getBoardPos(curX+i,curY-i) != null){
										isPathEmpty = false;
									};
								};
							} else { 	// Movement to the left
								
								for(int i = 1; i< fieldDistanceX; i++){		// The same thing for movement to the down left side, only the fields we observe are mirrored
									if(board.getBoardPos(curX-i,curY-i) != null){
										isPathEmpty = false;
									};
								};
							};
							
						};
						
						if(isPathEmpty){		// If the path is empty, the move is valid
							isValid = true;
						};
						
					};
				};				
				
			};
		};
			
					
		return isValid; // The boolean value will eventually be returned
		
		};
				
		
				
};
	
	
	
	
	
	
	
	
	
