
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
                    
                    smartCollision.Action();
                    
                    try {
                        Thread.sleep(100);
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
    
    LinkedList<DyObstacle> tt = new LinkedList<>();
    public void Action(){
        //action to avoid collision
        
    }
    
    public void stored(){//先判断方位，后判断距离，按船舶尺度划分，
        //danger area add to arealist
        for(int i =0 ;i < DataBase.ships.size();i++){
            double tx = DataBase.ships.get(i).getParameter(1);
            double ty = DataBase.ships.get(i).getParameter(2);
            for(DyObstacle o : DataBase.obstacle){
                if(Math.abs(tx-o.getParameter(1)) <200&&Math.abs(ty-o.getParameter(2))<200)
                    DataBase.area.get(i).add(o);
            }
        }
        //situation danger, vector store
        DyObstacle o;
        for(int i = 0;i < DataBase.area.size();i++){
            for(int j =0;j < DataBase.area.get(i).size();j++){
                o = DataBase.area.get(i).get(j);
                
            }
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
    
    //problems
    //1. input start and end point has some bugs, need analyse words
    //2. 
}
