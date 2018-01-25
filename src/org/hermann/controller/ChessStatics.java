package org.hermann.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hermann.controller.chessAI.PlayerAI;
import org.hermann.model.board.Board;
import org.hermann.model.pieces.King;
import org.hermann.model.pieces.Pawn;
import org.hermann.model.pieces.Piece;
import org.hermann.model.pieces.Rook;
import org.hermann.view.ChessGUI;

/** @param playerTurn A char that shows which player's turn it is 'w' for white and 'b' for black.
 *  @param boardFocus An array of integers (2) which stores the current focus on the board.
 *  				  Used in {@link BordSquaresMouseListener}.
 *  @param aiEnabled A boolean that indicates if the AI is enabled, can be toggled on and off by the
 *  				 <code>JButton</code> in {@link MenuPanel}.
 * */
public abstract class ChessStatics {
	private static BoardSquaresMouseListener mouseListener;
	private static ChessActionListener actionListener;
	private static char playerTurn = 'w';
	private static int[] boardFocus = null;
	private static boolean aiEnabled = false;
	private static PlayerAI ai = new PlayerAI();
	
	public static void main(String[] args) {
		
//		// This will be deleted in the final version, just to play around and try some things
//		Piece[][] testboard = {{null,null,		   null,		 null,		     new King('b'),		   null,		 null,new Rook('b')},
//							   {null,new Pawn('w'),null,	     null,	 		 null,		   null,		 null,null},
//							   {null,null,		   null,		 null,		     null, 			null,		 null,null},
//							   {null,null,null,		 null,		     null,		   null,		 null,null},
//							   {null,null,		   null,		 null,		     null,		   null,		 null,null},
//							   {null,null,		   null,		 null		,	 null,		   new Rook('w'),null,null},
//							   {null,new Pawn('b'),		   null,null,		     null,		   null,		 null,null},
//							   {null,null,		   null,		 null,		     new King('w'),null,		 null,new Rook('w')},};
					
		
		Board board1 = new Board();
		
		ChessGUI gui = new ChessGUI(board1);
		
		
	}



	/** After every turn we should check, if the game is over (if any moves are even possible)
	 * */
	public static void setPlayerTurn(char playerTurn) {
		ChessStatics.playerTurn = playerTurn;
		if (playerTurn == 'w'){
			actionListener.getFrame().getMenuPanel().setTurnLabel("It's player white's turn");
		}else{
			actionListener.getFrame().getMenuPanel().setTurnLabel("It's player black's turn");
			if (aiEnabled){
				ai.makeTurn(ChessStatics.getActionListener().getFrame().getBoardSquares().getBoard());
			}
		}
		
	}


	public static int[] getBoardFocus() {
		return boardFocus;
	}


	public static void setBoardFocus(int[] boardFocus) {
		ChessStatics.boardFocus = boardFocus;
	}

