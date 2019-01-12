/* Thread concept taken from youtube tutorial
 * Name: Anushan Vimalathasan
 * Date: January 23, 2017
 * Purpose: Create frames and windows to run the game
 */

package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import entity.KeyInput;
import entity.ObjectId;
import entity.Texture; //import from entity package in same project


public class Game extends Canvas implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L; //used to verify if all classes are loaded properly

	//variables used for the thread
	public static boolean running = false; 
	private Thread thread;

	//width and height of the window
	public static int WIDTH, HEIGHT;
	
	public static int LEVEL = 1; //keeps track of the current level
	
	//image for the background
	private BufferedImage background = null;
	
	static Handler handler; //create from handler class
	Scrolling scroll; //scrolling background
	static Texture tex; //used to get animations from texture class in entity
	
	//GUI
	JButton start, instr, ok, next; //buttons
	JLabel label, logoL; //images for gui
	JFrame frame = new JFrame("MapleStory Main Menu"); //main menu frame
	JFrame frame2 = new JFrame("Instructions"); //instructions frame
	JFrame frame3 = new JFrame ("Instructions Part 2"); //instructions part 2 frame
	Image waterfall; //waterfall image
	
	public Game(int width, int height){
		
		//get images for the UI
		BufferedImageLoader loader = new BufferedImageLoader(); //create loader from buffered image loader
		BufferedImage login = loader.loadImage("/loginScreen.png"); // load background for menu
		BufferedImage logo = loader.loadImage("/maplelogo.png"); // load maplestory logo
		BufferedImage startbutton = loader.loadImage("/startbutton.png"); // load start button image
		BufferedImage instrbutton = loader.loadImage("/instrbutton.png"); //load instruction button image
		BufferedImage instructions = loader.loadImage("/instuctions.png"); //load instructions page 1
		BufferedImage instructions2 = loader.loadImage("/instructions2.png"); //load instructions page 2
		BufferedImage nextI = loader.loadImage("/nextbutton.png"); //load next button image
		BufferedImage okI = loader.loadImage("/okbutton.png"); //load ok button image
		
		//FRAME 1 (MAIN MENU)
		//concept taken from http://stackoverflow.com/questions/4898584/java-using-an-image-as-a-button
		//create start button image
		start = new JButton(new ImageIcon(startbutton));
		start.setBorder(BorderFactory.createEmptyBorder());
		start.setContentAreaFilled(false);
		//create instruction button image
		instr = new JButton(new ImageIcon(instrbutton));
		instr.setBorder(BorderFactory.createEmptyBorder());
		instr.setContentAreaFilled(false);
		
		//create logo image in label
		logoL = new JLabel (new ImageIcon(logo));
		//create background image
		label = new JLabel(new ImageIcon(login));
		frame.setSize(width, height);
		frame.setBackground(Color.black);
		
		//sets the background as the label
		frame.setContentPane(label);
		
		//set bounds for each UI component
		logoL.setBounds(230, 50, 379, 139);
		start.setBounds(292, 200, 216, 59);
		instr.setBounds(292, 265, 216, 59);
		
		//add components to the main menu frame
		frame.add(start);
		frame.add(instr);
		frame.add(logoL);
		
		frame.setVisible(true); //make the main frame visible
		
		//FRAME 2 (INSTRUCTIONS)
		frame2.setSize(width, height+30);
		frame2.setContentPane(new JLabel(new ImageIcon(instructions)));
		//create instruction button image
		next = new JButton(new ImageIcon(nextI));
		next.setBorder(BorderFactory.createEmptyBorder());
		next.setContentAreaFilled(false);
		//create instruction button image
		ok = new JButton(new ImageIcon(okI));
		ok.setBorder(BorderFactory.createEmptyBorder());
		ok.setContentAreaFilled(false);
		
		next.setBounds (500, 450, 104, 39); //set bounds for the button
		
		//add butons to the frame
		frame2.add(next);
		frame2.add(ok);
		
		//FRAME 3 (INSTRUCTIONS PART 2)
		frame3.setSize(width, height);
		frame3.setContentPane(new JLabel (new ImageIcon(instructions2)));
	
		ok.setBounds(580, 510, 103, 46); //set bounds for the button
		frame3.add(ok); //add the button to frame 3
		
		//make buttons interactive
		start.addActionListener(this);
		instr.addActionListener(this);
		next.addActionListener(this);
		ok.addActionListener(this);
				
		//set window to center screeen
		frame.setLocationRelativeTo(null);
		frame2.setLocationRelativeTo(null);
		frame3.setLocationRelativeTo(null);
	}
	
	public Game() {
		// Empty constructor required to call game from Window class
	}

	/**Initializes the variables and images required for the game
	 * @param none
	 * @return none
	 */
	private void init(){
		
		// Gets Dimensions of Window
		WIDTH = getWidth();
		HEIGHT = getHeight() + 30;
		
		tex = new Texture(); //used to access textures from Texture class
		
		//Load the level
		BufferedImageLoader loader = new BufferedImageLoader();
		background = loader.loadImage("/background.jpg"); // loads background
		waterfall = loader.loadImage("/background_animation.gif"); //loads the waterfall image
		
		scroll = new Scrolling(0,0); //set default values for the background scroll
		handler = new Handler(scroll); //this allows the x values to be connected to the map instead of the window
		
		// Keyboard input
		this.addKeyListener(new KeyInput(handler));
		
	}
	
	
	/**Return tex so textures can be accesed by different classes
	 * @param Does not have any parameters
	 * @return Does not return a value
	 */
	public static Texture getInstance(){
		return tex; //used to get textures in other classes
	}
	
	public static void main (String args[]){
		// load the GUI with size 800 * 600
		new Game(800, 600);
	}

	/** Allow interactions between button and user */
	public void actionPerformed(ActionEvent event) {
		
		//start game when start button is pressed
		if (event.getSource() == start) {
			frame.setVisible(false); //hide main menu window
			new Window(800, 768, "MapleStory", new Game()); //create game window
		}
		//show instructions page when instructions button clicked
		if (event.getSource() == instr){
			frame2.setVisible(true); //show instructions window
			frame.setVisible(false); //hide main menu window
		}
		//return to main menu when ok button pressed
		if (event.getSource() == ok){
			frame.setVisible(true); //show main menu
			frame3.setVisible(false); //hide instructions page 2
		}
		//go to instructions page 2 from page 1
		if (event.getSource() == next){
			frame3.setVisible(true); //show instructions page 2
			frame2.setVisible(false); //hide instructions page 1
		}
	}

	// ******** FROM TUTORIAL THIS POINT ON ********* \\
	
	// Starts the game
		public synchronized void start(){
			// Safety if somehow runs twice
			if (running)
				return;
			running = true; //set running to true to run the thread
			//create and start thread
			thread = new Thread(this); 
			thread.start();
		}
		
		/** Runs the thread */
		public void run(){
			init(); //initialize all variables
			this.requestFocus(); //set focus to window
			long lastTime = System.nanoTime(); //time to find when to tick
			double amountOfTicks = 60.0; //sets the amount of ticks in a second
			double ns = 1000000000 / amountOfTicks; //nanoseconds per tick
			double delta = 0; //difference in time
			while(running){
				long now = System.nanoTime(); //current time
				delta += (now - lastTime) / ns; //formula to find value of delta
				lastTime = now; //reset previous time
				//runs every time delta reaches 1
				while(delta >= 1){
					delta--; //reset delta
					tick(); //tick all objects
					render(); //render all objects
				}	
			}
		}
		
		/** updates all objects from handler and scrolling
		 * @param none
		 * @return does not return a value
		 */
		private void tick(){
			handler.tick(); //call handler tick to tick all objects added to handler
			
			//tick the scrolling with the values of the player so that the background can scroll
			for (int i = 0; i < handler.object.size(); i++){
				if(handler.object.get(i).getId() == ObjectId.Player){
					scroll.tick(handler.object.get(i)); //call the tick from scrolling so the background scrolls
				}
			}
			
		}
		
		/** Renders images for graphics
		 * @param none
		 * @return does not return a value
		 */
		private void render(){
			// Buffers a image so that preloads next 'n' number of images depending on computer speed
			BufferStrategy buff = this.getBufferStrategy();
			if (buff == null){ 
				this.createBufferStrategy(3); // sets max # of buffers
				return;
			}
			
			Graphics g = buff.getDrawGraphics();
			Graphics2D g2d = (Graphics2D) g;
			// Game Graphics here
			
			//fill background
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g2d.translate(scroll.x, scroll.y); //keep x and y values consistent with scrolling
			
			g.drawImage(background, 0, 0, this); //draw the background
			g.drawImage(waterfall,2950,377, this); //draw the waterfall
			
			handler.render(g); //render all objects added to the handler
			
			g.dispose(); //refresh graphics
			buff.show(); //show all the buffered frames
		}
}
