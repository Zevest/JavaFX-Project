package engine;

import java.io.BufferedReader;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import constant.CURSOR;
import constant.SETTINGS;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.StrokeLineCap;
import util.*;


public class Sketch {

	ArrayList<Double> verticesX;
	ArrayList<Double> verticesY;
	//double shapeStartX;
	//double shapeStartY;
	Color fillColor = Color.WHITE;
	Color strokeColor = Color.BLACK;
	boolean isFilled = true;
	boolean isStroked = true;
	float strokeWeightVal = 0.1f;
	String fontName;
	private float fontSizeVal = 11;
	private Font currentFont = new Font(fontSizeVal);
	private long startTime = System.currentTimeMillis();
	double xOffset = 0.0;
	double yOffset = 0.0;
	double xScale = 1.0;
	double yScale = 1.0;
	double lastAngle = 0.0;
	
	
	public float PI = (float) Math.PI;
	public float HALF_PI = (float) (Math.PI / 2.0);
	public float TWO_PI = (float)(2 * Math.PI);
	public float TAU = (float) (2 * Math.PI);
	public float QUARTER_PI = (float) (Math.PI/4.0);
	public short width = 200, height = 200,displayWidth, displayHeight;
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
	public static final SETTINGS PIE = SETTINGS.PIE;
	public static final SETTINGS CHORD = SETTINGS.CHORD;
	public static final SETTINGS OPEN = SETTINGS.OPEN;
	public static final SETTINGS CLOSE = SETTINGS.CLOSE;
	public static final SETTINGS PROJECT = SETTINGS.PROJECT;
	public static final SETTINGS SQUARE = SETTINGS.SQUARE;
	public static final SETTINGS ROUND = SETTINGS.ROUND;
	public static final CURSOR ARROW = CURSOR.ARROW;
	public static final CURSOR CROSS = CURSOR.CROSS;
	public static final CURSOR HAND = CURSOR.HAND;
	public static final CURSOR MOVE = CURSOR.MOVE;
	public static final CURSOR TEXT = CURSOR.TEXT;
	public static final CURSOR WAIT = CURSOR.WAIT;
	
	private static final SETTINGS settingsVals[] = SETTINGS.values();
	boolean isFullScreen = false;
	boolean finished = false;
	

	CURSOR cursor = ARROW;
	SETTINGS ellipseModeVal = SETTINGS.CENTER;
	SETTINGS rectModeVal = SETTINGS.CORNER;
	SETTINGS strokeCapVal = SETTINGS.ROUND;
	boolean makingShape = false;

	int textSizeVal = 15;
	private SETTINGS textAligneValX = SETTINGS.LEFT;
	private SETTINGS textAligneValY = SETTINGS.BOTTOM;
	
	SETTINGS colorModeVal = SETTINGS.RGB;
	private double maxR = 255, maxG = 255, maxB = 255, maxA = 255;
	
	private SETTINGS imageModeVal = SETTINGS.CENTER;

	private Stack<PenMatrix> matrtixStack = new Stack<PenMatrix>();
	private Stack<PenStyle> styleStack = new Stack<PenStyle>();

	public float frameRate;
	public float deltaTime;
	public float deltaTimeMillis;

	private Canvas can;
	GraphicsContext pen;
	private float targetFrameRate = 60f;
	public long frameCount;
	boolean isLoop = true;
	// Mouse
	public float mouseX = FileManager.init();
	public float mouseY = 0;
	public SETTINGS mouseButton = SETTINGS.NONE;
	public boolean mousePressed;
	public boolean keyPressed;
	public String key;
	public KeyCode keyCode;
	public final String CODED = "CODED";
	
	private int beizerPointVal = 20, curvePointVal = 20;
	
	PixelReader px;
	PixelWriter pw;
	public int minXChange = 0, minYChange = 0, maxXChange = width, maxYChange = height;
	int maxWidth;
	int[] pixels;
	String sketchName = "sketch";
	public Surface surface = new Surface(sketchName);
	// BufferedImage test;
	private boolean loaded;
	private boolean changedPixel;
	
	

