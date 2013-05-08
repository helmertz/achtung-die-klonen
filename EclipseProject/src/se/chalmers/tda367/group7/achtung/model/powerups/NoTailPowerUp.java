package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class NoTailPowerUp implements BodyPowerUpEffect {

	@Override
	public int getDuration() {
		return 65;
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
		return type == Type.SELF;
	}
}
