/**
 * Template class for GUI making use of an external class listener
 * 1 Write own listener registration method for components to be listened to
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIOrbit extends JFrame 
{
    vec R,V;
    int WindowWidth = 700,
    WindowHeight = 700;
    int opWidth,opHeight;

    vec Vector;

    OrbitPane op;

   //Constructor
    public GUIOrbit(pos pi,pos pf,int space,int width,int height,Color LabelColor,Color BackColor)
    {
        super("Hohmann Simulator");

        op = new OrbitPane(space,width,height,pi,pf,WindowWidth,WindowHeight,LabelColor,BackColor);
        opWidth = width+2*space+WindowWidth;
        opHeight= WindowHeight;

          this.setLayout(null);
          this.setLocation(0,0);
          this.setSize(opWidth,opHeight);

          op.setLayout(null);
          op.setLocation(0,0);
          
          this.setContentPane(op);
          op.setVisible(true);

          
     
    }// end of constructor for GUIOrbit class

}//end of GUIOrbit Class
