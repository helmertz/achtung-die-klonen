package se.chalmers.tda367.group7.achtung.menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Settings;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainMenuController implements ScreenController, KeyInputListener {

	private Screen screen;
	private Nifty nifty;
	private boolean handleKeyIn;
	private Button element;
	private Label errorLabel;

	private final Map<Button, Integer> buttonKeyMap = new HashMap<Button, Integer>();

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		this.errorLabel = this.screen.findNiftyControl("errtxt", Label.class);
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
		Slider speedSlider = this.screen.findNiftyControl("speedSlider",
				Slider.class);
		Slider widthSlider = this.screen.findNiftyControl("widthSlider",
				Slider.class);
		Slider holeSlider = this.screen.findNiftyControl("holeSlider",
				Slider.class);
		Slider rotSlider = this.screen.findNiftyControl("rotSlider",
				Slider.class);

		float powerUpChance = calcPowerUpChance(pu.getValue());
		float speed = calcSpeed(speedSlider.getValue());
		float width = calcWidth(widthSlider.getValue());
		float holeChance = calcHoleChance(holeSlider.getValue());
		float rotSpeed = calcRotSpeed(rotSlider.getValue());

		int count = 0;
		List<PlayerInfoHolder> pInfoList = new ArrayList<PlayerInfoHolder>();
		for (int i = 1; i <= 8; i++) {
			CheckBox cb = this.screen.findNiftyControl("cbp" + i,
					CheckBox.class);

			if (cb.isChecked()) {
				count++;
			} else {
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

			// TODO more checks
			if (lKey == null || rKey == null) {
				// Not a pretty way to set default keys. Possibly not store in
				// Map as now.
				if (i == 1) {
					lKey = Keyboard.KEY_LEFT;
					rKey = Keyboard.KEY_RIGHT;
				} else if (i == 2) {
					lKey = Keyboard.KEY_A;
					rKey = Keyboard.KEY_D;
				} else {
					this.errorLabel.setText("Keys not set or invalid for "
							+ name);
					return;
				}
			}

			PlayerInfoHolder pih = new PlayerInfoHolder(lKey, rKey,
					Color.PLAYER_COLORS[i - 1], name);
			pInfoList.add(pih);
		}
		if (count < 2) {
			this.errorLabel.setText("Too few players");
			return;
		}

		// Clears previous message
		this.errorLabel.setText("");

		Settings settings = Settings.getInstance();
		settings.setPowerUpChance(powerUpChance);
		settings.setSpeed(speed);
		settings.setWidth(width);
		settings.setChanceOfHole(holeChance);
		settings.setRotationSpeed(rotSpeed);

		this.pcs.firePropertyChange("startPressed", null, pInfoList);
	}

	private float calcRotSpeed(float value) {
		// value = 0 -> 3
		// value = 50 -> 6
		// value = 100 -> 10

		float a = 16 / 9f;
		float b = -6;
		float c = 9;
		return calcExp(a, b, c, value);
	}

	private float calcHoleChance(float value) {
		// value = 0 -> 0 chance
		// value = 50 -> 0.015 chance
		// value = 100 -> 0.2 chance
		float a = 1369 / 9f;
		float b = -(9 / 6800f);
		float c = 9 / 6800f;

		return calcExp(a, b, c, value);
	}

	private float calcWidth(float value) {
		// value = 0 -> 3 speed
		// value = 50 -> 10 speed
		// value = 100 -> 65 speed

		float a = 3025 / 49f;
		float b = 95 / 48f;
		float c = 49 / 48f;

		return calcExp(a, b, c, value);
	}

	private float calcSpeed(float value) {
		// value = 0 -> 0 speed
		// value = 50 -> 6 speed
		// value = 100 -> 12 speed

		float a = 36 / 25f;
		float b = -24;
		float c = 25;

		return calcExp(a, b, c, value);
	}

	private float calcPowerUpChance(float value) {
		// value = 0 -> 0 chance
		// value = 50 -> 0.01 chance
		// value = 100 -> 0.1 chance

		float a = 81;
		float b = -(1 / 800f);
		float c = 1 / 800f;

		return calcExp(a, b, c, value);
	}

	private float calcExp(float a, float b, float c, float value) {
		return (float) (c * Math.pow(a, value / 100) + b);
	}

	public void onContinuePress() {
		this.pcs.firePropertyChange("continuePressed", false, true);
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

	/**
	 * A class that holds info for players that will be created.
	 */
	public static class PlayerInfoHolder {
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
