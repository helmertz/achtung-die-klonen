package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.World;
import se.chalmers.tda367.group7.achtung.model.WorldPowerUpEffect;

public class MorePowerUp implements WorldPowerUpEffect {

	@Override
	public void applyEffect(World world) {
		world.setPowerUpChance(world.getDefaultPowerUpChance() + (float)0.1);
	}

	@Override
	public void removeEffect(World world) {
		world.setPowerUpChance(world.getDefaultPowerUpChance());
	}

	@Override
	public int getDuration() {
		return 200;
	}
}
