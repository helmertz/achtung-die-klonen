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
		return this.button;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getDWheel() {
		return this.dWheel;
	}

	public boolean isPressed() {
		return this.pressed;
	}
}
