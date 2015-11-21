
package smartcollision;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class Ship {
    private double x , y, s, c;
    public LinkedList<Ship> dangerList = new LinkedList<>();
    public LinkedList<Double> dataList = new LinkedList<>();
    public ArrayList<Point> shipTrack = new ArrayList<>();
    
    public int Action = 0; // if 0 , no option , speed up 1//speed down 2//turn left 3//turn right 4
    public int Type = 0;//if 0, normal ship, sailing, fishing, out of control, limit by control, limit by draft
    
    private double stepx, stepy;
    private double c2r;
    
    public Ship(double x, double y, double s, double c, int type){
        this.x=x;
        this.y=y;
        if(s > 20)
            this.s = 20;
        else
            this.s = s;
        this.c=c;
        this.Type = type;
    }
    public Ship() {
        this.x = DataBase.defaultx;
        this.y = DataBase.defaulty;
        this.s = DataBase.defaults;
        this.c = DataBase.defaultc;
    }
    
    public double getParameter(int index){
        switch(index){
            case 1 : return x;
            case 2 : return y;
            case 3 : return s;
            case 4 : return c;
            default : return 0;//error
        }
    }
    
    public synchronized void giveValue(int index, double newValue){//change ship's parameter
        switch(index){
            case 1: x = newValue; break;
            case 2: y = newValue; break;
            case 3: s = newValue; break;
            case 4: c = newValue; break;
            default: x =DataBase.defaultx; y = DataBase.defaulty;
                s = DataBase.defaults; c = DataBase.defaultc;
                break;
        }
        
        while (this.c<0||this.c>=360) {
            if(this.c<0) this.c+=360;
            if(this.c>=360) this.c-=360;
        }
        if(this.c<0||this.c>360) System.err.println("course out of limits!!");
        if(this.s<0||this.s>20) System.err.println("speed out of limits!!");
    }
    int count = 0;
    public void goAhead(){//position, course, speed test
        c2r = Math.toRadians(c);
        stepx = s*Math.sin(c2r);
        stepy = s*Math.cos(c2r);
        giveValue(1, x+stepx);
        giveValue(2, y-stepy);
        
        if(shipTrack.size()>10000)
            shipTrack.clear();
        count++;
        if(count>5){
            if(!DataBase.trackrecord){
                shipTrack.add(new Point((int)x, (int)y));
                count = 0;
            }
        }
        if(x<0) x = 1120;
        if(x>1120) x = 0;
        if(y<0) y = 740;
        if(y>740) y = 0;
    }
    
}