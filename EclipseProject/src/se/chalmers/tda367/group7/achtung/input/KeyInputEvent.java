package se.chalmers.tda367.group7.achtung.input;

public class KeyInputEvent {

	private final int key;
	private final char character;
	private final boolean pressed;
	private final boolean repeat;

	public KeyInputEvent(int eventKey, char character, boolean pressed,
			boolean repeat) {
		this.key = eventKey;
		this.character = character;
		this.pressed = pressed;
		this.repeat = repeat;
	}

	public int getKey() {
		return this.key;
	}

	public char getCharacter() {
		return this.character;
	}

	public boolean isPressed() {
		return this.pressed;
	}

	public boolean isRepeat() {
		return this.repeat;
	}
}
