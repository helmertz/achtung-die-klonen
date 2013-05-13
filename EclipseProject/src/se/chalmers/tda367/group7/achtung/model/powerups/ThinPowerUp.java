package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class ThinPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "thin";
	private static final int DURATION = 150;
	private static final boolean STACKABLE = true;

	@Override
	public void applyEffect(Body body) {
		body.setWidth(body.getWidth() / 2);
	}

	@Override
	public void removeEffect(Body body) {
		body.setWidth(body.getWidth() * 2);
	}

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] {Type.SELF};
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
