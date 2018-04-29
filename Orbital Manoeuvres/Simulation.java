import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.lang.Object;
import java.util.Random; 
import java.io.*; 
import java.io.PrintWriter; 
import java.util.Scanner;

public class Simulation
{

    public static ArrayList<Oval> ovals;
    Maneuvers man;
    boolean flag,kill;
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
    double E=0,dvx=0,dvy=0,energy;
    double T=0;
    int t;
    pos Renter;
    FileInputStream textFile;
    PrintWriter writer,optimiser;
    int counter;
    Scanner inFile;
    //constructor
    public Simulation(pos pi,pos pf,PrintWriter writer, int counter, PrintWriter optimiser ,double energy){
        Pi=pi;
        man = new Maneuvers(); 
        this.writer=writer;
        this.optimiser=optimiser;
        this.counter=counter;
        this.energy = energy;
        Vector  = new vec(0,0,0);
        vec vvv = new vec(0,60,0);
        R = man.getvr(Pi);V=man.getvv(Pi);
        R0= man.getvr(Pi);
        hoh = new Hohmann(pi,pf);
        threadKill=false;
        repaintThreadKill=false;
        flag=false;

            
    }// end constructor
    
    //draw() is a method and will be called directly from inside of the Controller to invoker the animations and calculations.
    
    public void simulate(){
               kill=false;

       // A separate thread for the calculations is needed, should the user decide to press a button.
       thread = new Thread() 
       {
           @Override
           public void run() {
           //checking if this thread needs to be killed, controlled from the Controller.
           if(threadKill ==true){thread.interrupt();}
           //it helps us keep track of the real time. t and deltat are both in seconds.
           t=0;
           do{
           t+=deltat;
           Random rand = new Random();
           
            dvx = rand.nextDouble()*40-20;
            dvy = rand.nextDouble()*40-20;
         
            dv = new vec(dvx,dvy,0);

               
            writer.println(dvx);
            writer.println(dvy);
           
          
           
           
           //first Hohmann impulse
           if(t>hoh.Time1()+3*deltat){
               V=Vector.getSub(V,dv);
               E += Math.pow(Vector.getMag(dv),2)+2*Vector.getMag(dv)*Vector.getMag(V);
               if(Vector.getMag(R) < Vector.getMag(man.getvr(Pi))
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
                  vec deltaVfinal = Vector.getSub(Vfinal,V);
                  
                 T=t-hoh.Time1();
                  E+= Math.pow(Vector.getMag(deltaVfinal),2);
                 optimiser.println(counter+"  " + E + "  " + T);
                  V = Vfinal;
                 thread.interrupt();
                 kill=true;
                }
           }
           if(t>hoh.Time1()-deltat/2 && t<hoh.Time1()+deltat/2){
               vec deltav = new vec(+1000,0,0);
               V=Vector.getSub(V,deltav);
           }
           
           
           R = integratorOfRadius(R, V);
           V = integratorOfVelocity(R, V);
                       
                   }while(kill==false);}
        };
        thread.start();
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

    public int minfinder(int j){
        ArrayList<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("Optimiser.txt")))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i=0;
        optseries[] op = new optseries[arr.size()];

        while(i<arr.size()){
        op[i] = separator(arr,i);
        // if(op[i].R>energy){op = java.ArrayUtils.removeElement(op, i);}
        i++;
        }
        bubbleSort(op);
        int n=0;
        // while(j<arr.size()){
            // System.out.println(op[n].K);
            // n++;
        // }
        try{
        return op[arr.size()-j].K;}
        catch(ArrayIndexOutOfBoundsException ex){
            return 0;}
      
        
    }
    public optseries separator(ArrayList<String> arr, int i){
        String S;
        int K;
        double R,T;
        
        S = arr.get(i);
        String array1[]= S.split("  ");
        K = Integer.parseInt(array1[0]);
        R = Double.parseDouble(array1[1]);
        T = Double.parseDouble(array1[2]);
        optseries OP = new optseries(K,R,T);
        return OP;
    }
    public void bubbleSort(optseries[] op) {
     int n = op.length;  
        double temp = 0;  
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(op[j-1].T > op[j].T){  
                                 //swap elements  
                                 temp = op[j-1].T;  
                                 op[j-1].T = op[j].T;  
                                 op[j].T = temp;  
                         }  
                          
    }}
    
}
}



class optseries{
   public int K;
   public double R,T;
   public optseries(int K, double R,double T){
        this.K = K;
        this.R = R;
        this.T = T;
    }
    
}

        

