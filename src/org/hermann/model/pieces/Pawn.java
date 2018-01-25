package org.hermann.model.pieces;

import org.hermann.model.board.Board;

@SuppressWarnings("serial")
public class Pawn extends Piece {
	
	public Pawn(char color){
		super(color);
	};
	
	
	/** We need a function to tell whether a move is valid or not, a boolean value will be returned.
	 * The method receives the board,the current player, the current position on the board and the chosen destination. 
	 * Just realised: depending on which players turn it is we have to check the other directions. 
	 * The pawn starting in the upper half of the board is only allowed to be heading downwards while the other players
	 * pawn has to move in the opposite direction, this could become very annoying as the pawn has a very special movement pattern.
	 * Also, we have to make sure, that the player doesn't try to make the pieces run off the board.
	 * @param*/
	@Override
	public boolean isMoveValid(Board board,char player, int[] currentpos, int[] destination){
		boolean isValid = false;
		
		int curX = currentpos[0];
		int curY = currentpos[1];
		int desX = destination[0];
		int desY = destination[1];
		
		if (this.isPlayerProperty(player)){		 // Testing, whether the piece is player property or not
			
			if (desY < 8 && desY >= 0 && desX < 8 && desX >= 0){ 	// The destination mustn't exceed the boards borders  
				
				if (player=='w'){	// White is the bottom player, therefore the pawns movement here differs from the upper pawn.
				
					if (curY-1 == desY && curX == desX){ 	// If the pawn only wants to move forward one space...
						if (board.getBoardPos(desX,desY) == null){ 		// ...the next space has to be empty.
							isValid = true;
						};
					};
				
					if(curY-2 == desY && curX == desX && desY == 4){ 		// If the pawn wants to move forward two spaces...
						if(board.getBoardPos(curX,curY-1) == null && board.getBoardPos(curX,desY) == null ){ 	// ...both spaces have to be empty.
							isValid = true;
						}
					};
			
					if(curY-1 == desY && curX-1 == desX){		 //If the pawn wants to move diagonally to the left...
						if(board.getBoardPos(desX,desY) != null){		// ...the space has to be occupied...
							if(board.getBoardPos(desX,desY).getColor() != player){ // ... by an enemy piece.
								isValid=true;
							};
						};
					};
					
					if(curY-1 == desY && curX+1 == desX){		 //If the pawn wants to move diagonally to the right...
						if(board.getBoardPos(desX,desY) != null){		// ...the space has to be occupied...
							if(board.getBoardPos(desX,desY).getColor() != player){ // ... by an enemy piece.	
							isValid = true;
							};
						};
					};
					
				} else { 	// and if it's the opponents turn
					
					if (curY+1 == desY && curX == desX){ 	// If the pawn only wants to move forward one space...
						if (board.getBoardPos(desX,desY) == null){ 		// ...the next space has to be empty.
							isValid = true;
						};
					};
				
					if(curY+2 == desY && curX == desX && desY == 3){ 		// If the pawn wants to move forward two spaces...
						if(board.getBoardPos(curX,curY+1) == null && board.getBoardPos(desX,desY) == null ){ 	// ...both spaces have to be empty.
							isValid = true;
						}
					};
			
					if(curY+1 == desY && curX-1 == desX){		 //If the pawn wants to move diagonally to the left...
						if(board.getBoardPos(desX,desY) != null){		// ...the space has to be occupied...
							if(board.getBoardPos(desX,desY).getColor() != player){ // ... by an enemy piece.
								isValid=true;
							};
						};
					};
					
					if(curY+1 == desY && curX+1 == desX){		 //If the pawn wants to move diagonally to the right...
						if(board.getBoardPos(desX,desY) != null){		// ...the space has to be occupied...
							if(board.getBoardPos(desX,desY).getColor() != player){ // ... by an enemy piece.	
								isValid=true;
							};
						};
					};
					
					
					
				};
			};
		};
		
		return isValid;
	};
	
	
};
