/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create and animate the special attack of the player
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

public class Player_Projectile2 extends GameObject {

	private long damagedDelay = 1000; //used so that projectile does not do continuous damage
	private Animation projectileFired; //projectile animation 
	Texture tex; 
	Handler handler; //link to other objects
	float initX; //initial x position of projectile
	
	public Player_Projectile2 (float x, float y, int length, int height, Handler handler, ObjectId id, boolean DirLeft) {
		// float x - the x position of the Player_Projectile2                                                    
		// float y - the y position of the Player_Projectile2                                                    
		// int length - the length of the Player_Projectile2                                                     
		// int height - the height of the Player_Projectile2     
		// Handler handler - reuse handler to link with other objects
		// ObjectId id - the id of the Player_Projectile2 that allows the program to recognize the object type   
		// boolean DirLeft - true if facing left, false if facing right
		super(x, y-220, length, height, id);
		
		tex = Game.getInstance(); //allow access to all textures
		this.handler = handler; //reuse handler 
		
		dirLeft = DirLeft; //direction that projectile is facing
		initX = x; //initial x position of the projectile
		
		projectileFired = new Animation(5, tex.projectile[2], tex.projectile[3], tex.projectile[4], tex.projectile[5], tex.projectile[6], tex.projectile[7], tex.projectile[8]); //create animation for projectile

	}

	/** Run the animation for special attack
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {
		projectileFired.runAnimation();
		//remove the projectile when animation complete
		if (projectileFired.isFinished()){
			handler.removeObject(this);
		}
		
		Collision(object); //check collision with other objects
	}
	
	/** Check collision to apply damage
	 * @param list of objects
	 * @return none
	 */
	private void Collision(LinkedList <GameObject> object){
	
		for (int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			//tempObject.hit = false;
			if ((tempObject.getId() == ObjectId.Enemy || tempObject.getId() == ObjectId.Boss) && System.currentTimeMillis() - tempObject.timer > damagedDelay) {
				
				tempObject.timer = System.currentTimeMillis();
				//tempObject.hit = true;
				if (getBounds().intersects(tempObject.getBounds())) {
					tempObject.health -= 2;
					if (tempObject.getId() == ObjectId.Boss){
						tempObject.y -= 50;
						if (dirLeft)
							tempObject.x -= 50;
						else 
							tempObject.x += 50;
					}
					
				}
			}
		}
	}
	
	/** render the animation for special attack
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		//draw the animation of the special attack onto the screen
		if (dirLeft){ //facing left
			projectileFired.drawAnimation(g, (int) this.x-485, (int) this.y);
		}
		else{ //facing right
			projectileFired.drawAnimation(g, (int) this.x+565, (int) this.y, -585, 317);
		}
	}

	/** Get the collision box of the special attack
	 * @param none
	 * @return rectangle with hitbox of special attack
	 */
	public Rectangle getBounds() {
		if (!dirLeft) //facing right
			return new Rectangle ((int)x+100,(int)y+50, 365, 217);
		return new Rectangle ((int)x-385,(int)y+50, 385, 217);//facing left
	
	}
}

