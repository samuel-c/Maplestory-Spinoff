/* Concept taken from youtube tutorial
 * Variables added by both Samuel Chow and Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create an abstract class for all GameObjects to use
 */

package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject {

	// All Object Variables used for movement, textures, damage, health, and delay
	protected ObjectId id; // object type
	public float x; // x position
	public float y; // y position
	public int length, height; 
	public float velX = 0; // X velocity (movement speed)
	public float velY = 0; // Y velocity (jumping)
	public float health; 
	public float mana; 
	protected boolean falling = true; // when the player spawns they fall
	protected boolean jumping = false; 
	protected boolean dirLeft = false; // original direction facing is left
	protected boolean finished = true; // detects when animation is finished
	protected boolean moving = false; // when player moves
	protected boolean jumpAnimation = false; // when the double jump animation is needed
	protected int state; // the state of the object (movement, jumping, attacking)
	protected int jc = 0; // jump count used for double jump
	protected boolean pressedUp; // holds if player presses up
	public float attack; // the damage the objects do
	public long timer; // used for delay

	public GameObject(float x, float y, int length, int height, ObjectId id) {
		// sets the original delay to 0 because player has not used a ability yet
		this.timer = 0;
		this.x = x; // X position of the object
		this.y = y; // Y position of the object
		this.id = id; // The type of object 
		this.length = length; // The height of object
		this.height = height; // The width of object
	}

	// ABSTRACT METHODS (general purpose in each object)
	
	/** updates all objects called from the Handler Class tick method */
	public abstract void tick(LinkedList<GameObject> object); 

	/** redraws all images of the objects called from the Handler Class render method */
	public abstract void render(Graphics g); 

	/** draws a rectangle used for collision between objects */
	public abstract Rectangle getBounds(); 

	/** Returns the Object Id of the object */
	public ObjectId getId() {
		return id;
	}

}
