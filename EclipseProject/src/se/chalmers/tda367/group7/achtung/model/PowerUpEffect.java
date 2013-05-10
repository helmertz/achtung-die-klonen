package se.chalmers.tda367.group7.achtung.model;

public interface PowerUpEffect {

	/**
	 * @return the duration for this power up
	 */
	int getDuration();
	
	/**
	 * @return the name of the image for this power up
	 */
	String getName();
	
	boolean isStackable();

}
