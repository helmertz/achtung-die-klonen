package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.powerups.*;

public interface PlayerPowerUpEffect {

	// TODO not store the effects like this. Would definitely not work if
	// effects were mutable.
	public static final PlayerPowerUpEffect[] effects = new PlayerPowerUpEffect[]{
		new FatPowerUp(),
		new SlowPowerUp(),
		new SpeedPowerUp(),
		new ThinPowerUp(),
		new TurnPowerUp()
	};
	
	/**
	 * Add the effect of the power up to a body.
	 * @param body - the body that gets the power up added
	 */
	void applyEffect(Body body);
	
	/**
	 * Remove the effect of the power up from a body.
	 * @param player - the body that gets the power up removed
	 */
	void removeEffect(Body body);
	
	/**
	 * @return the duration for this power up
	 */
	int getDuration();
}
