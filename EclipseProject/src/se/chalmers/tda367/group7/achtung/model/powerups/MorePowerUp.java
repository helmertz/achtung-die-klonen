package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;

public class MorePowerUp implements RoundPowerUpEffect {

	private static final String NAME = "more-powerups"; 
	private static final int DURATION = 200;
	
	@Override
	public void applyEffect(Round round) {
		round.setPowerUpChance(round.getDefaultPowerUpChance() + (float) 0.06);
	}

	@Override
	public void removeEffect(Round round) {
		round.setPowerUpChance(round.getDefaultPowerUpChance());
	}

	@Override
	public int getDuration() {
		return DURATION;
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
