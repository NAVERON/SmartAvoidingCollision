
package smartcollision;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SmartCollision extends JFrame{
    
    public static void main(String[] args) {
        //initialing
        JFrame frame = new JFrame();
        Show show = new Show();
        SmartCollision smartCollision = new SmartCollision();
        //setup frame and add panel
        frame.setSize(1100, 730);
        frame.add(show);
        frame.setTitle("ONE STEP V2");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(!DataBase.begin){//end the game and clear panel
            while (!DataBase.pause){//for pause and rest for a minutes
                show.repaint();
                for(Ship b: DataBase.ships){
                    b.goAhead();
                }
                //analyse
                smartCollision.Action();//don't delete manual

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
    
    public void Action(){
        //action to avoid collision
        analyse();
        //Action and reset
        for(Ship dyship: DataBase.ships){
            switch(dyship.Action){
                case 1:{
                    dyship.giveValue(3, dyship.getParameter(3)+1);
                    dyship.Action = 0;
                    break;
                }
                case 2:{
                    dyship.giveValue(3, dyship.getParameter(3)-1);
                    dyship.Action = 0;
                    break;
                }
                case 3:{
                    dyship.giveValue(4, dyship.getParameter(4)+1);
                    dyship.Action = 0;
                    break;
                }
                case 4:{
                    dyship.giveValue(4, dyship.getParameter(4)-1);
                    dyship.Action = 0;
                    break;
                }
                //
            }
        }
        remove();
    }
    
    public void analyse(){//need return flag signal
        //danger area add to arealist
        for(Ship boat: DataBase.ships){//distance analyse
            int index = 0;
            for(Ship ship: DataBase.ships){
                if(boat!=ship){
                    if(Math.abs(boat.getParameter(1)-ship.getParameter(1))<200&&
                            Math.abs(boat.getParameter(2)-ship.getParameter(2))<200){
                        for(Ship temp:boat.dangerList){//don't repaet add, error
                            if(ship!=temp)
                                boat.dangerList.add(ship);
                        }
                        if(boat.dangerList.size()==0){boat.dangerList.add(ship);}
                    }
                }
            }//analyse every ships and add to danger list
            for(int i = 0;i<boat.dangerList.size();i++){//if danger is empty, then skip
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
                if(boat.dangerList.size()>boat.dataList.size())//repeat add, error
                    boat.dataList.add(rp);
                //System.out.println(rp);
                //analyse rp multiple
                
            }
            System.out.println(boat.dataList.size());
            for(int i = 0; i < boat.dataList.size(); i++){
                if(boat.dataList.get(i) > 0)
                    index = 3;
            }
            switch(index){
                case 1: boat.Action = 1; break;
                case 2: boat.Action = 2; break;
                case 3: boat.Action = 3; break;
                case 4: boat.Action = 4; break;
                
            }
            index = 0;
        }
        
    }
    
    public void remove(){
        //remove safe objects
        for(int i = 0;i<DataBase.ships.size();i++){
            Ship boat = DataBase.ships.get(i);
            double boatx = boat.getParameter(1);//anaship information
            double boaty = boat.getParameter(2);
            for(int j = 0;j<boat.dangerList.size();j++){
                Ship ship = boat.dangerList.get(j);
                double shipx = ship.getParameter(1);
                double shipy = ship.getParameter(2);
                if(Math.abs(boatx-shipx)>200 && Math.abs(boaty-shipy)>200){
                    boat.dangerList.remove(j);
                    boat.dataList.remove(j);
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
