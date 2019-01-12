/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create an enemy that can damage the player
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import window.Animation;
import window.Game;
import window.Handler;
import entity.GameObject;
import entity.ObjectId;
import entity.Texture;

public class Enemy extends GameObject{

	Animation enemy; //create new animation
	Texture tex; //allow access to textures
	int type; //each monster has its own type
	
	private Handler handler;
	
	//constants used for enemy type
	private static final int WOODMASK = 1;
	private static final int STONEMASK = 2;
	private static final int STUMP = 3;
	private static final int BAT = 4;
	
	
	private static int monsterCount = 0; //used to store number of monsters on field
	int maxhealth; //used for health bar
			
	public Enemy(float x, float y, int length, int height, Handler handler, int type, int vel, ObjectId id) {
		super(x, y, length, height, id);
		tex = Game.getInstance(); //allow access to textures
		monsterCount++; //increase monstercount whenever object created
		this.handler = handler; //reuse handler to link this object to the rest 
		this.velX = vel; //set speed of the monster
		this.type = type; //set the type for different types of monsters
	
		//randomly set the direction that the monster is facing
		if ((int)(Math.random()*2+1) == 1)
			velX *= -1;
		
		//set the properties of the monsnter depending on which type it is
		if (type == WOODMASK){
			this.enemy = new Animation(7, tex.enemy1[0] , tex.enemy1[1], tex.enemy1[2], tex.enemy1[3], tex.enemy1[4] , tex.enemy1[5], tex.enemy1[6], tex.enemy1[7]); //create animation for this type of monster
			health = 3; //set the health of the monster depending on which type it is
			attack = 30; //set the damage that the enemy deals to the player
		}
		else if (type == STONEMASK){
			this.enemy = new Animation(7, tex.enemy1[8] , tex.enemy1[9], tex.enemy1[10], tex.enemy1[11], tex.enemy1[12] , tex.enemy1[13], tex.enemy1[14], tex.enemy1[15]);
			health = 4;
			attack = 60;
		}
		else if (type == STUMP){
			this.enemy = new Animation(7, tex.enemy2[0] , tex.enemy2[1], tex.enemy2[2], tex.enemy2[3], tex.enemy2[4] , tex.enemy2[5]);
			health = 5;
			attack = 100;
		}
		else if (type == BAT){
			this.enemy = new Animation(7, tex.enemy3[0] , tex.enemy3[1], tex.enemy3[2], tex.enemy3[3], tex.enemy3[4] , tex.enemy3[5]);
			health = 2;
			attack = 20;
		}	
		
		maxhealth = (int) health; //max health used to draw health bars
	}

	/** Update properties of the enemy to allow movement
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {
		x += this.velX;	//change the x position based on velocity	
		Collision (object); //check collisions
		
		//remove monster when health reaches 0
		if (health <= 0){
			handler.removeObject(this); //remove enemy
			monsterCount--; //decrease monster count
		}	
		enemy.runAnimation(); //run the animation of the enemy
	}

	/** Check collisions with other objects
	 * @param object - list of objects
	 * @return none
	 */
	private void Collision(LinkedList <GameObject> object){
		for (int i = 0; i < handler.object.size(); i++) { //check each object in list
			GameObject tempObject = handler.object.get(i); //create a temp object for current object in list
			//check collision between enemy block
			if (tempObject.getId() == ObjectId.EnemyBlock) {
				if (getBounds().intersects(tempObject.getBounds())) {
					velX*=-1; //change direction when colliding with an enemy block
				}
			}	
		}
	}
	
	/** Paint the enemies onto the game window
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		//draw the animation of the enemy depending on direction that it is facing
		if (this.velX < 0)
			enemy.drawAnimation(g,(int)x, (int)y,  length, height);
		else if (this.velX > 0)
			enemy.drawAnimation(g,(int)x + length, (int)y,  -length, height);
			
		healthBar (g); //render the health bar
	}

	/** Paint the health bar of the monster onto the game window
	 * @param g - graphics
	 * @return none
	 */
	private void healthBar (Graphics g){
		//draw a black bar for the max health of the monster
		g.setColor(Color.black);
		g.fillRect ((int)x+50, (int) (y -10), (int) maxhealth*10, 10);
		
		//draw a green bar over the black bar to show remaining health
		g.setColor(Color.green);
		g.fillRect ((int)x+50, (int) (y -10), (int) health*10, 10);
	}
	
	/** Get the amount of monsters remaining
	 * @param none
	 * @return number of monsters remaining
	 */
	public static int getMonsterCount(){
		return monsterCount;
	}
	
	/** Get the collision box of the enemy
	 * @param none
	 * @return the rectangle for the enemy collision
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x+10, (int)y , (int)length-20, (int)height-5);
	}
}
