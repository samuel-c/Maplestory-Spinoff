/* Name: Samuel Chow
 * Date: January 23, 2017
 * Purpose: Use to create and store animations
 */

package entity;

import java.awt.image.BufferedImage;

import window.BufferedImageLoader;

public class Texture {

	// Declares variables with 'SpriteSheet' type
	SpriteSheet ps, pps, pps2, bs, fj, es1 ,es2, es3, boss, boss2, boss3, boss4, p;
	
	// Single Non-animated Textures
	private BufferedImage spike, status;
	
	// BufferedImage array that is to hold each frame image of the animation
	public BufferedImage [] player = new BufferedImage[13];
	public BufferedImage [] projectile = new BufferedImage[9];
	public BufferedImage [] blocks = new BufferedImage [19];
	public BufferedImage [] flashjump = new BufferedImage [5];
	public BufferedImage [] enemy1 = new BufferedImage[16]; 
	public BufferedImage [] enemy2 = new BufferedImage[6];
	public BufferedImage [] enemy3 = new BufferedImage[6];
	public BufferedImage [] balrog = new BufferedImage [27];
	public BufferedImage [] utility = new BufferedImage [2];
	public BufferedImage [] portal = new BufferedImage [8];
	
	//load spritesheet images
	public Texture(){
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try{
			// Loading in Sprite Sheets for animation
			ps = new SpriteSheet(loader.loadImage("/player_sheet.png"));
			pps = new SpriteSheet(loader.loadImage("/playerProjectile.png"));
			pps2 = new SpriteSheet (loader.loadImage("/attacksheet2.png"));
			bs = new SpriteSheet(loader.loadImage("/blocks_sheet.png"));
			fj = new SpriteSheet (loader.loadImage("/fj_spritesheet.png"));
			es1 = new SpriteSheet (loader.loadImage("/enemy_sheet1.png"));
			es2 = new SpriteSheet (loader.loadImage("/enemy_sheet2.png"));
			es3 = new SpriteSheet (loader.loadImage("/enemy_sheet3.png"));
			boss = new SpriteSheet (loader.loadImage("/move.png"));
			boss2 = new SpriteSheet (loader.loadImage("/attack1.png"));
			boss3 = new SpriteSheet (loader.loadImage("/ballsheet.png"));
			boss4 = new SpriteSheet (loader.loadImage("/diesheet.png"));
			p = new SpriteSheet (loader.loadImage("/portal.png"));
			
			// Loading in single texture images
			status = loader.loadImage("/StatusBar.png");
			spike = loader.loadImage("/spike.png");
		}
		catch(Exception e){
			e.printStackTrace(); //error if picture not found
		}
		
		getTextures();
	}
	
