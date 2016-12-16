
package smartcollision;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ship {
    private double x , y, s, c;
    public LinkedList<Ship> dangerList = new LinkedList<>();
    public LinkedList<Double> dataList = new LinkedList<>();
    public ArrayList<Point> shipTrack = new ArrayList<>();
    
    public final float  K = 0.05F, T = 10F;
    //////////////////////////////////////////////////////////////////////
    //action表示舵角
    public int Action = 0; // if 0 , no option , speed up 1//speed down 2//turn left 3//turn right 4
    //2016.12.09更改：action作为舵角标志量而非只是指示的参数，
    //action  舵角的大小并根据角加速度的变化来计算角速度的变化
    public int time = 0; //计算时间
    public float rt = 0;//角加速度
    public float r = 0;//角速度
    public float relcourse = 0;  //变化的角度
    public boolean danger = false;  //指示本船是否危险状态
    ///////////////////////////////////////////////////////////////////////
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
        this.Type = 0;
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
        if(this.s<0||this.s>20) System.err.println("speed out of limits!!");
    }
    int count = 0;
    public void goAhead(){//position, course, speed test
        c2r = Math.toRadians(c);
        stepx = s*Math.sin(c2r);
        stepy = s*Math.cos(c2r);
        giveValue(1, x+stepx);
        giveValue(2, y-stepy);
        
        if(shipTrack.size()>100){
            new Thread(){
                @Override
                public void run(){
                    try {
                        File f = new File("trackStore.txt");
                        if (!f.exists()) {
                            f.createNewFile();
                            f = new File("trackStore.txt");
                        }
                        FileWriter fw = new FileWriter(f);
                        BufferedWriter bw = new BufferedWriter(fw);
                        for (int i = 0; i < shipTrack.size(); i++) {
                            Point p = shipTrack.get(i);
                            fw.write(p.x+","+p.y);
                            fw.write("\n\r");   //回车换行有点问题
                        }
                        bw.flush();
                        fw.flush();
                        bw.close();
                        fw.close();
                        shipTrack.clear();
                    } catch (IOException ex) {
                        Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
            
        }
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
    
    //更改船舶自身的参数，以便于后续的计算
    public void shipDatachange(){
        //假定船舶参数已知： K= 0.05 T= 10
        if (Action != 0) { //有舵角时，存在角加速度，没有舵角时，角速度逐渐变为0，航向稳定
            time++;
        }else{   //当舵角为0时，角速度逐渐变小
            if (time>0) {
                time--;
            }else{
                time = 0;
            }
            if (r>1) {
                r-=1;
            }
            else if(r<-1){
                r+=1;
            }
            else{  //当角速度足够小时，忽略不计
                r = 0;
            }
        }
        //其中action=0， 表示没有角加速度，但是有角速度
        rt = (float) (K/T*Action*Math.exp(-time/T));
        r += rt*time;
        
        //relcourse = r;      //每次的角度变化按照角速度来算
        //r = (float) (K*Action*(1 - Math.exp(-time/T)));
        //relcourse = (float) (K*Action*(time - T + T*Math.exp(-time/T)));
        System.out.println("角加速度："+rt+"   角速度："+r+"   角度变化："+r+"\n");
        c += r;
    }
    
}