package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing the physical state of a player.
 */
public class Body {

	public static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 1;
	private static final float DEFAULT_ROTATION = 0;
	private static final float DEFAULT_ROTATION_SPEED = 0.1f;

	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg;	// the angle the snake is turning with.

	private float width;
	
	private Head head;
	private List<BodySegment> bodySegments;
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	
	public Body (Position position) {		
		// Width of the body is the same as the diameter of the head.
		head = new Head(position, width);
		bodySegments = new ArrayList<BodySegment>();
		
		width = DEFAULT_WIDTH;
		speed = DEFAULT_SPEED;
		rotationAngleDeg = DEFAULT_ROTATION;
		rotationSpeedDeg = DEFAULT_ROTATION_SPEED;
	}
	
	public Head getHead() {
		return head;
	}
	
	public List<BodySegment> getBodySegments() {
		return bodySegments;
	}
	
	public void addBodySegment(BodySegment bodySegment) {
		bodySegments.add(bodySegment);
	}

	public void setHeadPosition(Position position) {
		if(this.head == null) {
			head = new Head(position, width);
		} else {
			head.setPosition(position);
		}
	}

	/**
	 * Moves the body by the delta values.
	 */
	public void update() {
		updatePowerUps();

		updatePosition();
	}

	private void updatePosition() {
		// Update head with delta positions
		Position headPosition = head.getPosition();
		float x = headPosition.getX();
		float y = headPosition.getY();

		// Calculates the new delta position.
		float dx = (float) (speed * Math.cos(Math.toRadians(rotationAngleDeg)));
		float dy = (float) (speed * Math.sin(Math.toRadians(rotationAngleDeg)));

		headPosition.setX(x + dx);
		headPosition.setY(y + dy);

		// Create a new body segment and add to body segment list.
		// Will use previous body segment end as start, so that there are no
		// duplicated variables.
		// TODO - This will have to be thought through when holes is
		// implemented.
		if (bodySegments.isEmpty()) {
			addBodySegment(new BodySegment(new Position(x, y), new Position(x
					+ dx, y + dy), width));
		} else {
			Position start = bodySegments.get(bodySegments.size() - 1).getEnd();
			addBodySegment(new BodySegment(start, new Position(x + dx, y + dy),
					width));
		}
	}

	public float getWidth() {
		return width;
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

	public void setWidth(float width) {
		this.width = width;
		head.setDiameter(width);
	}

	public void turnRight() {
		rotationAngleDeg -= rotationSpeedDeg;
	}

	public void turnLeft() {
		rotationAngleDeg += rotationSpeedDeg;
	}

	public void setRotationAngleDeg(float angle) {
		rotationAngleDeg = angle;		
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Position getPosition() {
		return head.getPosition();
	}
}
