

/**
 * Maneuvers class is the main class in
 * calculating the final results using pos,
 * and the logic used in the program.

 */
public class Maneuvers
{
    // instance variables - replace the example below with your own
    public double mu;
    public vec Vector;
    //Constructor
    public Maneuvers()
    {
        Vector = new vec(0,0,0);
        mu=6.67*Math.pow(10,-11)*5.97*Math.pow(10,24);
     }//end of constructor
     
    // Time period of the orbit.
    public double getP(pos a){
    double P=2*Math.PI*Math.pow(Math.pow(a.a, 3)/mu,0.5);
    return P;
    }
    // Declination of the satellite at epoch in the orbit.
        public double getdelta(pos a){
        double delta = Math.asin(Math.sin(a.anom+a.per)*Math.sin(a.i));
        if((a.anom+a.per)>Math.PI){delta=-Math.abs(delta);}
        if((a.anom+a.per)<Math.PI){delta=Math.abs(delta);}
        return delta;
    }
    // Right ascension of the satellite at epoch in the orbit.
        public double getalpha(pos a){
        double amo = Math.atan(Math.cos(a.i)*Math.tan(a.anom+a.per));
        // this if statement fixes the effect of arctan used previous line.
       if((a.anom+a.per)>Math.PI && amo<0){amo+=2*Math.PI;}
        double alpha = amo+a.lon;
        return alpha;
    }
    // Radius of the position vector of the satellite at epoch.
            public double getr(pos a){
         double r = a.a*(1-a.e*a.e)/(1+a.e*Math.cos(a.anom));
         return r;
    }
    // velocity vector of the satellite at epoch.
        public vec getvv(pos a){
            // Radial velocity
        double  vr = mu/geth(a)*(a.e*Math.sin(a.anom))
            // Tangential velocity
        ,       vt = mu/geth(a)*(1+a.e*Math.cos(a.anom));
        vec res = Vector.getSum(Vector.getSCpro(getTheHat(a),vt),Vector.getSCpro(getrHat(a),vr));
        
        return res;
    }
    //Angular momentum per unit mass
    public double geth(pos a){
        double h = Math.pow(getr(a)*mu*(1+a.e*Math.cos(a.anom)),0.5);
        return h;} 
    //Unit radial component of the peri-focal frame
    public vec getrHat(pos a) {
        // B serves only to reduce the writting in the below formulae for x, y and z;
        double B = Math.cos(a.i)*Math.tan(a.anom+a.per);
        double alpha = getalpha(a)
        , delta = getdelta(a)
        , x = Math.cos(delta)*(Math.cos(a.anom+a.per)*Math.cos(a.lon)
                -Math.sin(a.anom+a.per)*Math.sin(a.lon)*Math.cos(a.i))/Math.cos(getdelta(a))
        , y = Math.cos(delta)*(Math.tan(a.lon)-B)/(B*Math.tan(a.lon)-1)*(Math.cos(a.anom+a.per)*Math.cos(a.lon)
                -Math.sin(a.anom+a.per)*Math.sin(a.lon)*Math.cos(a.i))/Math.cos(getdelta(a))
        , z = Math.sin(delta);
        vec rHat = new vec(x,y,z);
        return rHat;
    } 
    //Velocity vector of the satellite 
    public vec getvr(pos a){
        vec res = Vector.getSCpro(getrHat(a),getr(a));
        return res;
    }
    //Unit traversal component of the peri-focal frame
    public vec getTheHat(pos a){
        //dalp and ddel represent the infentesimal change in the right ascension and declination respectively,
        //with regards to an infinetly small change in true anomaly.
        double dalp= Math.pow((Math.cos(getalpha(a)-a.lon)/Math.cos(a.anom+a.per)),2)*Math.cos(a.i)
        , ddel= Math.sin(a.i)*Math.cos(a.anom+a.per)/(Math.cos(getdelta(a)))
        , x = -Math.cos(getdelta(a))*Math.sin(getalpha(a))*dalp-Math.cos(getalpha(a))*Math.sin(getdelta(a))*ddel
        , y = Math.cos(getdelta(a))*Math.cos(getalpha(a))*dalp-Math.sin(getalpha(a))*Math.sin(getdelta(a))*ddel
        , z = Math.cos(getdelta(a))*ddel;
        vec TheHat = new vec(x,y,z);
        return TheHat;  
    }
    //Unit vector for angular momentum per unit mass
    public vec gethHat(pos a){
        vec res = Vector.getCross(getrHat(a),getTheHat(a));
        return res;}
    //Angular momentum vector
    public vec getvh(pos a){
        vec res = Vector.getSCpro(Vector.getCross(getrHat(a),getTheHat(a)),geth(a));
        return res;
    }
}//end of class Maneuvers
