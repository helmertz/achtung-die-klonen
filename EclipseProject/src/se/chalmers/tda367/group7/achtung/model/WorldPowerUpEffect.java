package se.chalmers.tda367.group7.achtung.model;

public interface WorldPowerUpEffect extends PowerUpEffect {

	void applyEffect(Round round);

	void removeEffect(Round round);
}