	static int error = 0;
	


	private final void loadPixels() {
		if(pixels == null || pixels.length < maxWidth*height)
			pixels = new int[maxWidth * height];
		WritableImage tmp = can.snapshot(null, null);
		px = tmp.getPixelReader();
		WritablePixelFormat<IntBuffer> format= PixelFormat.getIntArgbInstance();
		px.getPixels(0, 0, width, height, format, pixels,  0, maxWidth);
		loaded = true;
	}

	final void updatePixels() {
		int offset = constrain(minYChange, 0, height) * maxWidth + constrain(minXChange, 0, width);
		pw = pen.getPixelWriter();
		WritablePixelFormat<IntBuffer> format= PixelFormat.getIntArgbInstance();
		pw.setPixels(
				constrain(minXChange, 0, width),
				constrain(minYChange, 0, height),
				constrain(maxXChange - minXChange, 1, width), 
				constrain(maxYChange - minYChange, 1, height),
				format, pixels, offset, maxWidth
				);
		
		minXChange = width;
		minYChange = height;
		maxXChange = 0;
		maxYChange = 0;
	}

	private final void exitWithError(String message, int stackOffset) {
		StackTraceElement[] temp = Thread.getAllStackTraces().get(Thread.currentThread());
		StackTraceElement e = temp[stackOffset];
		StackTraceElement e2 = temp[stackOffset+1];
		System.err.println(message);
		System.err.printf("Error %s in methods %s\n",e.getMethodName(), e2.getMethodName());
		
		for(int i = 4; i < temp.length; ++i) {
			System.err.println(temp[i].toString());
		}
		
		exit(-1);
	}
	
	final void resetPixelsFlags() {
		changedPixel = false;
		loaded = false;
	}
	
	final boolean getChangedPixel() {
		return changedPixel;
	}
	
	final boolean getLoaded() {
		return loaded;
	}
	
	
	
	public final color get(int x, int y) {
		if(pixels == null || !loaded) {
			//System.err.println("Error: Pixel Array is empty");
			//System.err.println("You must use the loadFastPixel function First Before trying accessing the pixel");
			//exit(-1);
			loadPixels();
			loaded = true;
		}
		if(y < 0 || y >= height || x < 0 || x >= width) {
			StackTraceElement[] temp = Thread.getAllStackTraces().get(Thread.currentThread());
			StackTraceElement e = temp[3];
			System.err.printf("java.lang.IllegalArgumentException at function %s in (%s:%d): Coordinates (%d, %d) out of range(%d, %d)\n", temp[2].getMethodName(),e.getFileName(), e.getLineNumber(),x, y, width, height);
			for(int i = 4; i < temp.length; ++i) {
				System.err.println(temp[i].toString());
			}
			
			exit(-1);
			exitWithError(String.format("Coordinates (%d, %d) out of range(%d, %d)\\n",x, y, width, height), 3);
		}
		return color(pixels[y *maxWidth + x]);
	}
	
	public final void delay(int time) {
		long startedTime = System.currentTimeMillis(), currentTime;
		do {
			currentTime = System.currentTimeMillis();
		}
		while( currentTime - startedTime < time);
			
	}
	
	public final int millis() {
		return (int) (System.currentTimeMillis() - startTime); 
	}
	
	public final int second() {
		return (int) (System.currentTimeMillis()/1000)%60;
	}
	
	public final int minute() {
		return (int) (System.currentTimeMillis()/1000/60)%60;
	}
	
	public final int hour() {
		Calendar cal = Calendar.getInstance();
		int offset = cal.getTimeZone().getDSTSavings();
		return (int) ((System.currentTimeMillis()+offset)/1000/60/60)%24;
	}
	
