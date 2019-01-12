/* Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create the scrolling background that scrolls in relation to character
 */

package window;

import entity.GameObject;

public class Scrolling {
	
	//create coordinate values
	public float x;
	public float y;
	
	public Scrolling (float xPot, float yPot){
		//change values of x and y when class called
		x = xPot; 
		y = yPot;
	}
	
	/**Change x and y values of background every tick in relation to player positon*/
	public void tick(GameObject player){
		
		//LEVEL 1
		if (Game.LEVEL == 1){
			if (player.x < 2985 && player.x > 310){ //restrictions on level 1
				x = -player.x + Game.WIDTH/2 - 100; //scroll background
			}
		}
		//LEVEL 2
		else if (Game.LEVEL == 2){
			if (player.x < 1280 && player.x > 310){ //restrictions on level 2
				x = -player.x + Game.WIDTH/2 - 100; //scroll background
			}
		}
		//LEVEL 3
		else if (Game.LEVEL == 3){
			if (player.x < 1926 && player.x > 310){ //restrictions on level 3
				x = -player.x + Game.WIDTH/2 - 100; //scroll background
			}
		}
	}
	
	
}