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
}
