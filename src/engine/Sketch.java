package engine;

import java.util.ArrayList;

import constant.COLOR_MODE;
import constant.DRAW_MODE;
import constant.MOUSE_BUTTON;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import util.FileManager;
import util.Util;
import util.color;





public class Sketch {

	ArrayList<Double> verticesX;
	ArrayList<Double> verticesY;
	Color fillColor = Color.WHITE;
	Color strokeColor = Color.BLACK;
	boolean isFilled = true;
	boolean isStroked = true;
	short strokeWeightVal = 1;
	String fontName;
	double xOffset = 0;
	double yOffset = 0;
	public double PI = Math.PI;
	public double HALF_PI = PI/2.0;
	public double TWO_PI = 2*PI;
	public short width = 200, height = 200; 
	boolean isFullScreen  = false;
	
	DRAW_MODE ellipseModeVal = DRAW_MODE.CENTER;
	DRAW_MODE rectModeVal = DRAW_MODE.CORNER;
	boolean makingShape = false;
	
	int textSizeVal = 15;
	DRAW_MODE textAligneVal = DRAW_MODE.LEFT;
	COLOR_MODE colorModeVal = COLOR_MODE.RGB;
	
	DRAW_MODE imageModeVal = DRAW_MODE.CENTER;
	
	private ArrayList<PenStack> infoStack = new ArrayList<PenStack>();
	
	public float frameRate;
	
	private Canvas can; 
	GraphicsContext pen;
	private float targetFrameRate = 60f;
	public long frameCount;
	boolean isLoop = true;
	
	//Mouse
	public float mouseX = 0;
	public float mouseY = 0;
	public MOUSE_BUTTON mouseButton = MOUSE_BUTTON.NONE;
	public String key;
	public KeyCode keyCode;
	public final String CODED = "CODED";
	private final int useless = FileManager.init();
	
	public Sketch(){}
	
	public void setup() {}
	
	public void draw() {}
	
	public void mousePressed() {}
	
	public void mouseReleased() {}
	
	public void keyPressed() {}
	
	public void keyReleased() {}
	
	public void mouseDragged() {}
		
	public final void size(int w, int h) {
		width = (short)w;
		can.setWidth(width);
		height = (short)h;
		can.setHeight(height);
	}
	
	public final void fullScreen() {
		isFullScreen = true;
		Core.setFullScreen();
		width = (short) Core.width;
		height = (short) Core.height;
		can.setWidth(width);
		can.setHeight(height);
		
	}
	
	protected final void setContext(Canvas canvas) {
		can = canvas;
		pen = can.getGraphicsContext2D();
	}


	
	//utility Function
	
	public final float min(double a, double ...other) {
		return (float) Util.min(a, other);
	}
	
	public final float max(double a, double ...other) {
		return (float) Util.max(a, other);
	}
	
	public final float abs(double a) {
		return (float) Util.abs(a);
	}
	
	public final float log(double a) {
		return (float) Math.log(a);
	}
	
	public final int _int(float o) {
		return (int) o;
	}
	public final float _float(int o) {
		return (float) o;
	}
	public final String _String(Object o) {
		return o.toString();
	}
	
	public final color color(int c) {
		return color.__color(c);
		
	}
	
	public final color color(int c,int a) {
		return color.__color(c, a);
	}
	
	public final color color(int r, int g, int b) {
		switch(colorModeVal) {
		case RGB:
			return color.__color(r, g, b);
		case HSB:
			double red = Math.max(0, Math.min(1.0, r/255.0));
			double green = Math.max(0, Math.min(1.0, g/255.0));
			double blue = Math.max(0, Math.min(1.0, b/255.0));
			return color.__color(red, green, blue, 1.0);
	}
		
		return (color.__color(r, g, b));
	}
	
	public final color color(int r, int g, int b, int a) {
		double red = Math.max(0, Math.min(1.0, r/255.0));
		double green = Math.max(0, Math.min(1.0, g/255.0));
		double blue = Math.max(0, Math.min(1.0, b/255.0));
		double alpha = Math.max(0, Math.min(1.0, a/255.0));
		switch(colorModeVal) {
			case RGB:
				return color.__color(red, green, blue, alpha);
			case HSB:
				return color.__color(red, green, blue, alpha);
		}
		return null;
	}
	
