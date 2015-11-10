
package smartcollision;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

public class DataBase {
    
    //default x, y, s, c    store objects and ships
    public static double defaultx = 300, defaulty = 400, defaults = 3, defaultc = 0, linecourse = 30;
    
    public static LinkedList<DyObstacle> obstacle = new LinkedList<>();
    public static LinkedList<Ship> ships = new LinkedList<>();
    public static ArrayList<Point> shipstrack = new ArrayList<>();
    
    public static int dirpointradius = 10;
    public static int obstacleradius = 10;
    //flags
    public static boolean tracklock = false;
    public static boolean begin = false;
    public static boolean clear = false;
    public static boolean pause = false;
    public static boolean danger = false;
    
    /**********************************caculate area*****************************************/
    public static ArrayList<LinkedList<DyObstacle>> distance = new ArrayList<>();
    
}
