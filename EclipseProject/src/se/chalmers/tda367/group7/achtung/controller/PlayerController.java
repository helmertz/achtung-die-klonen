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
		rightKey = key;
	}

	public void setLeftKey(int key) {
		leftKey = key;
	}

	public boolean onInputEvent(InputEvent e) {
		if(e.getKey() == leftKey) {
			leftDown = e.isPressed();
		}
		
		if(e.getKey() == rightKey) {
			rightDown = e.isPressed();
		}
		
		if(leftDown && !rightDown) {
			player.getBody().setTurnMode(Body.TurnMode.LEFT);
		} else if(!leftDown && rightDown) {
			player.getBody().setTurnMode(Body.TurnMode.RIGHT);
		} else {
			player.getBody().setTurnMode(Body.TurnMode.FORWARD);
		}
		return false;
	}
}