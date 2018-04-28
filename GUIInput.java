import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIInput extends JFrame 
{
  InputPane ip;
  int ipwidth,ipheight;
  int space,width,height;
  Color LabelColor,BackColor;
  
    //Notice how three dimension defining variables are passed through. 
    public GUIInput(int space,int width,int height,Color LabelColor,Color BackColor)
    {
        super("Input for Hohmann");
          this.space = space;
          this.width = width;
          this.height = height;
          this.LabelColor = LabelColor;
          this.BackColor = BackColor;
          /**
           * The following 2 statements are explained in
           * full detail in the documentation provided.
           */
          ipwidth = 3*space+2*width;
          ipheight = 7*space+4*height;
          ip = new InputPane(space,width,height,LabelColor,BackColor);
          ip.setLayout(null);
          ip.setLocation(0,0);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setContentPane(ip);
          this.setSize(ipwidth,ipheight);

    }//end GUIInput constructor
   
}//end GUIInput class
