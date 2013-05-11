package se.chalmers.tda367.group7.achtung.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.MouseInputEvent;

import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

// Class for controlling which events gets forwarded to nifty
public class CustomInputSystem implements InputSystem {

	private final List<KeyboardInputEvent> keyEventQueue = new ArrayList<KeyboardInputEvent>();
	private final List<MouseInputEvent> mouseEventQueue = new ArrayList<MouseInputEvent>();

	private final LwjglKeyboardInputEventCreator eventCreator = new LwjglKeyboardInputEventCreator();

	@Override
	public void forwardEvents(NiftyInputConsumer inputEventConsumer) {
		Iterator<KeyboardInputEvent> keyboardIterator = this.keyEventQueue
				.iterator();
		while (keyboardIterator.hasNext()) {
			inputEventConsumer.processKeyboardEvent(keyboardIterator.next());
			keyboardIterator.remove();
		}
		Iterator<MouseInputEvent> mouseIterator = this.mouseEventQueue
				.iterator();
		while (mouseIterator.hasNext()) {
			MouseInputEvent e = mouseIterator.next();
			inputEventConsumer.processMouseEvent(e.getX(), e.getY(),
					e.getDWheel(), e.getButton(), e.isPressed());
			mouseIterator.remove();
		}
	}

	public void addKeyEvent(KeyInputEvent e) {
		KeyboardInputEvent event = this.eventCreator.createEvent(
				Keyboard.getEventKey(), Keyboard.getEventCharacter(),
				Keyboard.getEventKeyState());
		this.keyEventQueue.add(event);
	}

	public void addMouseEvent(MouseInputEvent e) {
		this.mouseEventQueue.add(e);
	}

	@Override
	public void setMousePosition(int x, int y) {
	}

	@Override
	public void setResourceLoader(NiftyResourceLoader niftyResourceLoader) {
	}

}
