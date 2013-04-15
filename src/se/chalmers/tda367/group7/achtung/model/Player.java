package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

/**
 * This class holds all logic for moving the body.
 */
public class Player {

	public static final float DEFAULT_SPEED = 1;
	public static final float DEFAULT_ROTATION = 0;

	private String name;
	private Color color;
	
	private int points;
	private int id;
	private static int numberOfPlayers = 0;
	
	private float speed;
	private float rotationAngleDeg; // in degrees
	

	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	private Body body;
	
	public Player(String name, Color color, float startX, float startY) {
		this.name = name;
		this.body = new Body(new Vector2f(startX, startY));
		this.color = color;
		
		// id mechanism.
		id = numberOfPlayers;
		numberOfPlayers++;
	}

	/**
	 * Update the player. 
	 */
	public void update() {
		updatePowerUps();

		// Calculates the new delta position.
		float dx = (float) (speed * Math.cos(Math.toRadians(rotationAngleDeg)));
		float dy = (float) (speed * Math.sin(Math.toRadians(rotationAngleDeg)));
		
		// Update body with new delta positions.
		body.update(dx, dy);
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

	public List<PowerUp> getPowerUps() {
		return powerUps;
	}

	public int getPoints() {
		return points;
	}
	
	/**
	 * Adds the given number of points to the player.
	 * 
	 * @param points - the amount of points to add
	 */
	public void addPoints(int points) {
		this.points = points;
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
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRotationAngleDeg() {
		return rotationAngleDeg;
	}

	public void setRotationAngleDeg(float rotationDeg) {
		this.rotationAngleDeg = rotationDeg;
	}
	
	public float getWidth() {
		return body.getWidth();
	}

	public void setWidth(float width) {
		body.setWidth(width);
	}
}
