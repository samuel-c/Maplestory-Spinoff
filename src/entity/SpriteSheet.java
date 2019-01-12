/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Get subimages from a spritesheet
 */

package entity;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image; // holds the spritesheet image

	public SpriteSheet(BufferedImage image) {
		this.image = image; // when a sprite sheet is initialized from the Texture class, the class stores the whole sprite sheet image
	}

	/**
	 * Called from the texture class, when grab image is called the SpriteClass splits a portion of the whole sprite sheet image depending on the 
	 * 		col, row, width and height assuming all separate images are the same size
	 * @param int col - indicates the column of the image
	 * @param int row - indicates the row of the separate images in the sprite sheet
	 * @param int width - indicates the width of the separate images in the sprite sheet
	 * @param int height - indicates the width of the separate images in the sprite sheet
	 * @return BufferedImage img - returns the split image
	 */
	public BufferedImage grabImage(int col, int row, int width, int height) {
		// image.getSubimage (int x, int y, int w, int h)
		// "(col * width) - width" determines the top left x position of the image
		// "(row * height) - height" determines the top left y position of the image
		BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
		return img;
	}

}
