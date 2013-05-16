package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class MorePowerUp implements RoundPowerUpEffect {

	private static final String NAME = "more-powerups";
	private static final int DURATION = 200;
	private static final boolean STACKABLE = true;

	@Override
	public void applyEffect(Round round) {
		// TODO something other than doubling perhaps
		round.setPowerUpChance(round.getPowerUpChance() * 2);
	}

	@Override
	public void removeEffect(Round round) {
		round.setPowerUpChance(round.getPowerUpChance() / 2);
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
}
