import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * <a href="https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true">...</a>
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynomial function is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * Computes the f(x) value of the polynomial function at x.
	 * @param poly - polynomial function
	 * @param x - the value being searched
	 * @return f(x) - the polynomial function value at x.
	 */
	public static double f(double[] poly, double x) {
        double ans = 0;
        double exponent = 1;
        for(int i = 0 ;i < poly.length; i++) {
			ans += poly[i]*exponent;
            exponent *= x;
		}
		return ans;
	}
	/** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0.
	 * This function should be implemented recursively.
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double start = f(p,x1);
		double mid = (x1+x2)/2;
		double midY = f(p,mid);
		if (Math.abs(midY)<eps) {return mid;}
		if(midY *start<=0) {return root_rec(p, x1, mid, eps);}
		else {return root_rec(p, mid, x2, eps);}
	}
	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points, else returns null.
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		double [] ans;
		int lx = xx.length;
		int ly = yy.length;
		if(xx==null || yy==null || lx!=ly || lx<1 || lx>3)
            return null;
        if(lx == 3){
            ans = new double[3];
            double denom = (xx[0] - xx[1])*(xx[0] - xx[2])*(xx[1] - xx[2]);
            if (Math.abs(denom) < 1e-9) // if the denom is 0 (all 3 points are on a line(linear function)) it will return null
                return null;
            double A = (xx[2] * (yy[1] - yy[0]) + xx[1] * (yy[0] - yy[2]) + xx[0] * (yy[2] - yy[1])) / denom;
            double B = (pow(xx[2],2) * (yy[0] - yy[1]) + pow(xx[1],2) * (yy[2] - yy[0]) + pow(xx[0],2) * (yy[1] - yy[2])) / denom;
            double C = (xx[1] * xx[2] * (xx[1] - xx[2]) * yy[0] + xx[2] * xx[0] * (xx[2] - xx[0]) * yy[1] + xx[0] * xx[1] * (xx[0] - xx[1]) * yy[2]) / denom;

            ans[0] = C;
            ans[1] = B;
            ans[2] = A;
        } else if (lx == 2) {
            if (Math.abs(xx[0]-xx[1]) < 1e-9) // if the two coordinates are the same then there are infinity answers (but ill be returning null)
                return null;
            ans = new double[2];
            double A = (yy[1] - yy[0])/(xx[1] - xx[0]);
            double B = yy[0] - (A*xx[0]);

            ans[0] = B;
            ans[1] = A;
        }
        else {
            ans = new double[1];
            ans[0] = yy[0];
        }


        return ans;
	}
	/** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
	 * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
	 * @param p1 first polynomial function
	 * @param p2 second polynomial function
	 * @return true iff p1 represents the same polynomial function as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
    boolean ans = true;
    int high  = Math.max(p1.length,p2.length);
    for (int i = 0; i < high; i++)
        {
            if(Math.abs(f(p1,i)-f(p2,i)) > EPS)
            {
                return false;
            }

        }
    return ans;
	}

	/** 
	 * Computes a String representing the polynomial function.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynomial function represented as an array of doubles
	 * @return String representing the polynomial function:
	 */
	public static String poly(double[] poly) {
        StringBuilder sb = new StringBuilder("0");
		if(poly.length==0)
        {
             sb = new StringBuilder("0");
        }
		else {
            sb = new StringBuilder();
            for(int i = poly.length-1; i >= 0 ; i--)
            {
                if (Math.abs(poly[i]) > 1e-9)
                {
                    if ((poly[i] >= 0)&&(i != poly.length-1))
                        sb.append(" +");
                    else if ((poly[i]<0)&&(i != poly.length-1)) {
                        sb.append(" ");
                    }
                    if (i == 0)
                        sb.append(poly[i]);
                    else if (i == 1)
                        sb.append(poly[i]+"x");
                    else
                        sb.append(poly[i]+"x^"+i);
                }
            }
		}
        System.out.println(sb);
		return sb.toString();
	}
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        double ans = -1;
        double[] high, low;
        if (p1.length >= p2.length) {
            high = Arrays.copyOf(p1 ,p1.length);
            low = Arrays.copyOf(p2 ,p2.length);
        } else {
            high = Arrays.copyOf(p2 ,p2.length);
            low = Arrays.copyOf(p1 ,p1.length);
        }
        for (int i = 0; i < low.length; i++)
        {
            low[i] = (low[i]*-1);
        }

        double[] third = Arrays.copyOf(add(high,low) ,add(high,low).length);
        ans = root_rec(third,x1,x2,eps);
	return ans;
	}
	/**
	 * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
	 * This function computes an approximation of the length of the function between f(x1) and f(x2) 
	 * using n inner sample points and computing the segment-path between them.
	 * assuming x1 < x2. 
	 * This function should be implemented iteratively (none recursive).
	 * @param p - the polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = 0;
        double h = 0, h2 = 0;
        int i;
        for (i = 0; i < (numberOfSegments+1); i++) {
            h = i * ((x2 - x1) / (numberOfSegments + 1));
            h2 = (i + 1) * ((x2 - x1) / (numberOfSegments + 1));
            ans += vectorLength((x1 + h), f(p, x1 + h), x1 + h2, f(p, x1 + h2));
        }
        return ans;
    }
	
	/**
	 * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
	 * This function computes an approximation of the area between the polynomial functions within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynomial function
	 * @param p2 - second polynomial function
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynomial functions within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
		double ans = 0;
        double trapY = 0, trapX = 0;
        double[] high, low;
        if (p1.length >= p2.length) {
            high = Arrays.copyOf(p1 ,p1.length);
            low = Arrays.copyOf(p2 ,p2.length);
        } else {
            high = Arrays.copyOf(p2 ,p2.length);
            low = Arrays.copyOf(p1 ,p1.length);
        }
        for (int i = 0; i < low.length; i++)
        {
            low[i] = (low[i]*-1);
        }
        double[] third = Arrays.copyOf(add(high,low) ,add(high,low).length);
        double segDiv2 = (double) numberOfTrapezoid/2;
        for (int i = 0; i < numberOfTrapezoid; i++)
        {
            trapX = (x2 - x1)/numberOfTrapezoid;
            trapY = f(third,x1+(((x2-x1)/numberOfTrapezoid)*i)+((x2-x1)/(numberOfTrapezoid*2)));
            ans += trapY*trapX;
        }
		return Math.abs(ans);
	}
	/**
	 * This function computes the array representation of a polynomial function from a String
	 * representation. Note:given a polynomial function represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynomial function.
	 * @return
	 */
	public static double[] getPolynomFromString(String p) {
		double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        /** add you code below

         /////////////////// */
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
        int low = Math.min(p1.length,p2.length);
        double [] ans;
        if (p1.length >= p2.length)
            ans = Arrays.copyOf(p1,p1.length);
        else
            ans = Arrays.copyOf(p2,p2.length);
        for (int i = 0; i < low; i++)
            ans[i] = p1[i]+p2[i];
		return ans;
	}
	/**
	 * This function computes the polynomial function which is the multiplication of two polynomes (p1,p2)
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
        double[] ans = new double[p1.length+p2.length-1];
        for (int i = 0; i < p1.length; i++)
        {
            for (int j = 0; j < p2.length; j++)
            {
                ans[i+j] += p1[i]*p2[j];
            }
        }
		return ans;
	}
	/**
	 * This function computes the derivative of the p0 polynomial function.
	 * @param po
	 * @return
	 */
	public static double[] derivative (double[] po)
    {
        double [] ans;
        if (po.length != 0)
            ans = new double[po.length-1];
        else
        {
            ans = new double[po.length];
        }
        for (int i = 0; i < ans.length; i++)
        {
            ans[i]=po[i+1]*(i+1);
        }
		return ans;
	}

    /**
     * This function accepts 2 coordinates (x1,y1) and (x2,y2) and finds the distance between them.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double vectorLength (double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
    }

    /**
     * This function takes an array representing a polynome and returns it's integral polynome.
     * @param arr
     * @return
     */
    public static double[] integral (double[] arr){
        double [] ans = new double[arr.length+1];
        for (int i = 0; i < arr.length; i++)
        {
            ans[i+1] = (arr[i]/(i+1));
        }
        return ans;
    }

    /**
     * This function calculates a simple power operation while assuming the exponent is a natural number (a^b)
     * @param a - base value
     * @param b - Exponent
     * @return - a^b
     */
    public static double pow (double a, double b)
    {
        double pow = 1;
        for (int i = 0; i < b; i++)
        {
            pow = pow * a;
        }
        return pow;
    }
}
