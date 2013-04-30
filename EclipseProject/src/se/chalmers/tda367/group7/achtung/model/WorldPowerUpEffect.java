package se.chalmers.tda367.group7.achtung.model;

public interface WorldPowerUpEffect extends PowerUpEffect {

	void applyEffect(World world);
	
	void removeEffect(World world);
}
