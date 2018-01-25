package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Rook;
import org.junit.Test;

public class RookTest {

	Board board = new Board();
	
	
	@Test
	public void move7To5UpBlocked() {  // Testing upwards movement when blocked
		
		int[] currentPos = {0,7};
		int[] destination = {0,5};
		
		assertFalse("A rook should not be albe to jump over pieces",
					board.getBoardPos(0,7).isMoveValid(board, 'w', currentPos, destination));
	}
	
	
	@Test
	public void move7To5UpFree() { 	 // Testing upwards movement
		
		board.setBoardPos(0, 6, null); 	// Kill the pawn at [0,6] to clear the way 
		
		int[] currentPos = {0,7};
		int[] destination = {0,5};
		
		assertTrue("A rook should be able to move forward 2 spaces",
					board.getBoardPos(0,7).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move0To6RightFree() { 	// Testing movement to the right
		Rook rook = new Rook('w');
		
		board.setBoardPos(0, 3, rook);
		
		int[] currentPos = {0,3};
		int[] destination = {6,3};
		
		assertTrue("A rook should be able to move to the right",
					board.getBoardPos(0,3).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move0To6RightOccupied() { 	// Testing movement to the right when the destination is occupied by an enemy Piece
		Rook rook = new Rook('w');
		
		board.setBoardPos(0, 3, rook);
		
		int[] currentPos = {0,3};
		int[] destination = {6,3};
		
		assertTrue("A rook should be able to move to the right and capture another players piece",
					board.getBoardPos(0,3).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move7To0LeftFree() { // Testing movement to the left 	
		Rook rook = new Rook('w');
		
		board.setBoardPos(7, 4, rook);
		
		int[] currentPos = {7,4};
		int[] destination = {0,4};

		assertTrue("A rook should be able to move to te left",
					board.getBoardPos(7,4).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move7To0LeftOccupied() { // Testing movement to the left when destination is blocked by an enemy	
		Rook rook = new Rook('w');
		Pawn pawn = new Pawn('b'); 
		
		board.setBoardPos(0, 4, pawn);
		board.setBoardPos(7, 4, rook);
		
		int[] currentPos = {7,4};
		int[] destination = {0,4};

		assertTrue("A rook should be able to move to the left and capture another Piece",
					board.getBoardPos(7,4).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void captureFriend() { // Testing if it's possible to capture own pieces
		Rook rook = new Rook('w');
		Pawn pawn = new Pawn('w'); 
		
		board.setBoardPos(0, 4, pawn);
		board.setBoardPos(7, 4, rook);
		
		int[] currentPos = {7,4};
		int[] destination = {0,4};

		assertFalse("A rook shouldn't be able to capture an ally piece",
					board.getBoardPos(7,4).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void stayOnPosition(){	// Testing if staying at the current position is a valid move
		
		int[] currentPos = {0,7};
		int[] destination = {0,7};
	
		assertFalse("Staying is not a valid move",
					board.getBoardPos(0, 7).isMoveValid(board,'w',currentPos, destination));
	
	}
	
}
