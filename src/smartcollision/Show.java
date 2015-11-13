
package smartcollision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class Show extends javax.swing.JPanel{
    
    private Ship ship = new Ship();
    private double PI = Math.PI;
    private Graphics g;
    
    private double mousex, mousey;
    private double newx, newy;
    private double delMx, delMy;
    
    int pause = 0;
    int tracklock = 0;
    Point s = null, e = null;
    String str = "'Left Click' create obstacle ,'Q' create ships, 'Right Click' to Delete";
    
    public Show() {
        initComponents();
    }
    
    public void paintSpeed(Graphics g) {//speed limit : 0-20
        if(DataBase.ships.isEmpty()) return;
        Ship shipget = DataBase.ships.getLast();
        double paintSpeed = shipget.getParameter(3)*5;
        int basepointx = 105 - (int)paintSpeed;
        int basepointy = 105 - (int)paintSpeed;
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
        g.fillOval(basepointx, basepointy, radius, radius);
    }
    
    public void paintCourse(Graphics g) {
        if(DataBase.ships.isEmpty())
            return;
        Ship shipget = DataBase.ships.getLast();
        double paintCourse = Math.toRadians(shipget.getParameter(4));
        //take it caution
        double basepointx = 105 + (100-DataBase.dirpointradius)*Math.sin(paintCourse) - DataBase.dirpointradius;
        double basepointy = 105 - (100-DataBase.dirpointradius)*Math.cos(paintCourse) - DataBase.dirpointradius;
        
        g.setColor(Color.BLUE);
        g.fillOval((int)basepointx, (int)basepointy, 2 * DataBase.dirpointradius, 2 * DataBase.dirpointradius);
        
    }
    
    public void paintShips(Graphics g){
        g.setColor(Color.BLACK);
        double x, y, speed, c;
        int linestartx, linestarty, lineendx, lineendy;
        //////////////////////////////////////////////////////////////////////
        for(Ship b : DataBase.ships){
            x = b.getParameter(1);
            y = b.getParameter(2);
            speed = b.getParameter(3);
            c = Math.toRadians(b.getParameter(4));
            
            linestartx = (int)(x + 30*Math.sin(c));
            linestarty = (int)(y - 30*Math.cos(c));
            lineendx = (int) (linestartx + speed*Math.sin(c));
            lineendy = (int) (linestarty - speed*Math.cos(c));
            
            int[] trianglex = {linestartx, (int)(x + 10*Math.sin(c+PI/2)), 
                (int)(x + 10*Math.sin(c+3*PI/2))};
            int[] triangley = {linestarty, (int)(y - 10*Math.cos(c+PI/2)), 
                (int)(y - 10*Math.cos(c+3*PI/2))};
            //drawbody and courseline
            if(b == DataBase.ships.getLast()) g.setColor(Color.RED);
            g.drawPolygon(trianglex, triangley, 3);
            g.drawLine(linestartx, linestarty, lineendx, lineendy);
        }
        
    }
    
    public void paintTrack(){
        //if full, clear all points
        for(Ship boat: DataBase.ships){
            for(Point p: boat.shipTrack)
                g.fillOval((int)p.getX(), (int)p.getY(), 3, 3);
        }
        //can change color to aeparate
        for(DyObstacle obstacle: DataBase.obstacle){
            for(Point p: obstacle.obstacleTrack)
                g.fillOval((int)p.getX(), (int)p.getY(), 3, 3);
        }
    }
    
    public void paintObstacle(Graphics g){
        double startx, starty, endx, endy;
        double speed, course;
        g.setColor(Color.BLACK);
        
        for(DyObstacle o : DataBase.obstacle){
            startx = o.getParameter(1);
            starty = o.getParameter(2);
            speed = o.getParameter(3);
            course = Math.toRadians(o.getParameter(4));
            //drawbody
            
            g.drawRoundRect((int)(startx-DataBase.obstacleradius), (int)(starty-DataBase.obstacleradius), 
                    2*DataBase.obstacleradius, 2*DataBase.obstacleradius, 5, 5);
            
            endx = startx + speed * Math.sin(course);
            endy = starty - speed * Math.cos(course);
            //drawline
            g.drawLine((int)startx, (int)starty, (int)endx, (int)endy);
            
        }
    }
    
    public void paintLine(Graphics g){
        if(s!=null && e!=null){
            g.setColor(Color.MAGENTA);
            g.fillRect((int)s.getX(), (int)s.getY(), 20, 20);
            g.drawString(s.getX() + "::" + s.getY(), (int)s.getX(), (int)(s.getY()-5));
            
            g.setColor(Color.GRAY);
            g.fillRect((int)e.getX(), (int)e.getY(), 20, 20);
            g.drawString(e.getX()+"::"+e.getY(), (int)(e.getX()-100), (int)(e.getY()-5));
            
            g.setColor(Color.PINK);
            g.drawLine((int)s.getX()+10, (int)s.getY()+10, (int)e.getX()+10, (int)e.getY()+10);
        }
    }
    public void printString(Graphics g){
        //500,680
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        g.drawString(str, 200, 680);
    }
    
    @Override
    public void paint(Graphics g) {//paint will call repaint() method
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        //background first(depend on adjust) , speed and course
        
        this.g = g;//letout for paint
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString(mousex + " , " + mousey, 20, 680);//position 820,  680
        
        if(!DataBase.danger)//adjust for show danger
            g.setColor(new Color(152, 245, 255, 90));//background
        else
            g.setColor(new Color(255, 0, 0, 90));
        g.fillOval(5, 5, 200, 200);
        
        printString(g);
        paintSpeed(g);
        paintCourse(g);
        paintShips(g);
        paintObstacle(g);
        if(!DataBase.tracklock)
            paintTrack();
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
        mousex = evt.getX();
        mousey = evt.getY();
        str = "'Left Click' create obstacle ,'Q' create ships, 'Right Click' to Delete";
    }//GEN-LAST:event_formMouseMoved
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            str = "Drag to Create Moving obstacles";
        }
        if(evt.getModifiers()==8){
            str = "Start Point, Drag to Eng Point";
            s = new Point(evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMousePressed
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            newx = evt.getX();
            newy = evt.getY();
            
            double course = CaculateRatio(mousex, mousey, newx, newy);
            double differentx = newx - mousex;
            double differenty = newy - mousey;
            double speed = Math.sqrt(Math.pow(differentx, 2) + Math.pow(differenty, 2))/10;
            
            DyObstacle newobs = new DyObstacle(mousex, mousey, speed, course);
            DataBase.obstacle.add(newobs);
            str = "A Obstacle Exist";
        }
        if(evt.getModifiers()==8){
            e = new Point(evt.getX(), evt.getY());
            DataBase.linecourse = CaculateRatio(s.getX(), s.getY(), e.getX(), e.getY());
            str = "A Voyage Exist";
        }
    }//GEN-LAST:event_formMouseReleased
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        delMx = evt.getX();
        delMy = evt.getY();
        double disx, disy;
        double dis;
        DyObstacle o;
        Show.this.requestFocus();//get the focus in panel
        //delete option
        if(evt.getModifiers()==4){
            if(evt.getClickCount() == 2){
                DataBase.obstacle.clear();
                DataBase.ships.clear();
                for (Ship boat:DataBase.ships) {
                    boat.shipTrack.clear();
                }
                for (DyObstacle obs : DataBase.obstacle) {
                    obs.obstacleTrack.clear();
                }
                str = "Delete All Ships & Obstacles";
            }
            else{
                Iterator<DyObstacle> it = DataBase.obstacle.iterator();
                while(it.hasNext()){
                    o = it.next();
                    disx = Math.abs(delMx-o.getParameter(1));
                    disy = Math.abs(delMy-o.getParameter(2));
                    dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));//distance
                    if(dis <= 10){
                        str = "Delete a Obstacle";
                        it.remove();//delete the last returned//
                    }
                }
                Iterator<Ship> sh = DataBase.ships.iterator();
                while(sh.hasNext()){
                    ship = sh.next();
                    disx = Math.abs(delMx-ship.getParameter(1));
                    disy = Math.abs(delMy-ship.getParameter(2));
                    dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));
                    if(dis <= 10){
                        str = "Delete a Ship";
                        //如果移除船舶需要移除分析链表
                        //能否改变船舶类的变量，增加危险分析链表，挂接在船舶数据中，实现同步操作
                        sh.remove();
                    }
                }
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //switch-case
        
        if(evt.getKeyCode() == KeyEvent.VK_L){//delete line
            s = null;
            e = null;
            str = "Clear Voyage";
        }
        if(evt.getKeyCode() == KeyEvent.VK_SPACE){
            if(pause == 0){
                str = "Pause";
                printString(g);
                repaint();
                DataBase.pause = true;
                pause++;
            }
            else{
                str = "Play";
                DataBase.pause = false;
                pause--;
            }
        }
        /**************************************/
        if(DataBase.ships.isEmpty()){
            return;
        }
        
        ship = DataBase.ships.getLast();
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            str = "Speed Up";
            ship.giveValue(3, ship.getParameter(3)+1);
        }
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            str = "Speed Down";
            ship.giveValue(3, ship.getParameter(3)-1);
        }
        if(evt.getKeyCode() == KeyEvent.VK_LEFT){
            str = "Turning Left";
            ship.giveValue(4, ship.getParameter(4)-1);
        }
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
            str = "Turning Right";
            ship.giveValue(4, ship.getParameter(4)+1);
        }
        
        if(evt.getKeyCode() == KeyEvent.VK_T){
            if(tracklock == 0){
                DataBase.tracklock = true;
                str = "Track Opened";
                tracklock++;
            }
            else{
                DataBase.tracklock = false;
                str = "Track Locked";
                tracklock--;
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_Q){
            Ship shipnew = new Ship(mousex, mousey, DataBase.defaults, DataBase.linecourse);
            DataBase.ships.add(shipnew);
            DataBase.pause = false;
            str = "You Create a Ship";
        }
    }//GEN-LAST:event_formKeyReleased
                    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public double CaculateRatio(double x1, double y1, double x2, double y2){
        double differentx = x2 - x1;
        double differenty = y2 - y1;
        //double speed = Math.sqrt(Math.pow(differentx, 2) + Math.pow(differenty, 2))/10;
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
