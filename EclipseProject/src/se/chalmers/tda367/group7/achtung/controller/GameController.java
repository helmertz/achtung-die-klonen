package se.chalmers.tda367.group7.achtung.controller;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.sound.Sound;

public class GameController implements KeyInputListener {
	private final Game game;
	private final List<PlayerController> playerControllers = new ArrayList<>();

	public GameController(Game game) {
		this.game = game;
		int i = 0;
		for (Player p : game.getPlayers()) {

			PlayerController pc = new PlayerController(p);
			if (i == 0) {
				pc.setLeftKey(Keyboard.KEY_LEFT);
				pc.setRightKey(Keyboard.KEY_RIGHT);
			} else if (i == 1) {
				pc.setLeftKey(Keyboard.KEY_A);
				pc.setRightKey(Keyboard.KEY_D);
			} else {
				pc.setLeftKey(Keyboard.KEY_1);
				pc.setRightKey(Keyboard.KEY_2);

			}
			i++;
			this.playerControllers.add(pc);
		}
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
		this.game.newRound();
		this.game.getCurrentRound().addPropertyChangeListener(
				Sound.getInstance());
	}
}
