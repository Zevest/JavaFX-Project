package engine;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



enum DRAW_MODE{
	CORNER,
	CENTER,	
	LEFT,
	RIGHT
}

enum COLOR_MODE{
	RGB,
	HSB,
}

public class Sketch {

	protected ArrayList<Double> verticesX;
	protected ArrayList<Double> verticesY;
	protected Color fillColor = Color.WHITE;
	protected Color strokeColor = Color.BLACK;
	protected boolean isFilled = true;
	protected boolean isStroked = true;
	protected int strokeWeightVal = 1;
	protected String fontName;
	
	public int width = 200, height = 200; 
	
	protected DRAW_MODE ellipseModeVal;
	protected DRAW_MODE rectModeVal;
	protected boolean makingShape = false;
	
	protected int textSizeVal = 15;
	protected DRAW_MODE textAligneVal = DRAW_MODE.LEFT;
	protected COLOR_MODE colorModeVal = COLOR_MODE.RGB;
	
	public float frameRate;
	
	private Canvas can; 
	protected GraphicsContext pen;
	
	
	Sketch(){}
	
	public void setup() {
		size(1000, 1000);
		
	}
	
	public void draw() {
		ellipseMode(DRAW_MODE.CENTER);
		stroke(Color.GRAY);
		strokeWeight(10);
		line(width/2, height/2, 3*width/4, 3*height/4);
		fill(200, 20, 30);
		rect(width/2, height/2, 500,500);
		ellipseMode(DRAW_MODE.CORNER);
		fill(0, 20, 180);
		noStroke();
		rect(width/2, height/2, 500,500);
		strokeWeight(2);
		stroke(0);
		line(width/2, height/2, 3*width/4, 3*height/4);
	}
		
	public final void size(int w, int h) {
		width = w;
		can.setWidth(width);
		height = h;
		can.setHeight(height);
	}
	
	protected final void setContext(Canvas canvas) {
		can = canvas;
		pen = can.getGraphicsContext2D();
	}
	
	public final void clear() {
		pen.clearRect(0, 0, width, height);
	}
	
