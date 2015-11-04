package smartcollision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Show extends javax.swing.JPanel {
    
    public Ship ship;
    public DyObstacle obstacle;
    double PI = Math.PI;
    double mousex, mousey;
    double oldx, oldy, newx, newy;
    
    public Show(Ship ship, DyObstacle obstacle) {//fresh adjust is null!!!!!
        this.ship = ship;
        this.obstacle = obstacle;
        initComponents();
    }
    
    public void paintSpeed(Graphics g) {//speed limit : 0-20
        double paintSpeed = ship.getParameter(3) * 5;
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
        double paintCourse = Math.toRadians(ship.getParameter(4));
        //take it caution
        double basepointx = 105 + (100-DataBase.dirpointradius)*Math.sin(paintCourse) - DataBase.dirpointradius;
        double basepointy = 105 - (100-DataBase.dirpointradius)*Math.cos(paintCourse) - DataBase.dirpointradius;
        
        g.setColor(Color.BLUE);
        g.fillOval((int)basepointx, (int)basepointy, 2 * DataBase.dirpointradius, 2 * DataBase.dirpointradius);
        
    }
    
    public void paintShip(Graphics g){
        g.setColor(Color.BLACK);
        double x = ship.getParameter(1);
        double y = ship.getParameter(2);
        double s = ship.getParameter(3);
        double c = Math.toRadians(ship.getParameter(4));
        
        int linestartx = (int)(x + 30*Math.sin(c));
        int linestarty = (int)(y - 30*Math.cos(c));
        int lineendx = (int) (linestartx + s*Math.sin(c));
        int lineendy = (int) (linestarty - s*Math.cos(c));
        
        int[] trianglex = {linestartx, (int)(x + 10*Math.sin(c+PI/2)), 
            (int)(x + 10*Math.sin(c+3*PI/2))};
        int[] triangley = {linestarty, (int)(y - 10*Math.cos(c+PI/2)), 
            (int)(y - 10*Math.cos(c+3*PI/2))};
        //drawbody and courseline
        g.drawPolygon(trianglex, triangley, 3);
        g.drawLine(linestartx, linestarty, lineendx, lineendy);
    }
    
    public void paintObstacle(Graphics g){
        double startx, starty, endx, endy;
        double speed, course;
        
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
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
        g.setFont(font);
        g.drawString(mousex + "," + mousey, 820, 680);//position 820,  680
        if(true)//adjust for show danger
            g.setColor(new Color(152, 245, 255, 50));//background
        else 
            g.setColor(Color.GRAY);
        g.fillOval(5, 5, 200, 200);
        
        paintCourse(g);
        paintSpeed(g);
        paintShip(g);
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
        oldx = evt.getX();
        oldy = evt.getY();
    }//GEN-LAST:event_formMousePressed
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
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
        
    }//GEN-LAST:event_formMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Clear;
    private javax.swing.JTextField End_pt;
    private javax.swing.JLabel Label_pt;
    private javax.swing.JTextField Start_pt;
    private javax.swing.JButton Update;
    // End of variables declaration//GEN-END:variables

}
