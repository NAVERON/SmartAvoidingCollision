/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcollision;

/**
 *
 * @author Administrator
 */
public class Ship {
    private double x , y, s, c;
    
    public void Ship(double x, double y, double s, double c){
        this.x=x;
        this.y=y;
        this.s=s;
        this.c=c;
        
    }
    public double getparameter(int a){
        switch(a){
            case 1 : return x;
            case 2 : return y;
            case 3 : return s;
            case 4 : return c;
        }
        return 0;//if return 0 ,erron
    }
    
}
