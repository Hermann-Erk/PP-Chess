package org.hermann.media;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.hermann.controller.ChessStatics;

/**	I'm not sure what's the best way to import the chess piece images into my project,
 * but I think it's a good idea to create a separate class for them like Adrian did.
 * I don't know if it's really necessary to declare the constructor private and make
 * sure that there can only be one instance of Icons, but it won't do any harm...<p>
 *
 * It might be actually a good idea because when we scale the icons to be the size of
 * the <code>JButtons</code> in {@link BoardSquares} we make sure that we don't mix up images from two
 * different instances of <code>Icons</code> and get some scaled and some unscaled images.
 * But since we would only need one instance of <code>Icons</code> anyway, we could also make it an
 * attribute of {@link BoardSquares} and just use it nowhere else, doesn't really matter I suppose.<p>
 * */
public class Icons{
	private ImageIcon kingB, queenB, rookB, knightB, pawnB, bishopB,
				  	  kingW, queenW, rookW, knightW, pawnW, bishopW;
	private Image 	  woodBackground;
	private static int scaleHeight;
	/** So there's our private static class Icon holder which holds a static instance of Icons
	 * */
	private static class IconHolder{
		public static final Icons INSTANCE = new Icons();
	}
	
	/** This is the getter for our IconHolder instance
	 * */
	public static Icons getIcons(int height){
		scaleHeight = height/8;
		return IconHolder.INSTANCE;
	}
	
	
	/** In out constructor we just read in the image streams and put them
	 * into our attributes, we have to think about what to do if the images
	 * are not found, since a game without visible chess pieces on the board
	 * is pretty hard to play.<p>
	 * 
	 * Well, here's the thing: apparently it is not possible to set a BufferedImage
	 * as a background image or Icon in a <code>JButton</code>, we can only use <code>ImageIcon</code> objects
	 * as Icons using the .setIcon() method to get the picture into a <code>JButton</code> and
	 * since the entire visualization of the board is based on <code>JButtons</code> that's a
	 * problem.<p>
	 * 
	 * It would be possible to just read in the images in as icons, BUT apparently <code>ImageIcons</code>
	 * are not resizable, which leaves us with fixed sized pictures in a bunch of <code>JButtons</code>,
	 * which, on the other hand, have different height and width depending on the size of the
	 * screen. For a BufferedImage this wouldn't be a problem as there is a 
	 * </code>.getScaledInstace(int width, int height, int hints)</code> method, that makes it easy to
	 * scale the picture.<p>
	 * 
	 * So I came up with a rather ugly solution as a last resort:
	 * First we get the pictures as <code>BufferedImages</code> then we resize them and finally "cast"
	 * them into an <code>ImageIcon</code>. I don't really see any other solution to this problem.<p>
	 * 
	 * NOTE:
	 * 	We don't need to scale the images with a specific factor, but only make sure
	 *  that they fit into the <code>JButtons</code> in {@link BoardSquares}, I guess we'll just take the
	 * 	height of our {@link BoardSquares} and divide it by 8, so we'll have the height and
	 *  width of a single button (it's a square so we only need the height)
	 * */
	private Icons(){
		try{
			// Why can we use getClass() without any instance of an object, up
			// to this point I've only seen methods used on objects or is it
			// automatically used on "this"?
			Image kingBImage = ImageIO.read(this.getClass().getResourceAsStream("kingB.png"));
			Image queenBImage = ImageIO.read(this.getClass().getResourceAsStream("queenB.png"));
			Image rookBImage = ImageIO.read(this.getClass().getResourceAsStream("rookB.png"));
			Image knightBImage = ImageIO.read(this.getClass().getResourceAsStream("knightB.png"));
			Image pawnBImage = ImageIO.read(this.getClass().getResourceAsStream("pawnB.png"));
			Image bishopBImage = ImageIO.read(this.getClass().getResourceAsStream("bishopB.png"));
			Image kingWImage = ImageIO.read(this.getClass().getResourceAsStream("kingW.png"));
			Image queenWImage = ImageIO.read(this.getClass().getResourceAsStream("queenW.png"));
			Image rookWImage = ImageIO.read(this.getClass().getResourceAsStream("rookW.png"));
			Image knightWImage = ImageIO.read(this.getClass().getResourceAsStream("knightW.png"));
			Image pawnWImage = ImageIO.read(this.getClass().getResourceAsStream("pawnW.png"));
			Image bishopWImage = ImageIO.read(this.getClass().getResourceAsStream("bishopW.png"));
			Image woodBackgroundImage = ImageIO.read(this.getClass().getResourceAsStream("woodBackground2.png"));
			
			
			kingBImage = kingBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			queenBImage = queenBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			rookBImage = rookBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			knightBImage = knightBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			pawnBImage = pawnBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			bishopBImage = bishopBImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			kingWImage = kingWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			queenWImage = queenWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			rookWImage = rookWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			knightWImage = knightWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			pawnWImage = pawnWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			bishopWImage = bishopWImage.getScaledInstance(scaleHeight, scaleHeight, Image.SCALE_SMOOTH);
			// For some reason if I use the ChessGUI's Screensize, the whole thing crashes, but I don't
			// think that the screen resolution will ever change between initializing the GUI and initializing
			// this Icons instance
			woodBackground = woodBackgroundImage.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
					Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);
			
			kingB = new ImageIcon(kingBImage);
			queenB = new ImageIcon(queenBImage);
			rookB = new ImageIcon(rookBImage);
			knightB = new ImageIcon(knightBImage);
			pawnB = new ImageIcon(pawnBImage);
			bishopB = new ImageIcon(bishopBImage);
			kingW = new ImageIcon(kingWImage);
			queenW = new ImageIcon(queenWImage);
			rookW = new ImageIcon(rookWImage);
			knightW = new ImageIcon(knightWImage);
			pawnW = new ImageIcon(pawnWImage);
			bishopW = new ImageIcon(bishopWImage);
			
			
		}catch(IOException e){
			System.out.println("pics not found");
			System.exit(0);
		}
		
		
		
	}
		
	/** A single getter for all images at once using a switch to indicate which image to return
	 * */
	public ImageIcon getImageByName(String name) {
		// image is an abstract class, so we can't instantiate a new object
		ImageIcon chessPiece = null;
		
		switch(name){
		case "Kingb":
			chessPiece = kingB;
			break;
		case "Queenb":
			chessPiece = queenB;
			break;
		case "Rookb":
			chessPiece = rookB;
			break;
		case "Knightb":
			chessPiece = knightB;
			break;
		case "Pawnb":
			chessPiece = pawnB;
			break;
		case "Bishopb":
			chessPiece = bishopB;
			break;
		case "Kingw":
			chessPiece = kingW;
			break;
		case "Queenw":
			chessPiece = queenW;
			break;
		case "Rookw":
			chessPiece = rookW;
			break;
		case "Knightw":
			chessPiece = knightW;
			break;
		case "Pawnw":
			chessPiece = pawnW;
			break;
		case "Bishopw":
			chessPiece = bishopW;
			break;
		}
		
		return chessPiece;
	}
	
	/** We need a separate getter for the image (all other "images" are icons) 
	 * */
	public Image getBackgroundByName(String name){
		Image background = null;
		
		switch(name){
		case "WoodBackground":
			background = woodBackground;
			break;
		}
		
		return background;
	}
	
	
}
