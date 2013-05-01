package se.chalmers.tda367.group7.achtung.model;

public interface WorldPowerUpEffect extends PowerUpEffect {

	void applyEffect(Round world);
	
	void removeEffect(Round world);
}
