package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class InvertedControlsPowerUp implements PlayerPowerUpEffect {

	@Override
	public int getDuration() {
		return 150;
	}

	@Override
	public void applyEffect(Body body) {
		body.toggleInvertedControls();
	}

	@Override
	public void removeEffect(Body body) {
		body.toggleInvertedControls();
	}

	@Override
	public boolean isTypeOk(Type type) {
		return type == Type.EVERYONE_ELSE;
	}

}
