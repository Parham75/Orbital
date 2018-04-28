import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class StarterPane extends JPanel
{
    JLabel jlIntro = new JLabel("Welcome to the Maneuver Calculator");
    JLabel jlChoose = new JLabel("Please Choose one of the Options");
    
    JButton jbHoh = new JButton("Circular Hohmann");
    JButton jbShowVelPos = new JButton ("Show Velocity and Position");
    JButton jbContact = new JButton("Contact Us");
    JButton jbAbout = new JButton("About Us");
    JButton jbExit = new JButton("Exit");



    public StarterPane(int space,int width,int height,Color LabelColor,Color BackColor)
    {
        
        jlIntro.setBounds((int)(0.5*space),space,3*width,height);

        jlChoose.setBounds(space,3*space+2*height,width,height);
        jbShowVelPos.setBounds(space,4*space+3*height,width,height);
        jbHoh.setBounds(space,5*space+4*height,width,height);
        jbContact.setBounds(space,6*space+5*height,width,height);
        jbAbout.setBounds(space,7*space+6*height,width,height);
        // Exit Button should be half as wide as the other buttons
        int ExitWidth = width/2;
        jbExit.setBounds((int)(space+0.25*width),9*space+7*height,ExitWidth,height);
        
        this.setBackground(BackColor);
        jlIntro.setForeground(Color.white);
        jlChoose.setForeground(LabelColor);
        
        add(jlIntro);

        add(jlChoose);
        add(jbShowVelPos);
        add(jbHoh);
        add(jbContact);
        add(jbAbout);
        add(jbExit);
    }// end of constructor of the class StarterPane
}//end of the class StarterPane
