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
      JLabel jla2 = new JLabel("Length of Semi Major Axis 2");
      JTextField jtfa2 = new JTextField(10);
      JButton jbBack = new JButton("Back");


    public InputPane(int space,int width,int height,Color LabelColor,Color BackColor)
    {
       
          jla.setBounds(space , space, width, height);
          jtfa.setBounds(2*space+width , space, width, height);
          jla2.setBounds(space , height+2*space, width, height);
          jtfa2.setBounds(2*space+width, height+2*space, width,height);
          int ButtonWidth = width/2;
          jbSubmit.setBounds((int)(1.5*space+0.5*width), 5*space+2*height, ButtonWidth,height);
          jbBack.setBounds((int)(2.5*space+0.5*width+ButtonWidth), 5*space+2*height, ButtonWidth,height);
          jbClose.setBounds((int)(1.5*space+0.75*width),6*space+3*height, ButtonWidth,height);
          
          this.setBackground(BackColor);
          jla.setForeground(LabelColor);
          jla2.setForeground(LabelColor);
          add(jla);
          add(jtfa);
          add(jla2);
          add(jtfa2);
          
          add(jbSubmit);
          add(jbClose);
          add(jbBack);
    }//end of constructor


}//end of class
