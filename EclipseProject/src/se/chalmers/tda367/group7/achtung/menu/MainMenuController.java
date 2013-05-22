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
		
		loadGameSettings();
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

	public void onExitPress() {
		this.pcs.firePropertyChange("exit", false, true);
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

		float powerUpChance = calcSliderToValue(Settings.MIN_POWER_UP_CHANCE, Settings.MAX_POWER_UP_CHANCE, pu.getValue());
		float speed = calcSliderToValue(Settings.MIN_SPEED, Settings.MAX_SPEED, speedSlider.getValue());
		float width = calcSliderToValue(Settings.MIN_WIDTH, Settings.MAX_WIDTH, widthSlider.getValue());
		float holeChance = calcSliderToValue(Settings.MIN_CHANCE_OF_HOLE, Settings.MAX_CHANCE_OF_HOLE, holeSlider.getValue());
		float rotSpeed = calcSliderToValue(Settings.MIN_ROTATION_SPEED, Settings.MAX_ROTATION_SPEED, rotSlider.getValue());
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
	
	/**
	 * Gets information from the Settings singleton and sets to the menu elements.
	 */
	private void loadGameSettings() {
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

		Settings settings = Settings.getInstance();
		
		float powerUpChance = calcValueToSlider(Settings.MIN_POWER_UP_CHANCE, Settings.MAX_POWER_UP_CHANCE, settings.getPowerUpChance());
		float speed = calcValueToSlider(Settings.MIN_SPEED, Settings.MAX_SPEED, settings.getSpeed());
		float width = calcValueToSlider(Settings.MIN_WIDTH, Settings.MAX_WIDTH, settings.getWidth());
		float holeChance = calcValueToSlider(Settings.MIN_CHANCE_OF_HOLE, Settings.MAX_CHANCE_OF_HOLE, settings.getChanceOfHole());
		float rotSpeed = calcValueToSlider(Settings.MIN_ROTATION_SPEED, Settings.MAX_ROTATION_SPEED, settings.getRotationSpeed());
		boolean musicEnabled = settings.isMusicEnabled();
		boolean soundEffectsEnabled = settings.isSoundEffectsEnabled();

		pu.setValue(powerUpChance);
		speedSlider.setValue(speed);
		widthSlider.setValue(width);
		holeSlider.setValue(holeChance);
		rotSlider.setValue(rotSpeed);
		musicCheckBox.setChecked(musicEnabled);
		soundEffectsCheckBox.setChecked(soundEffectsEnabled);
		
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
	
	public void onResetPress() {
		// Resets all settings.
		Settings.getInstance().resetToDefaults();
		loadGameSettings();
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
	
	/**
	 * Calculates the settings value from a slider value.
	 * @param min - the minimum setting
	 * @param max - the maximum setting
	 * @param sliderValue - the value that the slider has
	 * 
	 * @return the value that can be used in game
	 */
	private float calcSliderToValue(float min, float max, float sliderValue) {
		float precent = sliderValue/100;
		// Here we move the min value to zero,
		// then calculate position and lastly
		// moves it back.
		return (max-min)*precent + min;
	}
	
	/**
	 * Calculates the slider value from a settings value.
	 * @param min - the minimum setting
	 * @param max - the maximum setting
	 * @param value - the value that the settings has
	 * 
	 * @return the value that can be used on slider
	 */
	private float calcValueToSlider(float min, float max, float value) {
		float precent = (value-min)/(max-min);
		
		return precent*100;
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
