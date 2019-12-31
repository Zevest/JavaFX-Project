package sketch.examples;

import engine.Sketch;
import javafx.scene.image.Image;

public class Example1 extends Sketch {
	Image img;
	public Example1() {}
	Grid grid;
	Turtle t;
	boolean run;
	
	public void setup(){
	  size(500,500);
	  grid = new Grid(20,20);
	  System.out.println("grid.world[0]" + grid.world[0]);
	  t = new Turtle(10,10);
	}

	public void draw(){
	  grid.display();
	  if(run) t.update();
	  t.display();
	}

	public void mousePressed(){
	  //t.update();
		//run = true;
	}
	public void mouseRelease() {
		//run = false;
	}
	
	
	public void keyPressed(){
	  float W = width / grid.w;
	  float H = height / grid.h;
	  int x = (int)(mouseX/W);
	  int y = (int)(mouseY/H);
	  //println(x,y,W,H);
	  
	  //f(keyCode == .SPACE)
	  //t.update();
	  if(key.equals(" "))
		  run = true;
	  if(key==CODED){
	    switch(keyCode){

	      case DOWN:
	        grid.set(x,y,"D");
	        break;
	      case LEFT:
	        grid.set(x,y,"L");
	        break;
	      case RIGHT:
	        grid.set(x,y,"R");
	        break;
	      default:
	    	  println("other");
	    	  break;
	    }
	  }
	}
	public void keyReleased(){
	  //if(key == " ")
	    run = false;
	}
	/*public void setup() {
		size(1000, 1000);
		//System.out.println(FileManager.getFileUrl("anime.jpg"));
		//img = loadImage("anime.jpg");
	}
	
	public void draw() {
		
		
	}*/
	class Grid{
		  String[] world;
		  int w, h;
		  float lw;
		  float lh;
		  
		  Grid(int w, int h){
		    this.w = w;
		    this.h = h;
		    lw = width/this.w;
		    lh = height/this.h;
		    world = new String[this.w * this.h];
		  }
		  
		  void display(){
		    float lw = width/w;
		    float lh = height/h;
		    textAlign(CENTER);
		    for(int i = 0; i < w*h; i++){
		      int[] pos = coord(i);
		      fill(255);
		      rect(pos[0] * lw, pos[1] * lh, lw, lh); 
		      if(world[i] != "" && world[i] != null){
		        fill(0);
		        text(world[i], pos[0] * lw + lw/2, pos[1]*lh+lh/1.5);
		      }
		    }
		  }
		  
		  void set(int x, int y, String val){
		    println(""+val+" Added to ("+x+","+y+")");
		    world[index(x,y)] = val;
		    println(world[index(x,y)] = val);
		  }
		  
		  String get(int x, int y){
			 // println("getting",world[index(x,y)]);
		    return world[index(x,y)];
		  }
		  
		  int[] coord(int index){
		    int[] temp = {index % w, (int) index/h};
		    return temp;
		  }
		  
		  int index(int x, int y){
		    return y * w + x;
		  }
		}
	
	class Turtle{
		  int x,y,orient, speed, forward;
		  float size;
		  
		  Turtle(int x,int y){
		    this.x = x;
		    this.y = y;
		    orient = 0;
		    speed = 1;
		    forward = 1;
		    size = grid.lw;
		  }
		  
		  void display(){
		    pushMatrix();
		    translate(x * grid.lw+0.5*size, y* grid.lh+0.5*size);
		    rotate(-HALF_PI*(orient%4));
		    //println(color(0, 200, 40));
		    fill(color(0,200,40));
		    //triangle(-.5*size,-.5*size,-.5*size,.5*size,.5*size,0);
		    rectMode(CENTER);
		    triangle(-0.5*size, -0.5*size, -0.5*size , 0.5*size, 0.5*size, 0);
		    popMatrix();
		  }
		  
		  void update(){
			//println(x, y, grid.index(x,y));
			//println(grid.w, grid.h);
		    //println(grid.get(x,y));
		    move(grid.get(x,y));
		    int[] dirX = {1,0,-1,0};
		    int[] dirY = {0,-1,0,1};
		    //println(dirX);
		    /*if(x+speed < grisd.w) x+=speed*forward;
		    if(y+speed < grid.h) y+=1*forward;
		    if(x-speed > 0) x+=speed*forward;
		    if(y-speed < 0) y+=speed*forward;
		    }
		  */
		    x+= dirX[(orient%4)] * speed * forward;
		    y+= dirY[(orient%4)] * speed * forward;
		    //println(x,y,orient,forward);
		  }
		  
		  void move(String data){
		    if (data=="U"){
		      forward = 1;
		    }else if (data=="D"){
		      forward *= -1;
		    }else if (data=="R"){
		      orient--;
		      if (orient < 0) orient = 3;
		    }else if (data=="L"){
		      orient++;
		      
		    }
		    
		  }
		}
}

