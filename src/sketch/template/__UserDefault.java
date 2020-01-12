package sketch.template;import constant.CURSOR;import constant.SETTINGS;import util.PVector;import util.color;import util.PShape;import engine.Sketch;import java.util.ArrayList;import javafx.scene.image.Image;import java.util.HashMap;import javafx.scene.input.KeyCode;import java.io.BufferedReader;public class __UserDefault extends Sketch {int day, month, year;
String[] days = {
		"Sunday","Monday","Tuesday","Wednesday",
		"Thursday","Friday","Saturday"
		
};
String[] months = {
		"January","February","March",
		"April","May","June",
		"July","August","September",
		"October","November","December"
};

public void setup() {
	size(500, 500);
	day = day();
	month = month();
	year = year();
	textAlign(CENTER);
	textSize(30);
	noStroke();
}

public void draw() {
	int secH = 50,minH = 50, hourH = 50;
	background(0);
	fill(255, 0, 0);
	rect(0, height/2, map(second(), 0, 59, 0, width), secH);
	fill(0, 255, 0);
	rect(0, height/2 + 100, map(minute(), 0, 59, 0, width), minH);
	fill(0, 0, 255);
	rect(0, height/2+200, map(hour(), 0, 23, 0, width), hourH);
	fill(255);
	
	int currentDay = (int)(System.currentTimeMillis()/1000/60/60/24 - 3)%7;
	text(days[currentDay] + ", " + months[month-1] + " " + day + getExt(day) + " " + year,width/2, height/6);
	text(hour()+":"+minute()+":"+second(), width/2, height/6*2);
}

public String getExt(int i) {
	switch(i) {
	case 1:
	case 21:
	case 31:
		return "st";
	case 2:
	case 22:
		return "nd";
	case 3:
	case 23:
		return "rd";
	default:
		return "th";
	}
}
}