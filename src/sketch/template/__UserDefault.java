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
float counter = 0;
float counter2 = 0;
float t = 62;
public void setup(){
  size(500, 500);
  surface.setLocation(displayWidth/2-width/2, displayHeight/2 - height/2);
  frameRate(t);
  textAlign(CENTER,BOTTOM);
  colorMode(HSB);
  surface.setResizable(true);
}

public void draw(){
	frameRate(t);
	background(255);
  //arc(200, 200, 400, 400, 0, PI+QUARTER_PI, CLOSE);
	stroke(0);
	fill(125);
	//strokeWeight(0.1);
	
	float lx = width/8;
	float ly = height/8;
	float ls = min(lx, ly)*1.3f;
	
	arc(lx, ly, ls, ls, 0, PI+QUARTER_PI, OPEN);
	arc(3*lx, ly, ls,ls, 0, PI+QUARTER_PI, CHORD);
	arc(lx, 3*ly, ls, ls, 0, PI+QUARTER_PI, PIE);
	arc(3*lx, 3*ly, ls, ls, 0, PI+QUARTER_PI);
	
	
	float l = min(width, height);
	
	float size = (l/4-35)*sin(counter);
	float sideCount = 8+5*cos(counter);
	float angle = TWO_PI/50.0f*(counter);
	
	
	fill(sin(counter*1.5)*128+128, 255, 255);
	drawShape(width/4*3, height/5, size, sideCount,  angle);
	
	float size2 = (l/4-35)*sin(counter2);
	float sideCount2 = 8+5*cos(counter2);
	float angle2 = TWO_PI/50.0f*(counter2);
	
	fill(sin(counter2*1.5)*128+128, 255, 255);
	drawShape(width/4*3, height/5*4, size2, sideCount2,  angle2);
	
	
	
	
	fill(0);
	float x = width/2 + cos(counter - HALF_PI) * l/2;
	float y = height/2 + sin(counter - HALF_PI) * l/2;
	line(width/2, height/2, x, y);
	float ball = min(width, height)*0.03f;
	ellipse(x, y, ball, ball);
	text("frameRateTarget " + t, width/2, height/2);
	counter+= deltaTime;
	counter2 += 0.01696;
	
}

public void mousePressed() {
	switch(mouseButton) {
	case LEFT:
		++t;
		break;
	case RIGHT:
		--t;
		break;
	case CENTER:
		t = 62;
		break;
	} 
	if (t < 1)
		t = 1;
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