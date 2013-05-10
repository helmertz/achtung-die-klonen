package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class ThinPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "thin"; 
	private static final int DURATION = 150; 	

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
	public boolean isTypeOk(Type type) {
		return type == Type.SELF || type == Type.EVERYONE_ELSE;
	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
