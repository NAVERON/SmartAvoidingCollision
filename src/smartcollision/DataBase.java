
package smartcollision;

import java.util.ArrayList;

public class DataBase {
    
    //default x, y, s, c    store objects
    public static double defaultx = 300, defaulty = 400, defaults = 8, defaultc = 40;
    
    public static ArrayList<DyObstacle> obstacle = new ArrayList<>();//should clear count if count is too large
    public static ArrayList<Ship> ships = new ArrayList<>();
    
    public static int dirpointradius = 10;
    public static int obstacleradius = 10;
    //flags
    public static boolean begin = false;
    public static boolean pause = false;//fresh kontrol
    public static boolean clear = false;//clear the panel
    
    /***************************************************************************/
    //caculate area
    
    
}
