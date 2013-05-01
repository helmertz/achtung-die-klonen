package se.chalmers.tda367.group7.achtung.model;

import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;

public class PowerUpFactory {

	private PowerUpFactory() {}
	
	public static PowerUpEntity getRandomEntity(Map map) {
		
		// Gets a random effect
		PowerUpEffect effect = PlayerPowerUpEffect.effects[(int)(PlayerPowerUpEffect.effects.length*Math.random())];
		
		float diameter = PowerUpEntity.getDefaultDiameter();
		
		float minX = diameter;
		float maxX = map.getWidth() - diameter;
		float minY = diameter;
		float maxY = map.getHeight() - diameter;
		
		Position randPos = Position.getRandomPosition(minX, maxX, minY, maxY);
	
		Type type = Type.values()[(int) (Type.values().length*Math.random())];
		
		PowerUpEntity entity = new PowerUpEntity(randPos, PowerUpEntity.getDefaultDiameter(), effect, type);
		return entity;
	}
}
