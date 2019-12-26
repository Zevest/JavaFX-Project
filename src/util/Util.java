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
	
}
