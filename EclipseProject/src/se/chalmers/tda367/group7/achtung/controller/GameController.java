package se.chalmers.tda367.group7.achtung.controller;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.sound.Sound;

public class GameController implements KeyInputListener {
	private final Game game;
	private final List<PlayerController> playerControllers = new ArrayList<>();

	public GameController(Game game) {
		this.game = game;

		// TODO - this should not be hardcoded.
		for (int i = 0; i < 3; i++) {
			String name = "Player " + (i + 1);
			if (i == 0) {
				addPlayer(name, Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT);
			} else if (i == 1) {
				addPlayer(name, Keyboard.KEY_A, Keyboard.KEY_D);
			} else {
				addPlayer(name, Keyboard.KEY_1, Keyboard.KEY_2);
			}
		}
	}

	/**
	 * Adds a player to the current game.
	 * 
	 * @param name
	 *            - the name of the player
	 * @param leftKey
	 *            - left key id
	 * @param rightKey
	 *            - right key id
	 */
	public void addPlayer(String name, int leftKey, int rightKey) {
		PlayerController pc = new PlayerController(this.game.getNewPlayer(name,
				Color.getRandomColor()));
		pc.setLeftKey(leftKey);
		pc.setRightKey(rightKey);

		this.playerControllers.add(pc);
	}

	// Called as quickly as possible after a key is pressed, not in sync with
	// game rate
	@Override
	public boolean onKeyInputEvent(KeyInputEvent event) {
		if (!event.isRepeat() && event.isPressed()
				&& event.getKey() == Keyboard.KEY_SPACE) {
			startRound();
			return true;
		}
		for (PlayerController pc : this.playerControllers) {
			if (pc.onInputEvent(event)) {
				return true;
			}
		}
		return false;
	}

	public void startRound() {
		if (!this.game.isOver()) {
			this.game.newRound();
			this.game.getCurrentRound().addPropertyChangeListener(
					Sound.getInstance());
		}
	}
}
