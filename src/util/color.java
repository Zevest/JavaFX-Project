package util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class color{
	public Paint col;
	float[] value;

	color(){}
    public static color __color(int c) {
		color tmp = new color();
		float[] v = {c, c, c, 255};
		tmp.col = Color.grayRgb(c);
		tmp.value = v;
		return tmp;
	}
	
    public static color __color(int c,int a) {
		color tmp = new color();
		float[] v = {c, c, c, a};
		tmp.col = Color.grayRgb(c, a/255.0);
		tmp.value = v;
		return tmp;
	}
	
    public static color __color(int r, int g, int b) {
		color tmp = new color();
		float[] v = {r, g, b, 255};
		tmp.col = Color.rgb(r, g, b);
		tmp.value = v;
		return tmp;

	}
	
    public static color __color(double r, double g, double b, double a) {

		double red = Math.max(0, Math.min(1.0, r/255.0));
		double green = Math.max(0, Math.min(1.0, g/255.0));
		double blue = Math.max(0, Math.min(1.0, b/255.0));
		color tmp = new color();
		float[] v = {
				(float) (red*255),
				(float) (green*255),
				(float) (blue*255),
				(float) a
			};
		tmp.col = Color.color(red, green, blue, a/255.0);
		tmp.value = v;
		return tmp;
	}
    @Override
    public String toString() {
    	return "(" + value[0] + ", " + value[1] + ", " + value[2] + ", " + value[3] + ")";
    }
}

