package se.chalmers.tda367.group7.achtung.menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainMenuController implements ScreenController {

	private Nifty nifty;
	private Screen screen;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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
		pcs.addPropertyChangeListener(listener);
	}

	public void removeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public void onStartPress() {
		pcs.firePropertyChange("startPressed", false, true);
	}
}
