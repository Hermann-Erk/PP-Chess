package org.hermann.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.hermann.model.board.Board;
import org.hermann.model.pieces.Bishop;
import org.hermann.model.pieces.Knight;
import org.hermann.model.pieces.Piece;
import org.hermann.model.pieces.Queen;
import org.hermann.model.pieces.Rook;
import org.hermann.view.BoardSquares;

/**	This mouselistener will help us to show possible moves on the board
 *  when the mouse hovers over a button which contains a chesspiece.
 *  We'll use the boards .getValidMoves - method to paint possible
 *  destinations in another color (probably green)
 * */
public class BoardSquaresMouseListener implements MouseListener {
	private JButton squares[][];
	private BoardSquares boardSquares;
	
	public BoardSquaresMouseListener(Board board,JButton squares[][], BoardSquares boardSquares){
		this.squares =  squares;
		this.boardSquares = boardSquares;
	}
	
	/**	So here's another annoying thing:
	 *  Since we made the visualized chessboard an eight by eight grid with
	 *  the gridlayoutmanager, we now need the position of the button in the
	 *  grid. There doesn't seem to be any easy way to access this information
	 *  using .getSource() or .getButton(). Even with .getX() and .getY() we
	 *  only get the mouse coordinates relative to the button, which doesn't
	 *  really help us either.
	 *  I think the easiest way to solve this problem is to use .getSource()
	 *  and then compare the source to every single JButton and then save the
	 *  coordinates in temporary variables.
	 *  
	 *  We also only mark the borders green if there's no piece in focus,
	 *  this will (together with our changes in mouseExited) cause the possible
	 *  moves to stay highlighted until a move with a focused piece is made.
	 * */
	@Override
	public void mouseEntered(MouseEvent e){
		
		if (ChessStatics.getBoardFocus() == null){
			//Here we search for the source of our MouseEvent and then use the "coordinates" of the button in our grid
			//to get the possible destinations for the piece standing on the field (we have to watch out for nullpointer
			//exceptions on empty fields!)
			ArrayList<int[]> possiblePositions = new ArrayList<int[]>();
			for (int i=0; i<8;i++){
				for(int j=0;j<8;j++){
					if(e.getSource() == squares[i][j]){
						 if (boardSquares.getBoard().getBoardPos(j, i) != null){
							 	int[] currentpos = {j,i};
					            possiblePositions = boardSquares.getBoard().getBoardPos(j, i).getValidMoves(boardSquares.getBoard(), ChessStatics.getPlayerTurn(), currentpos);       
				         }
					}
				}
			}
			
			// Here we give our destination fields a nice green border (alternatively we could color the whole field, but
			// I think it looks better this way)
			for (int[] position: possiblePositions){
				squares[position[1]][position[0]].setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
				//squares[position[1]][position[0]].setBackground(Color.green);
			}
		}
		
	}
	/** When the cursor exits the field, all borders are restored to their default color.
	 *  But we want to keep the green borders for a piece in focus, so we only restore
	 *  all the borders default colors, if there's no current boardFocus in ChessStatics.
	 * */
	@Override
	public void mouseExited(MouseEvent e){
		if(ChessStatics.getBoardFocus() == null){
			for (int i=0; i<8;i++){
				for(int j=0;j<8;j++){
					squares[j][i].setBorder(UIManager.getBorder("Button.border"));
				}
			}
		}
	}
	
