package se.chalmers.tda367.group7.achtung.menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Settings;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainMenuController implements ScreenController, KeyInputListener {

	private Screen screen;
	private Nifty nifty;
	private boolean handleKeyIn;
	private Button element;

	private final Map<Button, Integer> buttonKeyMap = new HashMap<Button, Integer>();

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
		Slider pu = this.screen.findNiftyControl("puslider", Slider.class);

		// TODO Allow 100? Do something logarithmic?
		float powerUpChance = pu.getValue() / 100;

		List<PlayerInfoHolder> pInfoList = new ArrayList<PlayerInfoHolder>();
		for (int i = 1; i <= 8; i++) {
			CheckBox cb = this.screen.findNiftyControl("cbp" + i,
					CheckBox.class);

			if (!cb.isChecked()) {
				continue;
			}

			TextField tf = this.screen.findNiftyControl("namep" + i,
					TextField.class);
			Button lButton = this.screen.findNiftyControl("keylp" + i,
					Button.class);
			Button rButton = this.screen.findNiftyControl("keyrp" + i,
					Button.class);

			String name = tf.getDisplayedText();

			Integer lKey = this.buttonKeyMap.get(lButton);
			Integer rKey = this.buttonKeyMap.get(rButton);

			// TODO more checks and show error response
			if (lKey == null || rKey == null) {
				continue;
			}

			PlayerInfoHolder pih = new PlayerInfoHolder(lKey, rKey, Color.RED,
					name);
			pInfoList.add(pih);
		}

		GameSetUpHolder gameSetUpHolder = new GameSetUpHolder(pInfoList);
		
		Settings settings = Settings.getInstance();
		settings.setPowerUpChance(powerUpChance);
		// TODO - fix sliders for these settings.
//		settings.setSpeed(6f);
//		settings.setWidth(10f);
//		settings.setChanceOfHole(chanceOfHole);
//		settings.setRotationSpeed(rotationSpeed);

		this.pcs.firePropertyChange("startPressed", null, gameSetUpHolder);
	}
	
	public void onContinuePress() {
		
	}

	public void onKeySet(String elementID) {
		// Mark that it will catch the next key event
		this.handleKeyIn = true;

		this.element = this.screen.findNiftyControl(elementID, Button.class);

		// Removes text to signal that user will fill in
		// TODO perhaps signal with color or popup somehow
		this.element.setText("");
	}

	@Override
	public boolean onKeyInputEvent(KeyInputEvent event) {
		if (this.handleKeyIn && event.isPressed()) {
			this.handleKeyIn = false;

			// TODO check with a blacklist
			if (event.getKey() == 0) {
				// Abort, would show up as none
				return false;
			}

			char c = Character.toUpperCase(event.getCharacter());
			String s = Character.toString(c);

			// Shows character if font can display it, otherwise the key's name
			String buttonText;
			if (!Character.isWhitespace(c)
					&& this.element.getFont().getWidth(s) > 0) {
				buttonText = s;
			} else {
				buttonText = event.getKeyName();
			}
			this.element.setText(buttonText);

			// Bind the selected to a button
			this.buttonKeyMap.put(this.element, event.getKey());

			return true;
		}
		return false;
	}

	// A couple of help classes for holding game settings before starting it
	public class GameSetUpHolder {
		private final List<PlayerInfoHolder> playerInfo;

		public GameSetUpHolder(List<PlayerInfoHolder> playerInfo) {
			this.playerInfo = playerInfo;
		}
	}

	public class PlayerInfoHolder {
		private final int leftKey;
		private final int rightKey;
		private final Color color;
		private final String name;

		public PlayerInfoHolder(int leftKey, int rightKey, Color color,
				String name) {
			this.leftKey = leftKey;
			this.rightKey = rightKey;
			this.color = color;
			this.name = name;
		}

		public int getLeftKey() {
			return this.leftKey;
		}

		public int getRightKey() {
			return this.rightKey;
		}

		public Color getColor() {
			return this.color;
		}

		public String getName() {
			return this.name;
		}
	}
}
