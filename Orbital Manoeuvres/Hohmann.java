

public class Hohmann extends Maneuvers
{
    pos pi,pf;
    //Constructor
    public Hohmann(pos pi,pos pf)
    {
        this.pi = pi;
        this.pf = pf;
    }
    //Position of the satellite just before the first impulse
    public pos posHohStarter(){
        
        pos res = new pos(pi.a,pi.e,pi.i,Math.PI,pi.lon,pi.per);
        return res;
    }
    // Position of the satellite just after the first impulse
    public pos posHoh1(){
        double RP = (pi.a*(1-pi.e))
            ,  a = (RP+pf.a)/2
            ,  e = 1-2*RP/(RP+pf.a)
            ,  i = pi.i
            ,  per = 0
            ,  lon = pi.lon
            ,  anom = 0;
        pos res = new pos(a,e,i,anom,lon,per);
        return res;
    }
    // Position of the satellite just before the second impulse
    public pos posHoh2(){
        double RP = (pi.a*(1-pi.e))
            ,  a = (RP+pf.a)/2
            ,  e = 1-2*RP/(RP+pf.a)
            ,  i = pi.i
            ,  per = 0
            ,  lon = pi.lon
            ,  anom = Math.PI;
        pos res = new pos(a,e,i,anom,lon,per);
        return res;
    }
    // Position of the satellite just after the second impulse
    public pos posHoh3(){
        double a = pf.a
            ,  e = 0
            ,  i = pi.i
            ,  per = 0
            ,  lon = pi.lon
            ,  anom = Math.PI+pi.per;
        pos res = new pos(a,e,i,anom,lon,per);
        return res;
    }
    //time elapsed before the first impulse
    public double Time1(){
        double time = getP(pi)/2;
        return time;
    }
    //time of the maneuver
    public double Time2(){
        double time = getP(posHoh2())/2;
        return time;
    }
    // Vector of the first impulse per unit mass
   public vec getHohdv1(){
        vec res = Vector.getSub(getvv(posHoh1()),getvv(posHohStarter()));
        return res;
    }
    // Vector of the second impulse per unit mass
    public vec getHohdv2(){
        vec res = Vector.getSub(getvv(posHoh2()),getvv(posHoh3()));
        return res;
    }
    // Energy needed for the transfer
    public double getEnergyHoh(){
        double E = (mu/(2*pi.a)-mu/(2*pf.a));
        return E;
    }
}
