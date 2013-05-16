package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class ClearPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "clear";
	private static final int DURATION = 0;
	private static final boolean STACKABLE = false;

	@Override
	public void applyEffect(Body body) {
		body.getBodySegments().clear();
	}

	@Override
	public void removeEffect(Body body) {
		// do nothing
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.EVERYONE };
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
	public String toString() {
		return NAME;
	}
	
}
