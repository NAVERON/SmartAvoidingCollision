
package smartcollision;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SmartCollision extends JFrame{
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SmartCollision smartCollision = new SmartCollision();
        
        Show show = new Show();
        show.repaint();//initialing
        
        //setup frame and add panel
        frame.setSize(1100, 730);
        frame.add(show);
        frame.setTitle("ONE STEP V2");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(!DataBase.begin){//end the game and clear panel
            while(!DataBase.clear){//reset new parameter
                while (!DataBase.pause){//for pause and rest for a minutes
                    show.repaint();
                    for(Ship b: DataBase.ships){
                        b.goAhead();
                    }
                    for(DyObstacle o : DataBase.obstacle){
                        o.goAhead();
                    }
                    
                    smartCollision.action();
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    LinkedList<DyObstacle> tt = new LinkedList<>();
    public void action(){
        //action to avoid collision
        for(Ship b: DataBase.ships){
            analyse(b);
//            for(int i=0;i<tt.size();i++)
//                System.out.println(tt.get(i));
        }
    }
    
    public void analyse(Ship ship){
        //store ship's distance
        for(DyObstacle o: DataBase.obstacle){
            double temp = distance(ship, o);
            if(temp<=200) tt.add(o);
        }
        if(tt.isEmpty())
            DataBase.distance.add(null);
        else
            DataBase.distance.add(tt);
    }
    
    public double distance(Ship ship, DyObstacle obstacle){
        double shipx = ship.getParameter(1);
        double shipy = ship.getParameter(2);
        double obstaclex = obstacle.getParameter(1);
        double obstacley = obstacle.getParameter(2);
        return Math.sqrt(Math.pow(obstaclex-shipx, 2)+Math.pow(obstaclex-shipx, 2));
    }
    
//    public class freshShow implements Runnable{
//        Thread freshThread;
//        
//        public freshShow() {
//            freshThread = new Thread(this, ""tt);
//            freshThread.start();
//        }
//        
//        @Override
//        public void run(){
//            while(!DataBase.begin){//begin flags
//                
//                show.repaint();
//                ship.goAhead();
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
    
    //problems
    //1. input start and end point has some bugs, need analyse words
    //2. 
}
