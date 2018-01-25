package org.hermann.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.hermann.model.board.Board;
import org.hermann.view.ChessGUI;


/**	Action listener for our <code>JButtons</code> in the <code>MenuPanel</code> and the items in our <code>JMenubar</code>,
 * 	we'll only use one of them for all buttons/items so we won't have to write actions listeners 
 * 	for every single one of them. Also we don't need an extra instance for every single <code>JButton</code>.
 * */
public class ChessActionListener implements ActionListener {
	private ChessGUI frame;
	
	public ChessActionListener(ChessGUI frame){
		this.frame = frame;
	}
	
	
	/**	So there are several ways to indicate which button/item caused the event and to decide which action
	 * 	to perform:
	 * 
	 * 	We could either use the <code>.getSource()</code> method to know which object in particular was the source of the event
	 * 	or we could go with Adrian's way to use the <code>.getActionCommand()</code> method to receive the command string associated
	 * 	with the action.
	 * 
	 * 	I decided for the <code>.getActionCommand()</code> method, because it will probably be easier to reuse some actions, if we 
	 * 	have several event sources, which are supposed to perform the same action (for whatever reason). We'll only have
	 * 	label the button or item in the menubar with the same string to achieve the same functionality.
	 * 
	 * Example: We already have an exit button in our control panel, but now we want another one in our menubar.
	 * 			If we used <code>.getSource()</code>, even if we use the same action lister, we would have to connect one action
	 * 			to several sources. However, if we use <code>.getActionCommand()</code>, we only need to connect the action to
	 * 			String "Exit" once and label both buttons "Exit".
	 * */
	@Override
	public void actionPerformed(ActionEvent e){
		switch (e.getActionCommand()){
		case "Load game":
			// TODO update boardSquares with new board 
			Board loadedBoard = ChessStatics.load();
			if(loadedBoard != null){
				frame.getBoardSquares().setBoard(loadedBoard);
				frame.getBoardSquares().repaintIcons();
			}
			ChessStatics.setPlayerTurn('w');
			break;
		case "Save game":
			ChessStatics.save(frame.getBoardSquares().getBoard());
			break;
		case "New game":
			frame.getBoardSquares().setBoard(new Board());
			frame.getBoardSquares().repaintIcons();
			ChessStatics.setPlayerTurn('w');
			break;
		case "Exit":
			// A box with three options to save, exit or continue the game
			Object message = "All your unsaved game progress will be lost.";
			Object[] possibleParameters = {"Save", "Exit", "Cancel"}; 
			int exitParameter = JOptionPane.showOptionDialog(frame, message, "Exit?",
							0,2,null,possibleParameters, possibleParameters[0]);
			switch (exitParameter) {
			case 0:
				ChessStatics.save(frame.getBoardSquares().getBoard());
				// There is no break here, so the program will close afterwards
			case 1:
				System.exit(0);
				break;
			case 2:
				break;
			}
			
			break;
		case "About":
				JOptionPane.showMessageDialog(frame,
						"All chess piece icons have been downloaded at www.virtualpieces.net.\nThe background image has been downloaded at www.techcredo.com.\n\nversion 1.2", "About game", 1);
			break;
		case "Toggle AI":
			// The AI should only be toggled, if it's the "actual" players turn
			if (ChessStatics.getPlayerTurn() == 'w'){
					ChessStatics.setAiEnabled(true);
			}else{
				JOptionPane.showMessageDialog(frame,
						"Can't toggle the AI in mid turn, please wait until it's player white's turn.", "Not able to switch to AI", 2);
			}
			break;
		case "Toggle Player":
			// It's probably not even possible to switch to an actual player while the AI makes its
			// turn, because it's way too fast, but just in case...
			if (ChessStatics.getPlayerTurn() == 'w'){
				ChessStatics.setAiEnabled(false);
			}else{
				JOptionPane.showMessageDialog(frame,
						"Can't toggle the AI in mid turn, please wait until it's player white's turn.", "Not able to switch to AI", 2);
			}
			break;
		}
			
		
	}
	
	
	public ChessGUI getFrame() {
		return frame;
	}

	public void setFrame(ChessGUI frame) {
		this.frame = frame;
	}
	
}
