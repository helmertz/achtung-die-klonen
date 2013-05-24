package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class RemoveWallsPowerUp implements RoundPowerUpEffect {

	private static final String NAME = "wall-remove";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = false;
	private static final float WEIGHT = 1;

	@Override
	public void applyEffect(Round round) {
		round.setWallsActive(false);
	}

	@Override
	public void removeEffect(Round round) {
		round.setWallsActive(true);
	}

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isStackable() {
		return STACKABLE;
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.EVERYONE };
	}

	@Override
	public String toString() {
		return NAME;
	}

	@Override
	public float getWeight() {
		return WEIGHT;
	}

}
