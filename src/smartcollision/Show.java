package smartcollision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class Show extends javax.swing.JPanel{
    
    private Ship ship;
    private double PI = Math.PI;
    private double mousex, mousey;
    private double oldx, oldy, newx, newy;
    
    private double delMx, delMy;
    private double medium = 0;
    
    private Graphics g;
    //boolean q_p;
    public Show() {
        initComponents();
    }
    
    public void paintSpeed(Graphics g) {//speed limit : 0-20
        if(DataBase.ships.isEmpty())
            return;
        Ship shipget = DataBase.ships.get(DataBase.ships.size()-1);
        double paintSpeed = shipget.getParameter(3) * 5;
        int basepointx = 105 - (int)paintSpeed;
        int basepointy = 105 - (int)paintSpeed;
        int radius = (int)paintSpeed * 2;
        
        if (paintSpeed >= 75)//set color
        {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }
        
        g.fillOval(basepointx, basepointy, radius, radius);
        
    }
    
    public void paintCourse(Graphics g) {
        if(DataBase.ships.isEmpty())
            return;
        Ship shipget = DataBase.ships.get(DataBase.ships.size()-1);
        double paintCourse = Math.toRadians(shipget.getParameter(4));
        //take it caution
        double basepointx = 105 + (100-DataBase.dirpointradius)*Math.sin(paintCourse) - DataBase.dirpointradius;
        double basepointy = 105 - (100-DataBase.dirpointradius)*Math.cos(paintCourse) - DataBase.dirpointradius;
        
        g.setColor(Color.BLUE);
        g.fillOval((int)basepointx, (int)basepointy, 2 * DataBase.dirpointradius, 2 * DataBase.dirpointradius);
        
    }
    
    public void paintShips(Graphics g){
        g.setColor(Color.BLACK);
        double x, y, s, c;
        int linestartx, linestarty, lineendx, lineendy;
        
        for(Ship b : DataBase.ships){
            x = b.getParameter(1);
            y = b.getParameter(2);
            s = b.getParameter(3);
            c = Math.toRadians(b.getParameter(4));
            
            linestartx = (int)(x + 30*Math.sin(c));
            linestarty = (int)(y - 30*Math.cos(c));
            lineendx = (int) (linestartx + s*Math.sin(c));
            lineendy = (int) (linestarty - s*Math.cos(c));
            
            int[] trianglex = {linestartx, (int)(x + 10*Math.sin(c+PI/2)), 
                (int)(x + 10*Math.sin(c+3*PI/2))};
            int[] triangley = {linestarty, (int)(y - 10*Math.cos(c+PI/2)), 
                (int)(y - 10*Math.cos(c+3*PI/2))};
            //drawbody and courseline
            g.drawPolygon(trianglex, triangley, 3);
            g.drawLine(linestartx, linestarty, lineendx, lineendy);
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
    
    @Override
    public void paint(Graphics g) {//paint will call repaint() method
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        //background first(depend on adjust) , speed and course
        this.g = g;//letout for paint
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString(mousex + "," + mousey, 820, 680);//position 820,  680
        
        if(true)//adjust for show danger
            g.setColor(new Color(152, 245, 255, 70));//background
        else
            g.setColor(Color.GRAY);
        g.fillOval(5, 5, 200, 200);
        
        paintCourse(g);
        paintSpeed(g);
        paintShips(g);
        paintObstacle(g);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Label_pt = new javax.swing.JLabel();
        End_pt = new javax.swing.JTextField();
        Start_pt = new javax.swing.JTextField();
        Update = new javax.swing.JButton();
        Clear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        setMaximumSize(new java.awt.Dimension(1024, 800));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setName("Show"); // NOI18N
        setNextFocusableComponent(this);
        setPreferredSize(new java.awt.Dimension(1024, 700));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
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

        Label_pt.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        Label_pt.setText("Setup Start Point And End Point x,y");
        add(Label_pt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 290, 20));

        End_pt.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        End_pt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                End_ptActionPerformed(evt);
            }
        });
        add(End_pt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 670, 125, 20));

        Start_pt.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        Start_pt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Start_ptActionPerformed(evt);
            }
        });
        add(Start_pt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 670, 125, 20));

        Update.setBackground(new java.awt.Color(255, 255, 255));
        Update.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        Update.setText("Update");
        Update.setActionCommand("Updete");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });
        add(Update, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 660, 100, 30));

        Clear.setBackground(new java.awt.Color(255, 255, 255));
        Clear.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        Clear.setText("Clear");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });
        add(Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 660, 100, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void End_ptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_End_ptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_End_ptActionPerformed

    private void Start_ptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Start_ptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Start_ptActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateActionPerformed

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearActionPerformed
    
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        mousex = evt.getX();
        mousey = evt.getY();
    }//GEN-LAST:event_formMouseMoved
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            oldx = evt.getX();
            oldy = evt.getY();
        }
    }//GEN-LAST:event_formMousePressed
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        if(evt.getModifiers()==16){
            newx = evt.getX();
            newy = evt.getY();
            
            double differentx = newx - oldx;
            double differenty = newy - oldy;
            double speed = Math.sqrt(Math.pow(differentx, 2) + Math.pow(differenty, 2))/10;
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
            
            while (course<0||course>=360) {
                if(course<0) course+=360;
                if(course>=360) course-=360;
            }
            
            DyObstacle newobs = new DyObstacle(oldx, oldy, speed, course);
            DataBase.obstacle.add(newobs);
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
        
        if(evt.getModifiers()==4){
            if(evt.getClickCount() >= 2){
                DataBase.obstacle.clear();
                DataBase.ships.clear();
            }
            else{
                Iterator<DyObstacle> it = DataBase.obstacle.iterator();
                while(it.hasNext()){
                    o = it.next();
                    disx = Math.abs(delMx-o.getParameter(1));
                    disy = Math.abs(delMy-o.getParameter(2));
                    dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));//distance
                    if(dis <= 10)
                        it.remove();//delete the last returned//
                }
                Iterator<Ship> sh = DataBase.ships.iterator();
                while(sh.hasNext()){
                    ship = sh.next();
                    disx = Math.abs(delMx-ship.getParameter(1));
                    disy = Math.abs(delMy-ship.getParameter(2));
                    dis = Math.sqrt(Math.pow(disx, 2)+Math.pow(disy, 2));
                    if(dis <= 10)
                        sh.remove();
                }
            }
        }
        if(evt.getModifiers()==8){
            double t = medium%2;
            if(t==0){
                DataBase.pause = true;
                medium++;
            }
            else{
                DataBase.pause = false;
                medium--;
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_Q){
            DataBase.pause = true;
            Ship shipnew = new Ship(mousex, mousey, DataBase.defaults, DataBase.defaultc);
            DataBase.ships.add(shipnew);
        }
        ship = DataBase.ships.get(DataBase.ships.size()-1);
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            ship.giveValue(3, ship.getParameter(3)+0.5);
        }
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            ship.giveValue(3, ship.getParameter(3)-0.5);
        }
        if(evt.getKeyCode() == KeyEvent.VK_LEFT){
            ship.giveValue(4, ship.getParameter(4)-1);
        }
        if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
            ship.giveValue(4, ship.getParameter(4)+1);
        }
