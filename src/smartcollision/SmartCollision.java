
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
        //show.repaint();//initialing
        
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
                    //analyse
                    smartCollision.Action();
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SmartCollision.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    public void Action(){
        //action to avoid collision
        
    }
    
    LinkedList<DyObstacle> tt = new LinkedList<>();
    public void stored(){
        //danger area add to arealist
        for(Ship boat: DataBase.ships){//analyse danger links of every ship
            double bx = boat.getParameter(1);
            double by = boat.getParameter(2);
            for(DyObstacle obs : DataBase.obstacle){
                if(Math.abs(bx-obs.getParameter(1)) <200 && Math.abs(by-obs.getParameter(2))<200)
                    boat.dangerList.add(obs);
            }
            //situation danger, vector store, select frome arealist
            
        }
        
        //test and remove safe obstacle
        
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
    
}
