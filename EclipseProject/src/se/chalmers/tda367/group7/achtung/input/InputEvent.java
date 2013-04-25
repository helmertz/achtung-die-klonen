package se.chalmers.tda367.group7.achtung.input;

public class InputEvent {
	
	private int key;
	private char character;
	private boolean pressed;

	public InputEvent(int eventKey, char character, boolean repeat) {
		this.key = eventKey;
		this.character = character;
		this.pressed = repeat;
	}

	public int getKey() {
		return key;
	}

	public char getCharacter() {
		return character;
	}

	public boolean isPressed() {
		return pressed;
	}
}
