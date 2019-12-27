package util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class color{
	public Paint col;
	float[] value;

	color(){}

	
    public static color __color(boolean isHSB, double r, double g, double b, double a, double maxR,double maxG,double maxB, double maxA) {

		color tmp = new color();
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
}

