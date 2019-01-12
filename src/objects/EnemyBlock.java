/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create a block that restricts enemy movement
 */

package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import entity.GameObject;
import entity.ObjectId;

public class EnemyBlock extends GameObject {

	public EnemyBlock(float x, float y, int length, int height, ObjectId id) {
		// float x - the x position of the EnemyBlock                                                    
		// float y - the y position of the EnemyBlock                                                    
		// int length - the length of the EnemyBlock                                                     
		// int height - the height of the EnemyBlock                                                                            
		// ObjectId id - the id of the EnemyBlock that allows the program to recognize the object type   
		super(x, y, length, height, id);
	}

	
	/** Must be implemented, does nothing as EnemyBlock is stationary */
	public void tick(LinkedList<GameObject> object) {
	}

	/** Must be implemented, does nothing as EnemyBlock is invisible (no rendering required)*/
	public void render(Graphics g) {
	}

	/** Get collision box of the enemy block
	 * @param none
	 * @return rectangle for collision box of the enemy block
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, length, height);
	}

}
