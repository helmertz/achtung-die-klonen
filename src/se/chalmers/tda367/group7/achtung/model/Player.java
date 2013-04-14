package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Player {

	private String name;
	private Color color;
	
	private int points;
	private int id;

	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	private Body body;
	
	public Player(String name, Color color, float startX, float startY) {
		this.name = name;
		this.body = new Body(new Vector2f(startX, startY));
		this.color = color;
	}

	/**
	 * Update the player.
	 */
	public void update() {
		updatePowerUps();
		// let the body handle movement related updates?
		body.update();
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
		return body.getSpeed();
	}

	public void setSpeed(float speed) {
		body.setSpeed(speed);
	}

	public double getRotationAngle() {
		return body.getRotationAngle();
	}

	public void setRotationAngle(float rotationAngle) {
		body.setRotationAngle(rotationAngle);
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
	
	public void setPosition(Vector2f position) {
		body.setHeadPosition(position);
	}

	public Body getBody() {
		return this.body;
	}

	public String getName() {
		return name;
	}
}
