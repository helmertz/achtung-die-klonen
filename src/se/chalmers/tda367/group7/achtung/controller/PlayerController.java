package se.chalmers.tda367.group7.achtung.controller;


import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.model.Player;

public class PlayerController {

	private int leftKey;
	private int rightKey;
	
	public Player player;
	
	public PlayerController() {
		// TODO Auto-generated constructor stub
	}

	public void setPlayer(Player player) {
		this.player = player;

	}

	public void setRightKey(int key) {
		rightKey = key;

	}

	public void setLeftKey(int key) {
		leftKey = key;
	}

	public boolean onInputEvent(InputEvent e) {
		int key = e.getKey();
		if (key == rightKey) {
			player.turnRight();
		} else if (key == leftKey) {
			player.turnLeft();
		}
		return false;
	}

}