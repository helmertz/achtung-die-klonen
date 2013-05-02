package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the physical state of a player.
 */
public class Body {
	private Head head;
	private List<BodySegment> bodySegments;
	private List<PowerUp> powerUps = new ArrayList<PowerUp>();
	
	private TurnMode turnMode;
	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg; // the angle the snake is turning with.
	private float width;
	private boolean dead;
	private boolean immortal;
	private boolean sharpTurnsActivated;
	private boolean generatingBodySegments;
	private Position prevPosition;
	private BodySegment lastSegment;
	private boolean makeHole;
	private int holeCount;

	public enum TurnMode {
		LEFT, RIGHT, FORWARD
	}

	public Body(Position position, float rotation) {
		dead = false;
		immortal = false;
		sharpTurnsActivated = false;
		width = BodyConstants.DEFAULT_WIDTH;
		speed = BodyConstants.DEFAULT_SPEED;
		rotationAngleDeg = rotation;
		rotationSpeedDeg = BodyConstants.DEFAULT_ROTATION_SPEED;
		generatingBodySegments = true;

		// Width of the body is the same as the diameter of the head.
		head = new Head(position, width);
		bodySegments = new ArrayList<BodySegment>();
		prevPosition = position;
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
		if (this.head == null) {
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
		prevPosition = head.getPosition();
		float x = prevPosition.getX();
		float y = prevPosition.getY();

		// Calculates the new delta position.
		float dx = (float) (speed * Math.cos(Math.toRadians(rotationAngleDeg)));
		float dy = (float) (speed * Math.sin(Math.toRadians(rotationAngleDeg)));

		Position newPosition = new Position(x + dx, y + dy);

		head.setPosition(newPosition);

		// Create a new body segment and add to body segment list.

		holeUpdate();
		
		if (!makeHole) {
			createBodySegment();
			holeCount = 0;
		} else {
			holeCount++;
			// Set to null so that no upcoming segment will be connected over
			// the hole
			lastSegment = null;
		}
	}

	private void holeUpdate() {
		makeHole = !generatingBodySegments
				|| !makeHole && Math.random() < BodyConstants.CHANCE_OF_HOLE
				// If a hole was made the previous time, it's often more likely
				// a hole still will be made.
				// The subtraction makes it so holes will be longer than the
				// number subtracted.
				|| makeHole && Math.random() > (holeCount - 2) / 10d;
	}

	private void createBodySegment() {
		// lastSegment is null after a hole has been made
		BodySegment segment;
		if (lastSegment != null) {
			// TODO handle differently to properly connect new segment to last
			segment = new BodySegment(prevPosition, head.getPosition(), width);
		} else {
			segment = new BodySegment(prevPosition, head.getPosition(), width);
		}
		addBodySegment(segment);

		// stores so next one can be connected to this
		lastSegment = segment;
	}

	private void doTurn() {

		if (turnMode == TurnMode.LEFT) {
			turnLeft();
		} else if (turnMode == TurnMode.RIGHT) {
			turnRight();
		}

		if (sharpTurnsActivated) {
			turnMode = TurnMode.FORWARD;
		}

	}

	public boolean isGeneratingHole() {
		return makeHole;
	}

	public float getWidth() {
		return width;
	}

	/**
	 * Adds a power-up to the player. When the player collides with a PowerUpEntity 
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
		while (i.hasNext()) {
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

	public void setLastPosition(Position pos) {
		prevPosition = pos;
	}

	public Position getLastPosition() {
		return prevPosition;
	}
}
