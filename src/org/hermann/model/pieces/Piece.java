package org.hermann.model.pieces;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import org.hermann.model.board.Board;

/**
 * The Piece class doesn't any longer have an attribute "position", as the
 * {@link Board} itself already includes this information. The pieces' methods will
 * now receive their position by the board, and then, if needed, ask the
 * board for the positions of other pieces.
 * 
 * @param color Color of the chess piece (w for white, b for black); final, since there's
 *              no way for a piece to change its color)
 * @param hasMoved A boolean value that indicates whether a piece has already moved at least once
 * 	               during the current game session, important for {@link King} and {@link Rook} due
 * 				   to castling.
 */
public abstract class Piece implements Serializable {
	private static final long serialVersionUID = 1L;
	private final char color;
	private boolean hasMoved = false; 

	public Piece(char color) {
		this.color = color;
	};

	/** Getter and Setter */

	/**
	 * We won't need a setter as the color is declared final and we don't want
	 * to change the color
	 */

	public java.lang.Character getColor() {
			return color;
	};

	// Methods
	/**
	 * Important for every piece, players should only be able to move their own
	 * pieces, the method is to be used in/before the isValidMove-methods...
	 */
	public boolean isPlayerProperty(char player) {
		return this.color == player;
	};

	/** Every piece needs a method to verify a move */
	public abstract boolean isMoveValid(Board board, char player,
			int[] currentpos, int[] destination);

	/**
	 * Takes the pieces position and checks possible moves on the board, returns
	 * an array of all possible fields to move to (worst case: no moves are
	 * possible, returns an empty array) Check the whole board? It's inefficient,
	 * but there are only 8*8 fields to check anyway, so it probably won't be an
	 * issue...
	 */
	public ArrayList<int[]> getValidMoves(Board board, char player,
			int[] currentpos) {
		ArrayList<int[]> possiblePositions = new ArrayList<int[]>();

		for (int y = 0; y < 8; y = y + 1) { // The whole board is checked for
										// possible destinations
			for (int x = 0; x < 8; x = x + 1) {
				int[] destination = new int[2]; // There needs to be a freshly
												// initialised array every time,
												// otherwise the same reference
												// to the array on the hard
												// drive would be used over and
												// over again and the arraylist
												// would eventually only contain
												// the last possible destination
												// n times, I think this
												// solution should work, even if
												// it doesn't look nice...
				destination[0] = x;
				destination[1] = y;
				
				// The isMoveValid method is used on every field on the board,
				// there might be ways to improve the efficiency (at least for some Pieces (e.g. King)),
				// but I think the saving isn't worth the effort and loss of readability, since there are
				// only 64 fields to check anyway
				if (this.isMoveValid(board, player, currentpos, destination)) { 
					possiblePositions.add(destination);

				}
				;
			}
			;
		}
		;

		return possiblePositions; // Returns the arraylist containing all
									// possible destinations.
	}

	/** Only the king has to know if a piece has moved to validate castling 
	 * */
	protected boolean hasMoved() {
		return hasMoved;
	}

	public void setMoved() {
		this.hasMoved = true;
	};

};
