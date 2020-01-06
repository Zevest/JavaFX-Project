package engine;

public class Surface {
	boolean resizable;
	String title;
	double x, y, w, h;
	
	Surface(String title){
		this.title = title;
		w = 200;
		h = 200;
		x = w/2;
		y = h/2;
		resizable = false;
	}
	
	Surface(String title, double w, double h){
		this.title = title;
		this.w = w;
		this.w = 200;
		x = w/2;
		y = h/2;
		resizable = false;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
		Core.setLocation(x, y);
	}
	

	
}
