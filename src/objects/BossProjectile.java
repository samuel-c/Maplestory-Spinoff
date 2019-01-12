/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create a projectile that the boss shoots at the player
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

public class BossProjectile extends GameObject {

	private Animation projectileFired;

	Texture tex;
	Handler handler;
	float initX; // original x position of the projectile used to calculate the distance traveled 
	final float ATTACK_RANGE = 500; // distance the projectile travels

	public BossProjectile(float x, float y, int length, int height, Handler handler, ObjectId id, boolean DirLeft) {
		// float x - the x position of the BossProjectile                                                    
		// float y - the y position of the BossProjectile                                                    
		// int length - the length of the BossProjectile                                                     
		// int height - the height of the BossProjectile                                                     
		// int type - the type of the BossProjectile that influences the BossProjectile texture                       
		// ObjectId id - the id of the BossProjectile that allows the program to recognize the object type  
		// boolean DirLeft - the direction the Object is facing (true = left, false = right)
		super(x, y + 220, length, height, id);
		dirLeft = DirLeft;
		attack = 100; // sets the attack damage value of the object
		initX = x; // holds the initial X position where it was cast so the program knows when it travels x distance then removes it
		tex = Game.getInstance(); // Reuses the already initialized textures from the Game Class
		
		if (dirLeft == true) { // initializes its X velocity depending on the direction the boss was facing when casting the ability
			this.velX = -3;
		} else {
			this.velX = 3;
		}
		
		this.handler = handler; // Reuses the handler that manages all GameObjects
		projectileFired = new Animation(3, tex.balrog[14], tex.balrog[15], tex.balrog[16]); // Initializes the animation (refer to boss for further understanding)
	}

	/**
	 * Method responsible for updating the properties of the boss projectile
	 * @param LinkedList<GameObject> object - the list of Game Objects from the handler class
	 */
	public void tick(LinkedList<GameObject> object) {
		x += velX; // changes x position depending on the speed
		projectileFired.runAnimation(); // Runs the projectile's animation

		if (x > initX + ATTACK_RANGE || x < initX - ATTACK_RANGE) { // removes object when it travels 'ATTACK_RANGE' distance
			handler.removeObject(this);
		}
	}

	/**
	 * Method responsible for drawing/displaying the textures of the object
	 * @param Graphics g - graphics
	 */
	public void render(Graphics g) {
		// TODO Auto-generated method stub

		if (dirLeft) { // if the projectile is facing left draws the animation facing left
			projectileFired.drawAnimation(g, (int) this.x, (int) this.y);
		} else { // if the projectile is facing right draws the animation facing right
			projectileFired.drawAnimation(g, (int) this.x + 314, (int) this.y, -69, 61);
		}

	}

	/**
	 * Creates a rectangle used for collision
	 * @return rectangle of collision box for the projectile
	 */
	public Rectangle getBounds() {
		if (!dirLeft) // if the projectile is facing right draws a hitbox accordingly
			return new Rectangle((int) x + 255, (int) y + 10, 49, 41);
		// if the projectile is facing left draws a hitbox accordingly
		return new Rectangle((int) x + 10, (int) y + 10, 49, 41);
	}

}
