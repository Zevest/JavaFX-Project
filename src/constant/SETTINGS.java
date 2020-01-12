package constant;

public enum SETTINGS {
	NONE(0),
	LEFT(1),
	RIGHT(2),
	CENTER(3),
	TOP(4),
	BOTTOM(5),
	CORNER(6),
	NEXT(7),
	BACK(8),
	RGB(9),
	HSB(10),
	PIE(11),
	CHORD(12),
	OPEN(13),
	CLOSE(14),
	PROJECT(15),
	ROUND(16),
	SQUARE(17);
	
	
	private int action;
	
	public int getValue() {
		return this.action;
	}
	
	private SETTINGS(int action) {
		this.action = action;
	}
	
}
