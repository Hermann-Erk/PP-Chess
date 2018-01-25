package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.King;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.junit.Test;


public class KingTest {

	private Piece[][] testboard = {{null,null,		   null,		 null,		     null,		   null,		 null,null},
								   {null,null,		   null,	     new Pawn('w'),	 null,		   new Pawn('w'),null,null},
								   {null,null,		   null,		 new Pawn('b'),  new King('b'),new Pawn('w'),null,null},
								   {null,null,		   null,		 new Pawn('w'),  null,		   new Pawn('b'),null,null},
								   {null,null,		   null,		 null,		     null,		   null,		 null,null},
								   {null,null,		   null,		 null,		     null,		   null,		 null,null},
								   {null,null,		   null,		 null,		     null,		   null,		 null,null},
								   {null,null,		   null,		 null,		     null,		   null,		 null,null},};

	Board board = new Board(testboard);
	
	
	@Test
	public void move42to41Free() {  // Testing kings movement
		
		int[] currentPos = {4,2};
		int[] destination = {4,1};
		
		assertTrue("The king should be able to move up one field",
		board.getBoardPos(4,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move42to43Free() {  // Testing kings movement
	
		int[] currentPos = {4,2};
		int[] destination = {4,3};
		
		assertTrue("The king should be able to move down one field",
		board.getBoardPos(4,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move42to31Occupied() {  // Testing kings movement
	
		int[] currentPos = {4,2};
		int[] destination = {3,1};
		
		assertTrue("The king should be able capture an enemy piece",
		board.getBoardPos(4,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move42to32Blocked() {  // Testing kings movement
	
		int[] currentPos = {4,2};
		int[] destination = {3,2};
		
		assertFalse("The king shouldn't be able to capture a friendly piece",
		board.getBoardPos(4,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	@Test
	public void move42to33Occupied() {  // Testing kings movement
	
		int[] currentPos = {4,2};
		int[] destination = {3,3};
		
		assertTrue("The king should be able capture an enemy piece",
		board.getBoardPos(4,2).isMoveValid(board, 'b', currentPos, destination));
	}
	
	
}
