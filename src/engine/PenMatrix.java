package engine;

import java.util.ArrayList;

import constant.SETTINGS;
import javafx.scene.paint.Color;

public class PenMatrix {
	protected ArrayList<Double> verticesX;
	protected ArrayList<Double> verticesY;
	
	double xOffset;
	double yOffset;
	double lastAngle;
	double xScale;
	double yScale;
	
	boolean makingShape;
	
	PenMatrix(Sketch s){
		verticesX = s.verticesX;
		verticesY = s.verticesY;
		xOffset = s.xOffset;
		yOffset = s.yOffset;
		
		makingShape = s.makingShape;
		
		lastAngle = s.lastAngle;
		xScale = s.xScale;
		yScale = s.yScale; 
	}
	
}

