
package src;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * INITIAL VERSION 
 * 
 * @author Alberto Fernandez Saucedo
 */
public class GamePanel extends JPanel implements Runnable 
{
    // size of the panel
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    
    // for the animation
    private Thread thread;
    
    // stops the animation
    private volatile boolean running = false;
    
    // for game termination
    private volatile boolean gameOver = false;
    
    public GamePanel()
    {
        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }//end constructor
    
    // wait for the JPanel to be added to the JFrame/JApplet before starting.
    public void addNotify()
    {
        super.addNotify(); // creates the peer
        startGame(); // start the thread
        
    }//end addNotify
    
    // initialize and start the thread
    private void startGame()
    {
        if(thread == null || !running )
        {
            thread = new Thread(this);
            thread.start();
        }//end if
    }//end startGame
    
    // called to stop execution
    public void stopGame()
    {
        running = false;
    }//end stopGame
    
    @Override
    public void run() 
    {
        running = true;
        while(running)
        {
            // game state is updated
            gameUpdate();
            // render to a buffer
            gameRender();
            // paint with the buffer
            repaint();
            
            try
            {
                Thread.sleep(20); // sleep a bit
            }
            catch(InterruptedException ie){
                System.exit(0);
            }//end try-catch
        }//end while
    }//end run
    
    private void gameUpdate()
    {
        if(!gameOver)
        {
            // stub
        }
    }//end gameUpdate
    
    private void gameRender()
    {
        // stub
    }
    
}//end GamePanel
