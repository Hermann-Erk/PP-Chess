package org.hermann.model.pieces;

import org.hermann.model.board.Board;

@SuppressWarnings("serial")
public class Queen extends Piece {

	public Queen(char color) {
		super(color);
	}
	
	/** Since the Queen is allowed to do both rook and bishop movement it would be the easiest to simply copy their codes,
	 *  it doesn't look too nice, but it works.
	 *  UPDATE: I just simulate a rook and a bishop standing on the queens field, it works just as well and look way more
	 *  		readable.
	 * */
	@Override
	public boolean isMoveValid(Board board,char player, int[] currentpos, int[] destination){
		boolean isValid = false;
				
				// We simulate a rook and a bishop on the queens field and test, whether one
				// of the movement patterns fulfills the movement requirements
				Rook dummyRook = new Rook(this.getColor());
				Bishop dummyBishop = new Bishop(this.getColor());
				
				if (dummyRook.isMoveValid(board, player, currentpos, destination) || 
						dummyBishop.isMoveValid(board, player, currentpos, destination)){
							isValid = true;
				}
						
				return isValid;	
		
		
	};
}
