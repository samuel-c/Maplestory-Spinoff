/* Name: Anushan Vimalathasan
 * Date: January 23, 2017 
 * Purpose: Create the window that the game will run on
 */

package window;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {

	public Window(int w, int h, String title, Game game){
		//Set up the window
		game.setPreferredSize(new Dimension(w,h));
		game.setMaximumSize(new Dimension(w,h));
		game.setMinimumSize(new Dimension(w,h));
		
		//create the frame for the game
		JFrame frame = new JFrame(title);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit program on close
		frame.setResizable(false); //fixed window size
		frame.setVisible(true); //show the frame
		
		
		frame.add(game); //add the game object to the window for rendering
		frame.pack();
		frame.setLocationRelativeTo(null); //center window on screen
		
		game.start(); //starts the game
	}
}