	/**	This method will help us actually move the pieces on the board using the mouse.
	 * 	
	 * 	In order to do that I added a small feature that could be pretty useful and will
	 *  probably make things a lot easier. I gave our ChessStatics class an additional
	 *  static attribute called boardFocus, an integer-array of the size 2.
	 *  
	 *  Now, if we click a piece on the board, its position will be saved as the boardFocus
	 *  and our focused field will be highlighted in blue (background changes). Otherwise
	 *  there would be no easy way to move a piece around, since we need the position of
	 *  the piece we want to move and its destination field on the board and a single mouse
	 *  click can't provide both positions, so we have to temporarily save one of them.
	 * 
	 * 	The method basically searches for the source of the event (the right JButton in the
	 *  grid) and then the following action depends on whether there's a piece currently 
	 *  focused:
	 *  
	 *  1. If there's no focus on any piece (boardFocus == null), but a field containing a 
	 *     chess piece has been clicked, the focus is set on that field. (If the field doesn't
	 *     contain a piece the focus stays null)
	 *  
	 *  2. If there's a focused piece, the method checks if the new clicked field is a valid field
	 *     for movement. This time we also have to check, if the king will be put in check.
	 *     However, we can't just use the isKingInCheck method on the board, before we make the move
	 *     and then decide, if we want to make the move or not, because isKingInCheck can only check
	 *     the current state of the board, so we have to make the move first, even if it's invalid,
	 *     then check, if the king is in check and then undo the move, if it wasn't allowed.
	 *     And to be able to do that, we have to make sure to not overwrite pieces and position on
	 *     the board that we can't restore afterwards.
	 *  	
	 *     If the move was valid, the static playerTurn in ChessStatics changes to the other player,
	 *     if it was invalid, the player gets another chance to make a valid move.
	 *     In any case, the focus is set back to null and the icons on the board are repainted.
	 *     
	 * 	At first I wasn't very comfortable about the idea of checking for invalid movements due to
	 *  the king being in check in our BoardSquaresMouseListener, because it seems to be directly
	 *  related to our isMoveValid methods. However, there are several reasons, why I decided to separate
	 *  this part from our isMoveValid methods:
	 *  	
	 *  	In order to tell, if the king is currently in check or not, we need to use the boards
	 *  	isKingInCheck method. But it is not possible for us to use isKingInCheck within our
	 *  	isMoveValid methods, since isKingInCheck calls isMoveValid to obtain the pieces movement
	 *  	patterns, using isKingInCheck in isMoveValid would lead to an infinite regress 
	 *  	(Endlosrekursion), so we are forced to split up checking the movement patterns and 
	 *  	possible attacks on the king.
	 *  
	 *  	We also make use of several outputs for the user here (in case of an invalid move due to
	 *  	the king being attacked for example), I think message panels like those don't belong into
	 *  	the model. I know, it would be possible to put the test, if the particular move puts the
	 *  	king in check, in the model and use a return code (true or false) to indicate if we need
	 *  	an error message for the user. But I actually think that the test itself doesn't belong
	 *  	into the model either, it temporarily saves the piece on the movement destination and then
	 *  	puts the piece the player wants to move on that position and only restores the former state,
	 *  	if the king is in check (the move is invalid), we move the piece to check if the move itself
	 *  	is valid. For me the concrete act of moving a piece is definitely something that belongs into
	 *  	our controller package, even if the test for movement validity looks a little odd here.
	 *  
	 *  	(This especially applies to castling (Rochade), as we have to move two pieces at once (king and rook)
	 *   	 which would look very weird, if we put the movement validation below into our model package, it would
	 *   	 probably resolve in a breach of our MVC model)
	 *   
	 *   	@param isCastling Boolean value that indicates if the player is castling or not.
	 *   					  In case of castling we don't check if the king is in check like
	 *   					  we usually do. Castling is handled separately, because we also
	 *   					  have to move the Rook, otherwise we would have to check if the
	 *   					  castling is valid, then if the king will be put in check and then
	 *   					  we would have to check again if there was castling involved (and in
	 *   					  which direction) to be able to move the rook.
	 *      
	 *      @param moveFailed We need this boolean value to indicate if the move was successful or
	 *      				  not. For instance we need to check if the game is over if the move
	 *      				  hasn't failed
	 *      
	 *      @param focusX 	  The focus is on the piece's position that the player wants to move, it's 
	 *       			   	  null if there's no piece focused
	 *  	
	 *  	@param x		  The horizontal position of the button, which is the event's source
	 *  					  {@link BoardSquares}
	 *  	
	 * */
	@Override
	public void mousePressed(MouseEvent e){
		
		// Here we check for the JButton causing the event
		ArrayList<int[]> possiblePositions = new ArrayList<int[]>();
		int x = 0;
		int y = 0;
		for (int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				if(e.getSource() == squares[i][j]){
					 x=j;
					 y=i;
				}
			}
		}
		
