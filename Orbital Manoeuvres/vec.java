
/**
 * vec is a class illustrates vectors; it includes three variables, x, y and z that represent the
 * first, second and third component of a 3D Cartesian coordinating system respectively. and some 
 * of three-dimensional vector operations.
 * 
 * @author (Mohammad Kordzanganeh) 
 * @version (15 Feb 2017)
 * 
 * Update Details : 
 * Fully Annotated
 */
public class vec
{
    // instance variables - replace the example below with your own
    public double x,y,z;
     
   public vec(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;}
    // Magnitude of a vector
    public  double getMag(vec a){
        double mag = Math.pow(a.x*a.x+a.y*a.y+a.z*a.z,0.5);
        return mag;
    }
    // Subtraction of two vectors
        public vec getSub(vec a,vec b){
        double x = a.x-b.x
        , y = a.y-b.y
        , z = a.z-b.z;
        vec res = new vec(x,y,z);
        return res;
    }
    // Addition of two vectors
        public vec getSum(vec a,vec b){
        double x = a.x+b.x
        , y = a.y+b.y
        , z = a.z+b.z;
        vec res = new vec(x,y,z);
        return res;
    }
    // Multiplication of a vector by a scalar
        public  vec getSCpro(vec a, double b){
        vec scpro = new vec(a.x*b,a.y*b,a.z*b);
        return scpro;
    }
    // Cross product of two vectors
        public  vec getCross(vec a,vec b) {
        double x , y ,z ;
        x =a.y*b.z-a.z*b.y;
        y =a.z*b.x-a.x*b.z;
        z =a.x*b.y-a.y*b.x;
        vec cpro = new vec(x,y,z);
        return cpro;
    }
    
        public double getDot(vec a,vec b) {
            double dot;
            dot = a.x*b.x+a.y*b.y+a.z*b.z;
            return dot;
        }
}
