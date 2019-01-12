/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create a boss that follows and attacks the player
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import window.Animation;
import window.Game;
import window.Handler;
import entity.GameObject;
import entity.ObjectId;
import entity.Texture;

public class Boss extends GameObject{
	
	private Animation move, attackA, die; // holds animations
	public static boolean dirLeft = true; // original direction facing is left
	private static boolean doAttack = false; // has not done special attack yet
	private long attackTime = 0; // initialized as 0 as enemy has not yet attacked 
	private boolean dead = false; // the enemy is alive
	public long timer; // timer accompanied with special attack delay initialized in GameObject
	
	private Handler handler;
	Texture tex; 
	
	public Boss(float x, float y, int length, int height, Handler handler, ObjectId id) {
		// float x - the x position of the Boss                                                    
		// float y - the y position of the Boss                                                    
		// int length - the length of the Boss                                                     
		// int height - the height of the Boss                                                     
		// Handler handler - reuse handler to link this object to others       
		// ObjectId id - the id of the Boss that allows the program to recognize the object type  
		super(x, y, length, height, id);
		this.handler = handler; // Reuses the same handler that manages all GameObjects
		tex = Game.getInstance(); // Reuses the already loaded textures that were loaded in the Game Class
		
		// Refer to Animation Class first integer refers to the speed of the animation, the other parameters send in the textures used for the animation
		move = new Animation(4, tex.balrog[0] , tex.balrog[1] , tex.balrog[2] , tex.balrog[3], tex.balrog[4], tex.balrog[5]); 
		attackA = new Animation (10, tex.balrog[6], tex.balrog[7], tex.balrog[8], tex.balrog[9], tex.balrog[10], tex.balrog[11], tex.balrog[12], tex.balrog[13]);
		die = new Animation (10, tex.balrog[17], tex.balrog[18], tex.balrog[19], tex.balrog[20], tex.balrog[21], tex.balrog[22], tex.balrog[23], tex.balrog[24], tex.balrog[25], tex.balrog[26]);
		
		// Initializes the health and attack damage of the boss
		health = 50;
		attack = 75;
	}

	/**
	 * Method responsible for updating the properties of the boss
	 */
	public void tick(LinkedList<GameObject> object) {		
		
		// Makes boss do its projectile attack every 5 seconds 
		if (System.currentTimeMillis() - attackTime > 5000) {
			attackTime = System.currentTimeMillis();
			doAttack = true; // indicates the boss is performing its attack animation
		}
		else if (attackA.isFinished()){  // When the attack animation is finished
			doAttack = false; // sets to false indicating boss is done performing its atatck animation
		}
		
		if (attackA.fire){ // fires a projectile when fire is true (when it is the second frame of the animation)
			handler.addObject((new BossProjectile(this.x, this.y, 69, 61, handler, ObjectId.BossP, dirLeft)));
		}
		
		if (!dead && !doAttack){ // If the boss is not dead and not attacking this is for movement
			
			for (int i = 0; i < handler.object.size(); i++) {
				
				GameObject tempObject = handler.object.get(i); // REMINDER: object is a LinkedList of GameObjects and .get retrieves the GameObject inside the index 
				
				if (tempObject.getId() == ObjectId.Player) { // Looks for GameObject with id of 'Player'
					// Moves towards players position
					if (x > tempObject.x - 100){
						x-= 1.5;
						dirLeft = true;
					}
					else if (x < tempObject.x){
						x+= 1.5;
						dirLeft = false;
					}
					
					if (y + 200 > tempObject.y){
						y-= 1;
					}
					else if (y + 200 < tempObject.y){
						y+= 1;
					}
				}
			}
		}
		
		if (health <= 0){ // When the health of the boss reaches to 0 or less runs the death animation and removes the boss from the game
			die.runAnimation(); // runs the death animation
			dead = true;
			//remove boss when dead
			if (die.isFinished()){
				handler.removeObject(this);
				JOptionPane.showMessageDialog(null, "YOU WIN! :)", "GAME OVER", JOptionPane.PLAIN_MESSAGE); // Displays a Dialog Box indicating the game is over
				System.exit(0); //close the program
				
			}
				
		}
		
		// runs bosses movement animation
		move.runAnimation();
	}
	
	/**
	 * Draws out the textures and animations of the boss object depending on the state
	 */
	public void render(Graphics g) {
		
		if (!dead){
			if (dirLeft){ // When facing left
				if (doAttack){ // Does attack animation when true (Refer to tick above) does every 5 seconds
					attackA.runAnimation();
					attackA.drawAnimation(g, (int) this.x, (int) this.y);
				}
				else // Movement animation
					move.drawAnimation(g, (int) this.x, (int) this.y);
			}
			else if (!dirLeft){ // When facing right
				if (doAttack){ // Does attack animation when true (Refer to tick above) does every 5 second
					attackA.runAnimation();
					attackA.drawAnimation(g, (int) this.x + 312, (int) this.y, -312, 311);
				}
				else // Movement animation
					move.drawAnimation(g, (int) this.x + 312, (int) this.y, -312, 304);
			}	
		}
		else{ // When dead draws death animation
			if (dirLeft){ 
				die.drawAnimation(g, (int) this.x+100, (int) this.y);
			}
			else {
				die.drawAnimation(g, (int) this.x + 260, (int) this.y, -160, 380);
			}
		}

		// displays health bar of the enemy
		healthBar (g);
	}
	
	/**
	 * Creates rectangles above the enemy that represents the health of the enemy
	 */
	private void healthBar (Graphics g){
		//health
		g.setColor(Color.black);
		g.fillRect ((int)x+130, (int) (y + 90), (int) 50, 10);
		g.setColor(Color.green);
		g.fillRect ((int)x+130, (int) (y + 90), (int) health, 10);
	}
	
	/**
	 * Creates a rectangle that represents the hitbox the enemy and used for collision
	 * @return rectangle of collision box for the boss
	 */
	public Rectangle getBounds() {
		if(dead) // removes the hitbox when the player is dead (so the player does not get hit)
			return new Rectangle (0, 0, 0, 0);
		return new Rectangle((int)x+length/6 + 58, (int)y+height/2 , (int)length/3, (int)height/2);
	}
	
}
