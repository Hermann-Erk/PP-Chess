package org.hermann.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/**	This classes only purpose is to easily set a background image for our {@link ChessGUI},
 *  receives an image in the initializer parameters and paints it
 * */
public class BackgroundImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	
	public BackgroundImagePanel(Image image){
		this.backgroundImage = image;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(backgroundImage, 0, 0, null);
	}
}