	/** This method should be executed every time a successful move has been made.
	 *  It basically checks, if there are any valid moves left for the next, that
	 *  wouldn't put his king in check, if the game is over, there should be some 
	 *  kind of message informing the player(s).
	 *  
	 *  Instead of putting this method directly into the board class I decided to
	 *  implement it in <code>ChessStatics</code>, because the actions that will follow if the
	 *  game is over will probably won't fit well into our model package.
	 * */
	public static void isGameOver(Board board){
		boolean gameOver = true;
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				if (board.getBoardPos(j, i) != null && board.getBoardPos(j, i).getColor() == ChessStatics.getPlayerTurn()){
					ArrayList<int[]> possiblePositions = new ArrayList<int[]>();
					possiblePositions = board.getBoardPos(j, i).getValidMoves(board, playerTurn,new int[] {j,i});
					for (int[] position: possiblePositions){
						// Simulating the move to see if the king is in check
						Piece pieceSave = board.getBoardPos(position[0], position[1]);
						board.setBoardPos(position[0], position[1], board.getBoardPos(j, i));
						board.setBoardPos(j, i, null);
						if(!board.isKingInCheck(playerTurn)){
							gameOver=false;
						}
						// Restoring the old game state
						board.setBoardPos(j, i, board.getBoardPos(position[0], position[1]));
						board.setBoardPos(position[0], position[1], pieceSave);	
					}
				}
			}
		}
		
		
		if (gameOver){
			// If there are no moves possible and the king is in check, the other player wins
			// however, if he isn't in check, chess rules say that it's a draw/remis
			if (board.isKingInCheck(playerTurn)){
				if(playerTurn == 'b'){
					JOptionPane.showMessageDialog(null, "Player white wins.", "Game Over", 1);
				}else{
					JOptionPane.showMessageDialog(null, "Player black wins.", "Game Over", 1);
				}
			}else{
				JOptionPane.showMessageDialog(null, "It's a draw, the king isn't in check, but the player can't move.", "Game Over", 1);
				System.out.println("It's a draw");
			}
		}
		
	}
	
	
	/** Saving the game progress, we only need to serialize the board and
	 *  all pieces standing on the board to be able to restore a game state.<p>
	 *  
	 *  This accidently solved one of my main problems:<p>
	 *  To perform a castling move (Rochade), the rules require that
	 *  "Neither the king nor the chosen rook have previously moved".
	 *  The board, however, only knows the pieces' positions and has no 
	 *  way to tell if pieces have been previously moved (other than 
	 *  comparing their current position with their starting location)<p>
	 *  
	 *  Since the board is an array, which contains actual piece object 
	 *  references, we also have to serialize the pieces themselves,
	 *  I didn't realize that before.<p>
	 *  Now we can simply give the rook and the king an additional
	 *  boolean attribute "hasMoved", which will be set true if they are
	 *  moved, so we'll probably be able to implement "special" chess
	 *  moves in future versions...<p>
	 *  
	 *  I also decided that the player can only save, if it's the white
	 *  players turn, i have two reasons for that:<p>
	 *  
	 *  1. The current player turn is only known by ChessStatics, it's static
	 *     and therefore visible, but we don't serialize it. When we load a
	 *     game, we can't know whose turn it was when the game was saved, so 
	 *     I decided to disable saving while player black makes his move.<p>
	 *  
	 *  2. I'm planning on implementing some sort of simple chess AI to play
	 *     against, so I don't want the player to save the game, while the {@link PlayerAI}
	 *     makes it's move, which would probably break something (it would
	 *     possibly save, that it's the AIs turn, but when loaded the AI would
	 *     not be triggered to move and the game would end up in a deadlock or 
	 *     something) and I really don't want to deal with stuff like that.
	 * */
	public static void save(Board board){
		JFrame frame = actionListener.getFrame();
		if (playerTurn == 'w'){
			final JFileChooser saveChooser = new JFileChooser();
			saveChooser.setFileFilter(new ChessFileFilter());		// The filefilter filters file endings
			saveChooser.setCurrentDirectory(new File("."));			// Here we set the default directory for saving 
			int saveSelection = saveChooser.showSaveDialog(null);	// Pops up a save dialog
			if (saveSelection == JFileChooser.APPROVE_OPTION) {
				try {
					File selectedFile = saveChooser.getSelectedFile();		// Gets the selected file (either marked in the window or written)
					if (!selectedFile.getAbsolutePath().endsWith(".chs")) {		// We automatically use the file ending
						selectedFile = new File(selectedFile.getAbsolutePath()
								+ ".chs");
					}
					FileOutputStream os = new FileOutputStream(selectedFile);	
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(board);				// Here we write the actual serializable data into the file
					oos.close();						// Here we close the handle
	
					JOptionPane.showConfirmDialog(frame, "Saving succeeded!",
							"Saving succeeded", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE);
	
				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(frame, "Saving failed!",
							"Saving failed", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Player black has to finish the turn before saving.", "Saving not possible", 0);
		}
	}
	
	
	/** By default after loading the white player has to make a turn (reasons above in save the method)
	 * @return The freshly loaded board
	 * */
	public static Board load() {
		Board loadedBoard = null;
		final JFileChooser loadChooser = new JFileChooser();	// Similar to saving we use a filefilter
		loadChooser.setFileFilter(new ChessFileFilter());		// to select only the .chs files
		loadChooser.setCurrentDirectory(new File("."));		
		int selection = loadChooser.showOpenDialog(null);		// Here's the loading selection window
		if (selection == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream is = new FileInputStream(			// This time we use input streams instead
						loadChooser.getSelectedFile());				// of output streams
				ObjectInputStream ois = new ObjectInputStream(is);
				Board board = (Board) ois
						.readObject();
				loadedBoard = board; 
				ois.close();

			} catch (Exception e1) {
				JOptionPane.showConfirmDialog(null, "Loading failed!",
						"Loding failed", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();

			}
		}
		return loadedBoard;				// We return the de-serialized board's and piece's data
	}
	
	public static BoardSquaresMouseListener getMouseListener() {
		return mouseListener;
	}


	public static void setMouseListener(BoardSquaresMouseListener mouseListener) {
		ChessStatics.mouseListener = mouseListener;
	}


	public static ChessActionListener getActionListener() {
		return actionListener;
	}


	public static void setActionListener(ChessActionListener actionListener) {
		ChessStatics.actionListener = actionListener;
	}


	public static char getPlayerTurn() {
		return playerTurn;
	}
	
	public static boolean isAiEnabled() {
		return aiEnabled;
	}


	public static void setAiEnabled(boolean aiEnabled) {
		ChessStatics.aiEnabled = aiEnabled;
		actionListener.getFrame().getMenuPanel().switchAIButton(aiEnabled);
	}
	
}
