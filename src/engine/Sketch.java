package engine;

import java.util.ArrayList;

import constant.CURSOR;
import constant.SETTINGS;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import util.FileManager;
import util.Noise;
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
	private float fontSizeVal = 11;
	private Font currentFont = new Font(fontSizeVal);
	
	double xOffset = 0;
	double yOffset = 0;
	double xScale = 1;
	double yScale = 1;
	
	public float PI = (float) Math.PI;
	public float HALF_PI = (float) PI/2.0f;
	public float TWO_PI = (float) 2*PI;
	public short width = 200, height = 200;
	public static final SETTINGS TOP = SETTINGS.TOP;
	public static final SETTINGS BOTTOM = SETTINGS.BOTTOM;
	public static final SETTINGS CENTER = SETTINGS.CENTER;
	public static final SETTINGS LEFT = SETTINGS.LEFT;
	public static final SETTINGS RIGHT = SETTINGS.RIGHT;
	public static final SETTINGS CORNER = SETTINGS.CORNER;
	public static final SETTINGS HSB = SETTINGS.HSB;
	public static final SETTINGS RGB = SETTINGS.RGB;
	public static final SETTINGS NEXT = SETTINGS.NEXT;
	public static final SETTINGS BACK = SETTINGS.BACK;
	public static final CURSOR ARROW = CURSOR.ARROW;
	public static final CURSOR CROSS = CURSOR.CROSS;
	public static final CURSOR HAND = CURSOR.HAND;
	public static final CURSOR MOVE = CURSOR.MOVE;
	public static final CURSOR TEXT = CURSOR.TEXT;
	public static final CURSOR WAIT = CURSOR.WAIT;

	boolean isFullScreen  = false;
	boolean finished = false;
	
	
	CURSOR cursor = ARROW;
	
	SETTINGS ellipseModeVal = SETTINGS.CENTER;
	SETTINGS rectModeVal = SETTINGS.CORNER;
	boolean makingShape = false;
	
	int textSizeVal = 15;
	private SETTINGS textAligneValX = SETTINGS.LEFT;
	private SETTINGS textAligneValY = SETTINGS.BOTTOM;
	SETTINGS colorModeVal = SETTINGS.RGB;
	private double maxR=255, maxG=255, maxB=255, maxA=255;
	private SETTINGS imageModeVal = SETTINGS.CENTER;
	
	private ArrayList<PenStack> infoStack = new ArrayList<PenStack>();
	
	public float frameRate;
	public float deltaTime;
	public float deltaTimeMillis;
	
	private Canvas can; 
	GraphicsContext pen;
	private float targetFrameRate = 60f;
	public long frameCount;
	boolean isLoop = true;
	
	//Mouse
	public float mouseX = 0;
	public float mouseY = 0;
	public SETTINGS mouseButton = SETTINGS.NONE;
	public String key;
	public KeyCode keyCode;
	public final String CODED = "CODED";
	private final int useless = FileManager.init();
	
	
	public Sketch(){}
	
	private static SETTINGS SETTINGS(int i) {
		// TODO Auto-generated method stub
		return null;
	}

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
	
	public final void exit() {
		finished = true;
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


	
	//////////////// utility Function ///////////////
	
	public final int _int(float o) {
		return (int) o;
	}
	public final float _float(int o) {
		return (float) o;
	}
	public final String _String(Object o) {
		return o.toString();
	}
	
	////////////////      MATH       ////////////////
	public final float min(double a, double ...other) {
		return (float) Util.min(a, other);
	}
	
	public final float min(double[] a) {
		return (float) Util.min(a[0],  a);
	}
	
	public final float max(double a, double ...other) {
		return (float) Util.max(a, other);
	}
	
	public final float max(double[] a) {
		return (float) Util.max(a[0],  a);
	}
	
	public final float abs(double a) {
		return (float) Math.abs(a);
	}
	
	public final float log(double a) {
		return (float) Math.log(a);
	}
	
	public final float floor(double a) {
		return (float) Math.floor(a);
	}
	
	public final float ceil(double a) {
		return (float) Math.ceil(a);
	}
	
	public final float round(double a) {
		return (float) Math.round(a);
	}
	
	public final float exp(double a) {
		return (float) Math.exp(a);
	}
	
	public final float mag(double a, double b) {
		return (float) Math.sqrt(a*a + b*b);
	}
	
	public final float mag(double a, double b, double c) {
		return (float) Math.sqrt(a*a + b*b + c*c);
	}
	
	public final float map(double value,double start1,double end1,double start2,double end2) {
		return (float) Util.map(value, start1, end1, start2, end2);
	}
	
	public final float norm(double value, double start, double stop) {
		return (float) Util.map(value, start, stop, 0, 1);
	}
	
	public final float lerp(double start, double end, double amt) {
		return (float) Util.lerp(start, end, amt);
	}
	
	public static float constrain(double val, double low, double high) {
		return (float) Util.constrain(val, low, high);
	}
	
	public static int constrain(long val, long low, long high) {
		return (int)  Util.constrain(val, low, high);
	}
	
	public final float dist(double x1, double y1, double x2, double y2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	public final float dist(double x1, double y1, double z1, double x2, double y2, double z2) {
		return (float) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
	}
	
	public final float sq(double a) {
		return  (float) (a*a);
	}
	
	public final float pow(double a, double b) {
		return(float) Math.pow(a, b);
	}
	
	public final float sqrt(double a ) {
		return (float) Math.sqrt(a);
	}
	
	
	////////////// Trigonometry ////////////////////////
	public final float sin(double a) {
		return (float) Math.sin(a);
	}
	
	public final float cos(double a) {
		return (float) Math.cos(a);
	}

	public final float asin(double a) {
		return (float) Math.asin(a);
	}
	
	public final float acos(double a) {
		return (float) Math.acos(a);
	}
	
	public final float tan(double a) {
		return (float) Math.tan(a);
	}
	
	public final float atan(double a) {
		return (float) Math.atan(a);
	}
	
	public final float atan2(double x, double y) {
		return (float) Math.atan2(x, y);
	}
	
	public final float degrees(double angle) {
		return (float) Math.toRadians(angle);
	}
	
	public final float radians(double angle) {
		return (float) Math.toDegrees(angle);
	}
	
	///////////// Random ///////////////////
	
	public final float random(double max) {
		return (float) Util.random(max);
	}
	
	
	public final float random(double min, double max) {
		return (float) Util.random(min, max);
	}
	
	
	public final float randomGaussian() {
		return (float) Util.randomGaussian();
	}
	
	public final void randomSeed(long seed) {
		Util.randomSeed(seed);
	}
	
	public final void noiseSeed(long seed) {
		Noise.changeSeed(seed);
	}
	
	public final float noise(double x) {
		return (float) Noise.noise2D((float)x, 0f);
	}
	
	public final float noise(double x, double y) {
		return (float) Noise.noise2D((float)x, (float)y);
	}
	public final float noise(double x, double y, double z) {
		return (float) Noise.noise3D((float)x, (float)y, (float)z);
	}
	
	public final color color(double c) {
		double nColor = Math.max(0, Math.min(1.0, c/maxR));
		switch(colorModeVal) {
			case RGB:
				return color.__color(false, nColor, nColor, nColor, 1.0, maxR, maxR, maxR, maxA);
			case HSB: 
				return color.__color(true, nColor, nColor, nColor, 1.0, maxR, maxR, maxR, maxA);
			default:
				return null;
		}
		
	}
	
	public final color color(double c,double a) {
		double nColor = Math.max(0, Math.min(1.0, c/maxR));
		double alpha = Math.max(0, Math.min(1.0, a/maxA));
		switch(colorModeVal) {
			case RGB:
				return color.__color(false, nColor, nColor, nColor, alpha, maxR, maxR, maxR, maxA);
			case HSB: 
				return color.__color(true, nColor, nColor, nColor, alpha, maxR, maxR, maxR, maxA);
			default:
				return null;
		}
	}
	
	public final color color(double r, double g, double b) {
		double red = Math.max(0, Math.min(1.0, r/maxR));
		double green = Math.max(0, Math.min(1.0, g/maxG));
		double blue = Math.max(0, Math.min(1.0, b/maxB));
		switch(colorModeVal) {
			case RGB:
				return color.__color(false, red, green, blue, 1.0, maxR, maxG, maxB, maxA);
			case HSB: 
				return color.__color(true, red, green, blue, 1.0, maxR, maxG, maxB, maxA);
			default:
				return null;
		}

	}
	
	public final color color(double r, double g, double b, double a) {
		double red = Math.max(0, Math.min(1.0, r/maxR));
		double green = Math.max(0, Math.min(1.0, g/maxG));
		double blue = Math.max(0, Math.min(1.0, b/maxB));
		double alpha = Math.max(0, Math.min(1.0, a/maxA));
		switch(colorModeVal) {
			case RGB:
				return color.__color(false, red, green, blue, alpha, maxR, maxG, maxB, maxA);
			case HSB: 
				return color.__color(true, red, green, blue, alpha, maxR, maxG, maxB, maxA);
			default:
				return null;
		}
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
		pen.setFill(color(val).col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void background(double val, double alpha) {
		pen.setFill(color(val, alpha).col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}
	
	public final void background(double r, double g, double b) {
		pen.setFill(color(r,g,b).col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void background(double r, double g, double b, double a) {
		pen.setFill(color(r,g,b,a).col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void background(String webColor) {
		pen.setFill(Color.web(webColor));
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void fill(color color) {
		isFilled = true;
		pen.setFill(color.col);
		fillColor = (Color) pen.getFill();
	}

	public final void fill(double val) {
		isFilled = true;
		pen.setFill(color(val).col);
		fillColor = (Color) pen.getFill();
		
	}
	
	public final void fill(double val, double a) {
		isFilled = true;
		pen.setFill(color(val, a).col);
		fillColor = (Color) pen.getFill();
	}

	public final void fill(double r, double g, double b) {
		isFilled = true;
		pen.setFill(color(r,g,b).col);
		fillColor = (Color) pen.getFill();
		
	}

	public final void fill(double r, double g, double b, double a) {
		isFilled = true;
		pen.setFill(color(r,g,b,a).col);
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

	public final void stroke(double val) {
		isStroked = true;
		pen.setStroke(color(val).col);
		strokeColor = (Color) pen.getStroke();
	}
	public final void stroke(double val, double a) {
		isStroked = true;
		pen.setStroke(color(val, a).col);
		strokeColor = (Color) pen.getStroke();
	}

	public final void stroke(double r, double g, double b) {
		isStroked = true;
		pen.setStroke(color(r,g,b).col);
		strokeColor = (Color) pen.getStroke();
	}

	public final void stroke(double r, double g, double b, double a) {
		isStroked = true;
		pen.setStroke(color(r,g,b,a).col);
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
	
	public final void colorMode(SETTINGS mode) {
		colorModeVal = mode;
	}
	public final void colorMode(SETTINGS mode, double maxV) {
		this.maxR = maxV;
		this.maxG = maxV;
		this.maxB = maxV;
		this.maxA = maxV;
		colorModeVal = mode;
	}
	public final void colorMode(SETTINGS mode, double maxR, double maxG, double maxB) {
		this.maxR = maxR;
		this.maxG = maxG;
		this.maxB = maxB;
		colorModeVal = mode;
	}
	public final void colorMode(SETTINGS mode, double maxR, double maxG, double maxB, double maxA) {
		this.maxR = maxR;
		this.maxG = maxG;
		this.maxB = maxB;
		this.maxA = maxA;
		colorModeVal = mode;
	}
	
	public final void ellipseMode(SETTINGS mode) {
		ellipseModeVal = mode;
	}
	
	public final void rectMode(SETTINGS mode) {
		rectModeVal = mode;
	}
	
	public final void imageMode(SETTINGS mode) {
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
		currentFont =  new Font(fontName, textSizeVal);
		pen.setFont(currentFont);
	}
	
	public final void rotate(double angle) {
		//pen.rotate(angle);
		pen.rotate(angle *(180.0/Math.PI));
	}
	
	
	public final void text(String text, double x, double y) {
		if(isFilled)
			switch(textAligneValY) {
			case BOTTOM:
				pen.fillText(text, x, y);
				break;
			case CENTER:
				pen.fillText(text, x, y+textSizeVal/2);
				break;
			case TOP:
				pen.fillText(text, x, y+textSizeVal);
				break;
			}
		if(isStroked)
			switch(textAligneValY) {
			case BOTTOM:
				pen.strokeText(text, x, y);
				break;
			case CENTER:
				pen.strokeText(text, x, y+textSizeVal/2);
				break;
			case TOP:
				pen.strokeText(text, x, y+textSizeVal);
				break;
			}
	}
	
	public final void textAlign(SETTINGS modeX) {
		textAligneValX = modeX;
		switch(textAligneValX) {
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
				System.err.println("Error: Undefined AlignmentMode " + modeX);
				System.exit(-1);
		}
	}
	public final void textAlign(SETTINGS modeX, SETTINGS modeY) {
		textAligneValX = modeX;
		textAligneValY = modeY;
		switch(textAligneValX) {
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
				System.err.println("Error: Undefined AlignmentMode " + modeX +" "+ modeY );
				System.exit(-1);
		}
	}

	public final void textSize(int size) {
		textSizeVal = size; 
		currentFont = new Font(fontName, textSizeVal);
		pen.setFont(currentFont);
	}
	
	public final float textWidth(String txt) {
		final Text text = new Text(txt);
        text.setFont(currentFont);
        return (float) text.getLayoutBounds().getWidth();
	}
	
	
	public final void translate(double x, double y) {
		pen.translate(-xOffset, -yOffset);
		xOffset = x;
		yOffset = y;
		pen.translate(xOffset, yOffset);
	}
	
	public final void scale(double x) {
		pen.scale(1.0/xScale, 1.0/yScale);
		xScale = x;
		yScale = x;
		pen.scale(x, x);
	}
	
	public final void scale(double x, double y) {
		pen.scale(1.0/xScale, 1.0/yScale);
		xScale = x;
		yScale = y;
		pen.scale(x, y);
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
	
	public final void cursor() {
		cursor = ARROW;
	}
	
	public final void cursor(CURSOR kind) {
		cursor = kind;
	}
}
