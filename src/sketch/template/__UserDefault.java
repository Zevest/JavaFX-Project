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
class Ant{
  int nodePos;
  ArrayList<Integer> visited;
  
  
  Ant(int i){
    nodePos = i;
    visited = new ArrayList<Integer>();
    visited.add(nodePos);
  }  
  
  ArrayList<Integer> genPossible(int n){
    ArrayList<Integer> possible = new ArrayList<Integer>();
    boolean can = true;
    for(int i = 0; i < n; i++){
      can = true;
      for(int member: visited){
        if(i == member){
          can = false;
          break;
        }
      }
      if(can){
        possible.add(i);
      }
    }
    println(possible);
    return possible;
  }
  
}
Graph g;
int count = 0;
public void setup(){
  size(1280, 720);
  g = new Graph(3);
  g.connectAll();
  surface.setTitle("Hello");
  //surface.setLocation(0, 0);
  cursor();
}

public void draw(){
  background(0);
  g.display();
  println("Drawing");
  noLoop();
  if(count >= 5)
	  exit(5);
}

public void mousePressed(){
  g.resetAll();
  //g.showOutput();
  redraw();
  ++count;
  surface.setLocation(mouseX, mouseY);
}
class Graph {
  ArrayList<Node> nodes;
  ArrayList<PVector> binds;
  ArrayList<Ant> ants;
  float maxDist = sqrt(width*width + height*height)/1.3f;
  int antPerNode = 2;
  Graph(int n){
    ants = new ArrayList<Ant>();
    nodes = new ArrayList<Node>();
    binds = new ArrayList<PVector>();
    for(int i = 0; i< n; ++i){
      nodes.add(new Node());
      for(int j = 0; j < antPerNode; ++j){
        ants.add(new Ant(i));
      }
    }
  }
  
  Graph(int n, int k){
    ants = new ArrayList<Ant>();
    nodes = new ArrayList<Node>();
    binds = new ArrayList<PVector>();
    for(int i = 0; i< n; ++i){
      nodes.add(new Node());
      for(int j = 0; j < antPerNode; ++j){
        ants.add(new Ant(i));
      }
    }
    initBinds(k);
  }
  
  void initNodes(int n){
    for(int i = 0; i< n; ++i){
      nodes.set(i,new Node());
    }
  }
  void initBinds(int n){
    int count = 0;
    while(count < n){
      if(bind((int) random(nodes.size()),(int) random(nodes.size())))
        count++;
    }
  }
  
  void connectAll(){
    binds = new ArrayList<PVector>();
    for(int i = 0; i < nodes.size(); ++i){
      for(int j = i+1; j < nodes.size(); ++j){
       bind(i,j); 
      } 
    }
  }
  
  void resetBinds(){
    int count = 0;
    while(count < binds.size()){
      if(rebind(count, (int) random(nodes.size()),(int) random(nodes.size())))
        count++;
    }
  }
  
  void showOutput(){
    println("Possible: ");
    for(Ant a: ants){
      ArrayList<Integer> possible = a.genPossible(binds.size());
      ArrayList<Float> proba = genProba(possible);
      println(proba);
    }
  }
  
  ArrayList<Float> genProba(ArrayList<Integer> Possible){
    ArrayList<Float> p = new ArrayList<Float>();
    for(int i: Possible){
      PVector data = binds.get(i);
      float dij = data.z - (int)data.z;
      float TRij = _int(data.z) / 10000f;
      println(dij, TRij);
      p.add(TRij / pow(dij, 7));
    }
    return p;
  }
  
  void resetAll(){
    int n = nodes.size();
    nodes = new ArrayList<Node>();
    binds = new ArrayList<PVector>();
    for(int i = 0; i< n; ++i){
      nodes.add(new Node());
    }
    connectAll();
  }
  
  boolean bind(int a, int b){
    if(a == b)
      return false;
    for(PVector p: binds){
      if(((int) p.x == a && b == (int) p.y)  || 
        ((int) p.y == a && b == (int) p.x))
        return false;
    }
    Node s = nodes.get(a), e = nodes.get(b);
    binds.add(new PVector(a, b, (int) random(10000) + min(0.9999999999,dist(s.x, s.y, e.x, e.y)/maxDist)));
    return true;
  }
  boolean rebind(int i, int a, int b){
    if(a == b)
      return false;
    for(PVector p: binds){
      if(((int) p.x == a && b == (int) p.y)  || 
        ((int) p.y == a && b == (int) p.x))
        return false;
    }
    binds.set(i, new PVector(a, b));
    return true;
  }
  
  void display(){
    
    strokeWeight(10);
    for(PVector p: binds){
      float val = p.z - (int) p.z;
      float p2 = (p.z - val)/10000;
      stroke(val* 255 , (1-val)*200, p2 * 255);
      Node a = nodes.get((int)p.x);
      Node b = nodes.get((int)p.y);
      line(a.x, a.y, b.x, b.y);
    }
    
    noStroke();
    for(Node n: nodes){
      
      n.display();
    }
    
    
  }
  

}
class Node {
  color data;
  float x, y, size = 25;
  Node(){
    data = color(random(255), random(255), random(255));
    this.x = random(size/2, width-size/2);
    this.y = random(size/2, height-size/2);
  }
  
  Node (color data){
    this.data = data;
    this.x = random(size/2, width-size/2);
    this.y = random(size/2, height-size/2);
  }
  
  Node(float x, float y){
    data = color(random(255), random(255), random(255));
    this.x = x;
    this.y = y; 
  }
  Node(color data, float x, float y){
    this.data = data;
    this.x = x;
    this.y = y; 
  }
  
  void display(){
    fill(this.data);
    ellipse(this.x, this.y, size, size);
  }
}
}