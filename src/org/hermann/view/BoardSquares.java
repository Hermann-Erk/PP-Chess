package org.hermann.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.hermann.controller.BoardSquaresMouseListener;
import org.hermann.controller.ChessStatics;
import org.hermann.media.Icons;
import org.hermann.model.board.Board;

/**
 * @param squares An 8x8 grid of <code>JButtons</code> that resembles the chess Board
 * @param board The chess Board that squares resembles
 * @param boardheight The height adapts to the <code>SCREEN_SIZE</code>, the {@link Icons}
 * 					  uses this height to scale its icon images.
 * */
@SuppressWarnings("serial")
public class BoardSquares extends JPanel {
	private JButton squares[][] = new JButton[8][8];
	private Board board;
	private int boardHeight;
	
	/** We need the screen size and the control panel height to calculate the maximum size
	 *  of our board squares
	 * */
	public BoardSquares(Dimension SCREEN_SIZE, int CONTROL_PANEL_HEIGHT,Board board){
		this.board = board;
		boardHeight = SCREEN_SIZE.height - CONTROL_PANEL_HEIGHT - 120;
		ChessStatics.setMouseListener(new BoardSquaresMouseListener(board,squares,this));
		this.initializeWindow(CONTROL_PANEL_HEIGHT);
		this.initializeContent();
	}
	
	/** Initializing the board squares making it rectangular and centering it 
	 *  below the MenuPanel.<p>
	 *  We're using a grid layout, because it's probably the easiest way to
     *  create an 8 by 8 grid of buttons.<p>
     *  We get the screen size via the ChessActionListener in ChessStatics, it's not pretty,
	 *  but still batter than having another attribute SCREEN_SIZE only to temporarily save
	 *  the value
	 * */
	private void initializeWindow(int CONTROL_PANEL_HEIGHT){
		this.setLayout(new GridLayout(8,8));
		this.setBounds(ChessStatics.getActionListener().getFrame().getScreenSize().width/2-boardHeight/2,
				CONTROL_PANEL_HEIGHT + 40, boardHeight, boardHeight);
		this.setVisible(true);
	}
	
	/** In this method we initialize the 8 by 8 grid of buttons and add our {@link BoardSquaresMouseListener}
	 *  to every button, we also set the squares' background according to the colors of a chess board.<p>
	 *  Then we set the background Icon for every button that resembles a field with a piece on it using
	 *  the icons from our static {@link Icons} instance. 
	 * */
	private void initializeContent(){
	
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            squares[i][j] = new JButton();
	            squares[i][j].addMouseListener(ChessStatics.getMouseListener());
	            
	            // We color the Buttons according to a chess board,
	            // the if statement is kind of similar to the Kronecker symbol
	            if ((i + j) % 2 == 0) {
	                squares[i][j].setBackground(Color.black);
	            } else {
	                squares[i][j].setBackground(Color.white);
	            }   
	            
	            // Watching out for nullpointer exceptions...
	            // with the simple class name we can use our Icon classes .getImageByName method
	            if (board.getBoardPos(j, i) != null){
		            String squarePiece = board.getBoardPos(j, i).getClass().getSimpleName();
		            
		            squares[i][j].setIcon(Icons.getIcons(boardHeight).getImageByName(squarePiece + board.getBoardPos(j, i).getColor()));
	            }
	            
	            this.add(squares[i][j]);
	        }
	    }
	
	}
	
	
	/**	Instead of repainting the GUI we only need to repaint the Icons in BoardSquares' JButtons
	 * */
	public void repaintIcons(){
		for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	        	 if (board.getBoardPos(j, i) != null){
		        	String squarePiece = board.getBoardPos(j, i).getClass().getSimpleName();
		        	squares[i][j].setIcon(Icons.getIcons(boardHeight).getImageByName(squarePiece + board.getBoardPos(j, i).getColor()));
	        	 }else{
	        		 squares[i][j].setIcon(null); 
	        	 }
	        }
	    }
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
}