//        q_p = false;
//        boolean u_p = false, d_p = false, l_p = false, r_p = false, space = false;
//        switch(evt.getKeyCode()){
//            case KeyEvent.VK_Q: q_p = true; break;
//            case KeyEvent.VK_UP: u_p = true; break;
//            case KeyEvent.VK_DOWN: d_p = true; break;
//            case KeyEvent.VK_LEFT: l_p = true; break;
//            case KeyEvent.VK_RIGHT: r_p = true; break;
//            case KeyEvent.VK_SPACE: space = true; break;
//        }
//        if(q_p){
//            DataBase.fresh = true;
//            ship = new Ship();
//            DataBase.ships.add(ship);
//            
//            while(q_p){
//                paintShips(g);
//                if(u_p){
//                    ship.giveValue(1, ship.getParameter(2)-5);
//                }
//                else if(d_p){
//                    ship.giveValue(2, ship.getParameter(2)+5);
//                }
//                else if(l_p){
//                    ship.giveValue(1, ship.getParameter(1)-5);
//                }
//                else if(r_p){
//                    ship.giveValue(1, ship.getParameter(1)+5);
//                }
//                else if(space){
//                    ship.giveValue(4, ship.getParameter(4)+5);
//                }
//            }   
//        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_Q){
            DataBase.pause = false;
        }
    }//GEN-LAST:event_formKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Clear;
    private javax.swing.JTextField End_pt;
    private javax.swing.JLabel Label_pt;
    private javax.swing.JTextField Start_pt;
    private javax.swing.JButton Update;
    // End of variables declaration//GEN-END:variables

}
