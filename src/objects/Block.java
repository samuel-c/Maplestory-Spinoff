/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Create a block for the player to stand on
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entity.GameObject;
import entity.ObjectId;
import entity.Texture;
import window.Game;

public class Block extends GameObject {

	// Reuses the already initialized textures from the Game Class
	Texture tex = Game.getInstance();
	
	// The 'type' variable influences the texture of the block
	private int type;
	
	public Block(float x, float y, int length, int height, int type, ObjectId id) {
		// float x - the x position of the block                                                    
		// float y - the y position of the block                                                    
		// int length - the length of the block                                                     
		// int height - the height of the block                                                     
		// int type - the type of the block that influences the block texture                       
		// ObjectId id - the id of the block that allows the program to recognize the object type   
		super(x, y, length, height, id);
		this.type = type; 
	}

	// Implemented Unused Method from interface class 'GameObject'
	public void tick(LinkedList<GameObject> object) {
		
	}

	
	/**
	 * Method responsible for drawing/displaying the textures of the object 
	 */
	public void render(Graphics g) {
		//draw the block that corresponds to the type in the constructor
		g.setColor(Color.white);
		g.drawImage(tex.blocks[type],(int) x, (int) y, length, height, null);
	}

	/**
	 * Creates a rectangle that represents the hitbox of the block (used for collision)
	 * @return rectangle of collision box for the block
	 */
	public Rectangle getBounds(){
		//collision box for the block
		return new Rectangle ((int) x, (int) y, length, height);
	}

}
