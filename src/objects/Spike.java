/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Create spike object that is obstacle to player
 */

package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import window.Game;
import entity.GameObject;
import entity.ObjectId;
import entity.Texture;

public class Spike extends GameObject{

	Texture tex;
	
	public Spike(float x, float y, int length, int height, ObjectId id) {
		// float x - the x position of the spike                                                    
		// float y - the y position of the spike                                                    
		// int length - the length of the spike                                                     
		// int height - the height of the spike                                                                          
		// ObjectId id - the id of the spike that allows the program to recognize the object type   
		super(x, y, length, height, id);
		tex = Game.getInstance(); //allow textures to be used

		this.attack = 50; //damage of spike
	}

	/**No values change, tick does nto need any statements
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {
	}

	/**Render the image of the spike 
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		g.drawImage(tex.utility[0],(int) x,(int) y, length, height, null);
	}
	
	/** Returns rectangle around spike for collision
	 * @param none
	 * @return returns rectangle collision box
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x+10, (int)y , (int)length-20, (int)height-5); //collision box for spike
	}

	
}
