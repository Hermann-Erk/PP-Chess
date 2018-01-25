package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Bishop;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.junit.Test;


/**Testing the Bishops movement
 * */
public class BishopTest {

	
	private Piece[][] testboard = {{null,null,null,null,null,null,null,null},
								   {null,null,null,null,null,null,null,null},
								   {null,null,null,new Bishop('w'),null,null,null,null},
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
	
}

	

