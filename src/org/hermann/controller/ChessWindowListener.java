package org.hermann.controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import org.hermann.view.ChessGUI;


public class ChessWindowListener implements WindowListener {
	private ChessGUI frame;
	
	public ChessWindowListener(ChessGUI jframe){
		this.frame = jframe;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/** We only need the window listener to change the closing operation to this save prompt.
	 * */
	@Override
	public void windowClosing(WindowEvent arg0) {
		Object message = "All your unsaved game progress will be lost.";
		Object[] possibleParameters = {"Save", "Exit", "Cancel"}; 
		int exitParameter = JOptionPane.showOptionDialog(frame, message, "Exit?",
						0,2,null,possibleParameters, possibleParameters[0]);
		switch (exitParameter) {
		case 0:
			ChessStatics.save(frame.getBoardSquares().getBoard());
		case 1:
			System.exit(0);
			break;
		case 2:
			break;
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
