package util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class color{
	public Paint col;
	float[] value;
	private int argb = 0;

	color(){}

	
    public static color __color(boolean isHSB, double r, double g, double b, double a, double maxR,double maxG,double maxB, double maxA) {

		color tmp = new color();
		
		/*int step = (int) (b*255);
		System.out.println(step);
		step = step & 0xff;
		System.out.println(step);*/
		
		
		long blue = (long)(b*255) & 0xff;
		long green = ((long)(g*255)) & 0xff;
		long red =  ((long)(r*255)) & 0xff;
		long alpha = ((long)(a*255)) & 0xff;
		long finalCol = blue + (green<<8) + (red<<16) + (alpha<<24);
		tmp.setArgb((int)finalCol);
		float[] v = {
				(float) (r*maxR),
				(float) (g*maxG),
				(float) (b*maxB),
				(float) (a*maxA)
			};
		if(isHSB)
			tmp.col = Color.hsb(r, g, b,a);
		else
			tmp.col = Color.color(r, g, b, a);
		tmp.value = v;
		return tmp;
	}
    
    @Override
    public String toString() {
    	return "(" + value[0] + ", " + value[1] + ", " + value[2] + ", " + value[3] + ")";
    }


	public int getArgb() {
		return argb;
	}


	public void setArgb(int argb) {
		this.argb = argb;
	}
}