	public final void println(Object o, Object ... other) {
		String buffer = o.toString();
		for(Object e: other) {
			if(e != null)
				buffer += " " + e.toString();
			else
				buffer += " null";
		}
		System.out.println(buffer);
	}

	public final void print(Object o, Object ... other) {
		String buffer = o.toString();
		for(Object e: other) {
			buffer += " " + e.toString();
		}
		System.out.print(buffer);
	}
	
	public final float random(double max) {
		return (float)(Math.random() * max);
	}
	
	
	public final float random(double min, double max) {
		return (float)(min + Math.random() * (max - min));
	}

	public final void frameRate(float rate) {
		targetFrameRate = rate;
	}

	protected final float getTargetFrameRate() {
		return targetFrameRate;
	}

	public final void loop() {
		isLoop = true;
	}

	public final void noLoop() {
		isLoop = false;
	}

	public final float dist(double x1, double y1, double x2, double y2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	public final float dist(double x1, double y1, double z1, double x2, double y2, double z2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
	}

	/// Draw function
	public final void clear() {
		pen.clearRect(0, 0, width, height);
	}

	public final void background(color color) {
		print(color.col);
		pen.setFill(color.col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void background(double val) {
		double color = Math.max(0, Math.min(1.0, val/255.0));
		switch(colorModeVal) {
			case RGB:
				
				pen.setFill(Color.color(color, color, color));
				break;
			case HSB:
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

	public final void fill(color color) {
		pen.setFill(color.col);
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

	public final void stroke(color color) {
		isStroked = true;
		pen.setStroke(color.col);
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
	
	public final void imageMode(DRAW_MODE mode) {
		imageModeVal = mode;
	}
	
	public final void line(double x1, double y1, double x2, double y2) {
		pen.strokeLine(x1 + xOffset, y1 + yOffset, x2 + xOffset, y2 + yOffset);
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
		switch(rectModeVal) {
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
			System.err.println("Error: Undefined rectangle Mode " + rectModeVal);
			System.exit(-1);
			
		}
	}
	
	public final void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		double[] posX = {x1, x2, x3};
		double[] posY = {y1, y2, y3};
		if(isFilled) {
			pen.fillPolygon(posX, posY, 3);
		}if(isStroked) {
			pen.strokePolygon(posX, posY, 3);
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
	
	public final void pushMatrix() {
		pen.save();
		infoStack.add(new PenStack(this));
	}
	
	public void popMatrix() {
		PenStack s = infoStack.get(infoStack.size()-1);
		pen.restore();
		infoStack.remove(infoStack.size()-1);
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
	
	public final void textFont(String name) {
		fontName = name; 
		pen.setFont(new Font(fontName, textSizeVal));
	}
	
	public final void rotate(double angle) {
		//pen.rotate(angle);
		pen.rotate(angle *(180.0/Math.PI));
	}
	
	
	public final void text(String text, double x, double y) {
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
	
	
	public final void translate(double x, double y) {
		pen.translate(-xOffset, -yOffset);
		xOffset = x;
		yOffset = y;
		pen.translate(xOffset, yOffset);
	}
	
	
	public final Image loadImage(String Name) {
		String url= FileManager.getFileUrl(Name);
		if(url != null)
			return new Image(url);
		System.err.println("Error: image not found.");
		return null;
	}
	
	public final void image(Image img, double x, double y) {
		switch(imageModeVal) {
			case CENTER:
				pen.drawImage(img, x - img.getWidth()/2, y - img.getHeight()/2);
				break;
			case CORNER:
				pen.drawImage(img, x, y);
				break;
			default:
				System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	}
	
	public final void image(Image img, double x, double y, double w, double h) {
		switch(imageModeVal) {
			case CENTER:
				pen.drawImage(img, x - w/2, y - h/2, w, h);
				break;
			case CORNER:
				pen.drawImage(img, x, y, w, h);
				break;
			default:
				System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	}
	
	public final void image(Image img, double dx, double dy, double dw, double dh, double sx, double sy, double sw, double sh) {

		switch(imageModeVal) {
			case CENTER:
				pen.drawImage(img, sx, sy, sw, sh, dx - dw/2, dy - dh/2, dw, dh);
				break;
			case CORNER:
				pen.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
				break;
			default:
				System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	} 
}
