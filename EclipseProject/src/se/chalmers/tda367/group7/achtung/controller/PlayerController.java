package se.chalmers.tda367.group7.achtung.controller;

import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.Player;

public class PlayerController {

	private int leftKey;
	private int rightKey;

	private boolean leftDown;
	private boolean rightDown;

	public Player player;

	public PlayerController(Player player) {
		this.player = player;
	}

	public void setRightKey(int key) {
		this.rightKey = key;
	}

	public void setLeftKey(int key) {
		this.leftKey = key;
	}

	public boolean onInputEvent(InputEvent e) {
		if (e.getKey() == this.leftKey) {
			this.leftDown = e.isPressed();
		}

		if (e.getKey() == this.rightKey) {
			this.rightDown = e.isPressed();
		}

		if (this.leftDown && !this.rightDown) {
			this.player.getBody().setTurnMode(Body.TurnMode.LEFT);
		} else if (!this.leftDown && this.rightDown) {
			this.player.getBody().setTurnMode(Body.TurnMode.RIGHT);
		} else {
			this.player.getBody().setTurnMode(Body.TurnMode.FORWARD);
		}
		return false;
	}
}