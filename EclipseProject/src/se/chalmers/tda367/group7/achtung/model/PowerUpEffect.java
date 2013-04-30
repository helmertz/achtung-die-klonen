package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.powerups.*;

public interface PowerUpEffect {

	// TODO not store the effects like this. Would definitely not work if
	// effects were mutable.
	public static final PowerUpEffect[] effects = new PowerUpEffect[]{
		new FatPowerUp(),
		new SlowPowerUp(),
		new SpeedPowerUp(),
		new ThinPowerUp(),
		new TurnPowerUp(),
		new RemoveWallsPowerUp()
	};
	
	/**
	 * @return the duration for this power up
	 */
	int getDuration();
	
}
