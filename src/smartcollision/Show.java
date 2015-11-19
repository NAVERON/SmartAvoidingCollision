
package smartcollision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class Show extends javax.swing.JPanel{
    
    private Ship ship;
    private double PI = Math.PI;
    private Graphics g;
    
    private double mousex, mousey;
    private double newx, newy;
    private double delMx, delMy;
    
    Point s = null, e = null;
    String helpStr = "", positionStr = "", speedStr = "", courseStr = "", typeShow = "";//information
    String typeStr = "Normal";
    int time = 0;//auto hide need
    int typeChange = 0;//change ship's type
    
    public Show() {
        initComponents();
    }
    
    public void paintProperty(Graphics g) {//speed limit : 0-20
        if(DataBase.ships.isEmpty()) {
            return;
        }
        //getlast ship
        Ship shiplast = DataBase.ships.getLast();
        
        if(!DataBase.danger)//adjust for show danger
            g.setColor(new Color(152, 245, 255, 90));//background
        else
            g.setColor(new Color(255, 0, 0, 90));
        g.fillOval(5, 5, 200, 200);
        
        //paint course
        double paintCourse = Math.toRadians(shiplast.getParameter(4));
        double basepointx = 105 + (100-DataBase.dirpointradius)*Math.sin(paintCourse) - DataBase.dirpointradius;
        double basepointy = 105 - (100-DataBase.dirpointradius)*Math.cos(paintCourse) - DataBase.dirpointradius;
        
        g.setColor(Color.BLUE);
        g.fillOval((int)basepointx, (int)basepointy, 2 * DataBase.dirpointradius, 2 * DataBase.dirpointradius);
        //paint speed
        double paintSpeed = shiplast.getParameter(3)*5;
        basepointx = 105 - (int)paintSpeed;
        basepointy = 105 - (int)paintSpeed;
        int radius = (int)paintSpeed * 2;
        //set color
        double div = paintSpeed/100;
        double red = div*255;
        double green = 255 -red;
        if(red > 255 || green < 0){
            red = 255;
            green = 0;
        }
        if(red < 0 || green > 255){
            red = 0;
            green = 255;
        }
        Color color = new Color((int)red, (int)green, 0);
        g.setColor(color);
        //color setup end
        g.fillOval((int)basepointx, (int)basepointy, radius, radius);
    }
    
    public void paintShips(Graphics g){
        double x, y, speed, c;
        g.setColor(Color.BLACK);
        //////////////////////////////////////////////////////////////////////
        for(Ship b : DataBase.ships){
            x = b.getParameter(1);
            y = b.getParameter(2);
            speed = b.getParameter(3);
            c = Math.toRadians(b.getParameter(4));
            if(b == DataBase.ships.getLast()) g.setColor(Color.RED);
            switch(b.Type){
                case 0: normalShip(x, y, speed, c,g); break;
                case 1: sailingShip(x, y, speed, c,g); break;
                case 2: fishingShip(x, y, speed, c,g); break;
                case 3: outofControl(x, y, speed, c,g); break;
                case 4: limitbyControl(x, y, speed, c,g); break;
                case 5: limitbuDraft(x, y, speed, c,g); break;
            }
        }
        
    }
    public void normalShip(double x, double y, double speed, double c,Graphics g){
        int linestartx, linestarty, lineendx, lineendy;
        linestartx = (int)(x + 20*Math.sin(c));
        linestarty = (int)(y - 20*Math.cos(c));
        lineendx = (int) (linestartx + speed*Math.sin(c));
        lineendy = (int) (linestarty - speed*Math.cos(c));
        
        int[] trianglex = {linestartx, 
            (int)(x + 7*Math.sin(c+PI/2)),
            (int)(x-10*Math.sin(c)+7*Math.sin(c+PI/2)),
            (int)(x-10*Math.sin(c)+7*Math.sin(c+3*PI/2)),
            (int)(x + 7*Math.sin(c+3*PI/2))
        };
        int[] triangley = {linestarty, 
            (int)(y - 7*Math.cos(c+PI/2)), 
            (int)(y+10*Math.cos(c)-7*Math.cos(c+PI/2)),
            (int)(y+10*Math.cos(c)-7*Math.cos(c+3*PI/2)),
            (int)(y - 7*Math.cos(c+3*PI/2))
        };
        //drawbody and courseline
        g.drawPolygon(trianglex, triangley, 5);
        g.drawLine(linestartx, linestarty, lineendx, lineendy);
    }
    public void sailingShip(double x, double y, double speed, double c,Graphics g){
        int[] sailx = {(int)(x + 15*Math.sin(c)),
            (int)(x + 7*Math.sin(c+PI/2)),
            (int)(x - 15*Math.sin(c)),
            (int)(x + 7*Math.sin(c+3*PI/2))
        };
        int[] saily = {(int)(y - 15*Math.cos(c)),
            (int)(y - 7*Math.cos(c+PI/2)),
            (int)(y + 15*Math.cos(c)),
            (int)(y - 7*Math.cos(c+3*PI/2))
        };
        g.drawPolygon(sailx, saily, 4);
        g.drawLine(
            (int)(x + 15*Math.sin(c)), (int)(y - 15*Math.cos(c)),
            (int) (x + 15*Math.sin(c) + speed*Math.sin(c)), 
            (int) (y - 15*Math.cos(c) - speed*Math.cos(c))
        );
    }
    public void fishingShip(double x, double y, double speed, double c,Graphics g){
        g.drawOval((int)(x-7.5), (int)(y-7.5), 15, 15);
        g.drawLine((int)x, (int)y, (int)(x+speed*Math.sin(c)), (int)(y-speed*Math.cos(c)));
    }
    public void outofControl(double x, double y, double speed, double c,Graphics g){
        g.drawRect((int)(x-7.5), (int)(y-7.5), 15, 15);
        g.drawLine((int)x, (int)y, (int)(x+speed*Math.sin(c)), (int)(y-speed*Math.cos(c)));
        g.drawLine((int)(x-7.5), (int)(y-7.5), (int)(x+7.5), (int)(y+7.5));
        g.drawLine((int)(x-7.5), (int)(y+7.5), (int)(x+7.5), (int)(y-7.5));
    }
    public void limitbyControl(double x, double y, double speed, double c,Graphics g){
        g.drawRect((int)(x-7.5), (int)(y-7.5), 15, 15);
        g.drawOval((int)(x-7.5), (int)(y-7.5), 15, 15);
        g.drawLine((int)x, (int)y, (int)(x+speed*Math.sin(c)), (int)(y-speed*Math.cos(c)));
    }
    public void limitbuDraft(double x, double y, double speed, double c, Graphics g){
        g.drawRoundRect((int)(x-7.5), (int)(y-7.5), 15, 15, 5, 5);
        g.drawLine((int)x, (int)y, (int)(x+speed*Math.sin(c)), (int)(y-speed*Math.cos(c)));
    }
    
    public void paintTrack(Graphics g){
        g.setColor(Color.MAGENTA);
        for(Ship boat: DataBase.ships){
            for(Point p: boat.shipTrack)
                g.fillOval((int)p.getX(), (int)p.getY(), 3, 3);
        }
    }
    
//    public void paintObstacle(Graphics g){
//        double startx, starty, endx, endy;
//        double speed, course;
//        g.setColor(Color.BLACK);
//        
//        for(DyObstacle o : DataBase.obstacle){
//            startx = o.getParameter(1);
//            starty = o.getParameter(2);
//            speed = o.getParameter(3);
//            course = Math.toRadians(o.getParameter(4));
//            //drawbody
//            
//            g.drawRoundRect((int)(startx-DataBase.obstacleradius), (int)(starty-DataBase.obstacleradius), 
//                    2*DataBase.obstacleradius, 2*DataBase.obstacleradius, 5, 5);
//            
//            endx = startx + speed * Math.sin(course);
//            endy = starty - speed * Math.cos(course);
//            //drawline
//            g.drawLine((int)startx, (int)starty, (int)endx, (int)endy);
//            
//        }
//    }
    
    public void paintLine(Graphics g){
        //start point 
        g.setColor(Color.MAGENTA);
        g.fillRect((int)s.getX(), (int)s.getY(), 20, 20);
        g.drawString(s.getX() + "::" + s.getY(), (int)s.getX(), (int)(s.getY()-5));
        //end point
        g.setColor(Color.GRAY);
        g.fillRect((int)e.getX(), (int)e.getY(), 20, 20);
        g.drawString(e.getX()+"::"+e.getY(), (int)(e.getX()-100), (int)(e.getY()-5));
        //voyage line
        g.setColor(Color.PINK);
        g.drawLine((int)s.getX()+10, (int)s.getY()+10, (int)e.getX()+10, (int)e.getY()+10);
    }
    
    public void printString(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        //auto hide
        time++;
        if(time>30){
            positionStr =""; speedStr =""; courseStr = ""; typeShow = "";
            time = 0;
        }
        g.drawString(mousex + " , " + mousey, 20, 680);//mouse position 820,  680
        g.drawString(helpStr, 170, 680);//help position
        g.drawString(positionStr, 850, 25);//infomation showing
        g.drawString(speedStr, 850, 50);
        g.drawString(courseStr, 850, 75);
        g.drawString(typeShow, 850, 100);
        
        g.setColor(Color.BLUE);
        switch(typeChange){
            case 0: typeStr = "Normal";break;
            case 1: typeStr = "Sailing";break;
            case 2: typeStr = "Fishing";break;
            case 3: typeStr = "Out of Control";break;
            case 4: typeStr = "Limit by Control";break;
            case 5: typeStr = "Limit by Draft";break;
        }
        g.drawString(typeStr, 920, 680);
    }
    
    @Override
    public void paint(Graphics g) {//paint will call repaint() method
        super.paint(g);
        //background first(depend on adjust) , speed and course
        this.g = g;//letout for paint
        printString(g);
        paintProperty(g);
        paintShips(g);
        if(!DataBase.tracklock)
            paintTrack(g);
        if(s != null && e != null)
            paintLine(g);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        setMaximumSize(new java.awt.Dimension(1024, 800));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setName("Show"); // NOI18N
        setNextFocusableComponent(this);
        setPreferredSize(new java.awt.Dimension(1100, 700));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents
    
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        Show.this.requestFocus();
        mousex = evt.getX();
        mousey = evt.getY();
        helpStr = "'Left Click & Drag' Create Ships, 'Right Click' Delete, 'Middle Button' Create Voyage";
    }//GEN-LAST:event_formMouseMoved
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            helpStr = "Drag to Create Moving Ship, 'Right Double Click' Delete All Ship";
        }
        if(evt.getModifiers()==8){
            helpStr = "Voyage start Point, Drag to End Point, 'L' Remove Voyage";
            s = new Point(evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMousePressed
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            newx = evt.getX();
            newy = evt.getY();
            double course = DataBase.CaculateRatio(mousex, mousey, newx, newy);
            double differentx = newx - mousex;
            double differenty = newy - mousey;
            double speed = Math.sqrt(Math.pow(differentx, 2) + Math.pow(differenty, 2))/10;
            //error ,type add
            ship = new Ship(mousex, mousey, speed, course, typeChange);
            DataBase.ships.add(ship);
            switch(ship.Type){
                case 0: typeShow = "Normal";break;
                case 1: typeShow = "Sailing";break;
                case 2: typeShow = "Fishing";break;
                case 3: typeShow = "Out of Control";break;
                case 4: typeShow = "Limit by Control";break;
                case 5: typeShow = "Limit by Draft";break;
            }
            helpStr = "A Ship Exist";//速度航向显示的精度问题
            positionStr = "Position : "+mousex+","+mousey;
            speedStr = "Speed : "+(int)speed;
            courseStr = "Course : "+(int)course;
            typeShow = "Type : "+typeShow;
            time = 0;
        }
        if(evt.getModifiers()==8){
            e = new Point(evt.getX(), evt.getY());
            DataBase.linecourse = DataBase.CaculateRatio(s.getX(), s.getY(), e.getX(), e.getY());
            helpStr = "A Voyage Exist";
        }
    }//GEN-LAST:event_formMouseReleased
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // 
        delMx = evt.getX();
        delMy = evt.getY();
        double disx, disy;
        double dis;
        
        //delete option
        if(evt.getModifiers()==4){
            if(evt.getClickCount() >= 2){
                DataBase.ships.clear();
                //delete track
                for (Ship boat:DataBase.ships) {
                    boat.shipTrack.clear();
                }
                helpStr = "Delete All Ships";
                positionStr =""; speedStr =""; courseStr = ""; typeShow = "";
            }
            else{
                Iterator<Ship> shIt = DataBase.ships.iterator();
                while(shIt.hasNext()){
                    ship = shIt.next();
                    disx = Math.abs(delMx-ship.getParameter(1));
                    disy = Math.abs(delMy-ship.getParameter(2));
                    dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));
                    if(dis <= 15){
                        helpStr = "Delete a Ship";
                        shIt.remove();
                    }
                }
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_C){
            typeChange++;
            if(typeChange>5)
                typeChange = 0;
        }
        
        if(DataBase.ships.isEmpty()){
            return;
        }
        
        ship = DataBase.ships.getLast();
        switch(evt.getKeyCode()){
            case KeyEvent.VK_UP: helpStr = "Speed Up"; ship.giveValue(3, ship.getParameter(3)+1); break;
            case KeyEvent.VK_DOWN: helpStr = "Speed Down"; ship.giveValue(3, ship.getParameter(3)-1); break;
            case KeyEvent.VK_LEFT: helpStr = "Turning Left"; ship.giveValue(4, ship.getParameter(4)-1); break;
            case KeyEvent.VK_RIGHT: helpStr = "Turning Right"; ship.giveValue(4, ship.getParameter(4)+1); break;
            //function
            case KeyEvent.VK_T:{//open or close track show
                helpStr = "Track Show/Hide";
                DataBase.tracklock = !DataBase.tracklock;
                break;
            }
            case KeyEvent.VK_R:{//open or close track record
                helpStr = "Track Record Open/Close";
                DataBase.trackrecord = !DataBase.trackrecord;
                break;
            }
            case KeyEvent.VK_I: {//get the information within mouse position
                Iterator<Ship> shIt = DataBase.ships.iterator();
                while(shIt.hasNext()){
                    ship = shIt.next();
                    double disx = Math.abs(mousex-ship.getParameter(1));
                    double disy = Math.abs(mousey-ship.getParameter(2));
                    double dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));
                    if(dis <= 15){
                        helpStr = "Get Ship Information";
                        positionStr = "position : "+(int)ship.getParameter(1)+","+(int)ship.getParameter(2);
                        speedStr = "Speed : "+(int)ship.getParameter(3);
                        courseStr = "Course : "+(int)ship.getParameter(4);
                        switch(ship.Type){
                            case 0: typeShow = "Normal";break;
                            case 1: typeShow = "Sailing";break;
                            case 2: typeShow = "Fishing";break;
                            case 3: typeShow = "Out of Control";break;
                            case 4: typeShow = "Limit by Control";break;
                            case 5: typeShow = "Limit by Draft";break;
                        }
                        typeShow = "Type : "+typeShow;
                        time = 0;
                    }
                }
                break;
            }
            
        }
        
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        //delete voyage
        if(evt.getKeyCode() == KeyEvent.VK_L){
            s = null;
            e = null;
            helpStr = "Clear Voyage";
        }
        //pause and play
        if(evt.getKeyCode() == KeyEvent.VK_SPACE){
            if(!DataBase.pause){
                helpStr = "Pause";
                printString(g);
                repaint();
                DataBase.pause = !DataBase.pause;
            }
            else{
                helpStr = "Play";
                DataBase.pause = !DataBase.pause;
            }
        }
    }//GEN-LAST:event_formKeyReleased
                    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    
}
