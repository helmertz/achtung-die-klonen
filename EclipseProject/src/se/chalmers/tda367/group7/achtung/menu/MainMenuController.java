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
		float powerUpChance = pu.getValue();

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
			if (lKey == null || rKey == null) {
				continue;
			}
			System.out.println(name);
			// TODO do some checks and show error response

			PlayerInfoHolder pih = new PlayerInfoHolder(lKey, rKey, Color.RED,
					name);
			pInfoList.add(pih);
		}

		GameSetUpHolder gameSetUpHolder = new GameSetUpHolder(pInfoList,
				powerUpChance, 10f, 6f);

		this.pcs.firePropertyChange("startPressed", null, gameSetUpHolder);
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
			this.buttonKeyMap.put(this.element, event.getKey());
			// Here do some event perhaps to signal MainController

			return true;
		}
		return false;
	}

	public class GameSetUpHolder {
		private final List<PlayerInfoHolder> playerInfo;
		private final float powerUpChance;
		private final float snakeSize;
		private final float snakeSpeed;

		public GameSetUpHolder(List<PlayerInfoHolder> playerInfo,
				float powerUpChance, float snakeSize, float snakeSpeed) {
			this.playerInfo = playerInfo;
			this.powerUpChance = powerUpChance;
			this.snakeSize = snakeSize;
			this.snakeSpeed = snakeSpeed;
		}

		public List<PlayerInfoHolder> getPlayerInfo() {
			return this.playerInfo;
		}

		public float getPowerUpChance() {
			return this.powerUpChance;
		}

		public float getSnakeSize() {
			return this.snakeSize;
		}

		public float getSnakeSpeed() {
			return this.snakeSpeed;
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
