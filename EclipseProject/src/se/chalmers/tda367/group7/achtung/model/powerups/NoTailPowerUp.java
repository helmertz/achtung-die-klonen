package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class NoTailPowerUp implements PlayerPowerUpEffect {

	@Override
	public int getDuration() {
		return 100;
	}

	@Override
	public void applyEffect(Body body) {
		body.setGeneratingBodySegments(false);
	}

	@Override
	public void removeEffect(Body body) {
		body.setGeneratingBodySegments(true);
	}

	@Override
	public boolean isTypeOk(Type type) {
		return type == Type.SELF || type == Type.EVERYONE_ELSE;
	}
}
