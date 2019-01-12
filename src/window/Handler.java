/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Link all the objects together under the handler class
 */

package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entity.GameObject;
import entity.ObjectId;
import objects.Block;
import objects.Boss;
import objects.Enemy;
import objects.EnemyBlock;
import objects.Player;
import objects.Portal;
import objects.Spike;


// Manages 'n' # of game objects instead of manually initializing
public class Handler {

	// Images for each level of the game
	BufferedImage level1 = null;
	BufferedImage level2 = null;
	BufferedImage level3 = null;

	// Create linked list to manage all of the objects in the game
	public LinkedList<GameObject> object = new LinkedList<GameObject>();

	// tempObject is used for comparisons between object within the linked list
	private GameObject tempObject;

	// Scrolling background
	private Scrolling scroll; //Scrolling object
	
	public Handler(Scrolling scroll) {
		
		this.scroll = scroll; //used to get scroll x and y values
		BufferedImageLoader loader = new BufferedImageLoader(); //used to load images
		// Load each level's images
		level1 = loader.loadImage("/level.png"); //level 1
		level2 = loader.loadImage("/level2.png"); //level 2
		level3 = loader.loadImage("/level3.png"); //level 3
		LoadImageLevel(level1); // load level 1 (game starts on level 1)
	}

	/** Updates 'n' number of objects in the list */
	public void tick() {
		//tick all objects under the object list
		for (int i = 0; i < object.size(); i++) {
			tempObject = object.get(i); //set the tempObject to the current object in the list
			tempObject.tick(object); //tick the object (each object has a tick method)
		}
	}

	/** call the render method of all objects in the linked list */
	public void render(Graphics g) {
		//render all blocks and portals first
		for (int i = 0; i < object.size(); i++) { //access all objects under list
			GameObject tempObject = object.get(i); //set tempObject to current object in list
			if (tempObject.getId() == ObjectId.Portal || tempObject.getId() == ObjectId.Block ){ //only render portal and block
				tempObject.render(g); //call render method to paint objects to frame
			}
		}
		//render everything else except player next
		for (int i = 0; i < object.size(); i++) { //access all objects under list
			GameObject tempObject = object.get(i);  //set tempObject to current object in list
			if (tempObject.getId() != ObjectId.Portal && tempObject.getId() != ObjectId.Block && tempObject.getId() != ObjectId.Player){ //does not render blocks, portal, or player
				tempObject.render(g); //call render method to paint objects to frame
			}
		}
		//render player last to ensure player is on the front layer
		for (int i = 0; i < object.size(); i++) { //access all objects under list
			GameObject tempObject = object.get(i);  //set tempObject to current object in list
			if (tempObject.getId() == ObjectId.Player){ //only render player
				tempObject.render(g); //call render method to paint objects to frame
			}
		}
	}

	/** clear linked list and add necessary objects for the next level*/
	public void switchLevel() {
		clearLevel(); //clear linked list
		scroll.x = 0; //set scrolling bg back to 0
		Game.LEVEL++; //increase level in the game
		//load level depending on LEVEL value
		if (Game.LEVEL == 2) {
			LoadImageLevel(level2); //level 2
		} else if (Game.LEVEL == 3) {
			LoadImageLevel(level3); //level 3
		}
	}

	/** clears linked list object*/
	public void clearLevel() {
		object.clear(); //clear list
	}

	/** Adds Object
	 * @param object to be added
	 */
	public void addObject(GameObject object) {
		this.object.add(object); //adds object to list
	}

	/** Removes Object
	 * @param object to be removed
	 */
	public void removeObject(GameObject object) {
		this.object.remove(object); //removes object from list
	}

