package se.chalmers.tda367.group7.achtung.model;

public class PowerUpEntity {
	
	private final Position position;
	private final float diameter;
	private final PowerUpEffect powerUpEffect;
	private Type type;
	private static final float DEFAULT_DIAMETER = 40;
	
	public enum Type {
		SELF, EVERYONE, EVERYONE_ELSE
	}
	
	public PowerUpEntity(Position position, float diameter,
			PowerUpEffect powerUpEffect, Type type) {
		this.position = position;
		this.diameter = diameter;
		this.powerUpEffect = powerUpEffect;
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public float getDiameter() {
		return diameter;
	}

	public PowerUpEffect getPowerUpEffect() {
		return powerUpEffect;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(diameter);
		result = prime
				* result
				+ ((powerUpEffect == null) ? 0 : powerUpEffect
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
		if (powerUpEffect == null) {
			if (other.powerUpEffect != null)
				return false;
		} else if (!powerUpEffect.equals(other.powerUpEffect))
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



	public Type getType() {
		return type;
	}

	public static float getDefaultDiameter() {
		return DEFAULT_DIAMETER;
	}
}
