package engine;

import java.util.ArrayList;

import constant.SETTINGS;
import javafx.scene.paint.Color;

public class PenStack {
	protected ArrayList<Double> verticesX;
	protected ArrayList<Double> verticesY;
	
	double xOffset;
	double yOffset;
	double lastAngle;
	double xScale;
	double yScale;
	Color fillColor;
	Color strokeColor;
	boolean isFilled;
	boolean  isStroked;
	short strokeWeightVal;
	String fontName;
	SETTINGS ellipseModeVal;
	SETTINGS rectModeVal;
	boolean makingShape;
	
	int textSizeVal;
	SETTINGS textAligneVal;
	SETTINGS colorModeVal;
	
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
		lastAngle = s.lastAngle;
		xScale = s.xScale;
		yScale = s.yScale; 
	}
	
}

