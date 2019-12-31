package sketch.examples;

import engine.Sketch;
import util.PVector;

public class Example3 extends Sketch {
	
	int w, h;
	float len, offsetX = 0, offsetY = 0;
	public void setup() {
		size(800, 800);
		w = 20;
		h = 20;
		len = sqrt(sq(w)+sq(h));
		cursor(MOVE);
		//noiseSeed(55051);
	}
	
	public void draw() {
		background(0);
		PVector mousePos = new PVector(mouseX, mouseY);  
		for(int j = 0; j < h; ++j) {
			float posY = j*height/h;
			for(int i = 0; i< w; ++i) {
				float val = noise((double)i/(double)w + offsetX,(double) j/(double)h + offsetY)*TWO_PI;
				float posX = i*width/w;
				
				PVector p = PVector.fromAngle(val);
				PVector v1 = PVector.sub(mousePos, new PVector(posX, posY)).normalize();
				p.rotate(PVector.angleBetween(p, v1));
				val += p.heading(); 
				stroke(255);
				line(posX, posY, posX + sin(val)*len, posY + + cos(val)*len);
			}
		}
		//textFont("Arial", 50);
		textSize(50);
		String t = "Hi\nSalut";
		text(t + textWidth(t), width/2, height/2);
		line(width/2, height/2, width/2 + textWidth(t), height/2);
		
		offsetX +=0.001;
		offsetY += 0.002;
		//noLoop();
	}
}
