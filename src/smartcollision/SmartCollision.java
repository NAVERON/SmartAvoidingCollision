
package smartcollision;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SmartCollision extends JFrame{
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Show show = new Show();
        SmartCollision smartCollision = new SmartCollision();
        //initialing
        
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
    
    //DataBase db= new DataBase();
    
    public void Action(){
        //action to avoid collision
        analyse();
        //Action and reset
        for(Ship dyship: DataBase.ships){
            if(dyship.Action == 3){
                dyship.giveValue(4, dyship.getParameter(4)+2);
                dyship.Action = 0;
            }
        }
        remove();
    }
    
    public void analyse(){//need return flag signal
        //danger area add to arealist
        for(Ship boat: DataBase.ships){//distance analyse
            for(Ship ship: DataBase.ships){
                if(boat!=ship){
                    if(Math.abs(boat.getParameter(1)-ship.getParameter(1))<100&&
                            Math.abs(boat.getParameter(2)-ship.getParameter(2))<100){
                        boat.dangerList.add(ship);
                    }
                }
            }//analyse every ships and add to danger list
            for(int i = 0;i<boat.dangerList.size();i++){
                Ship ship = boat.dangerList.get(i);
                double boatx = boat.getParameter(1);//anaship information
                double boaty = boat.getParameter(2);
                double bc = boat.getParameter(4);
                //get relation position
                double shipx = ship.getParameter(1);//other ship information
                double shipy = ship.getParameter(2);
                double rc = DataBase.CaculateRatio(boatx, boaty, shipx, shipy);
                double rp = rc - bc;//relation position
                if(rp>180) rp = rp - 360;//port - // starboard + //limit 0-180
                if(rp<-180) rp = 360 + rp;
                boat.sychronize.add(rp);
                System.out.println(rp);
                if(rp>0) boat.Action =3;
            }
            
        }
        
    }
    
    public void remove(){
        //remove safe objects
        for(Ship boat:DataBase.ships){
            double boatx = boat.getParameter(1);//anaship information
            double boaty = boat.getParameter(2);
            Iterator<Ship> shIt = boat.dangerList.iterator();
            while(shIt.hasNext()){
                Ship ship = shIt.next();
                double shipx = ship.getParameter(1);//other ship information
                double shipy = ship.getParameter(2);
                if(Math.abs(boatx-shipx)>200 && Math.abs(boaty-shipy)>200){
                    shIt.remove();
                }
            }
        }
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
