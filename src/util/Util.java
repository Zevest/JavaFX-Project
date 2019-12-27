package util;

public class Util {
	public static double min(double a, double[] b){
		double m  = a;
		for(double e : b)
			if(e < m)
				m = e;
		return m;
	}
	public static double max(double a, double[] b){
		double m  = a;
		for(double e : b)
			if(e > m)
				m = e;
		return m;
	}
	public static double abs(double a) {
		return (a > 0 ? a : -a);
	}
	
	public static double map(double value,double start1,double end1,double start2,double end2) {
		return (value/(end1-start1)*(end2-start2))+start2;
	}
	
	public static double lerp(double start, double end, double amt) {
		return (end-start) * amt;
	}
}
