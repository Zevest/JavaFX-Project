package util;

import java.util.Random;

public class Noise {
	private static Random randno = new Random();
	private static int width = 100, height = 100, depth = 100;
	private static int factor = 2;
	private static boolean initialized2D= false;
	private static boolean initialized3D= false;
	private static double[][][] gradient2D;
	private static double[][][][] gradient3D;
	
	private static void initND(int dim) {
		switch(dim) {
		case 2:
			gradient2D = new double[Noise.height*factor][Noise.width*factor][2];
			initialized2D= true;
			break;
		case 3:
			gradient3D = new double[Noise.depth][Noise.height][Noise.width][3];
			initialized3D= true;
			break;
		default:
			System.err.println("Error dim must 2 or 3 ");
			return;
		}
		switch(dim) {
		case 2:
			for(int j = 0; j < height*factor; ++j) 
				for(int i = 0; i < width*factor; ++i) {
					gradient2D[j][i][0] = randno.nextDouble();
					gradient2D[j][i][1] = randno.nextDouble();
				}
			break;
		case 3:
			for(int k = 0; k < depth; ++k) 
				for(int j = 0; j < height; ++j) 
					for(int i = 0; i < width; ++i) {
						gradient3D[k][j][i][0] = randno.nextDouble();
						gradient3D[k][j][i][1] = randno.nextDouble();
						gradient3D[k][j][i][2] = randno.nextDouble();
					}
			break;
		default:
			System.err.println("Error dim must 2 or 3 ");
			break;
		}
	}
	
	private static double dotGridGradient2D(int ix, int iy, double x, double y) {
	     // Compute the distance vector
	     double dx = x - (double)ix;
	     double dy = y - (double)iy;
	     // Compute the dot-product
	     return (dx*gradient2D[iy][ix][0] + dy*gradient2D[iy][ix][1]);
	 }
	
	private static double dotGridGradient3D(int ix, int iy, int iz, double x, double y, double z) {
	     // Compute the distance vector
	     double dx = x - (double)ix;
	     double dy = y - (double)iy;
	     double dz = z - (double)iz;	
	     // Compute the dot-product
	     return (dx*gradient3D[iz][iy][ix][0] + dy*gradient3D[iz][iy][ix][1] + dz*gradient3D[iz][iy][ix][2]);
	 }
	
	public static double noise2D(double x, double y) {
		 if(!initialized2D) {
			 initND(2);
		 }
	     // Determine grid cell coordinates
	     int x0 = (int) Math.floor(x);
	     int x1 = x0 + 1;
	     int y0 = (int) Math.floor(y);
	     int y1 = y0 + 1;
	 
	     // Determine interpolation weights
	     // Could also use higher order polynomial/s-curve here
	     double sx = x - (double)x0;
	     double sy = y - (double)y0;
	 
	     // Interpolate between grid point gradients
	     double n0, n1, ix0, ix1, value;
	     n0 = dotGridGradient2D(x0, y0, x, y);
	     n1 = dotGridGradient2D(x1, y0, x, y);
	     ix0 = Util.lerp(n0, n1, sx);
	     n0 = dotGridGradient2D(x0, y1, x, y);
	     n1 = dotGridGradient2D(x1, y1, x, y);
	     ix1 = Util.lerp(n0, n1, sx);
	     value = Util.lerp(ix0, ix1, sy);
	 
	     return value;
	 }
	
	public static double noise3D(double x, double y, double z) {
		 
		if(!initialized3D) {
			 initND(3);
		 }
		
	     // Determine grid cell coordinates
	     int x0 = (int) Math.floor(x);
	     int x1 = x0 + 1;
	     int y0 = (int) Math.floor(y);
	     int y1 = y0 + 1;
	     int z0 = (int) Math.floor(z);
	     int z1 = z0 + 1;
	 
	     // Determine interpolation weights
	     // Could also use higher order polynomial/s-curve here
	     double sx = x - (double)x0;
	     double sy = y - (double)y0;
	     double sz = z - (double)z0;
	     
	     // Interpolate between grid point gradients
	     double n0, n1, ix0, ix1, iy1, iy2, value;
	     n0 = dotGridGradient3D(x0, y0, z0, x, y, z);
	     n1 = dotGridGradient3D(x1, y0, z0, x, y, z);
	     ix0 = Util.lerp(n0, n1, sx);
	     n0 = dotGridGradient3D(x0, y1, z0, x, y, z);
	     n1 = dotGridGradient3D(x1, y1, z0, x, y, z);
	     ix1 = Util.lerp(n0, n1, sx);
	     iy1 = Util.lerp(ix0, ix1, sy);
	     
	     n0 = dotGridGradient3D(x0, y0, z1, x, y, z);
	     n1 = dotGridGradient3D(x1, y0, z1, x, y, z);
	     ix0 = Util.lerp(n0, n1, sx);
	     n0 = dotGridGradient3D(x0, y1, z1, x, y, z);
	     n1 = dotGridGradient3D(x1, y1, z1, x, y, z);
	     ix1 = Util.lerp(n0, n1, sx);
	     iy2 = Util.lerp(ix0, ix1, sy);
	     
	     value = Util.lerp(iy1, iy2, sz);
	     return value;
	 }
	
	public static void changeSeed(long seed) {
		initialized2D = false;
		initialized3D = false;
		randno.setSeed(seed);
	}
	
}
