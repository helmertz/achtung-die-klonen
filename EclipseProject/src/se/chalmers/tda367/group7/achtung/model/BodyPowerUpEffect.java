package se.chalmers.tda367.group7.achtung.model;


public interface BodyPowerUpEffect extends PowerUpEffect {

	/**
	 * Add the effect of the power up to a body.
	 * 
	 * @param body
	 *            - the body that gets the power up added
	 */
	void applyEffect(Body body);

	/**
	 * Remove the effect of the power up from a body.
	 * 
	 * @param player
	 *            - the body that gets the power up removed
	 */
	void removeEffect(Body body);

}
