package se.chalmers.tda367.group7.achtung.controller;

import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.input.InputListener;
import se.chalmers.tda367.group7.achtung.model.Player;

public class LWJGLPlayerController implements PlayerController, InputListener {

	private int leftKey;
	private int rightKey;
	
	public Player player;
	
	public LWJGLPlayerController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;

	}

	@Override
	public void setRightKey(int key) {
		rightKey = key;

	}

	@Override
	public void setLeftKey(int key) {
		leftKey = key;
	}

	@Override
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
