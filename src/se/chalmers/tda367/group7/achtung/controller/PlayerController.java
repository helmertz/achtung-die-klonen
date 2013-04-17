package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.model.Player;

public interface PlayerController {

	public void setPlayer(Player player);
	
	public void setRightKey(int key);

	public void setLeftKey(int key);

}
