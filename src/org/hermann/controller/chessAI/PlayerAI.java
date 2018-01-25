package org.hermann.controller.chessAI;

import javax.swing.JOptionPane;

import org.hermann.controller.ChessStatics;
import org.hermann.model.board.Board;
import org.hermann.model.pieces.Piece;
import org.hermann.model.pieces.Queen;

public class PlayerAI {
	private BoardEvaluation evaluation;
	
	public PlayerAI(){
		evaluation = new BoardEvaluation();
	}
	
	/** So this is how our AI moves:
	 *  It consults the {@link BoardEvaluation} which returns position and destination
	 *  of the piece to move.<p>
	 *  We also have to check, if the game is over (the AI is in checkmate),
	 *  to make sure that the AI doesn't move its king into check we test
	 *  as if it was an actual player (it usually shouldn't do that if there's
	 *  any other way, but it sometimes does weird stuff)
	 * */
	public void makeTurn(Board board){
		 
		int[][] move = evaluation.getBestMove(board);
		
//		System.out.print(move[0][0]);
//		System.out.println(move[0][1]);
//		
//		System.out.print(move[1][0]);
//		System.out.println(move[1][1]);
		
		// Check if the game is over yet (otherwise the AI would move it's king into check)
		ChessStatics.isGameOver(board);
		
		// Here the AI makes the actual move, if it moves the king into check, that probably
		// (hopefully) means, that there's no other way, which would mean checkmate
		Piece pieceSave = board.getBoardPos(move[1][0], move[1][1]);		
		board.setBoardPos(move[1][0], move[1][1], board.getBoardPos(move[0][0],move[0][1]));
		board.setBoardPos(move[0][0], move[0][1], null);
		if(board.isKingInCheck(ChessStatics.getPlayerTurn())){
			board.setBoardPos(move[0][0], move[0][1], board.getBoardPos(move[1][0], move[1][1]));
			board.setBoardPos(move[1][0], move[1][1], pieceSave);
		}
		
		// Pawn promotion
		if(move[0][1] == 6 && move[1][1] == 7 && board.getBoardPos(move[1][0], move[1][1]).getClass().getSimpleName().equals("Pawn")){
			board.setBoardPos(move[1][0], move[1][1], new Queen('b'));
		}
		
		
		// After making a turn the AI changes the player turn
		if (ChessStatics.getPlayerTurn() == 'w'){
			ChessStatics.setPlayerTurn('b');
		}else{
			ChessStatics.setPlayerTurn('w');
		}
		
	}
	
	
}
