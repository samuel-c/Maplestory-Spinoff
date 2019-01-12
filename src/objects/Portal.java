/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Create portal that allows player to go to next level
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

public class Portal extends GameObject {

	Texture tex;
	Animation portal;
	
	public Portal(float x, float y, int length, int height, ObjectId id) {
		// float x - the x position of the portal                                                    
		// float y - the y position of the portal                                                    
		// int length - the length of the portal                                                     
		// int height - the height of the portal                                                                            
		// ObjectId id - the id of the portal that allows the program to recognize the object type   
		super(x, y, length, height, id);
		tex = Game.getInstance(); //allow access to textures
		portal = new Animation(7, tex.portal[0], tex.portal[1], tex.portal[2], tex.portal[3], tex.portal[4], tex.portal[5], tex.portal[6], tex.portal[7]); //create animation for portal
	}
	
	/** Run the animation for portal
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {
		portal.runAnimation(); //run portal animation
	}
	
	/**Render the portal animation 
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		portal.drawAnimation(g, (int) x, (int) y, length, height); //draw animation of portal
	}
	
	/** Returns rectangle around portal for collision
	 * @param none
	 * @return returns rectangle collision box
	 */
	public Rectangle getBounds() {
			return new Rectangle ((int)x + 20,(int)y + 50,length - 40,height - 50); //collision of portal
	}

}

