package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.PlayerPowerUpEffect;

public class FatPowerUp implements PlayerPowerUpEffect{

	@Override
	public void applyEffect(Body body) {
		body.setWidth(body.getWidth()*2);
	}

	@Override
	public void removeEffect(Body body) {
		body.setWidth(body.getWidth()/2);
	}

	@Override
	public int getDuration() {
		return 150;
	}

}
