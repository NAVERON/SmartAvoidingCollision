
package smartcollision;

public class DyObstacle {
    private double x, y, s, c;
    
    private double stepx, stepy;
    private double c2r;
    
    public DyObstacle(double x, double y, double s, double c){
        this.x = x;
        this.y = y;
        if(s > 20) 
            this.s = 20;
        else 
            this.s = s;
        this.c = c;
    }
    public DyObstacle() {//default parameter
        this.x = DataBase.defaultx;
        this.y = DataBase.defaulty;
        this.s = DataBase.defaults;
        this.s = DataBase.defaultc;
    }
    
    public double getParameter(int a){
        switch (a){
            case 1 : return x;
            case 2 : return y;
            case 3 : return s;
            case 4 : return c;
            default : return 0;//error
        }
    }
    
    public void goAhead(){//position, course, speed test
        c2r = Math.toRadians(c);
        stepx = s*Math.sin(c2r);
        stepy = s*Math.cos(c2r);
        x+=stepx;
        y-=stepy;
        if(x<1) x = 1100;
        if(x>1100) x = 1;
        if(y<0) y = 800;
        if(y>800) y = 0;
    }
    
    
}
