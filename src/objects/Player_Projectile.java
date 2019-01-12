/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Create projectile for main character attack
 */

package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entity.GameObject;
import entity.ObjectId;
import entity.Texture;
import window.Animation;
import window.Game;
import window.Handler;

public class Player_Projectile extends GameObject {

	private Animation projectileFired; //animation used for projectile

	Texture tex;
	Handler handler;
	float initX; //initial x position
	final float ATTACK_RANGE = 600; //max distance the projectile can travel

	public Player_Projectile(float x, float y, int length, int height, Handler handler, ObjectId id, boolean DirLeft) {
		// float x - the x position of the Player_Projectile                                                    
		// float y - the y position of the Player_Projectile                                                    
		// int length - the length of the Player_Projectile                                                     
		// int height - the height of the Player_Projectile   
		// Handler handler - reuse handler to link objects together
		// ObjectId id - the id of the Player_Projectile that allows the program to recognize the object type   
		// boolean DirLeft - true if facing left, false if facing right
		super(x, y + 50, length, height, id);
		
		tex = Game.getInstance(); //used to get access to textures  
		this.handler = handler; //link this object to others by reusing handler
		
		initX = x; //initial x position of the projectile
		dirLeft = DirLeft; //direction that projectile is facing (true = left, false = right)
		
		//set velocity to negative or positive depending on direction
		if (dirLeft == true) {
			this.velX = -10;
		} else {
			this.velX = 10;
		}
		projectileFired = new Animation(5, tex.projectile[0], tex.projectile[1]); //add frames to animation
	}

	/** Update the position of the projectile
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {
		x += velX; //increase position by velocity 
		projectileFired.runAnimation(); //run the projectile animation

		//remove the projectile when it travels farther than the attack range
		if (x > initX + ATTACK_RANGE || x < initX - ATTACK_RANGE) {
			handler.removeObject(this);
		}

		Collision(object); //check collision with other objects
	}

	/** Check collision between this object and other objects
	 * @param object - list of objects
	 * @return none
	 */
	private void Collision(LinkedList<GameObject> object) {
		
		for (int i = 0; i < handler.object.size(); i++) { //check each object in the handler
			GameObject tempObject = handler.object.get(i); //create temp object as the current object in loop
			if ((tempObject.getId() == ObjectId.Enemy || tempObject.getId() == ObjectId.Boss)) { //check collision with enemy or boss
				if (getBounds().intersects(tempObject.getBounds())) { 
					handler.removeObject(this); //remove projectile on hit
					tempObject.health -= 1; //decrease health of enemy or boss
					if (tempObject.getId() == ObjectId.Boss) {
						//apply slight knockback if hitting boss
						if (dirLeft)
							tempObject.x -= 10;
						else
							tempObject.x += 10;
					}
				}
			}
		}
	}

	/** Renders the object
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		
		//draw animation depending on which direction projectile is facing
		if (dirLeft == true) {
			projectileFired.drawAnimation(g, (int) this.x, (int) this.y);
		} else if (dirLeft == false) {
			projectileFired.drawAnimation(g, (int) this.x + 48, (int) this.y, -48, 9);
		}
	}

	/** Gets the collision box of the projectile
	 * @param none
	 * @return rectangle of hitbox for projectile
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) x + 20, (int) y, 8, 9); //
	}

}
