
package smartcollision;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Ship {
    private double x , y, s, c;
    
    private double stepx, stepy;
    private double c2r;
    
    public Ship(double x, double y, double s, double c){
        this.x=x;
        this.y=y;
        if(s > 20)
            this.s = 20;
        else
            this.s = s;
        this.c=c;
    }
    public Ship() {
        this.x = DataBase.defaultx;
        this.y = DataBase.defaulty;
        this.s = DataBase.defaults;
        this.c = DataBase.defaultc;
    }
    
    public double getParameter(int a){
        switch(a){
            case 1 : return x;
            case 2 : return y;
            case 3 : return s;
            case 4 : return c;
            default : return 0;
        }
    }
    
    public void giveValue(int a, double newValue){//change ship's parameter
        switch(a){
            case 1: x = newValue; break;
            case 2: y = newValue; break;
            case 3: s = newValue; break;
            case 4: c = newValue; break;
        }
    }
    
    public void goAhead(){
        c2r = Math.toRadians(c);
        stepx = s*Math.sin(c2r);
        stepy = s*Math.cos(c2r);
        x+=stepx;
        y-=stepy;
    }
    
    //has some error ,need multiple thread
    public void changeDirection(double c){//change direction should be step and step
        double enddirection = this.c + c;
//        for(;;){
//            if (this.c >= enddirection) break;
//            this.c++;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        //0-360 limit
        while (this.c<0||this.c>=360) {
            if(this.c<0) this.c+=360;
            if(this.c>=360) this.c-=360;
        }
        if(this.c<0||this.c>360) System.err.println("course out of limits!!");
    }
    
    public void changeSpeed(double s){//speed at 0-20 kn
        //adjust s
        double endspeed = this.s + s;
//        for(;;){
//            if (this.s >= endspeed) break;
//            this.s++;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        if(this.s<0||this.s>20) System.err.println("speed out of limits!!");
    }
    
    
}
