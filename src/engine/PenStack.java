package engine;

import java.util.ArrayList;

import constant.COLOR_MODE;
import constant.DRAW_MODE;
import javafx.scene.paint.Color;

public class PenStack {
	protected ArrayList<Double> verticesX;
	protected ArrayList<Double> verticesY;
	
	double xOffset;
	double yOffset;
	Color fillColor;
	Color strokeColor;
	boolean isFilled;
	boolean  isStroked;
	short strokeWeightVal;
	String fontName;
	DRAW_MODE ellipseModeVal;
	DRAW_MODE rectModeVal;
	boolean makingShape;
	
	int textSizeVal;
	DRAW_MODE textAligneVal;
	COLOR_MODE colorModeVal;
	
	PenStack(Sketch s){
		verticesX = s.verticesX;
		verticesY = s.verticesY;
		xOffset = s.xOffset;
		yOffset = s.yOffset;
		fillColor = s.fillColor;
		strokeColor = s.strokeColor;
		isFilled = s.isFilled;
		isStroked = s.isStroked;
		strokeWeightVal = s.strokeWeightVal;
		fontName = s.fontName;
		ellipseModeVal = s.ellipseModeVal;
		rectModeVal = s.rectModeVal;
		makingShape = s.makingShape;
		textSizeVal = s.textSizeVal;
		colorModeVal = s.colorModeVal;
	}
	
}

