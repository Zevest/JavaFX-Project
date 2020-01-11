package sketch.template;import constant.CURSOR;import constant.SETTINGS;import util.PVector;import util.color;import util.PShape;import engine.Sketch;import java.util.ArrayList;import javafx.scene.image.Image;import java.util.HashMap;import javafx.scene.input.KeyCode;import java.io.BufferedReader;public class __UserDefault extends Sketch {float counter = 0;
float counter2 = 0;
float t = 62;


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

void drawStuff() {
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

void drawCurve1(float x, float y) {
	noFill();
	stroke(255, 102, 0);
	line(x+85, y+20, x+10, y+10);
	line(x+90, y+90, x+15, y+80);
	stroke(0, 0, 0);
	bezier(x+85, y+20, x+10,y+ 10, x+90, y+90, x+15, y+80);
}
void drawCurve2(float x, float y) {
	noFill();
	stroke(255, 102, 0);
	line(x+30, y+20, x+80, y+5);
	line(x+80, y+75, x+30, y+75);
	stroke(0, 0, 0);
	bezier(x+30, y+20, x+ 80,y+ 5, x+ 80, y+75,  x+30, y+75);
}

void drawBezierPoint() {
	/*noFill();
	bezier(85, 20, 10, 10, 90, 90, 15, 80);
	fill(255);*/
	int steps = 10;
	for (int i = 0; i <= steps; i++) {
	  float t = i / _float(steps);
	  float x = bezierPoint(85, 10, 90, 15, t);
	  float y = bezierPoint(20, 10, 90, 80, t);
	  ellipse(x, y, 5, 5);
	  //println(x,y,i,  t, _float(steps));
	}
}

void drawTangent1() {
	noFill();
	bezier(85, 20, 10, 10, 90, 90, 15, 80);
	int steps = 6;
	fill(255);
	for (int i = 0; i <= steps; i++) {
	  float t = i / _float(steps);
	  println("i", i);
	  // Get the location of the point
	  float x = bezierPoint(85, 10, 90, 15, t);
	  float y = bezierPoint(20, 10, 90, 80, t);
	  // Get the tangent points
	  float tx = bezierTangent(85, 10, 90, 15, t);
	  float ty = bezierTangent(20, 10, 90, 80, t);
	  // Calculate an angle from the tangent points
	  float a = atan2(ty, tx);
	  a += PI;
	  stroke(255, 102, 0);
	  line(x, y, cos(a)*30 + x, sin(a)*30 + y);
	  // The following line of code makes a line 
	  // inverse of the above line
	  //line(x, y, cos(a)*-30 + x, sin(a)*-30 + y);
	  stroke(0);
	  ellipse(x, y, 5, 5);
	}
}

void drawTangent2() {
	noFill();
	bezier(85, 20, 10, 10, 90, 90, 15, 80);
	stroke(255, 102, 0);
	int steps = 16;
	for (int i = 0; i <= steps; i++) {
	  float t = i / _float(steps);
	  float x = bezierPoint(85, 10, 90, 15, t);
	  float y = bezierPoint(20, 10, 90, 80, t);
	  float tx = bezierTangent(85, 10, 90, 15, t);
	  float ty = bezierTangent(20, 10, 90, 80, t);
	  float a = atan2(ty, tx);
	  a -= HALF_PI;
	  line(x, y, cos(a)*8 + x, sin(a)*8 + y);
	}
}
public void setup(){
  size(500, 500);
  surface.setLocation(displayWidth/2-width/2, displayHeight/2 - height/2);
  frameRate(t);
  textAlign(CENTER,BOTTOM);
  surface.setResizable(true);
  background(255);
}
float y = -5.0f;

public void draw(){
	background(255);
	shapeCurve();
	//noLoop();
	
}

void shapeCurve() {
	noFill();
	beginShape();
	vertex(30, 20);
	bezierVertex(80, 0, 80, 75, 30, 75);
	endShape();
}

void drawCurveTangent() {
	noFill();
	curve(5, 26, 73, 24, 73, 61, 15, 65); 
	int steps = 6;
	for (int i = 0; i <= steps; i++) {
	  float t = i / _float(steps);
	  float x = curvePoint(5, 73, 73, 15, t);
	  float y = curvePoint(26, 24, 61, 65, t);
	  //ellipse(x, y, 5, 5);
	  float tx = curveTangent(5, 73, 73, 15, t);
	  float ty = curveTangent(26, 24, 61, 65, t);
	  float a = atan2(ty, tx);
	  a -= PI/2.0;
	  line(x, y, cos(a)*8 + x, sin(a)*8 + y);
	}
}

void drawCurvePoint() {
	noFill();
	curve(5, 26, 5, 26, 73, 24, 73, 61);
	curve(5, 26, 73, 24, 73, 61, 15, 65);
	fill(255);
	ellipseMode(CENTER);
	int steps = 6;
	for (int i = 0; i <= steps; i++) {
	  float t = i / _float(steps);
	  float x = curvePoint(5, 5, 73, 73, t);
	  float y = curvePoint(26, 26, 24, 61, t);
	  ellipse(x, y, 5, 5);
	  x = curvePoint(5, 73, 73, 15, t);
	  y = curvePoint(26, 24, 61, 65, t);
	  ellipse(x, y, 5, 5);
	}
}

void drawCurve() {
	noFill();
	stroke(255, 102, 0);
	curve(5, 26, 5, 26, 73, 24, 73, 61);
	stroke(0); 
	curve(5, 26, 73, 24, 73, 61, 15, 65); 
	stroke(255, 102, 0);
	curve(73, 24, 73, 61, 15, 65, 15, 65);
}
}