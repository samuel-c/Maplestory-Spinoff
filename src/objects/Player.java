/* Name: Anushan Vimalathasan, Samuel Chow
 * Date: January 23, 2017
 * Purpose: Create and animate the player that moves with player input
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import entity.GameObject;
import entity.ObjectId;
import entity.Texture;
import window.Animation;
import window.Game;
import window.Handler;
import window.Scrolling;

public class Player extends GameObject{

	// Character Properties
	private float gravity = 0.5f;
	private final float MAX_SPEED = 7;

	//all animations required for player
	private Animation playerWalk;
	private Animation playerAttack;
	private Animation playerAttack2;
	private Animation flashJump;
	private Animation playerIdle;
	
	//all of the player states
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int ATTACKING = 3;
	private static final int DAMAGED = 4;
	private static final int ATTACKING2 = 5;
			
	//used to add delay on damage
	private long touchTime = 0;
	private int damagedDelay = 750; //0.75 seconds of delay between taking damage
	
	private Scrolling scroll; //for scrolling background
	Texture tex;
	private Handler handler; //create handler to link objects
	
	
	public Player(float x, float y, int length, int height, Handler handler, Scrolling scroll, ObjectId id) {
		// float x - the x position of the player                                                    
		// float y - the y position of the player                                                    
		// int length - the length of the player                                                     
		// int height - the height of the player   
		// Handler handler - reuses handler to link objects
		// ObjectId id - the id of the player that allows the program to recognize the object type   
		super(x, y, length, height, id);
		tex  = Game.getInstance(); //allow access to textures
		health = 600; //set intial health of player to 600
		mana = 600; //set initial mana of player to 600
		this.handler = handler; //reuse handler to link objects
		this.scroll = scroll; //sync with scrolling background

		pressedUp = false; //used for portal
		
		//initialize all animations with the frames from texture
		playerIdle = new Animation(7, tex.player[0], tex.player[1] , tex.player[2], tex.player[3], tex.player[4]);
		playerWalk = new Animation (7, tex.player[5], tex.player[6] , tex.player[7], tex.player[8]);
		playerAttack = new Animation (7, tex.player[10], tex.player[11], tex.player[12]);
		playerAttack2 = new Animation (10, tex.player[10], tex.player[11], tex.player[12]);
		flashJump = new Animation(2, tex.flashjump[0] , tex.flashjump[1], tex.flashjump[2], tex.flashjump[3], tex.flashjump[4]);
	}

	/** Update properties of player
	 * @param list of objects
	 * @return none
	 */
	public void tick(LinkedList<GameObject> object) {

		//regenerate 0.2 mana per tick
		if (mana < 600)
			mana += 0.2;
		
		//apply gravity when not grounded
		if(falling || jumping){
			velY += gravity;
			//put a cap on the velocity so player does not speed up too much
			if (velY > MAX_SPEED)
				velY = MAX_SPEED;
		}
		
		//end game when health is <= 0
		if (health <= 0){
			JOptionPane.showMessageDialog(null, "YOU LOSE! :(", "GAME OVER", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		//change state to jumping when player is falling while not attacking
		if (state != JUMPING && state != ATTACKING && state != ATTACKING2 && falling){
			state = JUMPING;
		}
		
		//change state to walking when landing and moving
		if (state == JUMPING && !jumping && moving){
			state = WALKING; //change state to walking	

			//reset velocity to normal when landing from double jump
			if (this.jc == 2){
				if (dirLeft)
					velX = -5;
				else
					velX = 5;
			}
			
			this.jc = 0; // reset jump count
		}
		
		//reset state to idle when character lands
		else if (!jumping && state == JUMPING){
			this.state = IDLE; 
			//reset velocity when landing from double jump
			if (this.jc == 2)
				velX = 0; 
			
			//reset jump count
			this.jc = 0;
			
		}
		
		//keep x velocity as 0 whenever in idle
		if (state == IDLE && velX != 0){
			velX = 0;
		}
		
		//change velX whenever the player is walking 
		if (state == WALKING && velX != 5 && !dirLeft ){ //walking right
			velX = 5;
		}
		else if (state == WALKING && velX != -5 && dirLeft ){ //walking left
			velX = -5;
		}
		
		//reset the frame count on the playerAttack2 animation
		if (!playerAttack2.isFinished()){
			if (state != ATTACKING2)
				playerAttack2.count = 0;
		}
		
		//change x and y values by the x and y velocity
		x += velX;
		y += velY;
		
		Collision (object); //check all collisions
		
		//run all animations other than attacks
		playerWalk.runAnimation();
		flashJump.runAnimation();
		playerIdle.runAnimation();
	}

	/** Check collisions between player and other objects
	 * @param object - list of all objects
	 * @return none
	 */
	private void Collision(LinkedList <GameObject> object){
		
		//check each object under the list
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i); //create tempObject for checking
			
			//collision with portal
			if (tempObject.getId() == ObjectId.Portal && pressedUp){ //activate when up arrow pressed
			    if (getBounds().intersects(tempObject.getBounds()) && pressedUp){ //when colliding
			    	
			    	//next level when all monsters are killed
			    	if (Enemy.getMonsterCount() <= 0){
			    		pressedUp = false; 
			    		JOptionPane.showMessageDialog(null, "Press OK to go to the next level.", "Portal Activated", JOptionPane.PLAIN_MESSAGE);
			    		handler.switchLevel(); //change level
			    	}
			    	//display message when monsters still alive
			    	else if (Enemy.getMonsterCount() > 0){
			    		pressedUp = false;
			    		JOptionPane.showMessageDialog(null, "A mysterious force does not let you pass, there are " + Enemy.getMonsterCount() + " monsters remaining.", "Portal Activated", JOptionPane.PLAIN_MESSAGE);
			    	}
			    }
			}

			// collision with blocks
			if (tempObject.getId() == ObjectId.Block) {
				// Top Collision
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.y + tempObject.height; //do not allow player to pass
					velY = 0; //player cannot move up
				} 
				else {
					falling = true;  //make player fall (to apply gravity)
				}

				// Bottom Collision
				if (getBounds().intersects(tempObject.getBounds())) {
					y = tempObject.y - height + 2; //do not allow player to pass
					velY = 0; //player does not move vertically
					
					//player no longer falling or jumping when touching ground
					falling = false; 
					jumping = false;
					
					jumpAnimation = false;//end double jump animation

				}

				// Right Collision
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.x - length; //do not allow player to pass
					//reset velocity to normal when colliding while double jumping
					if (jumpAnimation){
						if (dirLeft)
							velX = -5;
						else if (!dirLeft)
							velX = +5; 
					}
				}
				// Left Collision (refer to right collision section above for comments)
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = tempObject.x + 32;
					
					if (jumpAnimation){
						if (dirLeft)
							velX = -5;
						else if (!dirLeft)
							velX = +5; 
					}
				}
			}

			//Collision with enemies and boss 
			if (tempObject.getId() == ObjectId.Enemy || tempObject.getId() == ObjectId.Boss || tempObject.getId() == ObjectId.BossP) {
				// Right Collision
				if (getBoundsRight().intersects(tempObject.getBounds()) && System.currentTimeMillis() - touchTime > damagedDelay) { //can only be attacked 0.75 secs
					
					touchTime = System.currentTimeMillis(); //change touch time for delay
					
					//apply knockback
					velX = -6;
					velY = -3;
					
					state = DAMAGED; //change state with enemy collision
					//change jumping and falling to true because there is vertical knockback
					jumping = true;
					falling = true;
					
					//decrease health depending on enemy attack
					health -= tempObject.attack;
		
					jumpAnimation = false; //cancel double jump animation on collision
					
					//remove the boss projectile when it touches the player
					if (tempObject.getId() == ObjectId.BossP)
						handler.removeObject(tempObject);
				}

				// Left Collision (refer to right collision above for commenting)
				if (getBoundsLeft().intersects(tempObject.getBounds())
						&& System.currentTimeMillis() - touchTime > damagedDelay) {

					touchTime = System.currentTimeMillis();
					velX = 6;
					velY = -3;
					state = DAMAGED;
					jumping = true;
					falling = true;
					health -= tempObject.attack;
					
					jumpAnimation = false;
					
					//remove the boss projectile
					if (tempObject.getId() == ObjectId.BossP)
						handler.removeObject(tempObject);
				}
			}
			
			//Collision with the spike
			if (tempObject.getId() == ObjectId.Spike){
				if (getBounds().intersects(tempObject.getBounds())){
					
					//reset position of player to beginning of map
					if (Game.LEVEL == 1 || Game.LEVEL == 3){ //spawn point in 1 and 3 is same
					x = 224;
					y = 320;
					}
					else if (Game.LEVEL == 2){ //level 2 has different spawn point
						x = 60;
						y = 640;
					}
				
					scroll.x = 0; //reset the background scrolling
					health -= tempObject.attack; //decrease health by spike's attack
				}
			}
		}
	}
	
	/** Paints the player onto the game window
	 * @param graphics
	 * @return none
	 */
	public void render(Graphics g) {
		
		//draw the walking animation when player state is walking
		if (state == WALKING) {
			if (dirLeft == true) { //facing left
				playerWalk.drawAnimation(g, (int) x, (int) y, 52, 81);
			} else { //facing right
				playerWalk.drawAnimation(g, (int) x + 52, (int) y, -52, 81);
			}
		} 
		
		//draw the jumping animation when player is jumping or damaged
		else if (state == JUMPING || state == DAMAGED) {
			if (dirLeft == true) { //left
				g.drawImage(tex.player[9], (int) x, (int) y, 52, 79, null);
			} else { //right
				g.drawImage(tex.player[9], (int) x + 52, (int) y, -52, 79, null);
			}
		} 
		
		//draw the attacking animation when player attacks
		else if (state == ATTACKING) {
			if (dirLeft == true) { //left
				
				//draw and run animation of attack
				playerAttack.runAnimation();
				playerAttack.drawAnimation(g, (int) x - 28, (int) y, 80, 83);

			} else { //right
				playerAttack.runAnimation();
				playerAttack.drawAnimation(g, (int) x + 80, (int) y, -80, 83);
			}
			if (playerAttack.fire) { //add the projectile to the handler when the attack is on frame 2
				handler.addObject((new Player_Projectile(this.x, this.y, 48, 9, handler, ObjectId.PlayerP, dirLeft)));
			}
			//set state to idle when player is finished attacking
			if (playerAttack.isFinished()) {
				finished = true;
				state = IDLE;
			}
		} 
		//Draw attack animation for second attack
		else if (state == ATTACKING2){
			if (dirLeft == true) { //facing left
				//draw and run special attack animation
				playerAttack2.runAnimation();
				playerAttack2.drawAnimation(g, (int) x - 28, (int) y, 80, 83);

			} else { //facing right
				playerAttack2.runAnimation();
				playerAttack2.drawAnimation(g, (int) x + 80, (int) y, -80, 83);
			}
			
			//draw special attack when attack animation is on frame 2
			if (playerAttack2.fire) {
				mana -= 100; //decrease mana count on fire
				handler.addObject((new Player_Projectile2(this.x, this.y, 545, 317, handler, ObjectId.PlayerP2, dirLeft))); //add special attack to handler for rendering and collision
			}
			
			//reset state to idle when special attack is finished
			if (playerAttack2.isFinished()) {
				finished = true;
				state = IDLE;
				playerAttack2.count = 0; //reset the frame count on attack animation
			}
			
			
		} 
		
		//draw idle animation when player is in idle state
		else if (state == IDLE) {
			if (dirLeft == true) { //facing left
				playerIdle.drawAnimation(g, (int) x, (int) y, 52, 81);
			} 
			else { //facing right
				playerIdle.drawAnimation(g, (int) x + 52, (int) y, -52, 81);
			}
		}
		
		//draw the double jump animation whenever the player double jumps
		if (jumpAnimation){
			if (dirLeft == true) { //facing left
				flashJump.drawAnimation(g, (int) x, (int) y, 195, 94);
			} 
			else { //facing right
				flashJump.drawAnimation(g, (int) x+ 85, (int) y, -195, 94);
			}
		}

		statusBar (g); //render the health and mana bar of the player at the top of the screen
	}
	
	/** Renders the status bar of the player
	 * @param g - graphics
	 * @return none
	 */
	private void statusBar (Graphics g){
		float barX = 0; //x position of status bar
		barX = -1*(-x + Game.WIDTH/2 - 100) + 100; //formula to center health bar in relation to player x position
		
		//restrict movement of health bar so it does not scroll off screen when player reaches edge
		if (Game.LEVEL == 1) { //level 1
			if (barX < 110)
				barX = 110;
			else if (barX > 2776)
				barX = 2776;
		}
		else if (Game.LEVEL == 2){ //level 2
			if (barX < 110)
				barX = 110;
			else if (barX > 1071)
				barX = 1071;
		}
		else if (Game.LEVEL == 3){ //level 3
			if (barX < 110)
				barX = 110;
			else if (barX > 1720)
				barX = 1720;
		}
		
		//draw the empty status bar
		g.drawImage(tex.utility[1], (int)barX, 20, 600, 51, null);
		//draw the health bar
		g.setColor(Color.red);
		g.fillRect ((int)barX, 21, (int) health, 24); //draw health with health length
		//draw the mana bar
		g.setColor(Color.blue);
		g.fillRect ((int)barX, 46, (int) mana, 24); //draw mana with mana length
	}
	
	/** Gets the rectangle for player body collision
	 * @param none
	 * @return returns collision rectangle of player body
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) ((int)x + (length/2) - ((length/2)/2)), (int) ((int)y + (height/2) - 2), (int)length/2, (int)height/2);
	}
	
	/** Gets the rectangle for player head collision
	 * @param none
	 * @return returns collision rectangle of player head
	 */
	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int)x + (length/2) - ((length/2)/2)), (int)y, (int)length/2, (int)height/2);
	}
	
	/** Gets the rectangle for player left collision
	 * @param none
	 * @return returns collision rectangle of left side of player
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y + 5, (int)5, (int)height - 10);
	}
	
	/** Gets the rectangle for player right collision
	 * @param none
	 * @return returns collision rectangle of right side of player
	 */
	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int)x + length -5), (int)y + 5, (int)5, (int)height - 10);
	}
	
}
