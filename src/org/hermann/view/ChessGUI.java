package org.hermann.view;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.hermann.controller.ChessActionListener;
import org.hermann.controller.ChessStatics;
import org.hermann.controller.ChessWindowListener;
import org.hermann.media.Icons;
import org.hermann.model.board.Board;

/** We often use our <code>ChessGUI</code>
 *  to access the board held by the {@link BoardSquares},
 *  I don't really like this solution, but all the other ones I could imagine
 *  are worse. 
 * */
@SuppressWarnings("serial")
public class ChessGUI extends JFrame {
	private MenuPanel menuPanel;
	private BoardSquares boardSquares;
	// Screen size is in capital letters due to its constant behaviour
	private Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private final int CONTROL_PANEL_HEIGHT = 60;
	
	/** Constructor for our {@link ChessGUI},
	 *  we initialize the {@link BoardSquares} and {@link MenuPanel},
	 *  we also set our {@link MenuBar}.
	 * */
	public ChessGUI(Board board) {
		super("Chess Game");
		this.initializeWindow();
		ChessStatics.setActionListener(new ChessActionListener(this));
		this.setJMenuBar(new MenuBar());
		menuPanel = new MenuPanel(SCREEN_SIZE.width,CONTROL_PANEL_HEIGHT);
		this.getContentPane().add(menuPanel);
		boardSquares = new BoardSquares(SCREEN_SIZE,CONTROL_PANEL_HEIGHT,board);
		this.getContentPane().add(boardSquares);
		
		this.pack();
		this.setVisible(true);
	}
	
	/** We initialize the window and set its dimension to <code>SCREEN_SIZE</code>
	 *  this is important for the BoardSquares, because it adapts to
	 *  this dimension.<p>
	 *  We also set the Frames <code>ContentPane</code> Container to be a Container
	 *  for our background image.<p>
	 *  A layout manager is not necessary, it's nicer (and probably easier)
	 *  to place the content by hand.
	 * */
	private void initializeWindow(){
		this.setLayout(null);
		this.setPreferredSize(SCREEN_SIZE);
		this.setContentPane(new BackgroundImagePanel(Icons.getIcons(SCREEN_SIZE.height - CONTROL_PANEL_HEIGHT - 120).getBackgroundByName("WoodBackground")));
		this.addWindowListener(new ChessWindowListener(this));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);	// for now...
		// TODO custom cursor?
	}
	
	public BoardSquares getBoardSquares(){
		return boardSquares;
	}
	
	public MenuPanel getMenuPanel(){
		return menuPanel;
	}
	
	public Dimension getScreenSize(){
		return SCREEN_SIZE;
	}
}
