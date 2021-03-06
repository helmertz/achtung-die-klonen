package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class NoTailPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "immortal";
	private static final int DURATION = 120;
	private static final boolean STACKABLE = false;
	private static final float WEIGHT = 0.6f;

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public void applyEffect(Body body) {
		body.setSegmentGenerationEnabled(false);
		body.setImmortal(true);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSegmentGenerationEnabled(true);
		body.setImmortal(false);
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.SELF };
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
