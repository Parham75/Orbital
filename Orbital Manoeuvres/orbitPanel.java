import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.lang.Object;
import java.util.Random; 
import java.io.*; 
import java.util.Scanner;
import javax.imageio.ImageIO;

public class orbitPanel extends JPanel
{
    JLabel label1;
    public static ArrayList<Oval> ovals;
    Maneuvers man;
    Image img;
    boolean flag;
    Thread thread,repaintThread;
    int WidthAdjust,HeightAdjust,
    //Division by the following indicates a scale of 1:10 for the pixel:earth's radius
    EarthAdjust=637800;
    boolean threadKill,repaintThreadKill;
    int deltat=200;
    vec R,V,Vector,R0,dv;
    pos Pi,Pf;
    double G = 6.67*Math.pow(10,-11),
           M = 5.97*Math.pow(10,24);
    Hohmann hoh;
    double E=0,dvx=0,dvy=0;
    double T=0;
    int t;
    pos Renter;
    FileInputStream textFile;
    PrintWriter writer;
    int counter;
    Scanner inFile;
    
    //constructor
    public orbitPanel(pos pi,pos pf,int WindowWidth,int WindowHeight, Simulation s ){
        Pi=pi;Pf=pf;
        man = new Maneuvers(); 
        ovals = new ArrayList<>();
        this.WidthAdjust = WindowWidth/2;
        this.HeightAdjust = WindowHeight/2;
        this.writer=writer;
        this.counter=counter;
        this.setSize(WindowWidth,WindowHeight );
        this.setLayout(null);
        this.setLocation(0,0);
        try{
        img = ImageIO.read(new File("e.jpg"));
        }
        catch(IOException e){System.out.println("I'm an idiot sandwich");}
        
        int j = s.minfinder(1);
        try{
            textFile = new FileInputStream("name90.txt");
             inFile = new Scanner(textFile);}
        catch(IOException ioe)
         {
            System.out.println("IOException");
         }
        /**
         *Making two circles around the earth, representing the orbits,
         *I understand that this could be done using drawOval method in the paintComponent.
         *The reason that I decided to use this instead is to make sure that it works if the orbit changes
         *in a later version. inclined elliptical ovals are erratic to be drawn with drawOval method.
         */
        for (double t = 0; t < Math.PI * 2; t += 0.01) {
            //these positions vary with t, but t not only is not time, but neither is it linearly related to time.
            //t is the true anomaly at epoch.
            pos a = new pos(Pi.a, Pi.e, Pi.i, t, Pi.lon, Pi.per);
            pos b = new pos(Pf.a, Pf.e, Pf.i, t, Pf.lon, Pf.per);
            //To the ArrayList ovals are added two new members each time the for loop iterates.
            ovals.add(new Oval((int) (man.getvr(a).x / EarthAdjust) + WidthAdjust, (int) (man.getvr(a).y / EarthAdjust) + HeightAdjust, 1, 1));
            //It is worth noting that all of the odd and even locations in this ArrayList are occupied by the first and second orbits respectively,
            //therefore when added to the JPanel, one point of the second orbit goes after one from the first one.
            ovals.add(new Oval((int) (man.getvr(b).x / EarthAdjust) + WidthAdjust, (int) (man.getvr(b).y / EarthAdjust) + HeightAdjust, 1, 1));
        }
        
        Vector  = new vec(0,0,0);
        vec vvv = new vec(0,60,0);
        R = man.getvr(Pi);V=man.getvv(Pi);
        R0= man.getvr(Pi);
        hoh = new Hohmann(pi,pf);
        threadKill=false;
        flag=false;

            
    }// end constructor
    
