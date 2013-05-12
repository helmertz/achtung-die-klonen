package se.chalmers.tda367.group7.achtung.input;

public class KeyInputEvent {

	private final int key;
	private final char character;
	private final boolean pressed;
	private final boolean repeat;
	private String keyName;

	public KeyInputEvent(int eventKey, char character, boolean pressed,
			boolean repeat, String keyName) {
		this.key = eventKey;
		this.character = character;
		this.pressed = pressed;
		this.repeat = repeat;
		this.keyName = keyName;
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

	public String getKeyName() {
		return this.keyName;
	}
}
