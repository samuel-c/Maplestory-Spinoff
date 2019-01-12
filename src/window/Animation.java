/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Used to draw animations from sprite sheets
 */

package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int speed; //speed of animation
	private int frames; //frames in animation
	
	boolean finished = false; //determines if animation is completed
	public boolean fire = false; //when to launch projectile from attack animation
	public int index = 0; //increases every tick
	public int count = 0; //current frame
	
	private BufferedImage [] images; // # of images
	private BufferedImage currentImg; // Current Image to display
	
	// Infinite # of parameters of buffered image
	public Animation (int speed, BufferedImage ... args){
		this.speed = speed; //set speed of the animation
		this.finished = false; //set animation to unfinished when it starts
		images = new BufferedImage[args.length];
		for (int i = 0; i < args.length; i ++){
			images[i] = args[i]; // adds textures to images array
		}
		frames = args.length; // sets # of frames
		
	}
	
	/** Runs the animation
	 * @param none
	 * @return none
	 */
	public void runAnimation (){
		//change finish and fire to false until animation complete
		finished = false; 
		fire = false; 
		
		index++; //increase index every tick
		if (index > speed){
			index = 0; //reset index
			nextFrame(); //increase frame every speed ticks
		}
	}
	
	/** Draws next frame of animation
	 * @param none
	 * @return none
	 */
	private void nextFrame(){
		for (int i = 0; i < frames; i++){
			if (count == i) //set current image to the frame needed
				currentImg = images[i]; // currentImg - image to be displayed
		}
		count ++; //increase count (frame number)
		//fire projectile on frame 2
		if (count == 2)
			fire = true;
		//end animation when count is > amount of frames
		if (count > frames){
			finished = true;
			count = 0;
		}
	}
	
	/** check if animation is finished
	 * @param none
	 * @return finished; true if animation complete, false if animation incomplete
	 */
	public boolean isFinished(){
		return this.finished; //used to check if animation has completed last frame
	}
	
	/** change finished from other classes
	 * @param a - true for finished, false for not finished
	 * @return none
	 */
	public void setFinished(boolean a){
		this.finished = a; 
	}
	
	/** Draw current frame of animation
	 * @param g - graphics
	 * @param x - x position
	 * @param y - y position
	 * @return none
	 */
	public void drawAnimation(Graphics g, int x, int y){
		g.drawImage(currentImg,x,y,null); //draw the current frame in the animation
	}
	
	/** Draw current frame of animation (overloaded method if size needs to be changed)
	 * @param g - graphics
	 * @param x - x position
	 * @param y - y position
	 * @param scaleX - new width
	 * @param scaleY - new height
	 * @return none
	 */
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
		g.drawImage(currentImg, x, y, scaleX,scaleY,null); //draw current frame in animation with new size
	}
}
