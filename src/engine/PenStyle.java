package engine;

import java.util.ArrayList;

import constant.SETTINGS;
import javafx.scene.paint.Color;

public class PenStyle {
	
		
	Color fillColor;
	Color strokeColor;
	boolean isFilled;
	boolean  isStroked;
	short strokeWeightVal;
	String fontName;
	SETTINGS ellipseModeVal;
	SETTINGS rectModeVal;
	int textSizeVal;
	SETTINGS textAligneVal;
	SETTINGS colorModeVal;
	
	PenStyle(Sketch s){
		fillColor = s.fillColor;
		strokeColor = s.strokeColor;
		isFilled = s.isFilled;
		isStroked = s.isStroked;
		strokeWeightVal = s.strokeWeightVal;
		fontName = s.fontName;
		ellipseModeVal = s.ellipseModeVal;
		rectModeVal = s.rectModeVal;
		textSizeVal = s.textSizeVal;
		colorModeVal = s.colorModeVal;
	}
		

}
