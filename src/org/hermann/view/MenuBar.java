package org.hermann.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.hermann.controller.ChessStatics;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	
	/** The MenuBar contains buttons we already know from our
	 *  MenuPanel and also an "About" button to see my sources and
	 *  the version number (=> implemented in {@link ChessActionListener}),
	 *  the rest is rather self explaining
	 * */
	public MenuBar() {
		
		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(ChessStatics.getActionListener());
		gameMenu.add(newGame);
		
		gameMenu.addSeparator();
		
		JMenuItem loadGame = new JMenuItem("Load game");
		loadGame.addActionListener(ChessStatics.getActionListener());
		gameMenu.add(loadGame);
		
		JMenuItem saveGame = new JMenuItem("Save game");
		saveGame.addActionListener(ChessStatics.getActionListener());
		gameMenu.add(saveGame);
		
		gameMenu.addSeparator();
		
		JMenuItem exitGame = new JMenuItem("Exit");
		exitGame.addActionListener(ChessStatics.getActionListener());
		gameMenu.add(exitGame);
		
		this.add(gameMenu);
		
		
		JMenu helpMenu = new JMenu("?");
		
		JMenuItem aboutGame = new JMenuItem("About");
		aboutGame.addActionListener(ChessStatics.getActionListener());
		helpMenu.add(aboutGame);
		
		this.add(helpMenu);
		
	}
	
	
}
