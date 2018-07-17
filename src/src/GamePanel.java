
package src;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * INITIAL VERSION 
 * GamePanel acts as a fixed size white canvas, embedded inside a JFrame in 
 * applications or inside JApplets in applets. 
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
            
    // global variables for off-screen rendering
    private Graphics dbg;
    private Image dbImage;
    
    
    
    public GamePanel()
    {
        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus(); // JPanel now receives key events
        readyForTermination();
        
        // create game components
        // ...
        
        // listen for mouse presses
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e)
            {
                testPress(e.getX(), e.getY());
            }
        });
    }//end constructor
    
    private void readyForTermination()
    {
        addKeyListener(new KeyAdapter(){
           //listen for esc, q, end, ctrl-c
            public void keyPressed(KeyEvent e)
            {
                int keyCode = e.getKeyCode();
                if((keyCode == KeyEvent.VK_ESCAPE) ||
                    (keyCode == KeyEvent.VK_Q) ||
                    (keyCode == KeyEvent.VK_END) ||
                        ((keyCode == KeyEvent.VK_C) && e.isControlDown())){
                    running = false;
                }
            }
        });
    }//end readyForTermination
    
    private void testPress(int x, int y)
    {
        if(!gameOver){
            // do something
        }
    }//end testPress
    
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
    
    // Repeatedly: update, render, sleep so loop takes close to period nsecs.
    // Sleep inaccuracies are handled. The timing calculation use the Java 3D
    // timer.
    @Override
    public void run() 
    {
        
        
        long beforeTime, timeDiff, sleepTime;
        int period = 10;
        
        beforeTime = System.currentTimeMillis();
        
        running = true;
        while(running)
        {
            // game state is updated
            gameUpdate();
            // render to a buffer
            gameRender();
            // draw buffer to screen
            paintScreen();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = period - timeDiff;
            
            if(sleepTime <= 0)
                sleepTime = 5;
            
            try
            {
                Thread.sleep(20); // sleep a bit
            }
            catch(InterruptedException ie){
                System.exit(0);
            }//end try-catch
            
            beforeTime = System.currentTimeMillis();
        }//end while
    }//end run
    
    private void paintScreen()
    {
        // actively render the buffer image to the screen 
        Graphics g;
        try{
            g = this.getGraphics(); // get the panel's graphic context
            if((g != null) && (dbImage != null))
                g.drawImage(dbImage, 0, 0, null);
            Toolkit.getDefaultToolkit().sync(); // sync the display on some systems
            g.dispose();
        }
        catch(Exception e){
            System.out.println("Graphics context error: " + e);
        }
    }//end paintScreen
    
    private void gameUpdate()
    {
        if(!gameOver)
        {
            // stub
        }
    }//end gameUpdate
    
    // draws the current frame to an image buffer
    private void gameRender()
    {
        // create the buffer
        if(dbImage == null)
        {
            dbImage = createImage(WIDTH, HEIGHT);
        }
        if(dbImage == null)
        {
            System.out.println("dbImage is null");
            return;
        }
        else
        {
            dbg = dbImage.getGraphics();
        }
        
        // clear the background
        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);
        
        // draw game elements
        // ...
        
        if(gameOver)
            gameOverMessage(dbg);
    }//end gameRender
    
    private void gameOverMessage(Graphics g)
    {
        // center the game-over message
        String msg = "";
        int x = 0, y = 0;
        g.drawString(msg, x, y);
    }// end gameOverMessage
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(dbImage != null)
            g.drawImage(dbImage, 0, 0, null);
    }//end paintComponent
    
}//end GamePanel
