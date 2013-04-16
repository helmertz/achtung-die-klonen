package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all logic for moving the body.
 */
public class Player {

	private static final float DEFAULT_SPEED = 1;
	private static final float DEFAULT_ROTATION = 0;
	private static final float DEFAULT_ROTATION_SPEED = 0.1f;

	private String name;
	private Color color;
	
	private int points;
	private int id;
	private static int numberOfPlayers = 0;
	
	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg;	// the angle the snake is turning with.
	

	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	private Body body;
	
	public Player(String name, Color color) {
		this.name = name;
//		this.body = new Body(new Vector2f(startX, startY));
		this.color = color;
		
		speed = DEFAULT_SPEED;
		rotationAngleDeg = DEFAULT_ROTATION;
		rotationSpeedDeg = DEFAULT_ROTATION_SPEED;
		
		// id mechanism.
		id = numberOfPlayers;
		numberOfPlayers++;
	}

	/**
	 * Update the player. 
	 * The player will have to have a body before this method is called.
	 */
	public void update() {
		// TODO - throw some kind of exception if there is no body.
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
	
	public void setPosition(Position position) {
		body.setHeadPosition(position);
	}

	public Body getBody() {
		return this.body;
	}
	
	public void createNewBody(float startX, float startY) {
		body = new Body(new Position(startX, startY));
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
	
	public float getRotationSpeedDeg() {
		return rotationSpeedDeg;
	}
	
	public void setRotationSpeedDeg(float rotationSpeed) {
		this.rotationSpeedDeg = rotationSpeed;
	}
	
	public void turnRight() {
		rotationAngleDeg += rotationSpeedDeg;
	}
	
	public void turnLeft() {
		rotationAngleDeg -= rotationSpeedDeg;
	}
	
	public float getWidth() {
		return body.getWidth();
	}

	public void setWidth(float width) {
		body.setWidth(width);
	}
}
