package sketch.examples;

import constant.DRAW_MODE;
import engine.Sketch;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import util.PVector;
import util.color;

public class Example2 extends Sketch{
	//LifeParticles
	/////////////////////////////////////
	
	ArrayList<Particle> particles;
	QuadTree qt;
	AABB canvas;
	boolean debug = false;
	CircleRange cadre;

	int population = 1000;
	boolean Boxed = false;
	int clickCount = 0;
	String[] t = {"red", "blue", "green", "yellow", "white", "magenta", "cyan"};
	PVector[] pos;

	boolean followed = false;

	public void setup(){
	  //fullScreen();
		size(500, 500);
	  //frameRate(10);
	  particles = new ArrayList<Particle>();
	  color[] col = {color(255,0,0), color(0,0,255), color(0,255,0), color(255,255,0), color(255,255,255),
	                color(255,0,255), color(0,255,255)};
	  HashMap <String, HashMap> sete = createTable(t);
	  for(int i = 0; i< population; i++){
	    int R = _int(random(t.length)); 
	    new Colored();
	    particles.get(i).setColor(col[R]);
	    particles.get(i).setName(t[R]);
	    particles.get(i).setRelation(sete.get(t[R]));
	  }
	  pos = new PVector[2];
	  pos[0] = new PVector(0,0);
	  pos[1] = new PVector(width, height); 
	  canvas = new AABB(pos[0].x, pos[0].y, abs(pos[1].x - pos[0].x),  abs(pos[1].y - pos[0].y));
	}

	public void draw(){
	  qt = new QuadTree(width/2, height/2, width, height, 0);
	  background(0.1);
	  noStroke();
	  canvas.setPos(pos[0]);
	  canvas.setSize((pos[1].x - pos[0].x)*2, (pos[1].y - pos[0].y)*2);
	  for(Particle p: particles){ 
	    qt.insert(p);
	    fill(127);
	    if(!Boxed){
	      p.display();
	    }
	  }
	  
	  //if(debug){
	  //  ArrayList<Particle> notIn = new ArrayList<Particle>(particles);
	  //  ArrayList<Particle> others = qt.queryRange(cadre);
	  //  notIn.removeAll(others);
	  //  for(Particle p: notIn){
	  //    fill(255);
	  //    p.display();  
	  //  }
	  //  for(Particle other: others){
	  //        fill(255,0,0);
	  //        other.display();
	  //  }
	  //  noFill();
	  //  stroke(0,255,0);
	  //  ellipse(cadre.x, cadre.y, cadre.sizeX, cadre.sizeY);
	  //}
	  
	  //fill(0,255,2);
	  for(Particle p: particles){
	    ArrayList<Particle> others = qt.queryRange(p.hitBox);
	    others.remove(p);
	    p.setClose(others);
	    for(Particle other: others){
	      p.collide(other);
	    }
	    if(followed){
	      p.follow(mouseX, mouseY);
	    }
	    p.update();
	  }
	  
	  if(Boxed){
	    noFill();
	    stroke(0,255,0);
	    //rectMode(CORNER);
	    rect(canvas.x, canvas.y, canvas.sizeX, canvas.sizeY);
	    ArrayList<Particle> others = qt.queryRange(canvas);
	    for(Particle other: others){
	          other.display();
	    } 
	  }
	  rectMode(DRAW_MODE.CENTER);

	  stroke(255);
	  noFill();
	  //int i = 0;
	  if(debug){
	    for(AABB range: bounds){
	      strokeWeight(1);
	      // && range.containsPoint(mouseX, mouseY)){ 
	      rect(range.x, range.y, range.sizeX, range.sizeY);
	    }
	  }
	  bounds.removeAll(bounds);
	}

	void getLast(QuadTree  q){
	  if(q.child.isEmpty()){
	    print(q.points.size());
	  }
	  else {
	    for(QuadTree ch: q.child){
	      print(q.points.size(), ",");
	      print("(");
	      getLast(ch);
	      print(" )");
	    }
	  }
	  
	}

