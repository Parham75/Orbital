import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;
import java.applet.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Write a description of class OrbitPane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrbitPane extends JPanel
{
    orbitPanel op;
    int WindowWidth,WindowHeight;
    PrintWriter writer;
    JButton jbDraw = new JButton("Simulate the Trajectory")
    , jbClose = new JButton("Close")
    , jbBack = new JButton("Back");
    
    JLabel jlVelNote1 = new JLabel("Impulse Vector per unit mass 1")
    , jlVelNote2 = new JLabel("Impulse Vector per unit mass 2");
    
    JLabel jlVel1 = new JLabel(" X i + Y j"+"   "+"m/s")
    , jlVel2 = new JLabel(" X i + Y j"+"   "+"m/s");
    
    
    /**
     * Constructor for objects of class OrbitPane
     */
    public OrbitPane(int space,int width,int height,pos pi,pos pf,int WindowWidth,int WindowHeight,Color LabelColor,Color BackColor)
    {
        this.WindowWidth = WindowWidth;
        this.WindowHeight = WindowHeight;
        int BaClWidth = width/2;
        for(int i=0;i<1;i++){
            
            try{
                writer = new PrintWriter("name"+i+".txt", "UTF-8");
                
                op = null;
                op = new orbitPanel(pi,pf,WindowWidth,WindowHeight,writer,i);
                op.setLayout(null);
                op.setLocation(0,0);
                op.setSize(WindowWidth,WindowHeight);
                op.draw();
                
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("setup failed");
            }   
        }
        
        
        jlVelNote1.setBounds(space+WindowWidth,space,width,height);
        jlVel1.setBounds(space+WindowWidth,2*space+height,width,height);
        
        jlVelNote2.setBounds(space+WindowWidth,3*space+2*height,width,height);
        jlVel2.setBounds(space+WindowWidth,4*space+3*height,width,height);

        
        jbDraw.setBounds(space+WindowWidth,WindowHeight-2*space-2*height,width+space,height);
        jbBack.setBounds(space+WindowWidth,WindowHeight-space-height,BaClWidth,height);
        jbClose.setBounds(2*space+WindowWidth+BaClWidth,WindowHeight-space-height,BaClWidth,height);
        
        //this.setBackground(BackColor);
        jlVelNote1.setForeground(LabelColor);
        jlVel1.setForeground(LabelColor);
        jlVelNote2.setForeground(LabelColor);
        jlVel2.setForeground(LabelColor);
        
       
       
        
        add(op);
        add(jbDraw);
        add(jbBack);
        add(jbClose);
        add(jlVelNote1);add(jlVelNote2);
        add(jlVel1);add(jlVel2);
        
    }//end of constructor
    
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(WindowWidth, 0, WindowWidth, WindowHeight);
        g.setColor(Color.white);
        g2.draw(lin);
    }

}//end of OrbitPane
