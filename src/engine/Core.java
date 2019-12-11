package engine;

import engine.Time;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Core extends Application {
	public static int width = 800;
	public static int height = 600;
	public static long frameCount = -1;
	public double frameRate = 0; 
	private int targetFrameRate = 120;
	private Canvas canvas;
	protected GraphicsContext context;
	private Stage mainStage;
	private Group root;
	private Scene scene;
	protected Sketch sketch; 
	
    public static void main(String[] args) {
        Application.launch(Core.class, args);
    }
    
    
    protected void update(){
    	sketch.draw();  	
    }

    @Override
    public void start(Stage primaryStage) {
    	mainStage = primaryStage;
    	mainStage.setTitle("HelloWorld");
    	root = new Group();
    	scene = new Scene(root);
    	mainStage.setScene(scene);
    	
    	canvas = new Canvas(200, 200);
    	
    	sketch = new Sketch();
    	sketch.setContext(canvas);
    	sketch.setup();
    	canvas.setWidth(sketch.width);
    	canvas.setHeight(sketch.height);
    	root.getChildren().add(canvas);
    	
    	sketch.setContext(canvas);

    	new Time(this).start();
    	mainStage.show();
    	
    }
    
    public int getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(int targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}
	
}
