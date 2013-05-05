package se.chalmers.tda367.group7.achtung.controller;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.input.InputListener;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.sound.Sound;

public class GameController implements InputListener {
	private Game game;
	private List<PlayerController> playerControllers = new ArrayList<>();
	
	private boolean soundEnabled = true;
	
	public GameController(Game game) {	
		this.game = game;
		int i = 0;
		for(Player p : game.getPlayers()) {
			
			PlayerController pc = new PlayerController(p);
			if(i == 0) {
				pc.setLeftKey(Keyboard.KEY_LEFT);
				pc.setRightKey(Keyboard.KEY_RIGHT);
			} else if (i == 1){
				pc.setLeftKey(Keyboard.KEY_A);
				pc.setRightKey(Keyboard.KEY_D);
			} else {
				pc.setLeftKey(Keyboard.KEY_1);
				pc.setRightKey(Keyboard.KEY_2);
				
			}
			i++;
			playerControllers.add(pc);
		}
	}
	
	// Called as quickly as possible after a key is pressed, not in sync with game rate
	@Override
	public boolean onInputEvent(InputEvent event) {
		if(event.isPressed() && event.getKey() == Keyboard.KEY_SPACE) {
			startRound();
			return true;
		}
		for(PlayerController pc : playerControllers) {
			if(pc.onInputEvent(event)) {
				return true;
			}
		}
		return false;
	}
	
	public void startRound() {
		game.newRound();
		Round round = game.getCurrentRound();
		
		if (soundEnabled) {
			// TODO- maybe sound should be a singleton?
			Sound sound = new Sound();
			round.addPropertyChangeListener(sound);
			game.addPropertyChangeListener(sound);
		}
	}
}
