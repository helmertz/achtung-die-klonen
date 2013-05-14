package se.chalmers.tda367.group7.achtung.menu;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.MouseInputEvent;

import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;
import de.lessvoid.nifty.spi.input.InputSystem;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

// Class for controlling which events gets forwarded to nifty
public class CustomInputSystem implements InputSystem {

	private final Queue<KeyboardInputEvent> keyEventQueue = new ArrayDeque<KeyboardInputEvent>();
	private final Queue<MouseInputEvent> mouseEventQueue = new ArrayDeque<MouseInputEvent>();

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
		KeyboardInputEvent event = this.eventCreator.createEvent(e.getKey(),
				e.getCharacter(), e.isPressed());
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
