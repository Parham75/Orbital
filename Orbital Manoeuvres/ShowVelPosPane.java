import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class ShowVelPosPane extends JPanel
{
      int space,width,height,HeightOfPage;

    
      JLabel jlInput = new JLabel("Please Input the Data for the Satellite");
      JLabel jla = new JLabel("Length of Semi Major Axis");
      JTextField jtfa = new JTextField(10);
      JLabel jle = new JLabel("Eccentricity");
      JTextField jtfe = new JTextField(10);
      JLabel jli = new JLabel("Angle of Inclination");
      JTextField jtfi = new JTextField(10);
      JLabel jllon = new JLabel("longtitude of the Ascending Node");
      JTextField jtflon = new JTextField(10);
      JLabel jlper = new JLabel("Angle Between Periapsis and Node");
      JTextField jtfper = new JTextField(10);
      JLabel jlanom = new JLabel("True Anomaly");
      JTextField jtfanom = new JTextField(10);
      
      JButton jbGenerate = new JButton(" Generate Vectors " );
      JButton jbClose = new JButton("Close");
      JButton jbBack = new JButton("Back");
      
      
      JLabel jlVelTXT = new JLabel("Velocity Vector");
      JLabel jlVelX = new JLabel("X");
      JLabel jlVelY = new JLabel("Y");
      JLabel jlVelZ = new JLabel("Z");

      
      JLabel jlPosTXT = new JLabel("Position Vector");
      JLabel jlPosX = new JLabel("X");
      JLabel jlPosY = new JLabel("Y");
      JLabel jlPosZ = new JLabel("Z");

    /**
     * Constructor for objects of class ShowVelPosPane
     */
    public ShowVelPosPane(int space,int width,int height,int HeightOfPage,Color LabelColor,Color BackColor)
    {
        //HeightOfPage is a variable, which is passed from the JFrame 
        //to determine where the end of the vertical line should be
        this.space = space;
        this.width = width;
        this.height = height;
        this.HeightOfPage = HeightOfPage;
        jlInput.setBounds((int)((1.5*space)+(0.5*width)),space,2*width,height);
        
        this.setBackground(BackColor);
        jlInput.setForeground(Color.white);
        
        jla.setForeground(LabelColor);
        jle.setForeground(LabelColor);
        jli.setForeground(LabelColor);
        jlanom.setForeground(LabelColor);
        jllon.setForeground(LabelColor);
        jlper.setForeground(LabelColor);
        
        jlVelTXT.setForeground(LabelColor);
        jlVelX.setForeground(LabelColor);
        jlVelY.setForeground(LabelColor);
        jlVelZ.setForeground(LabelColor);
        
        jlPosTXT.setForeground(LabelColor);
        jlPosX.setForeground(LabelColor);
        jlPosY.setForeground(LabelColor);
        jlPosZ.setForeground(LabelColor);

        
        jla.setBounds(space,2*space+height,width,height);
        jtfa.setBounds(2*space+width,2*space+height,width,height);
        jle.setBounds(space,3*space+2*height,width,height);
        jtfe.setBounds(2*space+width,3*space+2*height,width,height);
        jli.setBounds(space,4*space+3*height,width,height);
        jtfi.setBounds(2*space+width,4*space+3*height,width,height);
        jllon.setBounds(space,5*space+4*height,width,height);
        jtflon.setBounds(2*space+width,5*space+4*height,width,height);
        jlper.setBounds(space,6*space+5*height,width,height);
        jtfper.setBounds(2*space+width,6*space+5*height,width,height);
        jlanom.setBounds(space,7*space+6*height,width,height);
        jtfanom.setBounds(2*space+width,7*space+6*height,width,height);
        
        
        jlVelTXT.setBounds((int)(5.5*space+3*width),space,width,height);
        jlVelX.setBounds(4*space+2*width,2*space+height,width,height);
        jlVelY.setBounds(5*space+3*width,2*space+height,width,height);
        jlVelZ.setBounds(6*space+4*width,2*space+height,width,height);

        
        
        jlPosTXT.setBounds((int)(5.5*space+3*width),3*space+2*height,width,height);
        jlPosX.setBounds(4*space+2*width,4*space+3*height,width,height);
        jlPosY.setBounds(5*space+3*width,4*space+3*height,width,height);
        jlPosZ.setBounds(6*space+4*width,4*space+3*height,width,height);
        
        jbGenerate.setBounds(4*space+2*width,7*space+6*height,2*width,height);
        jbBack.setBounds(5*space+4*width,7*space+6*height,(int)(width/2),height);
        jbClose.setBounds((int)(6*space+4.5*width),7*space+6*height,(int)(width/2),height);
        
        
        add(jlInput);
        add(jla);add(jtfa);
        add(jle);add(jtfe);
        add(jli);add(jtfi);
        add(jllon);add(jtflon);
        add(jlper);add(jtfper);
        add(jlanom);add(jtfanom);
        
        add(jlVelTXT);
        add(jlVelX);
        add(jlVelY);
        add(jlVelZ);
        
        add(jlPosTXT);
        add(jlPosX);
        add(jlPosY);
        add(jlPosZ);
        
        add(jbGenerate);add(jbBack);add(jbClose);
    }//end of constructor
    
    // Vertical line: Notice how it is related to the HeightOfPage
        public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(3*space+2*width, 0, 3*space+2*width, HeightOfPage);
        g.setColor(Color.white);
        g2.draw(lin);
    }

}//end of class ShowVelPosPane
