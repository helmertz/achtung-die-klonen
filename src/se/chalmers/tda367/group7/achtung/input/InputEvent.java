package se.chalmers.tda367.group7.achtung.input;

public class InputEvent {
	
	private int key;
	private char character;
	private boolean repeat;

	public InputEvent(int eventKey, char character, boolean repeat) {
		this.key = eventKey;
		this.character = character;
		this.repeat = repeat;
	}

	public int getKey() {
		return key;
	}

	public char getCharacter() {
		return character;
	}

	public boolean isRepeat() {
		return repeat;
	}
}
