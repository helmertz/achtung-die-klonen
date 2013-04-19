package se.chalmers.tda367.group7.achtung.model;

public class PowerUpEntity {
	
	private final Position position;
	private final int diameter;
	private final PlayerPowerUpEffect playerPowerUpEffect;
	
	public PowerUpEntity(Position position, int diameter,
			PlayerPowerUpEffect playerPowerUpEffect) {
		this.position = position;
		this.diameter = diameter;
		this.playerPowerUpEffect = playerPowerUpEffect;
	}

	public Position getPosition() {
		return position;
	}

	public int getDiameter() {
		return diameter;
	}

	public PlayerPowerUpEffect getPlayerPowerUpEffect() {
		return playerPowerUpEffect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diameter;
		result = prime
				* result
				+ ((playerPowerUpEffect == null) ? 0 : playerPowerUpEffect
						.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		if (diameter != other.diameter)
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
		return true;
	}
}
