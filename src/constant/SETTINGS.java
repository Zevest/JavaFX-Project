package constant;

public enum SETTINGS {
	NONE(0),
	ROUND(1),
	SQUARE(2),
	CORNER(3),
	NEXT(4),
	BACK(5),
	RGB(6),
	HSB(7),
	PIE(8),
	CHORD(9),
	OPEN(10),
	CLOSE(11),
	PROJECT(12),
	TOP(13),
	BOTTOM(14),
	CENTER(15),
	LEFT(16),
	UP(17),
	RIGHT(18),
	DOWN(19);
	
	
	private int val;
	
	public int getValue() {
		return this.val;
	}
	
	private SETTINGS(int val) {
		this.val = val;
	}
	
}
