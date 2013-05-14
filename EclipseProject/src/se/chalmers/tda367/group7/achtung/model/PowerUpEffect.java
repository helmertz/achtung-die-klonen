package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public interface PowerUpEffect {

	/**
	 * @return the duration for this power up
	 */
	int getDuration();

	/**
	 * @return the name of the image for this power up
	 */
	String getName();

	/**
	 * If the PowerUpEffect is not stackable and another of the same kind is
	 * picked up, the timer is reset. Else, the effect is applied again.
	 * 
	 * @return if the PowerUpEffect is stackable or not
	 * 
	 */
	boolean isStackable();
	
	/**
	 * Returns true if the type is allowed for the particular power up
	 * 
	 * @param type
	 *            - who the power up affects
	 * @return true - if type is allowed
	 */
	Type[] getAllowedTypes();

}
