package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.powerups.*;

public class PowerUpFactory {

	// TODO possibly not store the effects like this. Would definitely not work
	// if effects were mutable.
	private static final PowerUpEffect[] effects = new PowerUpEffect[] {
			new FatPowerUp(), new SlowPowerUp(), new SpeedPowerUp(),
			new ThinPowerUp(), new TurnPowerUp(), new RemoveWallsPowerUp(),
			new MorePowerUp(), new InvertedControlsPowerUp(),
			new ClearPowerUp(), new NoTailPowerUp(), new BodySwitchPowerUp() };

	private PowerUpFactory() {
	}

	public static PowerUpEntity getRandomEntity(Map map) {

		// Gets a random effect
		PowerUpEffect effect = getRandomEffect();

		float diameter = PowerUpEntity.getDefaultDiameter();

		float minX = diameter;
		float maxX = map.getWidth() - diameter;
		float minY = diameter;
		float maxY = map.getHeight() - diameter;

		Position randPos = Position.getRandomPosition(minX, minY, maxX, maxY);

		Type[] allowedTypes = effect.getAllowedTypes();
		Type type = allowedTypes[(int) (allowedTypes.length * Math.random())];

		PowerUpEntity entity = new PowerUpEntity(randPos, diameter, effect,
				type);
		return entity;
	}

	private static PowerUpEffect getRandomEffect() {
		// Gets a random effect based on the effect's weight. Higher weights
		// makes the effect more likely to be returned.

		float totWeight = 0;
		for (PowerUpEffect effect : effects) {
			totWeight += effect.getWeight();
		}

		float randWeight = (float) (totWeight * Math.random());

		float weightAccum = 0;
		for (PowerUpEffect effect : effects) {
			weightAccum += effect.getWeight();
			if (weightAccum > randWeight) {
				return effect;
			}
		}
		// This shouldn't be possible to reach, if there are effects in the
		// array and any effect return a non-zero weight
		throw new RuntimeException(
				"No random power-up effect can be found through weighting");
	}
}
