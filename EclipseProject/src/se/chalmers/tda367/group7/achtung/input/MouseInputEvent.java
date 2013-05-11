package se.chalmers.tda367.group7.achtung.input;

public class MouseInputEvent {

	private final int button;
	private final int x;
	private final int y;
	private final int dWheel;
	private final boolean pressed;
	
	public MouseInputEvent(int button, int x, int y, int dWheel, boolean pressed) {
		this.button = button;
		this.x = x;
		this.y = y;
		this.dWheel = dWheel;
		this.pressed = pressed;
	}

	public int getButton() {
		return button;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDWheel() {
		return dWheel;
	}
	
	public boolean isPressed() {
		return this.pressed;
	}
}
