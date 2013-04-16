package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BodyTest {

	@Test
	public void moveTest() {
		Body b = new Body(new Position(20, 50));
		b.setSpeed(1);

		// In degrees, 90 should mean straight up (positive y direction)
		b.setRotationAngleDeg(90);

		Position position = b.getPosition();

		float oldX = position.getX();
		float oldY = position.getY();

		// Performs one "game tick".
		b.update();

		float newX = position.getX();
		float newY = position.getY();

		// Checks if moves at all
		assertTrue(newX != oldX || newY != oldY);

		// Checks if y is increased by one,
		assertTrue(newX == oldX && Math.round(newY) == Math.round(oldY + 1));

		b.setRotationAngleDeg(0);

		b.update();

		newX = position.getX();
		newY = position.getY();

		// Checks if both x and y has increased by one;
		assertTrue(Math.round(newX) == Math.round(oldX + 1)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

}
