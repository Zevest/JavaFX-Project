package engine;


import javafx.animation.AnimationTimer;
import util.Math;
public class Time extends AnimationTimer{

	Core main;
	Time(Core main){
		this.main = main;
	}
	public static double deltaTime = 0;
	private long before = System.nanoTime(); 
	@Override
	public void handle(long now) {
		
		//while(deltaTime < 16.666) {
		deltaTime += ((now - before) / 1000000.0);
			//System.out.printf("now %d, before %d, deltaTime: %f\n", now, before, deltaTime);
		before = System.nanoTime();
		if(deltaTime > (960.0f / (float) main.getTargetFrameRate())) {
			main.frameRate = (1000.f / deltaTime);
			deltaTime = 0;
			
			main.update();
			++Core.frameCount;
			
		}
	}
	

}
