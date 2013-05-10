package se.chalmers.tda367.group7.achtung.model;

public interface RoundPowerUpEffect extends PowerUpEffect {

	void applyEffect(Round round);

	void removeEffect(Round round);
}
