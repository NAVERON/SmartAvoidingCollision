
package smartcollision;

import java.util.LinkedList;

public class DataBase {
    
    //default x, y, s, c    store Dyobstacles and Ships
    public static double defaultx = 300, defaulty = 400, defaults = 5, defaultc = 0, linecourse = 30;
    
    //public static LinkedList<DyObstacle> obstacle = new LinkedList<>();
    public static LinkedList<Ship> ships = new LinkedList<>();
    
    public static int dirpointradius = 10;
    //public static int obstacleradius = 10;
    //flags
    public static boolean tracklock = false;
    public static boolean begin = false;
    public static boolean clear = false;
    public static boolean pause = false;
    public static boolean danger = false;
    
    /**********************************caculate area*****************************************/
    
    public static double CaculateRatio(double start_x, double start_y, double end_x, double end_y){
        double differentx = end_x - start_x;
        double differenty = end_y - start_y;
        double course = 0;
        int adjust = 0;//switch case///
        
        if(differentx == 0 && differenty == 0) adjust = 0;
        else if(differentx >= 0 && differenty <0) adjust = 1;
        else if(differentx < 0 && differenty <= 0) adjust = 2;
        else if(differentx <= 0 && differenty > 0) adjust = 3;
        else if(differentx > 0 && differenty >= 0) adjust = 4;
        
        switch(adjust){
            case 0 : course = 0; break;
            case 1 : course = 450 - Math.toDegrees(Math.atan2(-differenty, differentx)); break;
            case 2 : course = 90 - Math.toDegrees(Math.atan2(-differenty, differentx)); break;
            case 3 : course = 90 - Math.toDegrees(Math.atan2(-differenty, differentx)); break;
            case 4 : course = 90 - Math.toDegrees(Math.atan2(-differenty, differentx)); break;
        }
        
        while (course<0||course>=360){
            if(course<0) course+=360;
            if(course>=360) course-=360;
        }
        return course;
    }
    
}
