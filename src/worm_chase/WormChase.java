package worm_chase;

import java.awt.event.WindowEvent;

/**
 *
 * @author Alberto Fernandez Saucedo
 */
public class WormChase {
    
    public void setBoxNumber(int no)
    {
        jtfBox.setText("Boxes used: " + no);
    }//end setBoxNumber
    
    public void setTimeSpent(long time)
    {
        jtfTime.setText("Time spent: " + time + " secs");
    }//end setTimeSpent
    
    public void windowActivated(WindowEvent e)
    {
        wp.resumeGame();
    }//end windowActivated
    
    public void windowDeactivated(WindowEvent e)
    {
        wp.pauseGame();
    }//end windowDeactivated
    
    public void windowDeiconified(WindowEvent e)
    {
        wp.resumeGame();
    }//end windowDeiconified
    
    public void windowIconified(WindowEvent e)
    {
        wp.pauseGame();
    }//end windowIconified
    
    public void windowClosing(WindowEvent e)
    {
        wp.stopGame();
    }//end windowClosing
    
    public static void main(String[] args)
    {
        int fps = DEFAULT_FPS;
        if(args.length != 0)
            fps = Integer.parseInt(args[0]);
        
        long period = (long)1000.0 / fps;
        System.out.println("fps: " + fps + "; period: " + period + " ms");
        new WormChase(period * 1_000_000L); // ms --> nanosecs
    }//end main
}//end WormChase
