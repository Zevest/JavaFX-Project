package sketch.template;
import constant.CURSOR;
import constant.SETTINGS;
import util.PVector;
import util.color;
import engine.Sketch;
public class __UserDefault extends Sketch {
public void isItWorking() {
	println("Yes", "it is indeed working");
}

class Test5{
	String rep = "yes";
	String question;
	Test5(String question){
		this.question = question + "?";
	}
	
	void ask() {
		println(this.question);
	}
	
	void answer() {
		println(this.rep);
	}
}

Test5 ques;
public void setup() {
	size(500, 500);
	isItWorking();
	ques = new Test5("Is is possible");
	ques.ask();
	ques.answer();
}

public void draw() {
	background(255);
	textSize(100);
	rect(0, 0, 200, 200);
	text("Hi", width/2, height/2);
}


}