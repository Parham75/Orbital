import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class Controller implements ActionListener 
{
    GUIInput input;
    GUIOrbit go;
    GUIStarter gs;
    GUIShowVelPos gsvp;
    public pos pi,pf;
    Hohmann hoh;
    Dimension width,height;
    
    Maneuvers man = new Maneuvers();
    //constructor
    public Controller(GUIStarter guiStarter)
    {
        // construct the GUI views
        this.gs = guiStarter;
        
        gs.sp.jbShowVelPos.addActionListener(this);
        gs.sp.jbHoh.addActionListener(this);
        
        gs.sp.jbAbout.addActionListener(this);
        gs.sp.jbExit.addActionListener(this);

    }//end constructor
    
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource()==gs.sp.jbExit){
            System.exit(10);
        }
            
        if(ae.getSource()==gs.sp.jbShowVelPos){
            gs.setVisible(false);
            //Opening GUIShowVelPos
            gsvp = new GUIShowVelPos(gs.space,gs.width,gs.height,gs.LabelColor,gs.BackColor);
            gsvp.setVisible(true);
            
            gsvp.svpp.jbGenerate.addActionListener(this);
            gsvp.svpp.jbClose.addActionListener(this);
            gsvp.svpp.jbBack.addActionListener(this);
        }
            
        if(ae.getSource()==gs.sp.jbHoh){
            
            gs.setVisible(false);
            input = new GUIInput(gs.space,gs.width,gs.height,gs.LabelColor,gs.BackColor);
            input.setVisible(true);
            
            input.ip.jbSubmit.addActionListener(this);
            input.ip.jbClose.addActionListener(this);
            input.ip.jbBack.addActionListener(this);
        }
            
        if(ae.getSource()==gs.sp.jbAbout){
            
            infoBox(" This program was made by 2xmo for studenthack vi 2018 for more information please check Devpost"
            ,"About Us");
        }
        
        
        // To prevent NullPointerException, I personally prefer this method over try and catch.    
        if(input !=null){
            
            if(ae.getSource()==input.ip.jbClose){
                        System.exit(10);
                }
            try{    
                if (ae.getSource()==input.ip.jbSubmit) 
                   {
                    
                    double a = Double.parseDouble(input.ip.jtfa.getText());
                    double a2 = Double.parseDouble(input.ip.jtfa2.getText());
                    
                    if(a>=0 || a2>=0){
                        // Eliminating the unshowable, the numbers are correspondent to the information given in the infoBox
                        // but are as well, secure enough to cover any level of precision of radius of earth(down to 2 significant figures).
                        if((a<20000000 || a>192000000) || (a2<20000000 || a2>192000000))
                        {
                            infoBox("The radii of the orbits must be between 4 and 30 radii of earth","Error : Radius Out of Range");
                        }
                        else
                        {
                            pi = new pos(a,0,0,0,0,0);
                            pf = new pos(a2,0,0,0,0,0);
                            
                            hoh = new Hohmann(pi,pf);
                        
                            input.setVisible(false);
                            
                            go = new GUIOrbit(pi,pf,input.space,input.width,input.height,input.LabelColor,input.BackColor);         
                            go.setVisible(true);
                            
                            //Adding ActionListeners to the GUI that has been added here.
                            go.op.jbBack.addActionListener(this);
                            go.op.jbDraw.addActionListener(this);
                            go.op.jbClose.addActionListener(this);
                        }
                        
                    }
                    else{
                        // a little banter, when a negative length is entered.
                        infoBox("Dear time traveller/extraterrestrial being,\nOn earth we still haven't discovered such concept as negative length,\n Accordingly, please enter a positive value.\n\nJoul gyuno gluboraol/ozglugollocglyuur voot,\nEm oulch quo cgyura fubom'g jyucseboloja caseh semsokg uc motugyubo romtch,\n Usseljotri, krouco omgol u kecyugyubo burao.","Error : Negative Length");  
                    }}
                }
            catch(NumberFormatException e){
                // catch any non-number values (including null and letters)
                infoBox("Please input the required data","Error : Wrong Numbers");
            }
            if(ae.getSource()==input.ip.jbBack){
    
                input.setVisible(false);
                gs.setVisible(true);
                }
        }
        
        if(gsvp !=null){
            try{
                 //Catching unvalid data
                 if(ae.getSource()==gsvp.svpp.jbGenerate){
                    double a = Double.parseDouble(gsvp.svpp.jtfa.getText());
                    double e = Double.parseDouble(gsvp.svpp.jtfe.getText());
                    double i = StringRad(gsvp.svpp.jtfi.getText());
                    double lon = StringRad(gsvp.svpp.jtflon.getText());
                    double per = StringRad(gsvp.svpp.jtfper.getText());
                    double anom = StringRad(gsvp.svpp.jtfanom.getText());
                    if(a>=0){
                        //Eccentricity must have a value between 0 inclusive and 1 exclusive.
                        if(e>=1 || e<0){ 
                            infoBox(" Eccentricity must be between 0 and 1","Error : Invalid Eccentricity");
                        }
                        else{
                            pi = new pos (a,e,i,anom,lon,per);
                            
                            
                            gsvp.svpp.jlVelX.setText(String.valueOf(man.getvv(pi).x)+"       i");
                            gsvp.svpp.jlVelY.setText(String.valueOf(man.getvv(pi).y)+"       j");
                            gsvp.svpp.jlVelZ.setText(String.valueOf(man.getvv(pi).z)+"       k       m/s");
                            
                            gsvp.svpp.jlPosX.setText(String.valueOf(man.getvr(pi).x)+"       i");
                            gsvp.svpp.jlPosY.setText(String.valueOf(man.getvr(pi).y)+"       j");
                            gsvp.svpp.jlPosZ.setText(String.valueOf(man.getvr(pi).z)+"       k       m");
                            
                            
                        }
                    }
                    else{
                        infoBox("Dear time traveller/extraterrestrial being,\nOn earth, we still haven't discovered such concept as negative length,\n Accordingly, please enter a positive value.\n\nJoul gyuno gluboraol/ozglugollocglyuur voot,\nEm oulch quo cgyura fubom'g jyucseboloja caseh semsokg uc motugyubo romtch,\nUsseljotri, krouco omgol u kecyugyubo burao.","Error : Negative Length");  
                    }
                        }
                    }
                    catch(NumberFormatException e){

                        infoBox("Please input the required data","Error : Wrong Numbers");
                    }
            if(ae.getSource()==gsvp.svpp.jbClose)
            {
                System.exit(10);
            }
            
            if(ae.getSource()==gsvp.svpp.jbBack)
            {
                gsvp.setVisible(false);
                gs.setVisible(true);
                
            }
                    
        }
        
        if( go != null){
            
            if(ae.getSource()==go.op.jbClose){
                System.exit(10);
            }
            
            if(ae.getSource()==go.op.jbBack){

                // Kill the threads so as to stop the running animation and calculations.
                go.op.op.threadKill = true;
                go.op.op.repaintThreadKill = true;
                
                //Clearing ovals to remove the previous data stored.
                go.op.op.ovals.clear();
                
                go.setVisible(false);
                
                input.setVisible(true);
            }
            
            if(ae.getSource()==go.op.jbDraw){
                
                go.op.op.draw();
                
                String Vel1x = String.valueOf(hoh.getHohdv1().x),
                       Vel1y = String.valueOf(hoh.getHohdv1().y),
                       Vel2x = String.valueOf(hoh.getHohdv2().x),
                       Vel2y = String.valueOf(hoh.getHohdv2().y);
                       
                //The following if statements remove the values due to uncertainty
                if(Math.abs(hoh.getHohdv1().x)<Math.pow(10,-5)){Vel1x="0.000000E00";}
                if(Math.abs(hoh.getHohdv1().y)<Math.pow(10,-5)){Vel1y="0.000000E00";}
                if(Math.abs(hoh.getHohdv2().x)<Math.pow(10,-5)){Vel2x="0.000000E00";}
                if(Math.abs(hoh.getHohdv2().y)<Math.pow(10,-5)){Vel2y="0.000000E00";}
                
                //Alteration of the JLabels inside of OrbitPane, the substring is called for the JPanel
                //to be able to contain the whole string in regards of size.
                go.op.jlVel1.setText(Vel1x.substring(0,5) +Vel1x.substring(Vel1x.length()-4,Vel1x.length())+
                "  i  "+Vel1y.substring(0,5) +Vel1y.substring(Vel1y.length()-4,Vel1y.length()-1) +" j"+"     "+"m/s");
                
                go.op.jlVel2.setText(Vel2x.substring(0,5) +Vel2x.substring(Vel2x.length()-4,Vel2x.length())+
                "  i  "+Vel2y.substring(0,5) +Vel2y.substring(Vel2y.length()-4,Vel2y.length()) +" j"+"     "+"m/s");
                
                // Disabling the Draw Button
                go.op.jbDraw.setEnabled(false);
            }
        }
                    
    }//End of the actionPerformed
 
    //Declaration of a MessageDialog in form of a method to avoid repetition in the above code
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
 
    public static double StringRad(String written){
        
        return Double.parseDouble(written)/180*Math.PI;
        
    }
    
}