	public final void background(Color color) {
		pen.setFill(color);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void background(int val) {
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.rgb(val, val, val));
				break;
			case HSB:
				double color = Math.max(0, Math.min(1.0, val/255.0));
				pen.setFill(Color.hsb(color, color, color));
				break;
		}
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void background(int r, int g, int b) {
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.rgb(r, g, b));
				break;
			case HSB:
				double red = Math.max(0, Math.min(1.0, r/255.0));
				double green = Math.max(0, Math.min(1.0, g/255.0));
				double blue = Math.max(0, Math.min(1.0, b/255.0));
				pen.setFill(Color.color(red, green, blue));
				break;
		}
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void background(int r, int g, int b, int a) {
		double red = Math.max(0, Math.min(1.0, r/255.0));
		double green = Math.max(0, Math.min(1.0, g/255.0));
		double blue = Math.max(0, Math.min(1.0, b/255.0));
		double alpha = Math.max(0, Math.min(1.0, a/255.0));
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.color(red, green, blue, alpha));
				break;
			case HSB:
				pen.setFill(Color.hsb(red, green, blue, alpha));
				break;
		}
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void background(String webColor) {
		pen.setFill(Color.web(webColor));
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void fill(Color color) {
		pen.setFill(color);
		fillColor = (Color) pen.getFill();
	}
	
	public final void fill(int val) {
		isFilled = true;
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.rgb(val, val, val));
				break;
			case HSB:
				double color = Math.max(0, Math.min(1.0, val/255.0));
				pen.setFill(Color.hsb(color, color, color));
				break;
		}
		fillColor = (Color) pen.getFill();
		
	}
	
	public final void fill(int r, int g, int b) {
		isFilled = true;
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.rgb(r, g, b));
				break;
			case HSB:
				double red = Math.max(0, Math.min(1.0, r/255.0));
				double green = Math.max(0, Math.min(1.0, g/255.0));
				double blue = Math.max(0, Math.min(1.0, b/255.0));
				pen.setFill(Color.color(red, green, blue));
				break;
		}
		fillColor = (Color) pen.getFill();
		
	}
	
	public final void fill(int r, int g, int b, int a) {
		isFilled = true;
		double red = Math.max(0, Math.min(1.0, r/255.0));
		double green = Math.max(0, Math.min(1.0, g/255.0));
		double blue = Math.max(0, Math.min(1.0, b/255.0));
		double alpha = Math.max(0, Math.min(1.0, a/255.0));
		switch(colorModeVal) {
			case RGB:
				pen.setFill(Color.color(red, green, blue, alpha));
				break;
			case HSB:
				pen.setFill(Color.hsb(red, green, blue, alpha));
				break;
		}
		fillColor = (Color) pen.getFill();
	}
	
	public final void fill(String webColor) {
		isFilled = true;
		pen.setFill(Color.web(webColor));
		fillColor = (Color) pen.getFill();
	}
	
	public final void noFill() {
		isFilled = false;
		pen.setFill(null);
		fillColor = (Color) pen.getFill();
	}
	
	public final void stroke(Color color) {
		isStroked = true;
		pen.setStroke(color);
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void stroke(int val) {
		isStroked = true;
		switch(colorModeVal) {
			case RGB:
				pen.setStroke(Color.rgb(val, val, val));
				break;
			case HSB:
				double color = Math.max(0, Math.min(1.0, val/255.0));
				strokeColor = Color.hsb(color, color, color);
				break;
		}
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void stroke(int r, int g, int b) {
		isStroked = true;
		switch(colorModeVal) {
			case RGB:
				pen.setStroke(Color.rgb(r, g, b));
				break;
			case HSB:
				double red = Math.max(0, Math.min(1.0, r/255.0));
				double green = Math.max(0, Math.min(1.0, g/255.0));
				double blue = Math.max(0, Math.min(1.0, b/255.0));
				pen.setStroke(Color.color(red, green, blue));
				break;
		}
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void stroke(int r, int g, int b, int a) {
		isStroked = true;
		double red = Math.max(0, Math.min(1.0, r/255.0));
		double green = Math.max(0, Math.min(1.0, g/255.0));
		double blue = Math.max(0, Math.min(1.0, b/255.0));
		double alpha = Math.max(0, Math.min(1.0, a/255.0));
		switch(colorModeVal) {
			case RGB:
				pen.setStroke(Color.color(red, green, blue, alpha));
				break;
			case HSB:
				pen.setStroke(Color.hsb(red, green, blue, alpha));
				break;
		}
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void stroke(String webColor) {
		isStroked = true;
		pen.setStroke(Color.web(webColor));
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void noStroke() {
		isStroked = false;
		pen.setStroke(null);
		strokeColor = (Color) pen.getStroke();
	}
	
	public final void strokeWeight(double size) {
		pen.setLineWidth(size);
	}
	
	public final void colorMode(COLOR_MODE mode) {
		colorModeVal = mode;
	}
	
	public final void ellipseMode(DRAW_MODE mode) {
		ellipseModeVal = mode;
	}
	
	public final void rectMode(DRAW_MODE mode) {
		rectModeVal = mode;
	}
	
	public final void line(double x1, double y1, double x2, double y2) {
		pen.strokeLine(x1, y1, x2, y2);
	}
	
	public final void ellipse(double x, double y, double w, double h) {
		switch(ellipseModeVal) {
			case CORNER:
				if(isFilled)
					pen.fillOval(x, y, w, h);
				if(isStroked)
					pen.strokeOval(x, y, w, h);
				break;
			case CENTER:
				if(isFilled)
					pen.fillOval(x-w/2, y-h/2, w, h);
				if(isStroked)
					pen.strokeOval(x-w/2, y-h/2, w, h);
				break;
			default:
				System.err.println("Error: Undefined ellipse Mode " + ellipseModeVal);
				System.exit(-1);
				
		}
	}
	
	public final void ellipse(double x, double y, double w) {
		switch(ellipseModeVal) {
			case CORNER:
				if(isFilled)
					pen.fillOval(x, y, w, w);
				if(isStroked)
					pen.strokeOval(x, y, w, w);
				break;
			case CENTER:
				if(isFilled)
					pen.fillOval(x-w/2, y-w/2, w, w);
				if(isStroked)
					pen.strokeOval(x-w/2, y-w/2, w, w);
				break;
			default:
				System.err.println("Error: Undefined ellipse Mode " + ellipseModeVal);
				System.exit(-1);
				
		}
	}
	
	public final void rect(double x, double y, double w, double h) {
		switch(ellipseModeVal) {
		case CORNER:
			if(isFilled)
				pen.fillRect(x, y, w, h);
			if(isStroked)
				pen.strokeRect(x, y, w, h);
			break;
		case CENTER:
			if(isFilled)
				pen.fillRect(x-w/2, y-h/2, w, h);
			if(isStroked)
				pen.strokeRect(x-w/2, y-h/2, w, h);
			break;
		default:
			System.err.println("Error: Undefined ellipse Mode " + ellipseModeVal);
			System.exit(-1);
			
	}
	}
	
	public final void beginShape() {
		makingShape = true;
		verticesX = new ArrayList<Double>();
		verticesY = new ArrayList<Double>();
	}
	
	public final void vextex(double x, double y) {
		verticesX.add(x);
		verticesY.add(y);
	}
	
	public final void endShape() {	
		int len = Math.min(verticesX.size(), verticesY.size());
		double[] posX = new double[len], posY = new double[len];
		for(int i = 0; i < len; ++i) {
			posX[i] = verticesX.get(i);
			posY[i] = verticesY.get(i);
		}
		if(isFilled) 
			pen.fillPolygon(posX, posY, len);
		else
			pen.strokePolygon(posX, posY, len);
		makingShape = false;
	}
	
	public final void textFont(String name) {
		fontName = name; 
		pen.setFont(new Font(fontName, textSizeVal));
	}
	
	public final void text(String text, int x, int y) {
		if(isFilled)
			pen.fillText(text, x, y);
		if(isStroked)
			pen.strokeText(text, x, y);
	}
	
	public final void textAlign(DRAW_MODE mode) {
		textAligneVal = mode;
		switch(textAligneVal) {
			case LEFT:
				pen.setTextAlign(TextAlignment.LEFT);
				break;
			case CENTER:
				pen.setTextAlign(TextAlignment.CENTER);
				break;
			case RIGHT:
				pen.setTextAlign(TextAlignment.RIGHT);
				break;
			default:
				System.err.println("Error: Undefined AlignmentMode " + mode);
				System.exit(-1);
		}
	}

	public final void textSize(int size) {
		textSizeVal = size; 
		pen.setFont(new Font(fontName, textSizeVal));
	}

}
