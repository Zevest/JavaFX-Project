package util;

import java.util.Random;

public class Util {
	private static Random randomno = new Random();
	
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
	
	public static double constrain(double val, double low, double high) {
		return (val < high ? (val > low ? val: low) : high);
	}
	
	public static double map(double value,double start1,double end1,double start2,double end2) {
		return (value/(end1-start1)*(end2-start2))+start2;
	}
	
	public static double lerp(double start, double end, double amt) {
		return start + (end-start) * amt;
	}
	
	public static final double random() {
		return (randomno.nextDouble());
	}
	
	public static final double random(double max) {
		return (randomno.nextDouble() * max);
	}
	
	
	public static final double random(double min, double max) {
		return (min + randomno.nextDouble() * (max - min));
	}
	
	
	public static double randomGaussian() {
		return  randomno.nextGaussian();
	}
	
	public static void randomSeed(long seed) {
		randomno.setSeed(seed);
	}
}
