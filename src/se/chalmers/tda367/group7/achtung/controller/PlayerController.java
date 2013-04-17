package se.chalmers.tda367.group7.achtung.controller;


import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.model.Player;

public class PlayerController {

	private int leftKey;
	private int rightKey;
	
	public Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}

	public void setRightKey(int key) {
		rightKey = key;

	}

	public void setLeftKey(int key) {
		leftKey = key;
	}

	public boolean onInputEvent(InputEvent e) {
		return false;
	}

	public void update(InputService service) {
		if(service.isKeyDown(leftKey)) {
			player.turnLeft();
		}
		if(service.isKeyDown(rightKey)) {
			player.turnRight();
		}
	}

}