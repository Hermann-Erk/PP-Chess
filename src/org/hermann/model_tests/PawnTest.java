package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Pawn;
import org.junit.Test;

public class PawnTest {

	Board board = new Board();
	
	// Testing player 'w'
	@Test
	public void move16To15StraightWhite() { 	// A white pawn moves forward one field from his starting position
		int[] currentPos = {1,6};
		int[] destination = {1,5};
		
		assertTrue("A white pawn can move forward one field from his starting position",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}

	@Test
	public void move16To14StraightWhite() { 	// A white pawn moves forward two fields from his starting position
		int[] currentPos = {1,6};
		int[] destination = {1,4};
		
		assertTrue("A white pawn can move forward two fields from his starting position",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move15To13StraightWhite() { 	// A white pawn tries to move forward two fields from a random position
		Pawn pawn = new Pawn('w');
		board.setBoardPos(1, 5, pawn);
		
		int[] currentPos = {1,5};
		int[] destination = {1,3};
		
		assertFalse("A white pawn can't move forward two spaces on a random field",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move14To13StraightWhite() { 	// A white pawn moves forward one field from a random position
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(1, 4, pawn);
		
		int[] currentPos = {1,4};
		int[] destination = {1,3};
		
		assertTrue("A white pawn can move forward one field",
					board.getBoardPos(1,4).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move16To05DiagonallyWhite() { 	// A white pawn doesn't move forward diagonally to the left when the field is free
		int[] currentPos = {1,6};
		int[] destination = {0,5};
		
		assertFalse("A white pawn can't move forward diagonally one field from his starting position, if the field is empty",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move16To25DiagonallyWhite() { 	// A white pawn doesn't move forward diagonally to the right when the field is free
		int[] currentPos = {1,6};
		int[] destination = {2,5};
		
		assertFalse("A white pawn can't move forward diagonally one field from his starting position, if the field is empty",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move16To25DiagonallyWhiteOccupied() { 	// A white pawn captures another piece diagonally to the right
		Pawn pawn = new Pawn('b');
		board.setBoardPos(2, 5, pawn);
		
		int[] currentPos = {1,6};
		int[] destination = {2,5};
		
		assertTrue("A white pawn can move forward diagonally one field from his starting position and capture another players piece",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move16To05DiagonallyWhiteOccupied() { 	// A white pawn doesn't move forward diagonally to the left when the field is free
		Pawn pawn = new Pawn('b');
		board.setBoardPos(0, 5, pawn);
		
		int[] currentPos = {1,6};
		int[] destination = {0,5};
		
		assertTrue("A white pawn can move forward diagonally one field from his starting position and capture another players piece",
					board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void WhiteCaptureFriend() { 	// A Pawn shouldn't capture a friendly Piece 
		Pawn pawn = new Pawn('w');
		board.setBoardPos(0, 5, pawn);
		
		int[] currentPos = {1,6};
		int[] destination = {0,5};
		
		assertFalse("A Pawn shouldn't capture a friendly Piece",
					 board.getBoardPos(1,6).isMoveValid(board, 'w', currentPos, destination));
	}
	
	
	// Testing player 'b'
	
	
	@Test
	public void move11To12StraightBlack() { 	// A black pawn moves forward one field from his starting position
		int[] currentPos = {1,1};
		int[] destination = {1,2};
		
		assertTrue("A black pawn can move forward one field from his starting position",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}

	@Test
	public void move11To13StraightBlack() { 	// A black pawn moves forward two fields from his starting position
		int[] currentPos = {1,1};
		int[] destination = {1,3};
		
		assertTrue("A black pawn can move forward two fields from his starting position",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move12To15StraightBlack() { 	// A black pawn tries to move forward two fields from a random position
		
		Pawn pawn = new Pawn('b');
		board.setBoardPos(1, 2, pawn);
		
		int[] currentPos = {1,2};
		int[] destination = {1,5};
		
		assertFalse("A black pawn can't move forward two fields from a random position",
					board.getBoardPos(1,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move23To24StraightBlack() { 	// A black pawn moves forward one field from a random position (if the destination field is empty)
		
		Pawn pawn = new Pawn('b');
		board.setBoardPos(2, 3, pawn);
		
		int[] currentPos = {2,3};
		int[] destination = {2,4};
		
		assertTrue("A black pawn can move forward one field",
					board.getBoardPos(2,3).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move11To02DiagonallyBlack() { 	// A black pawn doesn't move forward diagonally to the left when the field is free
		int[] currentPos = {1,1};
		int[] destination = {0,2};
		
		assertFalse("A black pawn can't move forward diagonally one field from his starting position, if the field is empty",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move11To22DiagonallyBlack() { 	// A black pawn doesn't move forward diagonally to the right when the field is free
		int[] currentPos = {1,1};
		int[] destination = {2,2};
		
		assertFalse("A black pawn can't move forward diagonally one field from his starting position, if the field is empty",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move11To22DiagonallyBlackOccupied() { 	// A black pawn captures another piece diagonally to the right
		Pawn pawn = new Pawn('w');
		board.setBoardPos(2, 2, pawn);
		
		int[] currentPos = {1,1};
		int[] destination = {2,2};
		
		assertTrue("A black pawn can move forward diagonally one field from his starting position and capture another players piece",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move11To02DiagonallyBlackOccupied() { 	// A black pawn captures another piece diagonally to the left
		Pawn pawn = new Pawn('w');
		board.setBoardPos(0, 2, pawn);
		
		int[] currentPos = {1,1};
		int[] destination = {0,2};
		
		assertTrue("A black pawn can move forward diagonally one field from his starting position and capture another players piece",
					board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void BlackCaptureFriend() { 	// A Pawn shouldn't capture a friendly Piece 
		Pawn pawn = new Pawn('b');
		board.setBoardPos(0, 2, pawn);
		
		int[] currentPos = {1,1};
		int[] destination = {0,2};
		
		assertFalse("A Pawn shouldn't capture a friendly Piece",
					 board.getBoardPos(1,1).isMoveValid(board, 'b', currentPos, destination));
	}
	
}
