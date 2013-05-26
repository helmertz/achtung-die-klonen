package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class SlowPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "slow";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = true;
	private static final float WEIGHT = 1;

	@Override
	public void applyEffect(Body body) {
		body.setSpeed(body.getSpeed() / 2);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSpeed(body.getSpeed() * 2);
	}

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.SELF, Type.EVERYONE_ELSE };
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

	@Override
	public float getWeight() {
		return WEIGHT;
	}

}
