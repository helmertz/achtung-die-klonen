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
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
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

	private Slider goalSlider;
	private CheckBox goalCheckbox;
	private Label goalLabel;
	private Slider powerUpSlider;
	private Slider speedSlider;
	private Slider widthSlider;
	private Slider holeSlider;
	private Slider rotSlider;
	private Slider sizeSlider;
	private CheckBox musicCheckBox;
	private CheckBox soundEffectsCheckBox;

	private static final int[] ILLEGAL_KEYS = new int[] { 0,
			Keyboard.KEY_SPACE, Keyboard.KEY_ESCAPE, Keyboard.KEY_RETURN,
			Keyboard.KEY_F3, Keyboard.KEY_F11, Keyboard.KEY_F12 };

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;

		fetchMenuControls();
		setSliderSettings();

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

	private void fetchMenuControls() {
		this.errorLabel = this.screen
				.findNiftyControl("errorText", Label.class);
		this.goalSlider = this.screen.findNiftyControl("goalSlider",
				Slider.class);
		this.goalCheckbox = this.screen.findNiftyControl("goalCheckbox",
				CheckBox.class);
		this.goalLabel = this.screen.findNiftyControl("goalLabel", Label.class);

		this.powerUpSlider = this.screen.findNiftyControl("powerUpSlider",
				Slider.class);
		this.speedSlider = this.screen.findNiftyControl("speedSlider",
				Slider.class);
		this.widthSlider = this.screen.findNiftyControl("widthSlider",
				Slider.class);
		this.holeSlider = this.screen.findNiftyControl("holeSlider",
				Slider.class);
		this.rotSlider = this.screen
				.findNiftyControl("rotSlider", Slider.class);
		this.sizeSlider = this.screen.findNiftyControl("sizeSlider",
				Slider.class);
		this.musicCheckBox = this.screen.findNiftyControl("music",
				CheckBox.class);
		this.soundEffectsCheckBox = this.screen.findNiftyControl("sound",
				CheckBox.class);
	}

	private void setSliderSettings() {
		configureSlider(this.goalSlider, Settings.MIN_GOAL, Settings.MAX_GOAL,
				1);

		// TODO possibly custom step size on some of these
		configureSlider(this.powerUpSlider, Settings.MIN_POWER_UP_CHANCE,
				Settings.MAX_POWER_UP_CHANCE);
		configureSlider(this.speedSlider, Settings.MIN_SPEED,
				Settings.MAX_SPEED);
		configureSlider(this.powerUpSlider, Settings.MIN_POWER_UP_CHANCE,
				Settings.MAX_POWER_UP_CHANCE);
		configureSlider(this.widthSlider, Settings.MIN_WIDTH,
				Settings.MAX_WIDTH);
		configureSlider(this.holeSlider, Settings.MIN_CHANCE_OF_HOLE,
				Settings.MAX_CHANCE_OF_HOLE);
		configureSlider(this.rotSlider, Settings.MIN_ROTATION_SPEED,
				Settings.MAX_ROTATION_SPEED);
		configureSlider(this.sizeSlider, Settings.MIN_MAP_SIZE,
				Settings.MAX_MAP_SIZE);
	}

	// By default, uses 100 steps
	private void configureSlider(Slider slider, float min, float max) {
		float step = (max - min) / 100f;
		configureSlider(slider, min, max, step);
	}

	private void configureSlider(Slider slider, float min, float max, float step) {
		slider.setMin(min);
		slider.setMax(max);
		slider.setStepSize(step);
		slider.setButtonStepSize(step);
	}

	private void updateGoalControls() {
		boolean autoGoal = this.goalCheckbox.isChecked();
		String goalString;
		if (autoGoal) {
			goalString = "auto";
			this.goalSlider.disable();
		} else {
			goalString = Integer
					.toString(Math.round(this.goalSlider.getValue()));
			this.goalSlider.enable();
		}
		this.goalLabel.setText(goalString);
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
				throw new MenuException("Too few players");
			}
			updateGameSettings();

			// Clears previous error messages
			showErrorText("");

			this.pcs.firePropertyChange("startPressed", null, pInfoList);
		} catch (MenuException e) {
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
			throw new MenuException("Please enter a name for player "
					+ playerNum);
		}

		Integer lKey = this.buttonKeyMap.get(lButton);
		Integer rKey = this.buttonKeyMap.get(rButton);

		if (lKey == null || rKey == null) {
			throw new MenuException("Keys not set or invalid for " + name);
		}

		return new PlayerInfoHolder(lKey, rKey,
				Color.PLAYER_COLORS[playerNum - 1], name);
	}

	public void onInstructionPress() {
		this.popup = this.nifty.createPopup("instructionPopup");
		this.nifty.showPopup(this.nifty.getCurrentScreen(), this.popup.getId(),
				null);
	}

	public void onAboutPress() {
		this.popup = this.nifty.createPopup("aboutPopup");
		this.nifty.showPopup(this.nifty.getCurrentScreen(), this.popup.getId(),
				null);
	}

	/**
	 * Polls the menu elements and sets information in the Settings singleton.
	 */
	private void updateGameSettings() {
		float powerUpChance = this.powerUpSlider.getValue();
		float speed = this.speedSlider.getValue();
		float width = this.widthSlider.getValue();
		float holeChance = this.holeSlider.getValue();
		float rotSpeed = this.rotSlider.getValue();
		boolean musicEnabled = this.musicCheckBox.isChecked();
		boolean soundEffectsEnabled = this.soundEffectsCheckBox.isChecked();

		int goal = Math.round(this.goalSlider.getValue());
		boolean autoGoal = this.goalCheckbox.isChecked();
		float mapSize = this.sizeSlider.getValue();

		Settings settings = Settings.getInstance();
		settings.setPowerUpChance(powerUpChance);
		settings.setSpeed(speed);
		settings.setWidth(width);
		settings.setChanceOfHole(holeChance);
		settings.setRotationSpeed(rotSpeed);
		settings.setMusicEnabled(musicEnabled);
		settings.setSoundEffectsEnabled(soundEffectsEnabled);
		settings.setGoalScore(goal);
		settings.setAutoGoalEnabled(autoGoal);
		settings.setMapSize(mapSize);
	}

	/**
	 * Gets information from the Settings singleton and sets to the menu
	 * elements.
	 */
	private void loadGameSettings() {
		Settings settings = Settings.getInstance();

		float powerUpChance = settings.getPowerUpChance();
		float speed = settings.getSpeed();
		float width = settings.getWidth();
		float holeChance = settings.getChanceOfHole();
		float rotSpeed = settings.getRotationSpeed();
		boolean musicEnabled = settings.isMusicEnabled();
		boolean soundEffectsEnabled = settings.isSoundEffectsEnabled();
		int goalScore = settings.getGoalScore();
		boolean autoGoal = settings.isAutoGoalEnabled();
		float mapSize = settings.getMapSize();

		this.powerUpSlider.setValue(powerUpChance);
		this.speedSlider.setValue(speed);
		this.widthSlider.setValue(width);
		this.holeSlider.setValue(holeChance);
		this.rotSlider.setValue(rotSpeed);
		this.musicCheckBox.setChecked(musicEnabled);
		this.soundEffectsCheckBox.setChecked(soundEffectsEnabled);
		this.sizeSlider.setValue(mapSize);

		this.goalSlider.setValue(goalScore);
		this.goalCheckbox.setChecked(autoGoal);

		updateGoalControls();
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
			this.nextButton = b;
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

			if (isIllegalKey(event.getKey())) {
				showErrorText(event.getKeyName() + " is not allowed");
				return false;
			} else {
				showErrorText("");
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

	private boolean isIllegalKey(int key) {
		for (int i = 0; i < ILLEGAL_KEYS.length; i++) {
			if (ILLEGAL_KEYS[i] == key) {
				return true;
			}
		}
		return false;
	}

	public void closePopup() {
		this.nifty.closePopup(this.popup.getId());
	}

	@NiftyEventSubscriber(id = "goalCheckbox")
	public void onCheckBoxChanged(final String id,
			final CheckBoxStateChangedEvent event) {
		updateGoalControls();
	}

	@NiftyEventSubscriber(id = "goalSlider")
	public void onSliderChangedEvent(final String id,
			final SliderChangedEvent event) {
		updateGoalControls();
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
