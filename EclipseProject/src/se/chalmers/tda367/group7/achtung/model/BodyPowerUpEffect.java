package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

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

	/**
	 * Returns true if the type is allowed for the particular power up
	 * 
	 * @param type
	 *            - who the power up affects
	 * @return true - if type is allowed
	 */
	Type[] getAllowedTypes();
}
