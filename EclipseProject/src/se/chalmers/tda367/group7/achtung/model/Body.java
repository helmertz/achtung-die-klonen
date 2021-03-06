package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the physical state of a player.
 */
public class Body {
	private final Head head;
	private final List<BodySegment> bodySegments;
	private final List<PowerUp<BodyPowerUpEffect>> powerUps = new ArrayList<PowerUp<BodyPowerUpEffect>>();
	private TurnMode turnMode;
	private float speed;
	private float rotationAngleDeg; // the angle the snake is facing.
	private float rotationSpeedDeg; // the angle the snake is turning with.
	private float width;
	private boolean dead;
	private boolean immortal;
	private boolean sharpTurnsActivated;
	private boolean invertedControls;
	private boolean segmentGenerationEnabled;
	private Position prevPosition;
	private BodySegment previousSegment;
	private boolean makeHole;
	private int holeCount;
	private Color color;
	private final Settings settings;

	public enum TurnMode {
		LEFT, RIGHT, FORWARD
	}

	public Body(Position position, float rotation) {
		this.settings = Settings.getInstance();
		this.dead = false;
		this.immortal = false;
		this.sharpTurnsActivated = false;
		this.width = this.settings.getWidth();
		this.speed = this.settings.getSpeed();
		this.rotationAngleDeg = rotation;
		this.rotationSpeedDeg = this.settings.getRotationSpeed();
		this.segmentGenerationEnabled = true;

		// Width of the body is the same as the diameter of the head.
		this.head = new Head(position, this.width);
		this.bodySegments = new ArrayList<BodySegment>();
		this.prevPosition = position;
	}

	public Head getHead() {
		return this.head;
	}

	public List<BodySegment> getBodySegments() {
		return this.bodySegments;
	}

	public void addBodySegment(BodySegment bodySegment) {
		this.bodySegments.add(bodySegment);
	}

	public void setHeadPosition(Position position) {
		this.head.setPosition(position);
	}

	public void update() {
		updatePowerUps();
		updateHeadPosition();
		updateSegments();
	}

	private void updateSegments() {
		// Create a new body segment and add to body segment list.
		holeUpdate();

		if (!this.makeHole) {
			createBodySegment();
			this.holeCount = 0;
		} else {
			this.holeCount++;
			// Set to null so that no upcoming segment will be connected over
			// the hole
			this.previousSegment = null;
		}
	}

	private void updateHeadPosition() {
		doTurn();

		// Update head with delta positions
		this.prevPosition = this.head.getPosition();
		float x = this.prevPosition.getX();
		float y = this.prevPosition.getY();

		// Calculates the new delta position.
		float dx = (float) (this.speed * Math.cos(Math
				.toRadians(this.rotationAngleDeg)));
		float dy = (float) (this.speed * Math.sin(Math
				.toRadians(this.rotationAngleDeg)));

		Position newPosition = new Position(x + dx, y + dy);

		this.head.setPosition(newPosition);
	}

	private void holeUpdate() {
		this.makeHole = !this.segmentGenerationEnabled
				|| !this.makeHole
				&& Math.random() < this.settings.getChanceOfHole()
				// If a hole was made the previous time, it's often more likely
				// a hole still will be made.
				// The subtraction makes it so holes will be longer than the
				// number subtracted.
				|| this.makeHole
				&& Math.random() > (this.holeCount - this.width / 5)
						/ (this.width * 0.8f);
	}

	private void createBodySegment() {
		// previousSegment is null after a hole has been made
		BodySegment segment;
		if (this.previousSegment != null) {
			segment = new BodySegment(this.previousSegment,
					this.head.getPosition(), this.width);
		} else {
			segment = new BodySegment(this.prevPosition,
					this.head.getPosition(), this.width);
		}
		addBodySegment(segment);

		// stores so next one can be connected to this
		this.previousSegment = segment;
	}

	private void doTurn() {

		if (this.turnMode == TurnMode.LEFT) {
			if (!this.invertedControls) {
				turnLeft();
			} else {
				turnRight();
			}
		} else if (this.turnMode == TurnMode.RIGHT) {
			if (!this.invertedControls) {
				turnRight();
			} else {
				turnLeft();
			}
		}

		if (this.sharpTurnsActivated) {
			this.turnMode = TurnMode.FORWARD;
		}

	}

	public boolean isGeneratingHole() {
		return this.makeHole;
	}

	public float getWidth() {
		return this.width;
	}

	/**
	 * Adds a power-up to the player. When the player collides with a
	 * PowerUpEntity it will receive an effect using this method.
	 * 
	 * @param effect
	 *            - the effect to add to the player
	 */
	public void addPowerUp(BodyPowerUpEffect effect) {

		if (!effect.isStackable()) {
			for (PowerUp<BodyPowerUpEffect> powerUp : this.powerUps) {
				if (powerUp.getEffect().getClass() == effect.getClass()) {
					powerUp.resetTimer();
					return;
				}
			}
		}

		effect.applyEffect(this);
		this.powerUps.add(new PowerUp<>(effect));
	}

	/**
	 * Updates all power ups, removes them if inactive.
	 */
	private void updatePowerUps() {

		// Using iterator since removing itself from list in an enhanced for
		// loop causes exception
		Iterator<PowerUp<BodyPowerUpEffect>> i = this.powerUps.iterator();
		while (i.hasNext()) {
			PowerUp<BodyPowerUpEffect> p = i.next();
			p.update();
			if (!p.isActive()) {
				p.getEffect().removeEffect(this);
				i.remove();
			}
		}
	}

	public void setWidth(float width) {
		this.width = width;
		this.head.setDiameter(width);
	}

	public void turnRight() {
		this.rotationAngleDeg += this.rotationSpeedDeg;
	}

	public void turnLeft() {
		this.rotationAngleDeg -= this.rotationSpeedDeg;
	}

	public void setRotationAngleDeg(float angle) {
		this.rotationAngleDeg = angle;
	}

	public void setRotationSpeedDeg(float angle) {
		this.rotationSpeedDeg = angle;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Position getPosition() {
		return this.head.getPosition();
	}

	public boolean isDead() {
		return this.dead;
	}

	public void kill() {
		if (!this.immortal) {
			this.dead = true;
		}
	}

	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}

	public boolean isImmortal() {
		return this.immortal;
	}

	public float getRotationAngleDeg() {
		return this.rotationAngleDeg;
	}

	public float getRotationSpeedDeg() {
		return this.rotationSpeedDeg;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setTurnMode(TurnMode turnMode) {
		this.turnMode = turnMode;
	}

	public void setSharpTurns(boolean sharpTurns) {
		this.sharpTurnsActivated = sharpTurns;
	}

	public void setInvertedControls(boolean invertedControls) {
		this.invertedControls = invertedControls;
	}

	public void toggleInvertedControls() {
		this.invertedControls = !this.invertedControls;
	}

	public boolean isSegmentGenerationEnabled() {
		return this.segmentGenerationEnabled;
	}

	public void setSegmentGenerationEnabled(boolean generatingBodySegments) {
		this.segmentGenerationEnabled = generatingBodySegments;
	}

	public void setLastPosition(Position pos) {
		this.prevPosition = pos;
	}

	public Position getLastPosition() {
		return this.prevPosition;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public List<PowerUp<BodyPowerUpEffect>> getPowerUps() {
		return this.powerUps;
	}

	public void setPreviousSegment(BodySegment previousSegment) {
		this.previousSegment = previousSegment;
	}

	public boolean hasInvertedControls() {
		return this.invertedControls;
	}

	public TurnMode getTurnMode() {
		return this.turnMode;
	}
}
