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
		setCorrectType(type);
	}

	private void setCorrectType(Type type) {
		if (this.powerUpEffect instanceof WorldPowerUpEffect) {
			this.type = Type.EVERYONE;
		} else if (this.powerUpEffect instanceof BodyPowerUpEffect) {
			this.type = type;
		}
	}

	public Position getPosition() {
		return this.position;
	}

	public float getDiameter() {
		return this.diameter;
	}

	public PowerUpEffect getPowerUpEffect() {
		return this.powerUpEffect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.diameter);
		result = prime
				* result
				+ ((this.powerUpEffect == null) ? 0 : this.powerUpEffect
						.hashCode());
		result = prime * result
				+ ((this.position == null) ? 0 : this.position.hashCode());
		result = prime * result
				+ ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PowerUpEntity other = (PowerUpEntity) obj;
		if (Float.floatToIntBits(this.diameter) != Float
				.floatToIntBits(other.diameter)) {
			return false;
		}
		if (this.powerUpEffect == null) {
			if (other.powerUpEffect != null) {
				return false;
			}
		} else if (!this.powerUpEffect.equals(other.powerUpEffect)) {
			return false;
		}
		if (this.position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!this.position.equals(other.position)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
	}

	public Type getType() {
		return this.type;
	}

	public static float getDefaultDiameter() {
		return DEFAULT_DIAMETER;
	}
}
