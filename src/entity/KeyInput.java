/* KeyAdapter Concept taken from youtube tutorial
 * Name: Anushan Vimalathasan, Samuel Chow
 * Date: January 23, 2017
 * Purpose: Allow user input for interaction with the player
 */

package entity;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import window.Handler;

public class KeyInput extends KeyAdapter {
	
	Handler handler;
	boolean leftPress = false, rightPress = false; //check if left or right key pressed
	
	// State constants for code clarity
	private static final int IDLE = 0; 
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int ATTACKING = 3;
	private static final int DAMAGED = 4;
	private static final int ATTACKING2 = 5;
	
	public KeyInput(Handler handler) {
		this.handler = handler; // reuses the already initialized handler from Game
	}

	//used when key is pressed
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode(); // Retrieves the key code of the key pressed
		
		// Exits Game when ESC key is pressed
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0); // exit game
		}
		
		// Looks for the player object and allows the user to move
		for (int i = 0; i < handler.object.size(); i++) { // IMPORTANT: handler.object is a linked list of GameObjects each GameObject has a id that lets the program know what type of object it is

			GameObject tempObject = handler.object.get(i); // retrieves the GameObject in the specific index 

			// If the current GameObjects id (type) is 'Player'
			if (tempObject.getId() == ObjectId.Player) { // Ensures the determined object is the Player
				
				if (key == KeyEvent.VK_LEFT && tempObject.finished && tempObject.state != ATTACKING && tempObject.state != DAMAGED) { // If the player presses left and is not attacking or damaged
					tempObject.state = WALKING; // changes to walking state in Player Class
					tempObject.dirLeft = true; // changes to true meaning is facing left direction
					tempObject.moving = true; // true = player is moving
					leftPress = true; // Used to check if left key or right key is pressed
					tempObject.velX = -5; // Changes player movement speed used to change the players X position
				}
				if (key == KeyEvent.VK_RIGHT && tempObject.finished && tempObject.state != ATTACKING && tempObject.state != DAMAGED) { // If the player presses right and is not attacking or damaged
					tempObject.state = WALKING; // Refer to above but for dirLeft it is false meaning it is facing right and velX is = 5
					tempObject.dirLeft = false;
					tempObject.moving = true;
					rightPress = true;
					tempObject.velX = 5;
				}
				if (key == KeyEvent.VK_C && tempObject.finished && (tempObject.state == JUMPING || tempObject.state == IDLE)){ // Changes to ATTACKING state when 'C' is pressed if not already attacking and only if the player is on state IDLE or JUMPING
					tempObject.state = ATTACKING;
				}
				if (key == KeyEvent.VK_Z && tempObject.finished && (tempObject.state == JUMPING || tempObject.state == IDLE) && tempObject.mana > 100){ // Changes to ATTACKING2 state if 'Z' is pressed if not already attacking, if the player is jumping or idle and if the player has enough mana
					tempObject.state = ATTACKING2;
				}
				if (key == KeyEvent.VK_UP){ // Tells the Player class that the up arrow key has been pressed for the interaction with the portal
					tempObject.pressedUp = true;
				}
				if (key == KeyEvent.VK_X && tempObject.state != ATTACKING && tempObject.jc < 2) { // Changes to jumping state if not ATTACKING and if the jc (jump count) does not exceed 2
					// Regular Jump
					if (tempObject.jc == 0) { // jc - jump count
						tempObject.jumping = true; // true = player is jumping
						tempObject.velY = -12; // Changes jump speed used to change to player's Y position 
						tempObject.state = JUMPING; // changes to jumping state in Player Class
					}
					// Double Jump
					if (tempObject.jc == 1 && tempObject.mana > 25) { // jc - jump count
						tempObject.mana -= 25; // reduces total mana 
						tempObject.jumpAnimation = true; // tells program to start the double jump animation
						if (tempObject.dirLeft) { // If facing left and double jump is cast
							tempObject.velX = -8;
							tempObject.velY = -5;
						} 
						else { // If facing right and double jump is cast
							tempObject.velX = 8;
							tempObject.velY = -5;
						}
					}
					tempObject.jc = tempObject.jc + 1; // increases jump count
				}
			}
		}
	}

	//used when key is released
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode(); // Gets Key Code of Key Pressed

		// Looks for the player object and stops movement once user releases key
		for (int i = 0; i < handler.object.size(); i++) { 
			
			GameObject tempObject = handler.object.get(i); // REMINDER: object is a LinkedList of GameObjects and .get retrieves the GameObject inside the index 
			
			if (tempObject.getId() == ObjectId.Player) { // Looks for the GameObject with the id of 'Player'
				
				if (key == KeyEvent.VK_LEFT) { // If the User releases the left arrow key and 
					leftPress = false; // indicates left key is been released
					if (!leftPress && !rightPress) { // if no key is pressed the Player stops moving
						tempObject.velX = 0; // Stops movement by decreasing
						tempObject.velX = 0; // Stops movement by decreasing speed to 0
						tempObject.state = IDLE; // Standing state
						tempObject.moving = false; // false - not moving
					}
				}
				if (key == KeyEvent.VK_RIGHT) {
					rightPress = false; // indicates the right key has been released
					if (!leftPress && !rightPress) { // if no key is pressed the Player stops moving
						tempObject.velX = 0; // Stops movement by decreasing speed to 0
						tempObject.state = IDLE; // Standing state
						tempObject.moving = false; // false - not moving
					}
				}
				if (key == KeyEvent.VK_UP){
					tempObject.pressedUp = false; // indicates the up arrow has been released
				}
			}
		}
	}

}
