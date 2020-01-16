package engine;

import java.io.File;
import java.nio.file.Paths;

import constant.CURSOR;
import constant.SETTINGS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import sketch.template.__UserDefault;
//import engine.Time;
//import engine.Sketch;
import util.FileManager;

public class Core extends Application {
	public static int width = 800;
	public static int height = 600;
	public static long frameCount = -1;
	public double frameRate = 0; 
	private float targetFrameRate = 60f;
	private Canvas canvas;
	protected GraphicsContext context;
	static private Stage mainStage;
	private Group root;
	private Scene scene;
	protected Sketch sketch; 
	static int error;
	static CURSOR[] cursorSet = CURSOR.values(); 
	protected int cursor = CURSOR.ARROW.getValue();
	static String projectName = "project3";
	static String dirPath = FileManager.path("sketchBooks");
	
	static Window window;
	static Rectangle2D bound;
	
	static boolean redraw = false;
	
	
    public static void main(String[] args) {
        /*Application.launch(Core.class, args);*/
    	/*for(String str: args)
    		System.out.println(str);*/
    	if(args.length > 0)
    		dirPath = args[0];
    	//System.out.println("dirPath is "+dirPath);
    	if(args.length > 1) 
    		projectName = args[1];
    	else
    		projectName = Paths.get(dirPath).getFileName().toString();
    	//System.out.println("PojectName is " + projectName);
    	//new SketchTemplate(FileManager.listFile(dirPath,"pjfx"), dirPath);
    	Application.launch(Core.class, args);
    	System.exit(error);
    }
     
    protected void update(double elapsedTime){
    	setTargetFrameRate(sketch.getTargetFrameRate());
    	sketch.deltaTime = (float)elapsedTime/1000.0f;
    	sketch.deltaTimeMillis = (float)elapsedTime;
    	if(cursor != sketch.cursor);
    		setCursor(sketch.cursor);
		mainStage.setResizable(sketch.surface.resizable);
		
		if(!(sketch.surface.title.equals(mainStage.getTitle())))
			mainStage.setTitle(sketch.surface.title);
		
		if((short)scene.getWidth() != sketch.surface.w)
			resizeX(scene.getWidth());
		
		if((short)scene.getHeight() != sketch.surface.h)
			resizeY(scene.getHeight());
		
		sketch.width = (short) sketch.surface.w;
		sketch.height = (short) sketch.surface.h;
		
    	if(sketch.isLoop || redraw) {
    		sketch.pushMatrix();
    		sketch.pushStyle();
    		sketch.draw();
    		if(sketch.getChangedPixel()) {
    			sketch.updatePixels();
    			sketch.resetPixelsFlags();
    		}else if(sketch.getLoaded()) {
    			sketch.resetPixelsFlags();
    		}
    		sketch.pushStyle();
    		sketch.popMatrix();
    		redraw = false;
    	}
    	//sketch.println(width, height, sketch.width, sketch.height);
    	sketch.frameRate = (float) frameRate;
    	++sketch.frameCount;
    	if(sketch.finished == true) {
    		error = Sketch.error;
    		Platform.exit();
    	}
    	
    	
    	
    }
    
    private void setCursor(int cursor2) {
    	if(cursor2 >= 0 && cursor2 < cursorSet.length)
    		cursor = cursor2;
    	else
    		cursor = 1;
    	CURSOR choice = cursorSet[cursor];
    	switch(choice) {
    	case CROSS:
    		scene.setCursor(Cursor.CROSSHAIR);
    		break;
    	case HAND:
    		scene.setCursor(Cursor.CLOSED_HAND);
    		break;
    	case MOVE:
    		scene.setCursor(Cursor.MOVE);
    		break;
    	case TEXT:
    		scene.setCursor(Cursor.TEXT);
    		break;
    	case WAIT:
    		scene.setCursor(Cursor.WAIT);
    		break;
    	case NONE:
    		scene.setCursor(Cursor.NONE);
    		break;
    	case ARROW:
    	default:
    		scene.setCursor(Cursor.DEFAULT);
    		break;
    	
    	}
    }

