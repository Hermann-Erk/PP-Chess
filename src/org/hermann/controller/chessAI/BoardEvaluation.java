package org.hermann.controller.chessAI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hermann.controller.ChessStatics;
import org.hermann.model.board.Board;
import org.hermann.model.pieces.Piece;

public class BoardEvaluation {
	
	public BoardEvaluation(){
		
	}
	
	/** This method is the brain of our {@link PlayerAI}, it's not that complex, but not that bad either.
	 *  It evaluates every single possible move and chooses the best one by giving points
	 *  for advantages and taking points for disadvantages as such:<p>
	 *  
	 *  1. If the move captures a piece, the move obviously gains points, the score is raised
	 *     for more important pieces.<p>
	 *  
	 *  	We also evaluate chances in the next round...<p>
	 *  
	 *  2. A move gains points if it causes the AI's pieces to have more possible moves next round
	 *     (better chances to make a good move).<p>
	 *     
	 *  3. On the other hand it loses points for giving the enemy more room to move.<p>
	 *  
	 *  4. The move gains points if it attacks another piece depending on it value (most points for king)<p>
	 *  
	 *  5. The move loses a lot of score if it risks the AI's pieces (a ridiculously high amount for the
	 *     king, since a move that exposes the king isn't even legal, exposing the king is a last resort
	 *     and would cause the AI to lose as there are no other possible moves).<p>
	 *  
	 *  This AI is supposed to act rather defensive, but we can still change the score gain and loss for
	 *  exposing and capturing pieces below and make it brute force attack the king.<p>
	 *  
	 *  I'm actually surprised how fast the AI is, there's almost no delay in-game.
	 *  So I thought it would be probably possible to make this board evaluation more "in depth" by not just
	 *  making a prediction for only the next turn, but for some more. However, this could become very 
	 *  complicated, because we have to think about the other players turn as well and we also would have to
	 *  use VERY complicated loops and/or recursion. I think the code below looks nice and readable as it is
	 *  and it works, I think that's enough for now. (Another thing is, that the computing time for more turns
	 *  would rise exponentially, since we have to evaluate every turn and also evaluate every possible turn it
	 *  would cause next round. Considering that moves get better score for making more moves possible in the 
	 *  next round, the delay would probably be unbearable)
	 *  
	 *  @return A two dimensional <code>Array</code> that contains the current position and the destination
	 *  		of the piece that is best to be moved.
	 * */
	protected int[][] getBestMove(Board board){
		int [][] bestMove = new int[2][2];
		int moveHighScore = 0;
		int moveScore = 0;
		
		// We go over the whole board and get all possible movements of all our pieces
		for(int i = 0; i < 8; i=i+1){
			for(int j=0; j < 8; j=j+1){
				if (board.getBoardPos(i,j) != null && board.getBoardPos(i, j).getColor() == ChessStatics.getPlayerTurn()){
					int[] currentPos = {i,j};
					
					List<int[]> possibleDestinations = board.getBoardPos(i,j).getValidMoves(board, ChessStatics.getPlayerTurn(), currentPos);
					
					for (int[] position: possibleDestinations){
						moveScore = 0;
						
						int x = position[0];
						int y = position[1];
						
						if(board.getBoardPos(x, y) != null && board.getBoardPos(x, y).getColor() == ChessStatics.getPlayerTurn()){
							
							// More score for more important pieces
							switch(board.getBoardPos(position[0], position[1]).getClass().getSimpleName()){
							case "King":
								moveScore = moveScore + 50;
								break;
							case "Queen":
								moveScore = moveScore + 65;
								break;
							case "Rook":
								moveScore = moveScore + 45;
								break;
							case "Knight":
								moveScore = moveScore + 40;
								break;
							case "Pawn":
								moveScore = moveScore + 15;
								break;
							case "Bishop":
								moveScore = moveScore + 45;
								break;
							}
							
						}
						
						
						// We actually make the move to see what it would be like and to
						// evaluate the consequences
						Piece pieceSave = board.getBoardPos(x, y);	
							board.setBoardPos(x, y, board.getBoardPos(i, j));
							board.setBoardPos(i, j, null);
							
							// Here we go over the whole board and give every field a number rating depending 
							// on how much the "player" would benefit from different factors concerning this
							// field
							
							// This iterates over the "new" board
							// to evaluate this move
							for(int k = 0; k < 8; k=k+1){
								for(int l=0; l < 8; l=l+1){
									
									// Here we take a look at the players chances and opportunities 
									if (board.getBoardPos(k,l) != null && board.getBoardPos(k, l).getColor() == ChessStatics.getPlayerTurn()){
												
										List<int[]> possibleDestinationsNextRound = board.getBoardPos(k,l).getValidMoves(board, ChessStatics.getPlayerTurn(),new int[] {k,l});
												
										// The more moves are possible in the next round the better
										for (int[] move: possibleDestinationsNextRound){
											moveScore++;	
										}
										
										// Now we take a look at the players attack opportunities next round
										for (int[] move: possibleDestinationsNextRound){
											if(board.getBoardPos(move[0], move[1]) != null && board.getBoardPos(move[0], move[1]).getColor() == ChessStatics.getPlayerTurn()){
												
												// More score for more important pieces
												switch(board.getBoardPos(move[0], move[1]).getClass().getSimpleName()){
												case "King":
													moveScore = moveScore + 15;
													break;
												case "Queen":
													moveScore = moveScore + 10;
													break;
												case "Rook":
													moveScore = moveScore + 7;
													break;
												case "Knight":
													moveScore = moveScore + 5;
													break;
												case "Pawn":
													moveScore = moveScore + 3;
													break;
												case "Bishop":
													moveScore = moveScore + 7;
													break;
												}
												
											}
											
										}
												
														
									}
									
									// Here we take a look at all the possible threats
									if (board.getBoardPos(k,l) != null && board.getBoardPos(k, l).getColor() != ChessStatics.getPlayerTurn()){
										
										char enemy = 'w';
										if (ChessStatics.getPlayerTurn() == 'w'){
											enemy = 'b';
										}
										
										List<int[]> possibleThreatsNextRound = board.getBoardPos(k,l).getValidMoves(board, enemy,new int[] {k,l});
										
										// The more moves are possible in the next round the better
										for (int[] threatMove: possibleThreatsNextRound){
											moveScore--;	
										}
										
										// Now we take a look at the players attack opportunities
										for (int[] move: possibleThreatsNextRound){
											if(board.getBoardPos(move[0], move[1]) != null && board.getBoardPos(move[0], move[1]).getColor() == ChessStatics.getPlayerTurn()){
												
												// More score for more important pieces
												switch(board.getBoardPos(move[0], move[1]).getClass().getSimpleName()){
												case "King":
													moveScore = moveScore - 1500;
													break;
												case "Queen":
													moveScore = moveScore - 120;
													break;
												case "Rook":
													moveScore = moveScore - 80;
													break;
												case "Knight":
													moveScore = moveScore - 65;
													break;
												case "Pawn":
													moveScore = moveScore - 30;
													break;
												case "Bishop":
													moveScore = moveScore - 80;
													break;
												}
												
											}
											
										}
									}
									
								}
							}
									
							// We never want a move that would put the king in check!
							if (board.isKingInCheck(ChessStatics.getPlayerTurn())){
								moveScore = moveScore - 100000;
							}	
							
							board.setBoardPos(i, j, board.getBoardPos(x, y));
							board.setBoardPos(x, y, pieceSave);			
							
							// If the current move scores better than any other move
							// we save it
							if(moveScore > moveHighScore || moveHighScore == 0){
								moveHighScore = moveScore;
								bestMove = new int[][] {{i,j},{x,y}};
							}
							
					}
				}				
			}
		}
		
		return bestMove;
		
	}
	
		
		
		
}
	
	
	

