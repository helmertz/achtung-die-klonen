package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class PowerUpFactory {

	private PowerUpFactory() {}
	
	public static PowerUpEntity getRandomEntity(float worldWidth, float worldHeight, float f) {
		
		// Gets a random effect
		PlayerPowerUpEffect effect = PlayerPowerUpEffect.effects[(int)(PlayerPowerUpEffect.effects.length*Math.random())];

		// TODO narrow this down more
		Position randPos = Position.getRandomPosition(0, worldWidth, 0, worldHeight);
	
		Type type = Type.values()[(int) (Type.values().length*Math.random())];
		
		PowerUpEntity entity = new PowerUpEntity(randPos, PowerUpEntity.getDefaultDiameter(), effect, type);
		return entity;
	}
}
