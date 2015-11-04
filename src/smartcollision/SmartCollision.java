
package smartcollision;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SmartCollision extends JFrame{
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Ship ship =new Ship(DataBase.defaultx, DataBase.defaulty, 
                DataBase.defaults, DataBase.defaultc);
        DyObstacle obstacle = new DyObstacle(DataBase.defaultx, DataBase.defaulty,
                DataBase.defaults, DataBase.defaultc);
        
        Show show = new Show(ship, obstacle);
        show.repaint();
        
        frame.setSize(0x400, 0x2da);
        frame.add(show);
        frame.setTitle("ONE STEP V2");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while (true) { //temp for debug and has a direct view          
            show.repaint();
            ship.goAhead();
            for(DyObstacle o : DataBase.obstacle){
                o.goAhead();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    public class freshshow implements Runnable{

        Thread freshThread;
        public freshshow() {
            
        }
        
        @Override
        public void run(){
            
        }
    }
}
