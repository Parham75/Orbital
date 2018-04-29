import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class InputPane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InputPane extends JPanel
{

      
      JLabel jla = new JLabel("Length of Semi Major Axis");
      JTextField jtfa = new JTextField(10);
      JButton jbSubmit = new JButton("Submit");
      JButton jbClose = new JButton("Close");
      JButton jbBack = new JButton("Back");


    public InputPane(int space,int width,int height,Color LabelColor,Color BackColor)
    {
       
          jla.setBounds(space , space, width, height);
          jtfa.setBounds(2*space+width , space, width, height);
          int ButtonWidth = width/2;
          jbSubmit.setBounds((int)(1.5*space+0.5*width), 5*space+2*height, ButtonWidth,height);
          jbBack.setBounds((int)(2.5*space+0.5*width+ButtonWidth), 5*space+2*height, ButtonWidth,height);
          jbClose.setBounds((int)(1.5*space+0.75*width),6*space+3*height, ButtonWidth,height);
          
          this.setBackground(BackColor);
          jla.setForeground(LabelColor);
          add(jla);
          add(jtfa);
          
          add(jbSubmit);
          add(jbClose);
          add(jbBack);
    }//end of constructor


}//end of class
