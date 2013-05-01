package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the physical state of a player.
 */
public class Body {
	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg;	// the angle the snake is turning with.

	private float width;
	private boolean dead;
	private boolean immortal;
	private int holeLengthCount;
	private Position holeTmpPos;
	
	private Head head;
	private List<BodySegment> bodySegments;
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	private TurnMode turnMode;
	private boolean sharpTurnsActivated;
	
	private boolean generatingBodySegments;
	
	public enum TurnMode {
		LEFT, RIGHT, FORWARD
	}
	
	public Body (Position position, float rotation) {
		dead = false;
		immortal = false;
		sharpTurnsActivated = false;
		holeLengthCount = 0;
		width = BodyConstants.DEFAULT_WIDTH;
		speed = BodyConstants.DEFAULT_SPEED;
		rotationAngleDeg = rotation;
		rotationSpeedDeg = BodyConstants.DEFAULT_ROTATION_SPEED;
		generatingBodySegments = true;
		
		// Width of the body is the same as the diameter of the head.
		head = new Head(position, width);
		bodySegments = new ArrayList<BodySegment>();
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
		doTurn();
		
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
		
		if(generatingBodySegments) {
			createBodySegment(x, y, end);
		}
	}

	private void createBodySegment(float x, float y, Position end) {
		if (bodySegments.isEmpty()) {
			addBodySegment(new BodySegment(new Position(x, y), end, width));
		} else if (generateRandomHole()) {
			holeTmpPos = end;
		} else if (holeLengthCount > 0) {
			addBodySegment(new BodySegment(holeTmpPos, end, width));
			holeLengthCount = 0;
		} else {
			Position start = bodySegments.get(bodySegments.size() - 1).getEnd();
			addBodySegment(new BodySegment(start, end, width));
		}
	}
	
	private void doTurn() {
		
		if(turnMode == TurnMode.LEFT) {
			turnLeft();
		} else if (turnMode == TurnMode.RIGHT) {
			turnRight();
		}

		if (sharpTurnsActivated) {
			turnMode = TurnMode.FORWARD;
		}
		
	}
	
	private boolean generateRandomHole() {
		double rand = Math.random();
		double chanceMod = 1;
		
		// This determines the length of the hole. Could be something simpler.
		if (holeLengthCount == 1) {
			chanceMod = 1/BodyConstants.CHANCE_OF_HOLE;
		} else if (holeLengthCount > 1) {
			chanceMod = 0.5/BodyConstants.CHANCE_OF_HOLE;
		}
		// Determine if there should be a hole.
		if (rand <= BodyConstants.CHANCE_OF_HOLE*chanceMod) {
			holeLengthCount++;
			return true;
		}
		return false;
	}
	
	public boolean isGeneratingHole() {
		return holeLengthCount != 0;
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
	public void setRotationSpeedDeg(float angle) {
		rotationSpeedDeg = angle;
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
	
	public float getRotationSpeedDeg() {
		return rotationSpeedDeg;
	}

	public float getSpeed() {
		return speed;
	}

	public void setTurnMode(TurnMode turnMode) {
		this.turnMode = turnMode;	
	}
	
	public void setSharpTurns(boolean sharpTurns) {
		this.sharpTurnsActivated = sharpTurns;
	}

	public boolean isGeneratingBodySegments() {
		return generatingBodySegments;
	}

	public void setGeneratingBodySegments(boolean generatingBodySegments) {
		this.generatingBodySegments = generatingBodySegments;
	}
}
