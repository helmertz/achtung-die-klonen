package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Player {

	private List<PowerUp> powerUps;
	private Body body = new Body();
	
	private int points;
	private final int id;
	private double speed;
	private Color color;
	private double rotationAngle;
	private String name;
	
	public Player(Color color, String name, int id) {
		powerUps = new ArrayList<PowerUp>();
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Update the player.
	 */
	public void update() {
		updatePowerUps();
	}
	
	/**
	 * Adds a power up to the player. When the player collides with a PowerUpEntity
	 * it will receive an effect using this method.
	 * @param effect - the effect to add to the player
	 */
	public void addPowerUp(PlayerPowerUpEffect effect) {
		PowerUp p = new PowerUp(effect);
		p.applyEffect(this);
		powerUps.add(p);
	}
	
	/**
	 * Updates all power ups, removes them if inactive.
	 */
	private void updatePowerUps() {
		for (PowerUp p : powerUps) {
			p.update();
			if (!p.isActive()) {
				removePowerUpAndEffect(p);
			}
		}
	}
	
	private void removePowerUpAndEffect(PowerUp powerUp) {
		powerUp.removeEffect(this);
		powerUps.remove(powerUp);
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	public List<PowerUp> getPowerUps() {
		return powerUps;
	}

	public int getPoints() {
		return points;
	}

	public int getId() {
		return id;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
	
	public void setPosition(Vector2f position) {
		body.setHeadPosition(position);
	}

}
