package se.chalmers.tda367.group7.achtung.menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainMenuController implements ScreenController, KeyInputListener {
	
	private Screen screen;
	private Nifty nifty;
	private boolean handleKeyIn;
	private Button element;
	private String player;
	private String key;
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onStartScreen() {
		// do nothing for now
	}

	@Override
	public void onEndScreen() {
		// do nothing for now
	}

	public void addListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public void onStartPress() {
		this.pcs.firePropertyChange("startPressed", false, true);
	}

	public void onKeySet(String elementID, String player, String key) {
		// Mark that it will catch the next key event
		this.handleKeyIn = true;
		
		// Key is right now L or R, not key used
		this.key = key;
		this.player = player;
		this.element = this.screen.findNiftyControl(elementID, Button.class);
		
		// Removes text to signal that user will fill in
		// TODO perhaps signal with color or popup somehow
		this.element.setText("");
	}

	@Override
	public boolean onKeyInputEvent(KeyInputEvent event) {
		if (this.handleKeyIn && event.isPressed()) {
			this.handleKeyIn = false;
			char c = Character.toUpperCase(event.getCharacter());
			String s = Character.toString(c);
			
			// Shows character if font can display it, otherwise the key's name
			String buttonText;
			if (this.element.getFont().getWidth(s) > 0) {
				buttonText = s;
			} else {
				buttonText = event.getKeyName();
			}
			this.element.setText(buttonText);

			// Here do some event perhaps to signal MainController

			return true;
		}
		return false;
	}

}