    //draw() is a method and will be called directly from inside of the Controller to invoker the animations and calculations.
    public void draw() {
       t=0;
       // A separate thread for the calculations is needed, should the user decide to press a button.
       thread = new Thread() 
       {
           @Override
           public void run() {
               
               
               // A timer to take the integration with.
               Timer timer = new Timer(1, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                   //checking if this thread needs to be killed, controlled from the Controller.
                   if(threadKill ==true){thread.interrupt();}
                   //t helps us keep track of the real time. t and deltat are both in seconds.
                   t+=deltat;
                   if(flag==false){
                   Random rand = new Random();
                    try{   
                    dvx = Double.parseDouble(inFile.nextLine());
                    dvy = Double.parseDouble(inFile.nextLine());}
                    catch(java.util.NoSuchElementException excep){ dvx=0;dvy=0;}
                    catch(NumberFormatException excp){dvx=0;dvy=0;}
                    System.out.println(dvx + "  " + dvy);
                    dv = new vec(dvx,dvy,0);
                    //first Hohmann impulse
                       if(t>hoh.Time1()+deltat){
                           V=Vector.getSub(V,dv);
                           E += Math.pow(Vector.getMag(dv),2)+2*Vector.getMag(dv)*Vector.getMag(V);
                           if(Vector.getMag(R) < (Vector.getMag(man.getvr(Pi))+100000)
                           || t-hoh.Time1()>500000
                           ){
                               
                              
                              double fi= Math.acos(Vector.getDot(R,R0)/Math.pow((Vector.getMag(R)),2));
                              if(R.y<0){
                                  Renter = new pos(Pi.a,0,0,2*Math.PI-fi,0,0);
                              }
                              else{
                                  Renter = new pos(Pi.a,0,0,fi,0,0);
                              }
                              vec Vfinal = man.getvv(Renter);
                              
                              
                             
                              V = Vfinal;
                              flag=true;
                              threadKill=true;
                            }
                       }
                       if(t>hoh.Time1()-deltat/2 && t<hoh.Time1()+deltat/2){
                           vec deltav = new vec(+1000,0,0);
                           V=Vector.getSub(V,deltav);
                       }}
                       
                       //second Hohmann impulse 
                       R = integratorOfRadius(R, V);
                       V = integratorOfVelocity(R, V);
                       
                   }
               });
               
               timer.start();


            }
        };
        thread.start();
       
       //Another thread is needed for repaint() to call the paintComponent everytime. Following the difficulties I have had 
       //in making this animation work, I encountered this sentence about repaint() in my research :
       //repaint "defers the actual painting and can collapse redundant requests into a single paint call."
       repaintThread = new Thread() {
           @Override
           public void run() {
               //A timer to repaint() the JPanel. It isn't necessary for the two timers to be in sync, but in this case they are.
               Timer timer = new Timer(1, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       //checking if this thread needs to be killed, controlled from the Controller.
                       if(threadKill ==true){thread.interrupt();}
                       
                       repaint();
                       
                   }
               });
               timer.start();
           }
       };
       repaintThread.start();

    }
    //adding a velocity*deltat to the radius that is same as trapezium rule in integration  v=dx/dt therefore (x2-x1)~dx/dt*(t2-t1)
    public vec integratorOfRadius(vec prevRad,vec Vel){
       vec Radius = Vector.getSum(prevRad,Vector.getSCpro(Vel,deltat));
       return Radius;
    }
    //adding an acceleration*deltat to the velocity that is same as trapezium rule in integration  a=dv/dt therefore (v2-v1)~dv/dt*(t2-t1)
    public vec integratorOfVelocity(vec prevRad,vec prevVel){
        double accel3 = -G*M/Math.pow(Vector.getMag(prevRad),3);
       vec acceleration = Vector.getSCpro(prevRad,accel3);
       vec velocity = Vector.getSum(prevVel,Vector.getSCpro(acceleration,deltat));
        return velocity;
    }

        
    //paintComponent
    @Override
    public void paintComponent(Graphics g) {
        //The Satellite
        g.setColor(Color.decode("#FF0000"));
        g.fillOval(WidthAdjust + (int)( R.x / EarthAdjust) - 4, HeightAdjust + (int)(R.y / EarthAdjust) - 4, 8, 8);
        
        //The Line connecting Earth to the satellite
        g.setColor(Color.decode("#009296"));
        g.drawLine(WidthAdjust + (int) (R.x / EarthAdjust), HeightAdjust + (int) (R.y / EarthAdjust), WidthAdjust, HeightAdjust);
        
        //Earth
        //Notice how the earth is painted after the Line; this is to make sure that 
        //the line is put behind the earth and so the full earth is visible at all times.
        g.setColor(Color.decode("#d3d3d3"));
        g.fillOval(WidthAdjust - 5, HeightAdjust - 5, 10, 10);

        //Orbits

        g.setColor(Color.white);

            for (int i = 0; i < ovals.size(); i++) {
                Oval temp = ovals.get(i);
                g.drawOval(temp.x,temp.y,temp.width,temp.height);
            }
        g.drawImage(img,WidthAdjust - 5, HeightAdjust - 5, null);
 
    }//end paintComponent


}

//class Oval to keep the created ovals.
class Oval {
    public int x,y,height,width;
    public Oval(int x,int y,int height,int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }//end of Oval class
}//end of orbitPanel class