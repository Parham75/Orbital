import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class GUIShowVelPos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIShowVelPos extends JFrame
{
    
    ShowVelPosPane svpp;
    int svppwidth,svppheight;
    /**
     * Constructor for objects of class GUIShowVelPos
     */
    public GUIShowVelPos(int space,int width,int height,Color LabelColor,Color BackColor)
    {
    super("Velocity and Position Generator");
     svppwidth = 5*width+9*space;
     svppheight = 8*space+7*height;
     this.setSize(svppwidth,svppheight);
     svpp = new ShowVelPosPane(space,width,height,svppheight,LabelColor,BackColor);
     this.setLayout(null);
     this.setLocation(0,0);
     this.setContentPane(svpp);
     svpp.setLayout(null);
     svpp.setLocation(0,0);
    }//end of constructor

}//end of the class GUIShowVelPos
