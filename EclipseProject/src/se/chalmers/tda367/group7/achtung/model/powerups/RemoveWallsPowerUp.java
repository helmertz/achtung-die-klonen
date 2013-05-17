package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class RemoveWallsPowerUp implements RoundPowerUpEffect {

	private static final String NAME = "wall-remove";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = false;
	private int stacks = 0;

	@Override
	public void applyEffect(Round round) {
		if(stacks != 0 && round.isWallsActive()) {
			stacks = 0;
		}
		this.stacks++;
		round.setWallsActive(false);
	}

	@Override
	public void removeEffect(Round round) {
		this.stacks--;
		if (this.stacks == 0) {
			round.setWallsActive(true);
		}
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

}
