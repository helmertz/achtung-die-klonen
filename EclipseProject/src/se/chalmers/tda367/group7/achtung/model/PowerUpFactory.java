package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class PowerUpFactory {

	private PowerUpFactory() {
	}

	public static PowerUpEntity getRandomEntity(Map map) {

		// Gets a random effect
		PowerUpEffect effect = PowerUpEffect.effects[(int) (PowerUpEffect.effects.length * Math
				.random())];

		float diameter = PowerUpEntity.getDefaultDiameter();

		float minX = diameter;
		float maxX = map.getWidth() - diameter;
		float minY = diameter;
		float maxY = map.getHeight() - diameter;

		Position randPos = Position.getRandomPosition(minX, minY, maxX, maxY);

		Type type = Type.values()[(int) (Type.values().length * Math.random())];

		if (effect instanceof WorldPowerUpEffect) {
			type = Type.EVERYONE;
		}
		if (effect instanceof BodyPowerUpEffect) {
			while (!((BodyPowerUpEffect) effect).isTypeOk(type)) {
				type = Type.values()[(int) (Type.values().length * Math
						.random())];
			}
		}

		PowerUpEntity entity = new PowerUpEntity(randPos,
				PowerUpEntity.getDefaultDiameter(), effect, type);
		return entity;
	}
}
