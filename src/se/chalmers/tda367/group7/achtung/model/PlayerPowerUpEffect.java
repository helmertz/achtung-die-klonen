package se.chalmers.tda367.group7.achtung.model;

public interface PlayerPowerUpEffect {

	/**
	 * Add the effect of the power up to player.
	 * @param player - the player that gets the power up added
	 */
	void applyEffect(Player player);
	
	/**
	 * Remove the effect of the power up from player.
	 * @param player - the player that gets the power up removed
	 */
	void removeEffect(Player player);
	
	/**
	 * @return the duration for this power up
	 */
	int getDuration();
}
