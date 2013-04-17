package se.chalmers.tda367.group7.achtung.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class LWJGLInputService implements InputService {

	private ArrayList<InputListener> listeners = new ArrayList<InputListener>();
	
	@Override
	public void addListener(InputListener listener) {
		listeners.add(listener);
	}
	

	@Override
	public void removeListener(InputListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public void update() {
		while (Keyboard.next()) {
			fireEvent(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
		}
	}

	private void fireEvent(int eventKey, char character, boolean pressed) {
		InputEvent event = new InputEvent(eventKey, character, pressed);
		for (InputListener l : listeners) {
			if(l.onInputEvent(event)) {
				break;
			}
		}
	}

	@Override
	public boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}

}