    static void setFullScreen() {
    	bound = Screen.getPrimary().getVisualBounds();
    	width = (int)bound.getWidth();
    	height = (int)bound.getHeight();
    	mainStage.setFullScreen(true);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	mainStage = primaryStage;
    	//primaryStage.setResizable(false);
    	
    	root = new Group();
    	scene = new Scene(root);
    	
    	mainStage.setScene(scene);
    	mainStage.setFullScreenExitHint("");
    	canvas = new Canvas(200, 200);
    	scene.setFill(Color.BLACK);
    	sketch = new __UserDefault();
    	sketch.setContext(canvas);
    	sketch.fill(255);
    	sketch.stroke(0);
    	sketch.strokeWeight(1);
    	sketch.colorMode(SETTINGS.RGB, 255, 255, 255, 255);
    	bound = Screen.getPrimary().getVisualBounds();
    	//sketch.maxWidth = (int)bound.getWidth();
    	mainStage.show();
    	window = Window.getWindows().get(0);
    	
    	
    	
    	
    	
    	//scene.
    	//
    	//

    	
    	//MouseEvent
    	scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	
    	        switch(mouseEvent.getButton()) {
    	        case PRIMARY:
    	        	sketch.mouseButton = SETTINGS.LEFT.getValue();
    	        	break;
    	        case MIDDLE:
    	        	sketch.mouseButton = SETTINGS.CENTER.getValue();
    	        	break;
    	        case SECONDARY:
    	        	sketch.mouseButton = SETTINGS.RIGHT.getValue();
    	        	break;
    	        case FORWARD:
    	        	sketch.mouseButton = SETTINGS.NEXT.getValue();
    	        	break;
    	        case BACK:
    	        	sketch.mouseButton = SETTINGS.BACK.getValue();
    	        	break;
    	        default:
    	        	sketch.mouseButton = SETTINGS.NONE.getValue();
    	        	break;
    	        }
    	        
    	        sketch.mousePressed = true;
    	    	sketch.mousePressed();
    	    	//sketch.mousePressed(mouseEvent)
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_RELEASED  , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	sketch.mousePressed = false;
    	    	sketch.mouseReleased();
    	    	//sketch.mouseReleased(mouseEvent);
    	    	sketch.mouseButton = SETTINGS.NONE.getValue();
    	    }
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>( ) {
    		@Override
    		public void handle(MouseEvent mouseEvent) {
    			sketch.pmouseX = sketch.mouseX;
    			sketch.mouseX =(float) mouseEvent.getX();
    			sketch.pmouseY = sketch.mouseY;
    			sketch.mouseY =(float) mouseEvent.getY();
    			sketch.mouseMove();
    			//sketch.mouseMove(mouseEvent);
    		}
    	});
    	
    	scene.addEventHandler(MouseEvent.MOUSE_DRAGGED , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        
    	    	sketch.mouseX = (float)mouseEvent.getX();
    			sketch.mouseY = (float)mouseEvent.getY();
    	    	sketch.mouseDragged();
    	    	//sketch.mouseDragged(event);
    	    }
    	});
    	
    	
    	
    	//KeyEvent
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//get The keyPressed

				String tmp = event.getText();
				sketch.key = (tmp.length() > 0 && tmp != "?") ? tmp : "CODED";
				sketch.keyCode = event.getCode().ordinal();
				 sketch.keyPressed = true;
				sketch.keyPressed();
				//sketch.keyPressed(event);
			}
    		
    	});
    	

    	
    	scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String tmp = event.getText();
				sketch.key = tmp.length() > 0 ? tmp : "CODED";
				sketch.keyCode = event.getCode().ordinal();
				sketch.keyPressed = false;
				sketch.keyReleased();
				//sketch.keyReleased(event);
				//set KeyPressed to 0
				//set keyCode to 0
			}
    		
    	});
    	
    	scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				String tmp = event.getText();
				sketch.key = tmp.length() > 0 ? tmp : "CODED";
				sketch.keyCode = event.getCode().ordinal();
				sketch.keyTyped();
				//sketch.keyTyped(event);
				//set KeyPressed to 0
				//set keyCode to 0
			}
    		
    	});
    	
    	if(sketch.surface.resizable) {
	    	mainStage.widthProperty().addListener((obs, oldVal, newVal) -> {
	    	     // Do whatever you want
	    		
	    		resizeX(scene.getWidth());
	    	});
	
	    	mainStage.heightProperty().addListener((obs, oldVal, newVal) -> {
	    	     // Do whatever you want
	    		
	    		resizeY(scene.getHeight());
	    	});
    	}
    	
    	
    	
    	
    	////////////
    
    	root.getChildren().add(canvas);
    	
    	
    	sketch.setContext(canvas);
    	sketch.displayWidth = (short) bound.getWidth();
    	sketch.displayHeight = (short) bound.getHeight();
    	mainStage.setTitle(Core.projectName);
    	sketch.init();
    	sketch.setup();
    	mainStage.sizeToScene();
    	//mainStage.setHeight(sketch.height+35);
    	//mainStage.setWidth(sketch.width+17);
    	
    	new Time(this).start();
    
    	
    }
    
    protected void resizeX(double newVal) {
    	sketch.size((int)newVal, (int) sketch.surface.h);
    }
    
    protected void resizeY(double newVal) {
    	sketch.size((int) sketch.surface.w,(int) newVal);
    }
    
    protected float getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(float targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
	static void setLocation(double x, double y) {
		mainStage.setX(x);
		mainStage.setY(y);
	}
	
}