	public void keyPressed(){
	  if(key.equals("d")){
	    debug = !debug;
	  }
	  if(key.equals("r")){
	    Boxed = !Boxed;
	  }
	  if(key.equals("y")){
	    followed = !followed;
	    println(followed);
	  }
	  if(key.equals("t")){
	    HashMap <String, HashMap> sete = createTable(t);
	    for(int i = 0; i< population; i++){
	      int R = _int(random(t.length)); 
	      particles.get(i).setRelation(sete.get(t[R]));
	    }
	  }
	}

	public void mousePressed(){
	  pos[0] = new PVector(mouseX, mouseY);
	}
	public void mouseDragged(){
	  pos[1].set(mouseX, mouseY);
	}
	
	////////////////////////////////////
	////////other Particle//////////////
	class Colored extends Particle{
		  ArrayList<Particle> close;
		  float a, b;
		  float r;
		  PVector Force;
		  float ze;
		  Colored(){
		    super();
		    init();
		  }
		  
		  Colored(float x, float y){
		    super(x, y);
		    init();
		  }
		  
		  void init(){
		    close = new ArrayList<Particle>();
		    //relation
		    a = random(size/2, 25);
		    b = random(a, 100);
		    r = 150;
		    hitBox.sizeX = r;
		    hitBox.sizeY = r;
		  }

		  void behavior(){
		    // represente it's behavior
		    for(Particle p: close){
		      Force = new PVector(p.pos.x - pos.x, p.pos.y - pos.y);
		      float dis = dist(p.pos.x, p.pos.y, pos.x, pos.y);
		      Force.normalize();
		      ze = attract(dis);
		      float fact = relation.get(p.type);
		      ze *= fact;
		      Force.mult(ze);
		      PVector flee = PVector.sub(pos,p.pos).mult(size/dis);
		 
		      if(dis < size){
		        appForce(flee);
		        //println(flee.mag());
		      }
		      
		      //Force.mult(1/(size/PVector.dist(p.pos,pos)));
		      //line(pos.x, pos.y,pos.x + Force.x, pos.y + Force.y);
		      appForce(Force);
		    }
		  }
		  
		  void setClose(ArrayList<Particle> ps){
		    close = new ArrayList<Particle>(ps);
		  }
		  
		  float attract(float X){
		    if(X < a){
		      return abs((a-log(X)));
		    }else if(X >a && X < (b-a)/2){
		      //println("A");
		      return abs((X - a)); 
		      
		    }else if(X >(b-a)/2 && X < b){
		       //println("B");
		      return abs(-X +b);
		    }
		    else {
		      Force.set(new PVector(random(-2,2), random(-2,2)));
		      //println("C");
		      return random(1);
		      
		    }
		  }
		  void display(){
		    fill(col);
		    super.display();
		  }
		}
	
	////////////////////////////////////
	/////////   Particle    ////////////
	class Particle {
		  PVector pos;
		  //PVector lastPos;
		  PVector vel;
		  PVector acc;
		  float size = 15;
		  color col;
		  float maxForce = 4;
		  CircleRange hitBox;
		  String type = "BASIC";
		  HashMap<String, Float> relation;

		  
		  
		  float ze = 1;
		  
		  Particle(float x, float y){
		    pos = new PVector(x, y);
		    //lastPos = new PVector (x, y);
		    particles.add(this);
		    vel = new PVector(0, 0);
		    acc = new PVector(0, 0);
		    hitBox = new CircleRange(x, y, size);
		    init();
		  }
		  void init(){
		    relation.put("ALL", 1.0f);
		  }
		  
		  void setName(String g){
		    type = g;
		  }  
		    
		  void setRelation(HashMap rel){
		    relation = rel;
		  }
		    
		  void setColor(color c){
		    col = c;
		  }
		  