	/**Load the level by using the map editor image
	 * @param image - layout of the map (must be square)
	 */
	public void LoadImageLevel(BufferedImage image) {
		
		//get width and height of image so that the loop does not go out of bounds
		int w = image.getWidth();
		int h = image.getHeight();

		// checks each pixel of the level image
		for (int xx = 0; xx < h; xx++) { //increase row number until it reaches height
			for (int yy = 0; yy < w; yy++) { //increase colum number until it reaches width
				
				//Colour chart for image
				/* White pixels: Main block used for floor
				 * Green pixels: Other Blocks and Enemy Blocks
				 * Full Blue pixel: Player
				 * Other blue pixels: Extra blocks
				 * Teal pixels: Portal
				 * Other pixels: Enemies, Spikes, or Boss 
				 */
				
				//pixel concept taken from youtbe tutorial
				int pixel = image.getRGB(xx, yy); //gets the RGB colour code of the pixel
				int red = (pixel >> 16) & 0xff; // red value of pixel
				int green = (pixel >> 8) & 0xff; // green value of pixel
				int blue = (pixel) & 0xff; // blue value of pixel

				// BLOCKS
				// Grass
				if (red == 0 && green == 88 && blue == 38) //checks the red, green, and blue values of the pixel to determine which object to add
					addObject(new Block(xx * 32, yy * 32, 32, 32, 0, ObjectId.Block)); //format of parameters taken from youtube tutorial
				if (red == 0 && green == 89 && blue == 82) 
					addObject(new Block(xx * 32, yy * 32, 32, 32, 1, ObjectId.Block));
				if (red == 0 && green == 91 && blue == 127)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 2, ObjectId.Block));
				if (red == 0 && green == 54 && blue == 99)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 3, ObjectId.Block));
				if (red == 255 && green == 255 && blue == 255)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 4, ObjectId.Block));

				// Dirt (same format as Grass section above)
				if (red == 25 && green == 123 && blue == 48) 
					addObject(new Block(xx * 32, yy * 32, 32, 32, 5, ObjectId.Block));
				if (red == 0 && green == 114 && blue == 54)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 6, ObjectId.Block));
				if (red == 0 && green == 116 && blue == 107)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 7, ObjectId.Block));
				if (red == 0 && green == 118 && blue == 163)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 8, ObjectId.Block));

				if (red == 0 && green == 74 && blue == 128)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 9, ObjectId.Block));
				if (red == 0 && green == 52 && blue == 113)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 10, ObjectId.Block));
				if (red == 27 && green == 20 && blue == 100)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 11, ObjectId.Block));
				if (red == 68 && green == 14 && blue == 98)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 12, ObjectId.Block));

				// Platform (same format as Grass section above)
				if (red == 141 && green == 198 && blue == 63)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 13, ObjectId.Block));
				if (red == 57 && green == 181 && blue == 74)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 14, ObjectId.Block));
				if (red == 0 && green == 166 && blue == 81)
					addObject(new Block(xx * 32, yy * 32, 32, 32, 15, ObjectId.Block));

				// MOBS (same format as Grass section above)
				if (red == 255 && green == 255 && blue == 0)// Bat
					addObject(new Enemy(xx * 32, yy * 32 - 100, 94, 110, this, 4, 4, ObjectId.Enemy)); //value subtracted from yy*32 is used to make monster be above platform
				if (red == 255 && green == 0 && blue == 255)// Wood Mask
					addObject(new Enemy(xx * 32, yy * 32 - 108, 127, 147, this, 1, 3, ObjectId.Enemy));
				if (red == 150 && green == 0 && blue == 255)// Stone Mask
					addObject(new Enemy(xx * 32, yy * 32 - 108, 127, 147, this, 2, 2, ObjectId.Enemy));
				if (red == 255 && green == 100 && blue == 0)// Stump
					addObject(new Enemy(xx * 32, yy * 32 - 93, 129, 130, this, 3, 1, ObjectId.Enemy));
				if (red == 0 && green == 255 && blue == 255) // Balrog Boss
					addObject(new Boss(xx * 32, yy * 32 - 300, 312, 304, this, ObjectId.Boss));

				// UTILITY (same format as Grass section above)
				if (red == 0 && green == 255 && blue == 0) // Enemy Block
					addObject(new EnemyBlock(xx * 32, yy * 32, 32, 32, ObjectId.EnemyBlock));
				if (red == 0 && green == 150 && blue == 150) // Portal
					addObject(new Portal(xx * 32, yy * 32 - 103, 104, 142, ObjectId.Portal));
				if (red == 0 && green == 0 && blue == 255) // Player
					addObject(new Player(xx * 32, yy * 32, 52, 82, this, scroll, ObjectId.Player));
				if (red == 143 && green == 58 && blue == 255)
					addObject(new Spike(xx * 32, yy * 32, 64, 32, ObjectId.Spike));
			}
		}
	}
}
