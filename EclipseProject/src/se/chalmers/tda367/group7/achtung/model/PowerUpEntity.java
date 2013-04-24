package se.chalmers.tda367.group7.achtung.model;

public class PowerUpEntity {
	
	private final Position position;
	private final float diameter;
	private final PlayerPowerUpEffect playerPowerUpEffect;
	private Type type;
	private static final float DEFAULT_DIAMETER = 40;
	
	public enum Type {
		SELF, EVERYONE, EVERYONE_ELSE
	}
	
	public PowerUpEntity(Position position, float diameter,
			PlayerPowerUpEffect playerPowerUpEffect, Type type) {
		this.position = position;
		this.diameter = diameter;
		this.playerPowerUpEffect = playerPowerUpEffect;
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public float getDiameter() {
		return diameter;
	}

	public PlayerPowerUpEffect getPlayerPowerUpEffect() {
		return playerPowerUpEffect;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(diameter);
		result = prime
				* result
				+ ((playerPowerUpEffect == null) ? 0 : playerPowerUpEffect
						.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerUpEntity other = (PowerUpEntity) obj;
		if (Float.floatToIntBits(diameter) != Float
				.floatToIntBits(other.diameter))
			return false;
		if (playerPowerUpEffect == null) {
			if (other.playerPowerUpEffect != null)
				return false;
		} else if (!playerPowerUpEffect.equals(other.playerPowerUpEffect))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static PowerUpEntity getRandomEntity(float worldWidth, float worldHeight) {
		
		// Gets a random effect
		PlayerPowerUpEffect effect = PlayerPowerUpEffect.effects[(int)(PlayerPowerUpEffect.effects.length*Math.random())];

		// TODO narrow this down more
		Position randPos = Position.getRandomPosition(0, worldWidth, 0, worldHeight);
	
		Type type = Type.values()[(int) (Type.values().length*Math.random())];
		
		PowerUpEntity entity = new PowerUpEntity(randPos, DEFAULT_DIAMETER, effect, type);
		return entity;
	}

	public Type getType() {
		return type;
	}
}
