package constant;

public enum CURSOR {
		NONE(0),
		ARROW(1),
		CROSS(2),
		HAND(3),
		MOVE(4),
		TEXT(5), 
		WAIT(6);
		
	
	private final int val;
	
	public final int getValue() {
		return this.val;
	}
	
	private CURSOR(int val) {
		this.val = val;
	}
}
