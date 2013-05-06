package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class SlowPowerUp implements PlayerPowerUpEffect {

	@Override
	public void applyEffect(Body body) {
		body.setSpeed(body.getSpeed()/2);
	}

	@Override
	public void removeEffect(Body body) {
		body.setSpeed(body.getSpeed()*2);
	}

	@Override
	public int getDuration() {
		return 150;
	}
	
	public boolean isTypeOk(Type type) {
		return type == Type.SELF || type == Type.EVERYONE_ELSE;
	}
	
}