	public final int day() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public final int month() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH+1);
	}
	
	public final int year() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	
	
	public final void set(int x, int y, color col) {
		if(pixels == null || pixels.length < maxWidth*height ||  !loaded) {
			loadPixels();
			//System.err.println("Error: Pixel Array is empty");
			//System.err.println("You must use the loadFastPixel function First Before trying accessing the pixel");
			//System.exit(-1);
			return;
		}

		if (x < minXChange)
			minXChange = x;
		if (y < minYChange)
			minYChange = y;
		
		if (x > maxXChange)
			maxXChange = x;
		if (y > maxYChange)
			maxYChange = y;
		
		if(minXChange >= maxXChange)
			maxXChange = minXChange+1;
		if(minYChange >= maxYChange)
			maxYChange = minYChange+1;

		if(y < 0 || y >= height || x < 0 || x >= width) {
			StackTraceElement[] temp = Thread.getAllStackTraces().get(Thread.currentThread());
			StackTraceElement e = temp[3];
			System.err.printf("java.lang.IllegalArgumentException at function %s in (%s:%d): Coordinates (%d, %d) out of range(%d, %d)\n", temp[2].getMethodName(),e.getFileName(), e.getLineNumber(),x, y, width, height);
			for(int i = 4; i < temp.length; ++i) {
				System.err.println(temp[i].toString());
			}
			
			exit(-1);
		}
		
		try {
			pixels[y * maxWidth + x] = col.getArgb();
			changedPixel = true;
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			exit(-1);
		}
	}

	public Sketch() {}
	
	////////////////// Override //////////////////
	
	public void setup() {}

	public void draw() {}

	public void mousePressed() {}

	public void mouseReleased() {}

	public void mouseDragged() {}

	public void mouseMove() {}
	
	public void keyPressed() {}

	public void keyReleased() {}
	
	public void keyTyped() {}

	//////////////// Settings /////////////////////
	
	public final void size(int w, int h) {
		surface.setSize(w, h);
		can.setHeight(surface.h);
		can.setWidth(surface.w);
		if(maxWidth < w){
			maxWidth = w;
		}
		width = (short)w;
		height = (short)h;
	}

	public final void exit() {
		finished = true;
	}
	
	public final void exit(int error) {
		finished = true;
		Sketch.error = error;
	}
	
	public final void redraw() {
		Core.redraw = true;
	}

	public final void fullScreen() {
		isFullScreen = true;
		Core.setFullScreen();
		width = (short) Core.width;
		can.setWidth(width);
		height = (short) Core.height;
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

	//////////////// MATH ////////////////
	public final float min(double a, double... other) {
		return (float) Util.min(a, other);
	}

	public final float min(double[] a) {
		return (float) Util.min(a[0], a);
	}

	public final float max(double a, double... other) {
		return (float) Util.max(a, other);
	}

	public final float max(double[] a) {
		return (float) Util.max(a[0], a);
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
		return (float) Math.sqrt(a * a + b * b);
	}

	public final float mag(double a, double b, double c) {
		return (float) Math.sqrt(a * a + b * b + c * c);
	}

	public final float map(double value, double start1, double end1, double start2, double end2) {
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

	public static final int constrain(long val, long low, long high) {
		return (int) Util.constrain(val, low, high);
	}

	public final float dist(double x1, double y1, double x2, double y2) {
		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public final float dist(double x1, double y1, double z1, double x2, double y2, double z2) {
		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
	}

	public final float sq(double a) {
		return (float) (a * a);
	}

	public final float pow(double a, double b) {
		return (float) Math.pow(a, b);
	}

	public final float sqrt(double a) {
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
		return (float) Math.toDegrees(angle);
	}

	public final float radians(double angle) {
		return (float) Math.toRadians(angle);
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
		return (float) Noise.noise2D((float) x, 0f);
	}

	public final float noise(double x, double y) {
		return (float) Noise.noise2D((float) x, (float) y);
	}

	public final float noise(double x, double y, double z) {
		return (float) Noise.noise3D((float) x, (float) y, (float) z);
	}

	public final color color(long v) {
		double red = (v & 0xff) / 255.0;
		double green = ((v >> 8) & 0xff) / 255.0;
		double blue = ((v >> 16) & 0xff) / 255.0;
		double alpha = ((v >> 24) & 0xff) / 255.0;
		switch (colorModeVal) {
		case RGB:
			return color.__color(false, red, green, blue, alpha, maxR, maxG, maxB, maxA);
		case HSB:
			return color.__color(true, red, green, blue, alpha, maxR, maxG, maxB, maxA);
		default:
			return null;
		}
	}

	public final color color(double c) {
		double nColor = Math.max(0, Math.min(1.0, c / maxR));
		switch (colorModeVal) {
		case RGB:
			return color.__color(false, nColor, nColor, nColor, 1.0, maxR, maxR, maxR, maxA);
		case HSB:
			return color.__color(true, nColor, 0, nColor, 1.0, maxR, maxR, maxR, maxA);
		default:
			return null;
		}

	}

	public final color color(double c, double a) {
		double nColor = Math.max(0, Math.min(1.0, c / maxR));
		double alpha = Math.max(0, Math.min(1.0, a / maxA));
		switch (colorModeVal) {
		case RGB:
			return color.__color(false, nColor, nColor, nColor, alpha, maxR, maxR, maxR, maxA);
		case HSB:
			return color.__color(true, nColor, 0, nColor, alpha, maxR, maxR, maxR, maxA);
		default:
			return null;
		}
	}

	public final color color(double r, double g, double b) {
		double red = Math.max(0, Math.min(1.0, r / maxR));
		double green = Math.max(0, Math.min(1.0, g / maxG));
		double blue = Math.max(0, Math.min(1.0, b / maxB));
		switch (colorModeVal) {
		case RGB:
			return color.__color(false, red, green, blue, 1.0, maxR, maxG, maxB, maxA);
		case HSB:
			return color.__color(true, red, green, blue, 1.0, maxR, maxG, maxB, maxA);
		default:
			return null;
		}

	}

	public final color color(double r, double g, double b, double a) {
		double red = Math.max(0, Math.min(1.0, r / maxR));
		double green = Math.max(0, Math.min(1.0, g / maxG));
		double blue = Math.max(0, Math.min(1.0, b / maxB));
		double alpha = Math.max(0, Math.min(1.0, a / maxA));
		switch (colorModeVal) {
		case RGB:
			return color.__color(false, red, green, blue, alpha, maxR, maxG, maxB, maxA);
		case HSB:
			return color.__color(true, red, green, blue, alpha, maxR, maxG, maxB, maxA);
		default:
			return null;
		}
	}

	public final void println(Object o, Object... other) {
		String buffer = o.toString();
		for (Object e : other) {
			if (e != null)
				buffer += " " + e.toString();
			else
				buffer += " null";
		}
		System.out.println(buffer);
	}

	public final void print(Object o, Object... other) {
		String buffer = o.toString();
		for (Object e : other) {
			buffer += " " + e.toString();
		}
		System.out.print(buffer);
	}

	public final void frameRate(float rate) {
		targetFrameRate = rate;
	}

	final float getTargetFrameRate() {
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
		pen.setFill(color(r, g, b).col);
		pen.fillRect(0, 0, width, height);
		pen.setFill(fillColor);
	}

	public final void background(double r, double g, double b, double a) {
		pen.setFill(color(r, g, b, a).col);
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
		pen.setFill(color(r, g, b).col);
		fillColor = (Color) pen.getFill();

	}

	public final void fill(double r, double g, double b, double a) {
		isFilled = true;
		pen.setFill(color(r, g, b, a).col);
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
		pen.setStroke(color(r, g, b).col);
		strokeColor = (Color) pen.getStroke();
	}

	public final void stroke(double r, double g, double b, double a) {
		isStroked = true;
		pen.setStroke(color(r, g, b, a).col);
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
	public final void colorMode(int mode) {
		if(mode >= 0 && mode < settingsVals.length)
			colorModeVal = settingsVals[mode];
		else
			colorModeVal = SETTINGS.RGB;
		
	}

	public final void colorMode(SETTINGS mode, double maxV) {
		this.maxR = maxV;
		this.maxG = maxV;
		this.maxB = maxV;
		this.maxA = maxV;
		colorModeVal = mode;
	}
	
	public final void colorMode(int mode, double maxV) {
		if(mode >= 0 && mode < settingsVals.length)
			colorMode(settingsVals[mode], maxV);
		else
			colorMode(SETTINGS.RGB, maxV);
		
	}

	public final void colorMode(SETTINGS mode, double maxR, double maxG, double maxB) {
		this.maxR = maxR;
		this.maxG = maxG;
		this.maxB = maxB;
		colorModeVal = mode;
	}

	public final void colorMode(int mode, double maxR, double maxG, double maxB) {
		if(mode >= 0 && mode < settingsVals.length)
			colorMode(settingsVals[mode], maxR, maxG, maxB);
		else
			colorMode(SETTINGS.RGB, maxR, maxG, maxB);
		
	}
	
	public final void colorMode(SETTINGS mode, double maxR, double maxG, double maxB, double maxA) {
		this.maxR = maxR;
		this.maxG = maxG;
		this.maxB = maxB;
		this.maxA = maxA;
		colorModeVal = mode;
	}
	public final void colorMode(int mode, double maxR, double maxG, double maxB, double maxA) {
		if(mode >= 0 && mode < settingsVals.length)
			colorMode(settingsVals[mode], maxR, maxG, maxB, maxA);
		else
			colorMode(SETTINGS.RGB, maxR, maxG, maxB, maxA);
		
	}
	

	public final void ellipseMode(SETTINGS mode) {
		ellipseModeVal = mode;
	}
	public final void ellipseMode(int mode) {
		if(mode >= 0 && mode < settingsVals.length)
			ellipseModeVal = settingsVals[mode];
		else
			ellipseModeVal = SETTINGS.CENTER;
		
	}

	public final void rectMode(SETTINGS mode) {
		rectModeVal = mode;
	}
	public final void rectMode(int mode) {
		if(mode >= 0 && mode < settingsVals.length)
			rectModeVal = settingsVals[mode];
		else
			rectModeVal = SETTINGS.CORNER;
		
	}

	public final void imageMode(SETTINGS mode) {
		imageModeVal = mode;
	}
	
	public final void imageMode(int mode) {
		if(mode >= 0 && mode < settingsVals.length)
			imageModeVal = settingsVals[mode];
		else
			imageModeVal = SETTINGS.CENTER;
		
	}

	public final void strokeCap(int mode) {
		if(mode >= 15 && mode <= 17)
			strokeCapVal = settingsVals[mode];
		else
			strokeCapVal = SETTINGS.ROUND;
	}
	
	public final void strokeCap(SETTINGS mode) {
		switch(mode) {
		case SQUARE:
			pen.setLineCap(StrokeLineCap.BUTT);
			break;
		case PROJECT:
			pen.setLineCap(StrokeLineCap.SQUARE);
			break;
		case ROUND:
		default:
			pen.setLineCap(StrokeLineCap.ROUND);
			break;
		}
	}
	
	
	public final void line(double x1, double y1, double x2, double y2) {
		pen.strokeLine(x1 + xOffset, y1 + yOffset, x2 + xOffset, y2 + yOffset);
	}

	public final void arc(double x1, double y1, double x2, double y2, double start, double stop) {
		if(isFilled)
			pen.fillArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.ROUND);
		if(isStroked)
			pen.strokeArc(x1-(x2/2), y1-(y2/2) , x2, y2, -degrees(start), -degrees(stop-start), ArcType.OPEN);
	}
		
		 
	
	public final void arc(double x1, double y1, double x2, double y2, double start, double stop, int type) {
		if(type > settingsVals.length)
			arc(x1, y1, x2, y2, start, stop);
		else
			arc(x1, y1, x2, y2, start, stop, settingsVals[type]);
	}
	
	public final void arc(double x1, double y1, double x2, double y2, double start, double stop, SETTINGS arcType) {
		
		
		if(isFilled) {
			switch(arcType) {
				case OPEN:
					pen.fillArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.OPEN);
					break;
				case CHORD:
				case CLOSE:
					pen.fillArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.CHORD);
					break;
				case PIE:
				default:
					pen.fillArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.ROUND);
					break;
			}
		}
		if(isStroked) {
			switch(arcType) {
				case PIE:
					pen.strokeArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.ROUND);
					break;
				case CHORD:
				case CLOSE:
					pen.strokeArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.CHORD);
					break;
				default:
				case OPEN:
					pen.strokeArc(x1-(x2/2), y1-(y2/2), x2, y2, -degrees(start), -degrees(stop-start), ArcType.OPEN);
					break;
			}
		}

	}
	
	
	public final void beizerDetail(int detail) {
		beizerPointVal = detail;
	}
	
	public final float bezierPoint(double a, double b, double c, double d, double t) {
		return  (float)(pow(1- t, 3)*a + 3 * sq(1 - t) * t * b + 3*(1-t)*sq(t)*c+pow(t,3)*d);
	}
	
	public final float bezierTangent(double a, double b, double c,double d,  double t){
		return (float) (3 * sq(1-t) * (b-a) + 6 * (1-t)*t*(c-b)+3*sq(t)*(d-c));
	}
	
	public final void bezier(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double x, y, step = 1.0/beizerPointVal;
		pen.beginPath();
		pen.moveTo(x1, y1);
		for(double t = 0; t < 1.0001; t+= step) {
			x = bezierPoint(x1, x2, x3, x4, t);
			y = bezierPoint(y1, y2, y3, y4, t);
			pen.lineTo(x, y);
		}
		pen.lineTo(x4, y4);
		pen.stroke();
		
	}
	
	public final void curveDetail(int detail) {
		curvePointVal = detail;
	}

	
	public final float curvePoint(double a, double b, double c, double d, double t) {
		return  0.5f * (float) (
					(2 * b) +
					(-a + c) * t +
					(2 * a - 5 * b + 4 * c - d) * sq(t) +
					(-a + 3 * b - 3 * c + d) * pow(t, 3)
				);

				
	}
	
	public final float curveTangent(double a, double b, double c,double d,  double t) {
		return 0.5f * (float) (
				(-a + c) +
				2*(2 * a - 5 * b + 4 * c - d) * t +
				3*(-a + 3 * b - 3 * c + d) * sq(t)
			);

				
		
	}
	
	public final void curve(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double x, y, step = 1.0/curvePointVal;
		pen.beginPath();
		pen.moveTo(x2, y2);
		for(double t = 0; t < 1.0001; t+= step) {
			x = curvePoint(x1, x2, x3, x4, t);
			y = curvePoint(y1, y2, y3, y4, t);
			pen.lineTo(x, y);
		}
		pen.lineTo(x3, y3);
		pen.stroke();
	}
	
	
	
	public final void ellipse(double x, double y, double w, double h) {
		switch (ellipseModeVal) {
		case CORNER:
			if (isFilled)
				pen.fillOval(x, y, w, h);
			if (isStroked)
				pen.strokeOval(x, y, w, h);
			break;
		case CENTER:
			if (isFilled)
				pen.fillOval(x - w / 2, y - h / 2, w, h);
			if (isStroked)
				pen.strokeOval(x - w / 2, y - h / 2, w, h);
			break;
		default:
			System.err.println("Error: Undefined ellipse Mode " + ellipseModeVal);
			System.exit(-1);

		}
	}
	
	
	public final void circle(double x, double y, double w) {
		switch (ellipseModeVal) {
		case CORNER:
			if (isFilled)
				pen.fillOval(x, y, w, w);
			if (isStroked)
				pen.strokeOval(x, y, w, w);
			break;
		case CENTER:
			if (isFilled)
				pen.fillOval(x - w / 2, y - w / 2, w, w);
			if (isStroked)
				pen.strokeOval(x - w / 2, y - w / 2, w, w);
			break;
		default:
			System.err.println("Error: Undefined ellipse Mode " + ellipseModeVal);
			System.exit(-1);

		}
	}

	
	public final void point(double x, double y) {
		//pen.
	}
	
	
	
	public final void rect(double x, double y, double w, double h) {
		switch (rectModeVal) {
		case CORNER:
			if (isFilled)
				pen.fillRect(x, y, w, h);
			if (isStroked)
				pen.strokeRect(x, y, w, h);
			break;
		case CENTER:
			if (isFilled)
				pen.fillRect(x - w / 2, y - h / 2, w, h);
			if (isStroked)
				pen.strokeRect(x - w / 2, y - h / 2, w, h);
			break;
		default:
			System.err.println("Error: Undefined rectangle Mode " + rectModeVal);
			System.exit(-1);

		}
	}

	public final void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		double[] posX = { x1, x2, x3 };
		double[] posY = { y1, y2, y3 };
		if (isFilled) {
			pen.fillPolygon(posX, posY, 3);
		}
		if (isStroked) {
			pen.strokePolygon(posX, posY, 3);
		}
	}

	public final void beginShape() {
		makingShape = true;
		if(verticesX == null || verticesY == null) {
			verticesX = new ArrayList<Double>();
			verticesY = new ArrayList<Double>();
		} else {
			verticesX.clear();
			verticesY.clear();
		}
		//
	}

	public final void vertex(double x, double y) {
		verticesX.add(x);
		verticesY.add(y);
		/*if(makingShape)
			pen.lineTo(x, y);
		else {
			pen.moveTo(x, y);
			shapeStartX = x;
			shapeStartY = y;
		}*/
		//makingShape = true;
	}
	
	public final void bezierVertex(double x2, double y2, double x3, double y3, double x4, double y4) {
		double x1 = verticesX.get(verticesX.size()-1);
		double y1 = verticesY.get(verticesY.size()-1);
		double x, y, step = 1.0/beizerPointVal;
		for(double t = 0; t < 1.0001; t+= step) {
			x = bezierPoint(x1, x2, x3, x4, t);
			y = bezierPoint(y1, y2, y3, y4, t);
			verticesX.add(x);
			verticesY.add(y);
		}
		verticesX.add(x4);
		verticesY.add(y4);
	}

	/*public final void curveVertex(double x, double y) {
		double x, y, step = 1.0/curvePointVal;
		pen.beginPath();
		pen.moveTo(x2, y2);
		for(double t = 0; t < 1.0001; t+= step) {
			x = curvePoint(x1, x2, x3, x4, t);
			y = curvePoint(y1, y2, y3, y4, t);
			pen.lineTo(x, y);
		}
		pen.lineTo(x3, y3);
		pen.stroke();
	}*/
	
	public final void endShape() {
		endShape(SETTINGS.OPEN); 
	}
	
	public final void endShape(int mode) {
		if(mode < settingsVals.length)
			endShape(settingsVals[mode]);
		else
			exitWithError("Undefined Shape mode " + mode, 3);
		
	}
	
	public final void endShape(SETTINGS mode) {
		int len = Math.min(verticesX.size(), verticesY.size());
		pen.beginPath();
		pen.moveTo(verticesX.get(0), verticesY.get(0));
		for (int i = 1; i < len; ++i) {
			pen.lineTo(verticesX.get(i), verticesY.get(i));
		}
		switch(mode) {
		case CLOSE:
			pen.closePath();
			break;
		case OPEN:
		default:
			break;
		}
		
		if (isFilled)
			pen.fill();
		if(isStroked)
			pen.stroke();
		makingShape = false;
	}

	
	public final void pushMatrix() {
		pen.save();
		matrtixStack.push(new PenMatrix(this));
	}

	public final void popMatrix() {
		PenMatrix s = matrtixStack.pop();
		pen.restore();
		verticesX = s.verticesX;
		verticesY = s.verticesY;
		xOffset = s.xOffset;
		yOffset = s.yOffset;
		makingShape = s.makingShape;
		lastAngle = s.lastAngle;
		xScale = s.xScale;
		yScale = s.yScale;
	}
	
	public final void pushStyle() {
		styleStack.push(new PenStyle(this));
	}
	
	public final void popStyle() {
		PenStyle s = styleStack.pop();
		pen.restore();
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
	
	
	public final void textFont(String name) {
		fontName = name;
		currentFont = new Font(fontName, textSizeVal);
		pen.setFont(currentFont);
	}

	public final void rotate(double angle) {
		// pen.rotate(angle);
		pen.rotate(-lastAngle);
		pen.rotate(angle * (180.0 / Math.PI));
		lastAngle = angle * (180.0 / Math.PI);
	}

	public final void text(String text, double x, double y) {
		if (isFilled)
			switch (textAligneValY) {
			case BOTTOM:
				pen.fillText(text, x, y);
				break;
			case CENTER:
				pen.fillText(text, x, y + textSizeVal / 2);
				break;
			case TOP:
				pen.fillText(text, x, y + textSizeVal);
				break;
			default:
				break;
			}
		if (isStroked)
			switch (textAligneValY) {
			case BOTTOM:
				pen.strokeText(text, x, y);
				break;
			case CENTER:
				pen.strokeText(text, x, y + textSizeVal / 2);
				break;
			case TOP:
				pen.strokeText(text, x, y + textSizeVal);
				break;
			default:
				break;
			}
	}

	public final void text(Object text, double x, double y) {
		if (isFilled)
			switch (textAligneValY) {
			case BOTTOM:
				pen.fillText(text.toString(), x, y);
				break;
			case CENTER:
				pen.fillText(text.toString(), x, y + textSizeVal / 2);
				break;
			case TOP:
				pen.fillText(text.toString(), x, y + textSizeVal);
				break;
			default:
				break;
			}
		if (isStroked)
			switch (textAligneValY) {
			case BOTTOM:
				pen.strokeText(text.toString(), x, y);
				break;
			case CENTER:
				pen.strokeText(text.toString(), x, y + textSizeVal / 2);
				break;
			case TOP:
				pen.strokeText(text.toString(), x, y + textSizeVal);
				break;
			default:
				break;
			}
	}

	public final void textAlign(SETTINGS modeX) {
		textAligneValX = modeX;
		switch (textAligneValX) {
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
		switch (textAligneValX) {
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
			System.err.println("Error: Undefined AlignmentMode " + modeX + " " + modeY);
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
		pen.scale(1.0 / xScale, 1.0 / yScale);
		xScale = x;
		yScale = x;
		pen.scale(x, x);
	}

	public final void scale(double x, double y) {
		pen.scale(1.0 / xScale, 1.0 / yScale);
		xScale = x;
		yScale = y;
		pen.scale(x, y);
	}

	public final Image loadImage(String Name) {
		String url = FileManager.getFileUrl(Name);
		if (url != null)
			return new Image(url);
		System.err.println("Error: image not found.");
		return null;
	}

	public final void image(Image img, double x, double y) {
		switch (imageModeVal) {
		case CENTER:
			pen.drawImage(img, x - img.getWidth() / 2, y - img.getHeight() / 2);
			break;
		case CORNER:
			pen.drawImage(img, x, y);
			break;
		default:
			System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	}

	public final void image(Image img, double x, double y, double w, double h) {
		switch (imageModeVal) {
		case CENTER:
			pen.drawImage(img, x - w / 2, y - h / 2, w, h);
			break;
		case CORNER:
			pen.drawImage(img, x, y, w, h);
			break;
		default:
			System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	}

	public final void image(Image img, double dx, double dy, double dw, double dh, double sx, double sy, double sw,
			double sh) {
		switch (imageModeVal) {
		case CENTER:
			pen.drawImage(img, sx, sy, sw, sh, dx - dw / 2, dy - dh / 2, dw, dh);
			break;
		case CORNER:
			pen.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);
			break;
		default:
			System.err.println("Invalid Image Draw Mode : " + imageModeVal);
		}
	}

	public final BufferedReader createReader(String fileName) {
		return FileManager.createReader(fileName);
	}
	
	public final void cursor() {
		cursor = ARROW;
	}
	
	public final void cursor(CURSOR kind) {
		cursor = kind;
	}
	
	public final void noCursor() {
		cursor = CURSOR.NONE;
	}
	
	public final void smooth(boolean smoothing) {
		pen.setImageSmoothing(smoothing);
	}

	
}
