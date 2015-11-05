
package smartcollision;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SmartCollision extends JFrame{
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        Show show = new Show();
        show.repaint();//initialing
        
        //setup frame and add panel
        frame.setSize(0x400, 0x2da);
        frame.add(show);
        frame.setTitle("ONE STEP V2");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(!DataBase.begin){//end the game and clear panel
            while(!DataBase.pause){//for pause and rest for a minutes
                while (!DataBase.fresh) {//beginfresh graphics and repaint
                    show.repaint();
                    for(Ship b: DataBase.ships){
                        b.goAhead();
                    }
                    for(DyObstacle o : DataBase.obstacle){
                        o.goAhead();
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void action(){
        //action to avoid collision
    }
    
//    //for try
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
}
