package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Knight;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.junit.Test;


public class KnightTest {

		private Piece[][] testboard = {{null,null,		   new Pawn('b'),null,		     new Pawn('b'),null,		   null,null},
				   					   {null,new Pawn('b'),null,	     null,		     null,		 new Pawn('b'),null,null},
				   					   {null,null,		   new Pawn('w'),new Knight('w'),null,		 null,		   null,null},
				   					   {null,new Pawn('b'),null,		 new Pawn('w'),  null,		 new Pawn('b'),null,null},
				   					   {null,null,		   new Pawn('b'),null,		     new Pawn('b'),null,		   null,null},
				   					   {null,null,		   null,		 null,		     null,		 null,		   null,null},
				   					   {null,null,		   null,		 null,		     null,		 null,		   null,null},
				   					   {null,null,		   null,		 null,		     null,		 null,		   null,null},};
				
	Board board = new Board(testboard);
	
	
	@Test
	public void move32To20Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {2,0};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To40Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {4,0};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To11Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {1,1};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To13Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {1,3};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To24Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {2,4};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To44Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {4,4};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To51Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {5,1};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To53Occupied() {  // Testing knights capture movement
	
		int[] currentPos = {3,2};
		int[] destination = {5,3};
		
		assertTrue("A knight can jump over other pieces and capture enemies",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To53Free() {  // Testing knights movement without capturing
		
		board.setBoardPos(5,3, null);
		
		int[] currentPos = {3,2};
		int[] destination = {5,3};
		
		assertTrue("A knight can jump over other pieces and occupy an empty field",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
	@Test
	public void move32To53Blocked() {  // Testing knights friend capturing ability
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(5,3, pawn);
		
		int[] currentPos = {3,2};
		int[] destination = {5,3};
		
		assertFalse("A knight can't capture friendly pieces",
		board.getBoardPos(3,2).isMoveValid(board, 'w', currentPos, destination));
	}
	
}
