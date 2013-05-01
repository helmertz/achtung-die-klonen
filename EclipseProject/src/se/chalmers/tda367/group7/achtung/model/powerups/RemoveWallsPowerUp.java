package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.WorldPowerUpEffect;

public class RemoveWallsPowerUp implements WorldPowerUpEffect {

	@Override
	public void applyEffect(Round world) {
		world.setWallsActive(false);
		// TODO: better way of signaling this
		System.out.println("you can now pass through walls!");
	}

	@Override
	public void removeEffect(Round world) {
		world.setWallsActive(false);
	}

	@Override
	public int getDuration() {
		return 150;
	}
}
