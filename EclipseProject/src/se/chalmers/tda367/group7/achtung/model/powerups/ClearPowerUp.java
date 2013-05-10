package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class ClearPowerUp implements BodyPowerUpEffect {

	private static final String NAME = "clear"; 
	private static final int DURATION = 0; 
	
	@Override
	public void applyEffect(Body body) {
		body.getBodySegments().clear();
	}

	@Override
	public void removeEffect(Body body) {
		// do nothing
	}

	@Override
	public boolean isTypeOk(Type type) {
		return type == Type.EVERYONE;
	}

	@Override
	public int getDuration() {
		return DURATION;
	}
	
	@Override
	public String getName() {
		return NAME;
	}


}
