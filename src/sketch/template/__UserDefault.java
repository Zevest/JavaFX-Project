package sketch.template;
import constant.CURSOR;
import constant.SETTINGS;
import util.PVector;
import util.color;
import engine.Sketch;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
public class __UserDefault extends Sketch {
public void setup(){
  size(500, 500);
  surface.setLocation(displayWidth/2-width/2, displayHeight/2 - height/2);
}

public void draw(){
 // arc(200, 200, 400, 400, 0, PI+QUARTER_PI, CLOSE);
	/*strokeWeight(0.1);
	arc(100, 50, 80, 80, 0, PI+QUARTER_PI, OPEN);
	arc(50, 100, 80, 80, 0, PI+QUARTER_PI, CHORD);
	arc(100, 100, 80, 80, 0, PI+QUARTER_PI, PIE);
	
	
	
	arc(50, 55, 50, 50, 0, HALF_PI);
	noFill();
	arc(50, 55, 60, 60, HALF_PI, PI);
	arc(50, 55, 70, 70, PI, PI+QUARTER_PI);
	arc(50, 55, 80, 80, PI+QUARTER_PI, TWO_PI);
	
	strokeWeight(0.1);
	ellipse(200, 200, 100, 100);
	line(0, 0, width, height);
	rect(50, 55, 50, 50);
	rect(50, 55, 60, 60);
	rect(50, 55, 70, 70);
	rect(50, 55, 80, 80);*/
	//drawShape(width/2, height/2, 200, 4, QUARTER_PI);
	drawShape(width/2, height/2, 200*sin(frameCount/500.0),8+5*cos(frameCount/200.0),  TWO_PI/50.0f*(frameCount/6.0f));
	
}
void drawShape(float x, float y, float l, float n, float startAngle){
	  //pushMatrix();
	  //translate(x, y);
	  beginShape();
	  for(int i = 0; i < n; i++){
	    float angle = TWO_PI/n * i + startAngle;
	    vertex(x + cos(angle)*l, y + sin(angle)* l); 
	  }
	  endShape();
	  
	  //popMatrix();
	}
}