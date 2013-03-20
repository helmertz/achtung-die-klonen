package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.input.Keyboard;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.World;


public class WorldController {

	private World world;

	public WorldController(World world) {
		this.world = world;
	}

	public void update() {
		Keyboard.poll();
		while (Keyboard.next()) {
			handleKey(Keyboard.getEventKey(), Keyboard.getEventKeyState());
		}

		for (Player p : this.world.getPlayers()) {
			if (Keyboard.isKeyDown(p.getLeftKeyCode())) {
				p.turnLeft();
			}
			if (Keyboard.isKeyDown(p.getRightKeyCode())) {
				p.turnRight();
			}
			p.update();
		}

	}

	private void handleKey(int eventKey, boolean pressed) {
		if (!pressed) {
			return;
		}
		if (eventKey == Keyboard.KEY_ESCAPE) {
			// Do something
		}
	}
}
