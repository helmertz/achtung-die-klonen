package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;

public class RemoveWallsPowerUp implements RoundPowerUpEffect {

	private static final String NAME = "wall-remove";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = false;

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

}
