package org.hermann.model_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Bishop;
import org.hermann.model.pieces.King;
import org.hermann.model.pieces.Knight;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.hermann.model.pieces.Queen;
import org.hermann.model.pieces.Rook;
import org.junit.Test;

public class KingCheckTest {

	
	private Piece[][] testboard = {{null,null,		   null,		 null,		     null,		   null,		 null,null},
			   					   {null,null,		   null,	     null,	 		 null,		   null,		 null,null},
			   					   {null,null,		   null,		 null,		     new King('b'),null,		 null,null},
			   					   {null,null,		   null,		 null,		     null,		   null,		 null,null},
			   					   {null,null,		   null,		 null,		     null,		   null,		 null,null},
			   					   {null,null,		   null,		 null,		     null,		   null,		 null,null},
			   					   {null,null,		   null,		 null,		     null,		   null,		 null,null},
			   					   {null,null,		   null,		 null,		     null,		   null,		 null,null},};

	Board board = new Board(testboard);
	
	
	// Rook tests
	@Test
	public void rookTest1() {
		
		Rook rook = new Rook('w');
		board.setBoardPos(3,2,rook);
	
		assertTrue("The king is put in check by the rook standing right next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void rookTest2() {
		
		Rook rook = new Rook('w');
		board.setBoardPos(4,6,rook);
	
		assertTrue("The king is put in check by the rook standing in a straigh line to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void rookTest3() {
		
		Rook rook = new Rook('w');
		Rook rook2 = new Rook('w');
		board.setBoardPos(3,2,rook);
		board.setBoardPos(5,2,rook2);
		
		assertTrue("The king is put in check by the two rooks standing next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void rookTest4() {
		
		Rook rook = new Rook('w');
		board.setBoardPos(3,1,rook);
	
		assertFalse("The king isn't put in check by the rook standing diagonally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void rookTest5() {
		
		Rook rook = new Rook('b');
		board.setBoardPos(4,1,rook);
	
		assertFalse("The king isn't put in check by the rook of his own color",
					board.isKingInCheck('b'));
	}
	
	// King tests
	@Test
	public void kingTest1() {
		
		King king = new King('w');
		board.setBoardPos(3,2,king);
	
		assertTrue("The king is put in check by the king standing right next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void kingTest2() {
		
		King king = new King('w');
		board.setBoardPos(3,2,king);
	
		assertTrue("The king is put in check by the king standing right next to him",
					board.isKingInCheck('w'));
	}
	
	@Test
	public void kingTest3() {
		
		King king = new King('w');
		board.setBoardPos(1,2,king);
	
		assertFalse("The king isn't put in check by the king standing far away from him",
					board.isKingInCheck('b'));
	}
	
	// Bishop test
	@Test
	public void bishopTest1() {
		
		Bishop bishop = new Bishop('w');
		board.setBoardPos(3,1,bishop);
	
		assertTrue("The king is put in check by the bishop standing diagonally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void bishopTest2() {
		
		Bishop bishop = new Bishop('w');
		board.setBoardPos(6,4,bishop);
	
		assertTrue("The king is put in check by the bishop standing diagonally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void bishopTest3() {
		
		Bishop bishop = new Bishop('w');
		board.setBoardPos(4,1,bishop);
	
		assertFalse("The king isn't put in check by the bishop standing horizontally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void bishopTest4() {
		
		Bishop bishop = new Bishop('b');
		board.setBoardPos(3,1,bishop);
	
		assertFalse("The king isn't put in check by a bishop of his own color",
					board.isKingInCheck('b'));
	}
	
	// Queen tests
	@Test
	public void queenTest1() {
		
		Queen queen = new Queen('w');
		board.setBoardPos(3,1,queen);
	
		assertTrue("The king is put in check by the queen standing diagonally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void queenTest2() {
		
		Queen queen = new Queen('w');
		board.setBoardPos(6,2,queen);
	
		assertTrue("The king is put in check by the queen standing horizontally next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void queenTest3() {
		
		Queen queen = new Queen('w');
		board.setBoardPos(4,6,queen);
	
		assertTrue("The king is put in check by the queen standing vertically far away from him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void queenTest4() {
		
		Queen queen = new Queen('w');
		board.setBoardPos(3,4,queen);
	
		assertFalse("The king isn't put in check by the queen standing in a knight jumping position",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void queenTest5() {
		
		Queen queen = new Queen('b');
		board.setBoardPos(4,1,queen);
	
		assertFalse("The king isn't put in check by a queen of the same color",
					board.isKingInCheck('b'));
	}
	
	//Pawn tests
	@Test
	public void pawnTest1() {
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(3,3,pawn);
	
		assertTrue("The king is put in check by the pawn standing diagonally under him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void pawnTest2() {
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(5,3,pawn);
	
		assertTrue("The king is put in check by the pawn standing diagonally under him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void pawnTest3() {
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(4,3,pawn);
	
		assertFalse("The king isn't put in check by the pawn standing vertically under him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void pawnTest4() {
		
		Pawn pawn = new Pawn('w');
		board.setBoardPos(3,1,pawn);
	
		assertFalse("The king isn't put in check by the pawn standing diagonally above him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void pawnTest5() {
		
		Pawn pawn = new Pawn('b');
		board.setBoardPos(5,1,pawn);
	
		assertFalse("The king isn't put in check by a pawn of the same color",
					board.isKingInCheck('b'));
	}
	
	//Knight tests
	@Test
	public void knightTest1() {
		
		Knight knight = new Knight('w');
		board.setBoardPos(3,0,knight);
	
		assertTrue("The king is put in check by the knight in the right position",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void knightTest2() {
		
		Knight knight = new Knight('w');
		board.setBoardPos(2,1,knight);
	
		assertTrue("The king is put in check by the knight in the right position",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void knightTest3() {
		
		Knight knight = new Knight('w');
		board.setBoardPos(4,1,knight);
	
		assertFalse("The king isn't put in check by the knight right next to him",
					board.isKingInCheck('b'));
	}
	
	@Test
	public void knightTest4() {
		
		Knight knight = new Knight('b');
		board.setBoardPos(3,0,knight);
	
		assertFalse("The king isn't put in check by a knight of the same color",
					board.isKingInCheck('b'));
	}
}
