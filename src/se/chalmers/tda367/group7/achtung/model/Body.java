package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the physical state of a player.
 */
public class Body {

	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 10;
	private static final float DEFAULT_ROTATION_SPEED = 5f;
	private static final double CHANS_OF_HOLE = 0.015;

	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg;	// the angle the snake is turning with.

	private float width;
	private boolean dead;
	private boolean immortal;
	private int holeLenthCounter;
	private Position holeTmpPos;
	
	private Head head;
	private List<BodySegment> bodySegments;
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	private TurnMode turnMode;
	
	// TODO better names
	public enum TurnMode {
		LEFT, RIGHT, FORWARD
	}
	
	public Body (Position position, float rotation) {		
		// Width of the body is the same as the diameter of the head.
		head = new Head(position, width);
		bodySegments = new ArrayList<BodySegment>();
		
		dead = false;
		immortal = false;
		holeLenthCounter = 0;
		width = DEFAULT_WIDTH;
		speed = DEFAULT_SPEED;
		rotationAngleDeg = rotation;
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

		if (!dead) {
			updatePosition();
		}
	}

	private void updatePosition() {
		if(turnMode == TurnMode.LEFT) {
			turnLeft();
		} else if (turnMode == TurnMode.RIGHT) {
			turnRight();
		}
		
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
		
		Position end = new Position(x + dx, y + dy);
		if (bodySegments.isEmpty()) {
			addBodySegment(new BodySegment(new Position(x, y), end, width));
		} else if (generateRandomHole()) {
			holeTmpPos = end;
		} else if (holeLenthCounter > 0) {
			addBodySegment(new BodySegment(holeTmpPos, end, width));
			holeLenthCounter = 0;
		} else {
			Position start = bodySegments.get(bodySegments.size() - 1).getEnd();
			addBodySegment(new BodySegment(start, end, width));
		}
	}
	
	private boolean generateRandomHole() {
		double rand = Math.random();
		double chansMod = 1;
		
		// This determines the length of the hole. Could be something simpler.
		switch (holeLenthCounter) {
		case 1:
			chansMod = 1/CHANS_OF_HOLE;
			break;
		case 2:
			chansMod = 0.5/CHANS_OF_HOLE;
			break;
		case 3:
			chansMod = 0.45/CHANS_OF_HOLE;
			break;
		case 4:
			chansMod = 0.4/CHANS_OF_HOLE;
			break;
		case 5:
			chansMod = 0.4/CHANS_OF_HOLE;
			break;

		default:
			break;
		}
		// Determin if there should be a hole.
		if (rand <= CHANS_OF_HOLE*chansMod) {
			holeLenthCounter++;
			return true;
		}
		return false;
	}
	
	public boolean isGeneratingHole() {
		return holeLenthCounter != 0;
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

		// Using iterator since removing itself from list in an enhanced for
		// loop causes exception
		Iterator<PowerUp> i = powerUps.iterator();
		while(i.hasNext()) {
			PowerUp p = i.next();
			p.update();
			if (!p.isActive()) {
				p.removeEffect(this);
				i.remove();
			}
		}
	}

	public void setWidth(float width) {
		this.width = width;
		head.setDiameter(width);
	}

	public void turnRight() {
		rotationAngleDeg += rotationSpeedDeg;
	}

	public void turnLeft() {
		rotationAngleDeg -= rotationSpeedDeg;
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
	
	public boolean isDead() {
		return dead;
	}
	
	public void kill() {
		if (!immortal) {
			dead = true;
		}
	}
	
	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}
	
	public boolean isImmortal() {
		return immortal;
	}

	public float getRotationAngleDeg() {
		return rotationAngleDeg;
	}

	public float getSpeed() {
		return speed;
	}

	public void setTurnMode(TurnMode turnMode) {
		this.turnMode = turnMode;
	}
}
