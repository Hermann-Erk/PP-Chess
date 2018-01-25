package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.hermann.model.pieces.Queen;
import org.hermann.model.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

/** We can copy most of the rooks and bishops tests 
 * */

public class QueenTest {

	
	private Piece[][] testboard = {{null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,new Queen('w'),null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},};
			 					
	Board board = new Board(testboard);
	
	
	@Test
	public void move32To10Blocked() {  // Testing upwards left movement when blocked
		
		Pawn pawn = new Pawn('b');
		
		board.setBoardPos(2,1, pawn);
		
		int[] currentPos = {3,2};
		int[] destination = {1,0};
		
		assertFalse("A bishop should not be able to jump over pieces",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To10Free() {  // Testing upwards left movement
		
		int[] currentPos = {3,2};
		int[] destination = {1,0};
		
		assertTrue("A bishop should be able to move diagonally to the upper left",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	
	@Test
	public void move32To50Blocked() {  // Testing upwards right movement when blocked
		
		Pawn pawn = new Pawn('b');
		
		board.setBoardPos(4,1, pawn);
		
		int[] currentPos = {3,2};
		int[] destination = {5,0};
		
		assertFalse("A bishop should not be able to jump over pieces",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To50Free() {  // Testing upwards right movement
		
		int[] currentPos = {3,2};
		int[] destination = {1,0};
		
		assertTrue("A bishop should be able to move diagonally to the upper right",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	
	
	
	@Test
	public void move32To05Blocked() {  // Testing downwards left movement when blocked
		
		Pawn pawn = new Pawn('b');
		
		board.setBoardPos(1,4, pawn);
		
		int[] currentPos = {3,2};
		int[] destination = {0,5};
		
		assertFalse("A bishop should not be able to jump over pieces",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To05Free() {  // Testing downwards left movement
		
		int[] currentPos = {3,2};
		int[] destination = {0,5};
		
		assertTrue("A bishop should be able to move diagonally to the lower left",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	
	@Test
	public void move32To76Blocked() {  // Testing downwards right movement when blocked
		
		Pawn pawn = new Pawn('b');
		
		board.setBoardPos(6,5, pawn);
		
		int[] currentPos = {3,2};
		int[] destination = {7,6};
		
		assertFalse("A bishop should not be able to jump over pieces",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To76Free() {  // Testing downwards right movement
		
		int[] currentPos = {3,2};
		int[] destination = {7,6};
		
		assertTrue("A bishop should be able to move diagonally to the lower right",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}

	@Test
	public void captureEnemy() {
		
		Pawn pawn = new Pawn('b');
		
		board.setBoardPos(7,6, pawn);
		
		
		int[] currentPos = {3,2};
		int[] destination = {7,6};
		
		assertTrue("A bishop should be able to capture an enemy piece",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void captureFriend() {
		
		Pawn pawn = new Pawn('w');
		
		board.setBoardPos(7,6, pawn);
		
		
		int[] currentPos = {3,2};
		int[] destination = {7,6};
		
		assertFalse("A bishop should not be able to capture a friendly piece",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void stayOnPosition(){
	
		int[] currentPos = {3,2};
		int[] destination = {3,2};
		
		assertFalse("Staying on the same field isn't a valid move",
					board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
		
	}
	
	//===================================
	
	Board freshboard = new Board();
	
	Queen queen = new Queen('w');
	
	@Before
	public void setup(){
		freshboard.setBoardPos(0,7,queen);
	}
	
	
	@Test
	public void move7To5UpBlocked() {  // Testing upwards movement when blocked
		
		int[] currentPos = {0,7};
		int[] destination = {0,5};
		
		assertFalse("A rook should not be albe to jump over pieces",
					freshboard.getBoardPos(0,7).isMoveValid(freshboard, 'w', currentPos, destination));
	}
	
	
	@Test
	public void move7To5UpFree() { 	 // Testing upwards movement
		
		freshboard.setBoardPos(0, 6, null); 	// Kill the pawn at [0,6] to clear the way 
		
		int[] currentPos = {0,7};
		int[] destination = {0,5};
		
		
		assertTrue("A rook should be able to move forward 2 spaces",
					freshboard.getBoardPos(0,7).isMoveValid(freshboard, 'w', currentPos, destination));
	}
	
	@Test
	public void move0To6RightFree() { 	// Testing movement to the right
		
		freshboard.setBoardPos(0, 3, queen);
		
		int[] currentPos = {0,3};
		int[] destination = {6,3};
		
		assertTrue("A rook should be able to move to the right",
					freshboard.getBoardPos(0,3).isMoveValid(freshboard, 'w', currentPos, destination));
	}
	
	@Test
	public void move0To6RightOccupied() { 	// Testing movement to the right when the destination is occupied by an enemy Piece
		Rook rook = new Rook('w');
		
		freshboard.setBoardPos(0, 3, rook);
		
		int[] currentPos = {0,3};
		int[] destination = {6,3};
		
		assertTrue("A rook should be able to move to the right and capture another players piece",
					freshboard.getBoardPos(0,3).isMoveValid(freshboard, 'w', currentPos, destination));
	}
	
	@Test
	public void move7To0LeftFree() { // Testing movement to the left 	
		Rook rook = new Rook('w');
		
		freshboard.setBoardPos(7, 4, rook);
		
		int[] currentPos = {7,4};
		int[] destination = {0,4};

		assertTrue("A rook should be able to move to te left",
					freshboard.getBoardPos(7,4).isMoveValid(freshboard, 'w', currentPos, destination));
	}
	
	@Test
	public void move7To0LeftOccupied() { // Testing movement to the left when destination is blocked by an enemy	
		Rook rook = new Rook('w');
		Pawn pawn = new Pawn('b'); 
		
		freshboard.setBoardPos(0, 4, pawn);
		freshboard.setBoardPos(7, 4, rook);
		
		int[] currentPos = {7,4};
		int[] destination = {0,4};

		assertTrue("A rook should be able to move to the left and capture another Piece",
					freshboard.getBoardPos(7,4).isMoveValid(freshboard, 'w', currentPos, destination));
	}
}
