
package smartcollision;

public class DyObstacle {
    private double x, y, s, c;
    
    double stepx, stepy;
    double c2r;
    
    public DyObstacle(double x, double y, double s, double c){
        this.x = x;
        this.y = y;
        if(s > 30) 
            this.s = 30;
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
    
    public void goAhead(){
        c2r = Math.toRadians(c);
        stepx = s*Math.sin(c2r);
        stepy = s*Math.cos(c2r);
        x+=stepx;
        y-=stepy;
    }
    
    
}
