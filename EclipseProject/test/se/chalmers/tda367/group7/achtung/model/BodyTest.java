package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.chalmers.tda367.group7.achtung.model.powerups.FatPowerUp;

public class BodyTest {

	private Body b;
	private Position startPosition;

	@Before
	public void createBody() {
		this.startPosition = new Position(20, 50);
		this.b = new Body(this.startPosition, 0);
	}

	@Test
	public void testGetHead() {
		Head head = this.b.getHead();
		assertTrue(this.startPosition.equals(head.getPosition()));
		assertTrue(Settings.getInstance().getWidth() == head.getDiameter());
	}

	@Test
	public void testGetBodySegments() {
		assertTrue(this.b.getBodySegments().size() == 0);
		int updates = 10;
		int desiredSize = 10;
		for (int i = 0; i < updates; i++) {
			this.b.update();
			if (this.b.isGeneratingHole()) {
				desiredSize--;
			}
		}
		assertTrue(this.b.getBodySegments().size() == desiredSize);
	}

	@Test
	public void testGetWidth() {
		assertTrue(this.b.getWidth() == Settings.getInstance().getWidth());
	}

	@Test
	public void testAddPowerUp() {
		BodyPowerUpEffect effect = new FatPowerUp();
		float widthBefore = this.b.getWidth();

		this.b.addPowerUp(effect);

		assertTrue(this.b.getWidth() == 2 * widthBefore);

	}

	@Test
	public void testSetWidth() {
		float width = 54721;
		this.b.setWidth(width);

		assertTrue(this.b.getWidth() == width);
	}

	@Test
	public void testIsDead() {
		assertFalse(this.b.isDead());
	}

	@Test
	public void testKill() {
		this.b.kill();
		assertTrue(this.b.isDead());
	}

	@Test
	public void testSetImmortal() {
		this.b.setImmortal(true);
		assertTrue(this.b.isImmortal());
		this.b.kill();
		assertFalse(this.b.isDead());

		this.b.setImmortal(false);
		assertFalse(this.b.isImmortal());
		this.b.kill();
		assertTrue(this.b.isDead());
	}

	@Test
	public void moveTest() {
		this.b.setSpeed(1);

		// In degrees, 90 should mean straight up (positive y direction)
		this.b.setRotationAngleDeg(90);

		Position position = this.b.getPosition();

		float oldX = position.getX();
		float oldY = position.getY();

		// Performs one "game tick".
		this.b.update();

		position = this.b.getPosition();

		float newX = position.getX();
		float newY = position.getY();

		// Checks if moves at all
		assertTrue(newX != oldX || newY != oldY);

		// Checks if y is increased by one,
		assertTrue(newX == oldX && Math.round(newY) == Math.round(oldY + 1));

		this.b.setRotationAngleDeg(0);

		this.b.update();

		position = this.b.getPosition();

		newX = position.getX();
		newY = position.getY();

		// Checks if both x and y has increased by one;
		assertTrue(Math.round(newX) == Math.round(oldX + 1)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

	@Test
	public void hasMovedOneYPixelAtSpeedOne() {
		this.b.setHeadPosition(new Position(10, 10));
		this.b.setSpeed(1);
		this.b.setRotationAngleDeg(90);

		Position oldPosition = this.b.getPosition();

		float oldX = oldPosition.getX();
		float oldY = oldPosition.getY();

		// Performs one "game tick".
		this.b.update();

		Position newPosition = this.b.getPosition();

		float newX = newPosition.getX();
		float newY = newPosition.getY();

		assertTrue(Math.round(newX) == Math.round(oldX)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

	@Test
	public void hasMovedOneOfEachCoordinateAtSpeedOne() {
		this.b = BodyFactory.getBody(new Map(1000, 1000));
		this.b.setHeadPosition(new Position(10, 10));
		this.b.setSpeed(1);
		this.b.setRotationAngleDeg(45);

		Position oldPosition = this.b.getPosition();

		float oldX = oldPosition.getX();
		float oldY = oldPosition.getY();

		// Performs one "game tick".
		this.b.update();

		Position newPosition = this.b.getPosition();

		float newX = newPosition.getX();
		float newY = newPosition.getY();

		// Checks if both x and y has increased by one;
		assertTrue(Math.round(newX) == Math.round(oldX + 1)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

}
