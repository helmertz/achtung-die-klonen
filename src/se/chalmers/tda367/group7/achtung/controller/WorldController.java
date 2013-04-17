package se.chalmers.tda367.group7.achtung.controller;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.input.InputListener;
import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.World;

public class WorldController implements InputListener {
	private World world;
	private List<PlayerController> playerControllers = new ArrayList<>();
	
	public WorldController(World world) {	
		this.world = world;
		for(Player p : world.getPlayers()) {
			PlayerController pc = new PlayerController(p);
			pc.setLeftKey(Keyboard.KEY_LEFT);
			pc.setRightKey(Keyboard.KEY_RIGHT);
			playerControllers.add(pc);
		}
	}
	
	// Called as quickly as possible after a key is pressed, not in sync with game rate
	@Override
	public boolean onInputEvent(InputEvent event) {
		if(event.isPressed() && event.getKey() == Keyboard.KEY_SPACE) {
			world.startRound();
			return true;
		}
		for(PlayerController pc : playerControllers) {
			if(pc.onInputEvent(event)) {
				return true;
			}
		}
		return false;
	}
	
	// Called at a constant game speed
	public void update(InputService service) {
		for(PlayerController pc : playerControllers) {
			pc.update(service);
		}
	}
}
