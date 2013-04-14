package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Player {

	private String name;
	private Color color;
	
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();;
	private Body body;
	
	public Player(String name, Color color, float startX, float startY) {
		this.name = name;
		this.body = new Body(new Vector2f(startX, startY));
		this.color = color;
	}

	public void setPosition(Vector2f position) {
		body.setHeadPosition(position);
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
				p.removeEffect(this);
				powerUps.remove(p);
			}
		}
	}

	public void setSpeed(float speed) {
		body.setSpeed(speed);
	}

	public void setRotation(float rotation) {
		body.setRotation(rotation);
	}

	public float getSpeed() {
		return body.getSpeed();
	}

	public float getRotation() {
		return body.getRotation();
	}

	public Body getBody() {
		return body;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}