		  Particle(){
		    pos = new PVector(random(width), random(height));
		    particles.add(this);
		    //lastPos = new PVector(pos.x,pos.y);
		    vel = new PVector(0, 0);
		    acc = new PVector(0, 0);
		    hitBox = new CircleRange(pos.x, pos.y, size*2);
		    init();
		  }
		  void display(){
		    noStroke();
		    ellipse(pos.x, pos.y, size, size);
		  }
		  
		  void behavior(){
		    follow(mouseX, mouseY);
		  }
		  
		  void edges(){
		    if (pos.x > width) pos.x = 0;
		    if (pos.x < 0) pos.x = width;
		    if (pos.y > height) pos.y = 0;
		    if (pos.y < 0) pos.y = height;
		  }
		  void update(){
		    //lastPos.set(pos.x, pos.y);
		    behavior();
		    acc.limit(maxForce);
		    vel.add(acc);
		    vel.limit(10);
		    pos.add(vel);
		    acc.mult(0);
		    vel.mult(0.98);
		    edges();
		    hitBox.x = pos.x;
		    hitBox.y = pos.y;
		  }
		  void appForce(PVector f){
		    acc.add(f);
		  }
		  
		  boolean collide(Particle p){
		    return dist(pos.x, pos.y, p.pos.x, p.pos.y) <= size/2 + p.size/2;
		  }
		  
		  void setClose(ArrayList<Particle> ps){
		    //println("notBehave" + ps);
		  }
		  void follow(PVector target){
		    PVector steer = new PVector(target.x - pos.x, target.y - pos.y);
		    steer.setMag(maxForce);
		    appForce(steer);
		  }
		  void follow(float x, float y){
		    PVector steer = new PVector(x - pos.x, y - pos.y);
		    steer.setMag(maxForce);
		    appForce(steer);
		  }
		}
	
	////////////////////////////////////
	////////////   Relation   //////////

	HashMap<String, HashMap> createTable(String[] name){
	  HashMap<String, HashMap> tables = new HashMap<String, HashMap>();
	  for(int i = 0; i < name.length; i++){
	    HashMap<String, Float> r = new HashMap<String, Float>();
	    for(int j = 0; j < name.length; j++){  
	      r.put(name[j], (float)random(-2,2));
	    }
	    tables.put(name[i], r);
	  }
	  return tables;
}
	////////////////////////////////////
	/////////////   Tree    ////////////
	class QuadTree {
		  int node_capacity = 4;
		  AABB boundary;
		  int level;
		  ArrayList<Particle> points;
		  ArrayList<QuadTree> child;
		  boolean subdivided;
		  
		  QuadTree(float x, float y, float size){
		    boundary = new AABB(x, y, size);
		    points = new ArrayList<Particle>();
		    child = new ArrayList<QuadTree>();
		    level = 0;
		    subdivided = false;
		  }
		  QuadTree(float x, float y, float sizeX, float sizeY,int  lvl){
		    boundary = new AABB(x, y, sizeX, sizeY);
		    points = new ArrayList<Particle>();
		    child = new ArrayList<QuadTree>();
		    level = lvl;
		    subdivided = false;
		  }
		  
		// Insérer un point dans le QuadTree
		  boolean insert(Particle p){
		 // Ignorer les objets qui n'appartiennent pas à ce quadtree
		    if(!boundary.containsPoint(p.pos.x,p.pos.y))
		      return false;// l'objet ne doit pas être ajouté
		    
		// S'il reste de la place dans ce quadtree, y ajouter l'objet
		    if(points.size() < node_capacity){
		        points.add(p);
		        return true;
		    }
		    
		// Sinon, subdiviser le quadtree, puis ajouter le point au nœud qui l'acceptera
		    if(!subdivided){
		      subdivide();
		      subdivided = true;
		    }
		    
		    
		    for(QuadTree qt : child){
		      //for(Particle par: points){
		      //  if(qt.insert(par)){
		      //    break;
		      //  }
		      //}
		      if(qt.insert(p)){
		        //fill(255, 0, 0, 10);
		        //rect(qt.boundary.x, qt.boundary.y, qt.boundary.sizeX, qt.boundary.sizeY);
		        return true;
		      }
		    }
		    //points.removeAll(points); 
		// Sinon, le point ne peut être inséré,
		// pour une raison inconnue (cela ne devrait jamais arriver)
		    return false;
		  }
		  