	/**
	 * Initializes each index of the designated array's with the individual frame images given the preset locations
	 */
	private void getTextures(){
	// Utility Textures
		utility[0] = spike; // Spike
		utility[1] = status; // Status Bar
		
		// Portal
		portal[0] = p.grabImage(1, 1, 104, 142);
		portal[1] = p.grabImage(2, 1, 104, 142);
		portal[2] = p.grabImage(3, 1, 104, 142);
		portal[3] = p.grabImage(4, 1, 104, 142);
		portal[4] = p.grabImage(5, 1, 104, 142);
		portal[5] = p.grabImage(6, 1, 104, 142);
		portal[6] = p.grabImage(7, 1, 104, 142);
		portal[7] = p.grabImage(8, 1, 104, 142);
		
	// Player Textures
		// IDLE
		player[0] = ps.grabImage(1, 3, 52, 81); // Idle Animation Frame 1
		player[1] = ps.grabImage(2, 3, 52, 81); // Idle Animation Frame 2
		player[2] = ps.grabImage(3, 3, 52, 81); // Idle Animation Frame 3
		player[3] = ps.grabImage(4, 3, 52, 81); // Idle Animation Frame 4
		player[4] = ps.grabImage(5, 3, 52, 81); // Idle Animation Frame 5

		// WALK
		player[5] = ps.grabImage(1, 4, 52, 81); // Walk Animation Frame 1 
		player[6] = ps.grabImage(2, 4, 52, 81); // Walk Animation Frame 2 
		player[7] = ps.grabImage(3, 4, 52, 81); // Walk Animation Frame 3 
		player[8] = ps.grabImage(4, 4, 52, 81); // Walk Animation Frame 4 
		
		// JUMP
		player[9] = ps.grabImage(1, 2, 52, 81); // Jump
		
		// ATTACK
		player[10] = ps.grabImage(1, 1, 80, 83); // Attack Animation Frame 1  
		player[11] = ps.grabImage(2, 1, 80, 83); // Attack Animation Frame 2  
		player[12] = ps.grabImage(3, 1, 80, 83); // Attack Animation Frame 3  
		
		// DOUBLE JUMP ANIMATION
		flashjump[0] = fj.grabImage(1, 1, 195, 94); // Double Jump Animation Frame 1   
		flashjump[1] = fj.grabImage(2, 1, 195, 94); // Double Jump Animation Frame 2   
		flashjump[2] = fj.grabImage(3, 1, 195, 94); // Double Jump Animation Frame 3   
		flashjump[3] = fj.grabImage(4, 1, 195, 94); // Double Jump Animation Frame 4   
		flashjump[4] = fj.grabImage(5, 1, 195, 94); // Double Jump Animation Frame 5   
	
		
		
	//Player Projectile Textures
		// BASIC PROJECTILE
		projectile[0] = pps.grabImage(1, 1, 48, 9); // Player Animation Projectile Frame 1  
		projectile[1] = pps.grabImage(2, 1, 48, 9); // Player Animation Projectile Frame 2  
		
		// SKILL ANIMATION PROJECTILE
		projectile[2] = pps2.grabImage(1, 1, 585, 317); // Player Animation Projectile #2 Frame 1   
		projectile[3] = pps2.grabImage(2, 1, 585, 317); // Player Animation Projectile #2 Frame 2   
		projectile[4] = pps2.grabImage(3, 1, 585, 317); // Player Animation Projectile #2 Frame 3   
		projectile[5] = pps2.grabImage(4, 1, 585, 317); // Player Animation Projectile #2 Frame 4   
		projectile[6] = pps2.grabImage(5, 1, 585, 317); // Player Animation Projectile #2 Frame 5   
		projectile[7] = pps2.grabImage(6, 1, 585, 317); // Player Animation Projectile #2 Frame 6
		projectile[8] = pps2.grabImage(7, 1, 585, 317); // Player Animation Projectile #2 Frame 7
		
	// Enemy Textures
		// WOODEN MASK
		enemy1[0] = es1.grabImage (1,1,137,157); // Wooden Mask Animation Frame 1
		enemy1[1] = es1.grabImage (2,1,137,157); // Wooden Mask Animation Frame 2
		enemy1[2] = es1.grabImage (3,1,137,157); // Wooden Mask Animation Frame 3
		enemy1[3] = es1.grabImage (4,1,137,157); // Wooden Mask Animation Frame 4
		enemy1[4] = es1.grabImage (5,1,137,157); // Wooden Mask Animation Frame 5
		enemy1[5] = es1.grabImage (6,1,137,157); // Wooden Mask Animation Frame 6
		enemy1[6] = es1.grabImage (7,1,137,157); // Wooden Mask Animation Frame 7
		enemy1[7] = es1.grabImage (8,1,137,157); // Wooden Mask Animation Frame 8
		
		// STONE MASK
		enemy1[8] = es1.grabImage (1,2,137,157);  // Wooden Mask Animation Frame 1
		enemy1[9] = es1.grabImage (2,2,137,157);  // Wooden Mask Animation Frame 2 
		enemy1[10] = es1.grabImage (3,2,137,157); // Wooden Mask Animation Frame 3 
		enemy1[11] = es1.grabImage (4,2,137,157); // Wooden Mask Animation Frame 4
		enemy1[12] = es1.grabImage (5,2,137,157); // Wooden Mask Animation Frame 5
		enemy1[13] = es1.grabImage (6,2,137,157); // Wooden Mask Animation Frame 6
		enemy1[14] = es1.grabImage (7,2,137,157); // Wooden Mask Animation Frame 7
		enemy1[15] = es1.grabImage (8,2,137,157); // Wooden Mask Animation Frame 8
        
		// STUMP
		enemy2[0] = es2.grabImage (1,1,129,130); // Stump Animation Frame 1
		enemy2[1] = es2.grabImage (2,1,129,130); // Stump Animation Frame 2
		enemy2[2] = es2.grabImage (3,1,129,130); // Stump Animation Frame 3
		enemy2[3] = es2.grabImage (4,1,129,130); // Stump Animation Frame 4
		enemy2[4] = es2.grabImage (5,1,129,130); // Stump Animation Frame 5
		enemy2[5] = es2.grabImage (6,1,129,130); // Stump Animation Frame 6
		
		// BAT
		enemy3[0] = es3.grabImage (1,1,94,110); // Bat Animation Frame 1
		enemy3[1] = es3.grabImage (2,1,94,110); // Bat Animation Frame 2
		enemy3[2] = es3.grabImage (3,1,94,110); // Bat Animation Frame 3
		enemy3[3] = es3.grabImage (4,1,94,110); // Bat Animation Frame 4
		enemy3[4] = es3.grabImage (5,1,94,110); // Bat Animation Frame 5
		enemy3[5] = es3.grabImage (6,1,94,110); // Bat Animation Frame 6
		
	// Tile Textures
		// GRASS
		blocks[0] = bs.grabImage(3,1,32,32); // Left Curve Edge Grass  
		blocks[1] = bs.grabImage(4,3,32,32); // Right Curve Edge Grass
		blocks[2] = bs.grabImage(1,1,32,32); // Left End Grass 
		blocks[3] = bs.grabImage(4,3,32,32); // Right End Grass
		blocks[4] = bs.grabImage(3,3,32,32); // Grass Normal
		
		// DIRT
		blocks[5] = bs.grabImage(2,1,32,32); // Left Side Curve Dirt 
		blocks[6] = bs.grabImage(5,1,32,32); // Right Side Curve Dirt
		blocks[7] = bs.grabImage(4,1,32,32); // Left Bottom Dirt  
		blocks[8] = bs.grabImage(4,2,32,32); // Right Bottom Dirt  

		blocks[9] = bs.grabImage(1,4,32,32);  // Left Dirt 
		blocks[10] = bs.grabImage(3,4,32,32); // Right Dirt
		blocks[11] = bs.grabImage(2,4,32,32); // Middle Dirt
		blocks[12] = bs.grabImage(5,2,32,32); // Bottom Dirt 
		
		// PLATFORMS
		blocks[13] = bs.grabImage(1,2,32,32); // Left Platform
		blocks[14] = bs.grabImage(2,2,32,32); // Mid Platform
		blocks[15] = bs.grabImage(3,2,32,32); // Right Platform
		
		// WATER
		blocks[16] = bs.grabImage(1,3,32,32); // Water 1
		blocks[17] = bs.grabImage(2,3,32,32); // Water 2
		
	//BALROG (BOSS) Textures
		// FLYING
		balrog[0] = boss.grabImage(1, 1, 312, 304); // Balrog Fly Animation Frame 1
		balrog[1] = boss.grabImage(4, 1, 312, 304); // Balrog Fly Animation Frame 2
		balrog[2] = boss.grabImage(3, 1, 312, 304); // Balrog Fly Animation Frame 3
		balrog[3] = boss.grabImage(2, 1, 312, 304); // Balrog Fly Animation Frame 4
		balrog[4] = boss.grabImage(3, 1, 312, 304); // Balrog Fly Animation Frame 5
		balrog[5] = boss.grabImage(4, 1, 312, 304); // Balrog Fly Animation Frame 6
		
		// ATTACK
		balrog[6] = boss2.grabImage(1, 1, 312, 311);  // Balrog Attack Animation Frame 1
		balrog[7] = boss2.grabImage(2, 1, 312, 311);  // Balrog Attack Animation Frame 2
		balrog[8] = boss2.grabImage(3, 1, 312, 311);  // Balrog Attack Animation Frame 3
		balrog[9] = boss2.grabImage(4, 1, 312, 311);  // Balrog Attack Animation Frame 4
		balrog[10] = boss2.grabImage(5, 1, 312, 311); // Balrog Attack Animation Frame 5
		balrog[11] = boss2.grabImage(6, 1, 312, 311); // Balrog Attack Animation Frame 6
		balrog[12] = boss2.grabImage(7, 1, 312, 311); // Balrog Attack Animation Frame 7
		balrog[13] = boss2.grabImage(8, 1, 312, 311); // Balrog Attack Animation Frame 8
		
		// BOSS PROJECTILE
		balrog[14] = boss3.grabImage(1, 1, 69, 61); // Balrog Projectile Animation Frame 1
		balrog[15] = boss3.grabImage(2, 1, 69, 61); // Balrog Projectile Animation Frame 2
		balrog[16] = boss3.grabImage(3, 1, 69, 61); // Balrog Projectile Animation Frame 3
		
		// DEATH ANIMATION
		balrog[17] = boss4.grabImage(1, 1, 160, 380);  // Balrog Death Animation Frame 1
		balrog[18] = boss4.grabImage(2, 1, 160, 380);  // Balrog Death Animation Frame 2
		balrog[19] = boss4.grabImage(3, 1, 160, 380);  // Balrog Death Animation Frame 3
		balrog[20] = boss4.grabImage(4, 1, 160, 380);  // Balrog Death Animation Frame 4
		balrog[21] = boss4.grabImage(5, 1, 160, 380);  // Balrog Death Animation Frame 5
		balrog[22] = boss4.grabImage(6, 1, 160, 380);  // Balrog Death Animation Frame 6
		balrog[23] = boss4.grabImage(7, 1, 160, 380);  // Balrog Death Animation Frame 7
		balrog[24] = boss4.grabImage(8, 1, 160, 380);  // Balrog Death Animation Frame 8
		balrog[25] = boss4.grabImage(9, 1, 160, 380);  // Balrog Death Animation Frame 9
		balrog[26] = boss4.grabImage(10, 1, 160, 380); // Balrog Death Animation Frame 10
		
	}

}
