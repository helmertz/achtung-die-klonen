package se.chalmers.tda367.group7.achtung.input;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class LWJGLInputService implements InputService {

	private final List<KeyInputListener> keyListeners = new ArrayList<KeyInputListener>();
	private final List<MouseInputListener> mouseListeners = new ArrayList<MouseInputListener>();

	public LWJGLInputService () {
	    try {
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	public void addKeyListener(KeyInputListener listener) {
		this.keyListeners.add(listener);
	}

	@Override
	public void removeKeyListener(KeyInputListener listener) {
		this.keyListeners.remove(listener);
	}

	@Override
	public void addMouseListener(MouseInputListener listener) {
		this.mouseListeners.add(listener);
	}

	@Override
	public void removeMouseListener(MouseInputListener listener) {
		this.mouseListeners.remove(listener);	
	}

	@Override
	public void update() {
		while (Keyboard.next()) {
			fireKeyEvent(Keyboard.getEventKey(), Keyboard.getEventCharacter(),
					Keyboard.getEventKeyState(), Keyboard.isRepeatEvent(),
					Keyboard.getKeyName(Keyboard.getEventKey()));
		}
		while (Mouse.next()) {
			fireMouseEvent(Mouse.getEventButton(), Mouse.getEventX(),
					Display.getHeight() - Mouse.getEventY(),
					Mouse.getEventDWheel(), Mouse.getEventButtonState());
		}
	}

	private void fireKeyEvent(int eventKey, char character, boolean pressed,
			boolean repeat, String keyName) {
		KeyInputEvent event = new KeyInputEvent(eventKey, character, pressed,
				repeat, keyName);
		for (KeyInputListener l : this.keyListeners) {
			if (l.onKeyInputEvent(event)) {
				break;
			}
		}
	}
	
	private void fireMouseEvent(int button, int x, int y, int dWheel, boolean pressed) {
		MouseInputEvent event = new MouseInputEvent(button, x, y, dWheel, pressed);
		for (MouseInputListener l : this.mouseListeners) {
			if (l.onMouseInputEvent(event)) {
				break;
			}
		}
	}
}
