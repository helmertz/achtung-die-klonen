package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.powerups.*;

public class PowerUpFactory {

	// TODO possibly not store the effects like this. Would definitely not work
	// if effects were mutable.
	public static final PowerUpEffect[] effects = new PowerUpEffect[] {
			new FatPowerUp(), new SlowPowerUp(), new SpeedPowerUp(),
			new ThinPowerUp(), new TurnPowerUp(), new RemoveWallsPowerUp(),
			new MorePowerUp(), new InvertedControlsPowerUp(),
			new ClearPowerUp(), new NoTailPowerUp() };

	private PowerUpFactory() {
	}

	public static PowerUpEntity getRandomEntity(Map map) {

		// Gets a random effect
		PowerUpEffect effect = effects[(int) (effects.length * Math.random())];

		float diameter = PowerUpEntity.getDefaultDiameter();

		float minX = diameter;
		float maxX = map.getWidth() - diameter;
		float minY = diameter;
		float maxY = map.getHeight() - diameter;

		Position randPos = Position.getRandomPosition(minX, minY, maxX, maxY);
		
		Type type = null;
		
		if (effect instanceof RoundPowerUpEffect) {
			type = Type.EVERYONE;
		}
		if (effect instanceof BodyPowerUpEffect) {
			Type[] allowedTypes = ( (BodyPowerUpEffect) effect).getAllowedTypes();
			type = allowedTypes[(int) (allowedTypes.length * Math.random())];
		}
		
		//Emergency Solution
		if (type == null) {
			type = Type.EVERYONE;
		}

		PowerUpEntity entity = new PowerUpEntity(randPos,
				PowerUpEntity.getDefaultDiameter(), effect, type);
		return entity;
	}
}
