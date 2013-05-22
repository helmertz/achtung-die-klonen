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
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainMenuController implements ScreenController, KeyInputListener {

	private Screen screen;
	private boolean handleKeyIn;
	private Button pressedButton;
	private Button nextButton;
	private Label errorLabel;

	private final Map<Button, Integer> buttonKeyMap = new HashMap<Button, Integer>();
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private Nifty nifty;
	private Element popup;

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.screen = screen;
		this.errorLabel = this.screen.findNiftyControl("errorText", Label.class);
		this.nifty = nifty;
		// Binds first two player's keys to default buttons
		Button p1l = this.screen.findNiftyControl("keylp1", Button.class);
		Button p1r = this.screen.findNiftyControl("keyrp1", Button.class);
		Button p2l = this.screen.findNiftyControl("keylp2", Button.class);
		Button p2r = this.screen.findNiftyControl("keyrp2", Button.class);
		this.buttonKeyMap.put(p1l, Keyboard.KEY_LEFT);
		this.buttonKeyMap.put(p1r, Keyboard.KEY_RIGHT);
		this.buttonKeyMap.put(p2l, Keyboard.KEY_A);
		this.buttonKeyMap.put(p2r, Keyboard.KEY_D);
	}

	public void setShowContinue(boolean show) {
		Button continueButton = this.screen.findNiftyControl("continueButton",
				Button.class);
		continueButton.getElement().setVisible(show);
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

	public void onNewGamePress() {
		// Uses try to abort and show error message on invalid setup
		try {
			List<PlayerInfoHolder> pInfoList = createPlayerInfo();
			if (pInfoList.size() < 2) {
				throw new RuntimeException("Too few players");
			}
			updateGameSettings();

			// Clears previous error messages
			showErrorText("");

			this.pcs.firePropertyChange("startPressed", null, pInfoList);
		} catch (RuntimeException e) {
			showErrorText(e.getMessage());
		}
	}

	private void showErrorText(String text) {
		this.errorLabel.setText(text);
	}

	/**
	 * Creates a list of all players information from what's entered in the
	 * menu.
	 * 
	 * @return a list of player information
	 */
	private List<PlayerInfoHolder> createPlayerInfo() {
		List<PlayerInfoHolder> pInfoList = new ArrayList<PlayerInfoHolder>();
		for (int i = 1; i <= 8; i++) {
			PlayerInfoHolder playerInfo = getPlayerInfo(i);
			if (playerInfo != null) {
				pInfoList.add(playerInfo);
			}
		}
		return pInfoList;
	}

	/**
	 * Gets information associated with a player from the menu.
	 * 
	 * @param playerNum
	 *            the number of the player
	 * @return an object containing info about a player
	 */
	private PlayerInfoHolder getPlayerInfo(int playerNum) {
		CheckBox cb = this.screen.findNiftyControl("cbp" + playerNum,
				CheckBox.class);

		if (!cb.isChecked()) {
			return null;
		}

		TextField tf = this.screen.findNiftyControl("namep" + playerNum,
				TextField.class);
		Button lButton = this.screen.findNiftyControl("keylp" + playerNum,
				Button.class);
		Button rButton = this.screen.findNiftyControl("keyrp" + playerNum,
				Button.class);

		String name = tf.getDisplayedText();

		if (name.length() < 1) {
			throw new RuntimeException("Please enter a name for player "
					+ playerNum);
		}

		Integer lKey = this.buttonKeyMap.get(lButton);
		Integer rKey = this.buttonKeyMap.get(rButton);

		if (lKey == null || rKey == null) {
			throw new RuntimeException("Keys not set or invalid for " + name);
		}

		return new PlayerInfoHolder(lKey, rKey,
				Color.PLAYER_COLORS[playerNum - 1], name);
	}
	
	public void onInstructionPress () {
		this.popup = this.nifty.createPopup("instructionPopup");
		this.nifty.showPopup(this.nifty.getCurrentScreen(), this.popup.getId(),
				null);
	}

	/**
	 * Polls the menu elements and sets information in the Settings singleton.
	 */
	private void updateGameSettings() {
		Slider pu = this.screen.findNiftyControl("puslider", Slider.class);
		Slider speedSlider = this.screen.findNiftyControl("speedSlider",
				Slider.class);
		Slider widthSlider = this.screen.findNiftyControl("widthSlider",
				Slider.class);
		Slider holeSlider = this.screen.findNiftyControl("holeSlider",
				Slider.class);
		Slider rotSlider = this.screen.findNiftyControl("rotSlider",
				Slider.class);
		CheckBox musicCheckBox = this.screen.findNiftyControl("music",
				CheckBox.class);
		CheckBox soundEffectsCheckBox = this.screen.findNiftyControl("sound",
				CheckBox.class);

		float powerUpChance = calcPowerUpChance(pu.getValue());
		float speed = calcSpeed(speedSlider.getValue());
		float width = calcWidth(widthSlider.getValue());
		float holeChance = calcHoleChance(holeSlider.getValue());
		float rotSpeed = calcRotSpeed(rotSlider.getValue());
		boolean musicEnabled = musicCheckBox.isChecked();
		boolean soundEffectsEnabled = soundEffectsCheckBox.isChecked();

		Settings settings = Settings.getInstance();
		settings.setPowerUpChance(powerUpChance);
		settings.setSpeed(speed);
		settings.setWidth(width);
		settings.setChanceOfHole(holeChance);
		settings.setRotationSpeed(rotSpeed);
		settings.setMusicEnabled(musicEnabled);
		settings.setSoundEffectsEnabled(soundEffectsEnabled);
	}

	public void onContinuePress() {
		// Updates sound settings when continuing
		CheckBox musicCheckBox = this.screen.findNiftyControl("music",
				CheckBox.class);
		CheckBox soundEffectsCheckBox = this.screen.findNiftyControl("sound",
				CheckBox.class);

		Settings settings = Settings.getInstance();

		settings.setMusicEnabled(musicCheckBox.isChecked());
		settings.setSoundEffectsEnabled(soundEffectsCheckBox.isChecked());

		this.pcs.firePropertyChange("continuePressed", false, true);
	}

	// Called from nifty (using reflection)
	public void onKeyButtonPressed(String elementID) {
		Button button = this.screen.findNiftyControl(elementID, Button.class);
		activateButton(button);

		// If the left key is pressed, store a reference to the right key so
		// user can set both right away
		this.nextButton = null;
		if (elementID.charAt(elementID.length() - 3) == 'l') {
			Button b = this.screen.findNiftyControl(
					elementID.replace('l', 'r'), Button.class);
			if (b.getText().length() == 0) {
				this.nextButton = b;
			}
		}
	}

	/**
	 * Sets that the next key event will be handled and the button associated
	 * with the event.
	 * 
	 * @param button
	 *            the button associated with the event
	 */
	private void activateButton(Button button) {
		// Clears text of previous pressed button if a key wasn't entered
		if (this.pressedButton != null) {
			this.pressedButton.setText("");
		}

		this.pressedButton = button;

		// Mark that the next key event will be catched
		this.handleKeyIn = true;

		// Removes any key that might have been mapped to the button
		this.buttonKeyMap.remove(this.pressedButton);

		// Removes text to signal that user will fill in
		// TODO perhaps signal with color or popup somehow
		this.pressedButton.setText("(enter)");
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
					&& this.pressedButton.getFont().getWidth(s) > 0) {
				buttonText = s;
			} else {
				buttonText = event.getKeyName();
			}
			this.pressedButton.setText(buttonText);

			// Bind the selected to a button
			this.buttonKeyMap.put(this.pressedButton, event.getKey());

			// Activates the next button if one has been set
			if (this.nextButton != null
					&& this.pressedButton != this.nextButton) {
				this.pressedButton = null;
				activateButton(this.nextButton);
			} else {
				this.nextButton = null;
				this.pressedButton = null;
			}
			return true;
		}
		return false;
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

	public void closePopup() {
		this.nifty.closePopup(this.popup.getId());
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
