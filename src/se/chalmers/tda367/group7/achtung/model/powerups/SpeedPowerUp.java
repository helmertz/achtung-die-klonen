package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;

public class SpeedPowerUp implements PlayerPowerUpEffect {

	@Override
	public void applyEffect(Body body) {
		body.setSpeed(body.getSpeed() + 7);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSpeed(body.getSpeed() - 7);
	}

	@Override
	public int getDuration() {
		return 60;
	}

	
}