		  void subdivide(){
		    for(int j = -1; j< 2; j+=2){
		      for(int i = -1; i< 2; i+=2){
		        child.add(new QuadTree(boundary.x + (i * boundary.sizeX/4),
		                               boundary.y + (j * boundary.sizeY/4),
		                               boundary.sizeX/2,
		                               boundary.sizeY/2,
		                               level+ 1));
		      }
		    }
		  }
		  
		  void getChildDim(){
		    if(child != null){
		      for(QuadTree ch: child){
		        //print(ch.boundary.x, ch.boundary.y, ch.boundary.sizeX, ch.boundary.sizeY, "; ");
		    	  
		      }
		      //println();
		    }
		  }
		  
		  ArrayList<Particle> queryRange(AABB range){
		    ArrayList<Particle> pointsInRange = new ArrayList<Particle>();
		    
		    if(!boundary.intersectsAABB(range))
		      return pointsInRange;
		    
		    for(Particle p: points){
		      if(range.containsPoint(p.pos.x,p.pos.y))
		        pointsInRange.add(p);
		    }
		    
		    if(!subdivided){
		      return pointsInRange;
		    }
		    for(QuadTree qt: child){
		      pointsInRange.addAll(qt.queryRange(range));
		    }
		    return pointsInRange;
		  }
		}

		ArrayList<AABB> bounds = new ArrayList<AABB>();
		class AABB{
		  float x, y, sizeX, sizeY;
		  AABB(float x, float y, float s){
		    this.x = x;
		    this.y = y;
		    sizeX = s/2;
		    sizeY = s/2;
		    bounds.add(this);
		  }
		  
		  AABB(float x, float y, float s, boolean added){
		    this.x = x;
		    this.y = y;
		    sizeX = s/2;
		    sizeY = s/2;
		    if(added)
		      bounds.add(this);
		  }
		  
		  AABB(float x, float y, float sx, float sy){
		    this.x = x;
		    this.y = y;
		    sizeX = sx;
		    sizeY = sy;
		    bounds.add(this);
		  }
		  
		  AABB(float x, float y, float sx, float sy, boolean added){
		    this.x = x;
		    this.y = y;
		    sizeX = sx;
		    sizeY = sy;
		    if(added)
		      bounds.add(this);
		  }
		  
		  boolean containsPoint(float x, float y){
		    return (x> this.x - sizeX/2 && x< this.x + sizeX/2)
		        && (y> this.y - sizeY/2 && y< this.y + sizeY/2);
		  }
		  
		  boolean intersectsAABB(AABB range){
		    if(range.x - range.sizeX/2 > x+sizeX/2 || 
		       range.x + range.sizeX/2 < x-sizeX/2 ||
		       range.y - range.sizeY/2 > y+sizeY/2 || 
		       range.y + range.sizeY/2 < y-sizeY/2)
		       return false;
		     return true;
		  }
		  void setPos(float x, float y){
		    this.x = x; this.y = y;
		  }
		  void setPos(PVector pv){
		    this.x = pv.x; this.y = pv.y;
		  }
		  void setSize(float x, float y){
		    this.sizeX = x; this.sizeY = y;
		  }
		  void setSize(PVector pv){
		    this.sizeX = pv.x; this.sizeY = pv.y;
		  }
		}

		class CircleRange extends AABB{
		  
		  CircleRange(float x, float y, float s){
		    super(x,y,s, false);
		  }
		  boolean containsPoint(float x, float y){
		    return dist(this.x, this.y, x, y) < this.sizeX/2;
		  }
		  
		  boolean intersect(CircleRange c){
		    return dist(this.x, this.y, x, y) < this.sizeX/2 + c.sizeX/2;
		  }
		}
	
	////////////////////////////////////
	
}
