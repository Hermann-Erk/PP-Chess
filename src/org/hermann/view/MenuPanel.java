package org.hermann.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hermann.controller.ChessStatics;

/** The <code>MenuPanel</code> will have <code>turnLabel</code> as an attribute,
 *  because we will need to change it every time a turn
 *  has been made
 *  We also have the <code>toogleAIButton</code> to switch between an
 *  actual player and the {@link PlayerAI} during the game  
 * */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	private JLabel turnLabel;
	private JButton toggleAIButton;
	
	public MenuPanel(int width, int height){
		this.initializeWindow(width,height);
		this.initializeContent();		
	}
	
	/** Initializes the buttons and labels, we don't use
	 *  a layout manager, so set the bounds by hand
	 * */
	private void initializeContent(){
		
		JButton newGameButton = new JButton("New game");
		newGameButton.addActionListener(ChessStatics.getActionListener());
		newGameButton.setBounds(10, 10, 100, 40);
		this.add(newGameButton);
		
		JButton loadingButton = new JButton("Load game");
		loadingButton.addActionListener(ChessStatics.getActionListener());
		loadingButton.setBounds(120, 10, 100, 40);
		this.add(loadingButton);
		
		JButton savingButton = new JButton("Save game");
		savingButton.addActionListener(ChessStatics.getActionListener());
		savingButton.setBounds(230, 10, 100, 40);
		this.add(savingButton);
		
		// This label shows who's turn it is
		turnLabel = new JLabel("It's player white's turn");
		turnLabel.setBounds(340, 10, 180, 40);
		this.add(turnLabel);
		
		// This button will toggle the AI on and off
		toggleAIButton = new JButton("Toggle AI");
		toggleAIButton.addActionListener(ChessStatics.getActionListener());
		toggleAIButton.setBounds(630, 10, 120, 40);
		this.add(toggleAIButton);
		
		// loading bar for possible ChessBot? But now for something completely different first...
		
	}
	
	/** This initializes the window and sets the size
	 *  according to the given value
	 * */
	private void initializeWindow(int width, int height){
		Dimension windowSize = new Dimension(width,height);
		this.setLayout(null);			// We need no layout-manager
		this.setSize(windowSize);
		this.setVisible(true);
	}
	
	/** After every turn we change the turn label to show,
	 *  which player has to move next
	 * */
	public void setTurnLabel(String turn){
		turnLabel.setText(turn);
	}
	
	/** We only use a single button to toggle between AI and player,
	 *  so we have to change the text according to the current state
	 * */
	public void switchAIButton(boolean isActive){
		if(isActive){
			toggleAIButton.setText("Toggle Player");
		}else{
			toggleAIButton.setText("Toggle AI");
		}
	}

	/** Instead of having a grey background, the Panel is now transparent,
	 *  but for some reason the turnLabel-JLabel glitches out this way...   
	 * */
//	protected void paintComponent(Graphics g){
//
//	}
	
}