		// If there's no active focus and we click on a piece, we set a new focus 
		// and change the background
		if(ChessStatics.getBoardFocus() == null && boardSquares.getBoard().getBoardPos(x, y) != null 
				&& boardSquares.getBoard().getBoardPos(x, y).getColor() == ChessStatics.getPlayerTurn()){
			 ChessStatics.setBoardFocus(new int[] {x,y});
			 int focusX = ChessStatics.getBoardFocus()[0];
			 int focusY = ChessStatics.getBoardFocus()[1];
			 squares[focusY][focusX].setBackground(Color.BLUE);
		}else{
		
			if(ChessStatics.getBoardFocus() != null){
				// focusX and focusY for better readability
				int focusX = ChessStatics.getBoardFocus()[0];
				int focusY = ChessStatics.getBoardFocus()[1];
				possiblePositions = boardSquares.getBoard().getBoardPos(focusX, focusY).getValidMoves(boardSquares.getBoard(), ChessStatics.getPlayerTurn(), ChessStatics.getBoardFocus());
				
				// Here we check if the clicked field matches a valid destination field
				boolean validMove = false; 
				for (int[] position: possiblePositions){
					if(position[0] == x && position[1] == y){
							validMove = true;
					}
				}
				
				// We reset the old focused fields color to normal
				if ((focusX + focusY) % 2 == 0) {
	                squares[focusY][focusX].setBackground(Color.black);
	            } else {
	                squares[focusY][focusX].setBackground(Color.white);
	            }   
				
				
				if(validMove){
					boolean moveFailed = false;
					boolean isCastling = false; 
					
					//castling (Rochade)
					// if the white king castles kingside
					if(focusX == 4 && focusY == 7 && x == 6 && y == 7 && boardSquares.getBoard().getBoardPos(focusX, focusY).getClass().getSimpleName().equals("King")){
						isCastling = true;
						if(!boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
							// We have to simulate movement to the destination field to be able to tell
							// if the king would be in check on his way (We don't have to check the kings
							// destination itself, because a move that makes the king end up in check is 
							// never valid)
							boardSquares.getBoard().setBoardPos(5,7,boardSquares.getBoard().getBoardPos(focusX, focusY));
							boardSquares.getBoard().setBoardPos(focusX,focusY,null);
							if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
								moveFailed = true;
								// We need to reset the Pieces afterwards
								JOptionPane.showMessageDialog(null, "The king can't move through an attacked field when castling!", "Invalid move", 2);
							}
							boardSquares.getBoard().setBoardPos(focusX,focusY,boardSquares.getBoard().getBoardPos(5, 7));
							boardSquares.getBoard().setBoardPos(5, 7, null);
							
							// We only check further, if the move hasn't failed yet
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(x, y, boardSquares.getBoard().getBoardPos(focusX, focusY));
								boardSquares.getBoard().setBoardPos(focusX, focusY, null);
								if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
									boardSquares.getBoard().setBoardPos(focusX, focusY, boardSquares.getBoard().getBoardPos(x, y));
									boardSquares.getBoard().setBoardPos(x, y, null);
									moveFailed = true;
									JOptionPane.showMessageDialog(null, "You can't castle into check!", "Invalid move", 2);
									System.out.println("This move would put your king in check!");
								}
							}
							
							// If castling is possible, we move the rook as well
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(5, 7, boardSquares.getBoard().getBoardPos(7, 7));
								boardSquares.getBoard().setBoardPos(7, 7, null);
							}
						}else{
							// if the king is in check before castling
							JOptionPane.showMessageDialog(null, "The king can't castle out of check!", "Invalid move", 2);
							moveFailed = true;
						}
						
					}
					
					// if the black king castles kingside
					if(focusX == 4 && focusY == 0 && x == 6 && y == 0 && boardSquares.getBoard().getBoardPos(focusX, focusY).getClass().getSimpleName().equals("King")){
						isCastling = true;
						if(!boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
							// We have to simulate movement to the destination field to be able to tell
							// if the king would be in check on his way (We don't have to check the kings
							// destination itself, because a move that makes the king end up in check is 
							// never valid)
							boardSquares.getBoard().setBoardPos(5,0,boardSquares.getBoard().getBoardPos(focusX, focusY));
							boardSquares.getBoard().setBoardPos(focusX,focusY,null);
							if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
								moveFailed = true;
								// We need to reset the Pieces afterwards
								JOptionPane.showMessageDialog(null, "The king can't move through an attacked field when castling!", "Invalid move", 2);
							}
							boardSquares.getBoard().setBoardPos(focusX,focusY,boardSquares.getBoard().getBoardPos(5, 0));
							boardSquares.getBoard().setBoardPos(5, 0, null);
							
							// We only check further, if the move hasn't failed yet
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(x, y, boardSquares.getBoard().getBoardPos(focusX, focusY));
								boardSquares.getBoard().setBoardPos(focusX, focusY, null);
								if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
									boardSquares.getBoard().setBoardPos(focusX, focusY, boardSquares.getBoard().getBoardPos(x, y));
									boardSquares.getBoard().setBoardPos(x, y, null);
									moveFailed = true;
									JOptionPane.showMessageDialog(null, "You can't castle into check!", "Invalid move", 2);
									System.out.println("This move would put your king in check!");
								}
							}
							
							// If castling is possible, we move the rook as well
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(5, 0, boardSquares.getBoard().getBoardPos(7, 0));
								boardSquares.getBoard().setBoardPos(7, 0, null);
							}
						}else{
							// if the king is in check before castling
							JOptionPane.showMessageDialog(null, "The king can't castle out of check!", "Invalid move", 2);
							moveFailed = true;
						}
						
					}
					
					
					// if white is castling queenside
					if(focusX == 4 && focusY == 7 && x == 2 && y == 7 && boardSquares.getBoard().getBoardPos(focusX, focusY).getClass().getSimpleName().equals("King")){
						isCastling = true;
						if(!boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
							// We have to simulate movement to the destination field to be able to tell
							// if the king would be in check on his way (We don't have to check the kings
							// destination itself, because a move that makes the king end up in check is 
							// never valid)
							boardSquares.getBoard().setBoardPos(3,7,boardSquares.getBoard().getBoardPos(focusX, focusY));
							boardSquares.getBoard().setBoardPos(focusX,focusY,null);
							if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
								moveFailed = true;
								// We need to reset the Pieces afterwards
								JOptionPane.showMessageDialog(null, "The king can't move through an attacked field when castling!", "Invalid move", 2);
							}
							boardSquares.getBoard().setBoardPos(focusX,focusY,boardSquares.getBoard().getBoardPos(3, 7));
							boardSquares.getBoard().setBoardPos(3, 7, null);
							
							// We only check further, if the move hasn't failed yet
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(x, y, boardSquares.getBoard().getBoardPos(focusX, focusY));
								boardSquares.getBoard().setBoardPos(focusX, focusY, null);
								if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
									boardSquares.getBoard().setBoardPos(focusX, focusY, boardSquares.getBoard().getBoardPos(x, y));
									boardSquares.getBoard().setBoardPos(x, y, null);
									moveFailed = true;
									JOptionPane.showMessageDialog(null, "You can't castle into check!", "Invalid move", 2);
									System.out.println("This move would put your king in check!");
								}
							}
							
							// If castling is possible, we move the rook as well
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(3, 7, boardSquares.getBoard().getBoardPos(0, 7));
								boardSquares.getBoard().setBoardPos(0, 7, null);
							}
						}else{
							// if the king is in check before castling
							JOptionPane.showMessageDialog(null, "The king can't castle out of check!", "Invalid move", 2);
							moveFailed = true;
						}
						
					}
					
					// if black is castling queenside
					if(focusX == 4 && focusY == 0 && x == 2 && y == 0 && boardSquares.getBoard().getBoardPos(focusX, focusY).getClass().getSimpleName().equals("King")){
						isCastling = true;
						if(!boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
							// We have to simulate movement to the destination field to be able to tell
							// if the king would be in check on his way (We don't have to check the kings
							// destination itself, because a move that makes the king end up in check is 
							// never valid)
							boardSquares.getBoard().setBoardPos(3,0,boardSquares.getBoard().getBoardPos(focusX, focusY));
							boardSquares.getBoard().setBoardPos(focusX,focusY,null);
							if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
								moveFailed = true;
								// We need to reset the Pieces afterwards
								JOptionPane.showMessageDialog(null, "The king can't move through an attacked field when castling!", "Invalid move", 2);
							}
							boardSquares.getBoard().setBoardPos(focusX,focusY,boardSquares.getBoard().getBoardPos(3, 0));
							boardSquares.getBoard().setBoardPos(3, 0, null);
							
							// We only check further, if the move hasn't failed yet
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(x, y, boardSquares.getBoard().getBoardPos(focusX, focusY));
								boardSquares.getBoard().setBoardPos(focusX, focusY, null);
								if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
									boardSquares.getBoard().setBoardPos(focusX, focusY, boardSquares.getBoard().getBoardPos(x, y));
									boardSquares.getBoard().setBoardPos(x, y, null);
									moveFailed = true;
									JOptionPane.showMessageDialog(null, "You can't castle into check!", "Invalid move", 2);
									System.out.println("This move would put your king in check!");
								}
							}
							
							// If castling is possible, we move the rook as well
							if(!moveFailed){
								boardSquares.getBoard().setBoardPos(3, 0, boardSquares.getBoard().getBoardPos(0, 0));
								boardSquares.getBoard().setBoardPos(0, 0, null);
							}
						}else{
							// if the king is in check before castling
							JOptionPane.showMessageDialog(null, "The king can't castle out of check!", "Invalid move", 2);
							moveFailed = true;
						}
						
					}
					
					
					
					// "Normal" movement
					// We don't want to do this, if there's castling involved
					
					// Here we first save what's standing on the field we want to 
					// move on, so we can restore it, if the king is in check after
					// we made the move
					Piece pieceSave = boardSquares.getBoard().getBoardPos(x, y);
					if(!isCastling){	
						boardSquares.getBoard().setBoardPos(x, y, boardSquares.getBoard().getBoardPos(focusX, focusY));
						boardSquares.getBoard().setBoardPos(focusX, focusY, null);
						if(boardSquares.getBoard().isKingInCheck(ChessStatics.getPlayerTurn())){
							boardSquares.getBoard().setBoardPos(focusX, focusY, boardSquares.getBoard().getBoardPos(x, y));
							boardSquares.getBoard().setBoardPos(x, y, pieceSave);
							moveFailed = true;
							JOptionPane.showMessageDialog(null, "This move would put your king in check!", "Invalid move", 2);
						}
					}
					
					
					// Pawn promotion
					// If a Pawn advances to the last row, a panel will pop up and give the player the choice
					// to swap the Pawn for another Piece
					// !Note: The Pawn has already moved at this point, therefore we address the piece on the 
					// last row 
					if(!moveFailed){
						boolean promote = false; 
						if(ChessStatics.getPlayerTurn() == 'w'){
							if(focusY == 1 && y == 0 && boardSquares.getBoard().getBoardPos(x, y).getClass().getSimpleName().equals("Pawn")){
								promote = true;
							}
						} else {
							if(focusY == 6 && y == 7 && boardSquares.getBoard().getBoardPos(x, y).getClass().getSimpleName().equals("Pawn")){
								promote = true;
							}
						}
						
						// Rook and Bishop are not really necessary, but still allowed to choose
						if(promote){
							Object message = "Your Pawn advanced towards the last row!\nYou may promote him now.";
							Object[] possibleParameters = {"Queen", "Bishop", "Rook", "Knight"}; 
							int exitParameter = JOptionPane.showOptionDialog(boardSquares, message, "Promotion",
											0,2,null,possibleParameters, possibleParameters[0]);
							switch (exitParameter) {
							case 0:
								boardSquares.getBoard().setBoardPos(x, y, new Queen(ChessStatics.getPlayerTurn()));
								break;
							case 1:
								boardSquares.getBoard().setBoardPos(x, y, new Bishop(ChessStatics.getPlayerTurn()));
								break;
							case 2:
								boardSquares.getBoard().setBoardPos(x, y, new Rook(ChessStatics.getPlayerTurn()));
								break;
							case 3:
								boardSquares.getBoard().setBoardPos(x, y, new Knight(ChessStatics.getPlayerTurn()));
								break;
							};
						}
						
						// In case of a successful move we need to set the pieces hasMoved value to true
						// (It has already moved, therefore we use the destination fields)
						boardSquares.getBoard().getBoardPos(x, y).setMoved();
						
					}
					
					// If the move was successful, we switch player turns in ChessStatics
					ChessStatics.setBoardFocus(null);
					if(!moveFailed){
						if(ChessStatics.getPlayerTurn() == 'w'){
							ChessStatics.setPlayerTurn('b');
						}else{
							ChessStatics.setPlayerTurn('w');
						}
						
						
						// After turns have been switched, we test, if any more moves are
						// possible
						boardSquares.repaintIcons();
						
						// In case of a successful move we also remove the "possible moves"
						// borders, otherwise it looks weird
						for (int i=0; i<8;i++){
							for(int j=0;j<8;j++){
								squares[j][i].setBorder(UIManager.getBorder("Button.border"));
							}
						}
						
						ChessStatics.isGameOver(boardSquares.getBoard());
						
					}
					boardSquares.repaintIcons();
				}else{
					// If the move isn't validated by the isMoveValid method
					// (it doesn't follow the pieces movement pattern) 
					ChessStatics.setBoardFocus(null);
					
					JOptionPane.showMessageDialog(null, "This piece is not allowed to go there!", "Invalid move", 2);
				}
			
			}
		
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		
	}

}
