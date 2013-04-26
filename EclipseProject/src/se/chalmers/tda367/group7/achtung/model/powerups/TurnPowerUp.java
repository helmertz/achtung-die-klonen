package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyConstants;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;

public class TurnPowerUp implements PlayerPowerUpEffect {

	@Override
	public void applyEffect(Body body) {
		body.setSharpTurns(true);
		body.setRotationSpeedDeg(90);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSharpTurns(false);
		// TODO fix this variable some way.
		body.setRotationSpeedDeg(BodyConstants.DEFAULT_ROTATION_SPEED);
	}

	@Override
	public int getDuration() {
		return 150;
	}

}
