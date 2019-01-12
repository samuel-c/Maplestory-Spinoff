/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Used to load images
 */

package window;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image; //the image to be loaded
	
	public BufferedImage loadImage(String path){
		
		try {
			image = ImageIO.read(getClass().getResource(path)); //load the image given path
		//if image not found
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image; //return loaded image
	}

	
}
