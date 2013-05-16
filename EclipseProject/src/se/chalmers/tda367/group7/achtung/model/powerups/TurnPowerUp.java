package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.Settings;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class TurnPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "sharp-turn";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = false;

	@Override
	public void applyEffect(Body body) {
		body.setSharpTurns(true);
		body.setRotationSpeedDeg(90);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSharpTurns(false);
		// TODO fix this variable some way.
		body.setRotationSpeedDeg(Settings.getInstance().getRotationSpeed());
	}

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.EVERYONE_ELSE };
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
