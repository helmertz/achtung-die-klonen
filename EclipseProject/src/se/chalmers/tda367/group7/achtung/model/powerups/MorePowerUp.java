package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.WorldPowerUpEffect;

public class MorePowerUp implements WorldPowerUpEffect {

	@Override
	public void applyEffect(Round round) {
		round.setPowerUpChance(round.getDefaultPowerUpChance() + (float)0.06);
	}

	@Override
	public void removeEffect(Round round) {
		round.setPowerUpChance(round.getDefaultPowerUpChance());
	}

	@Override
	public int getDuration() {
		return 200;
	}
}
