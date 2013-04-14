package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.lwjgl.util.vector.Vector2f;

public class PlayerTest {

	@Test
	public void moveTest() {
		Player p = new Player("Test Player", Color.WHITE, 20, 50);
		p.setSpeed(1);

		// In degrees, 90 should mean straight up (positive y direction)
		p.setRotation(90);

		Vector2f position = p.getBody().getHead().getPosition();

		float oldX = position.getX();
		float oldY = position.getY();

		// Performs one "game tick".
		p.update();

		float newX = position.getX();
		float newY = position.getY();

		// Checks if moves at all
		assertTrue(newX != oldX || newY != oldY);

		// Checks if y is increased by one,
		assertTrue(newX == oldX && Math.round(newY) == Math.round(oldY + 1));

		p.setRotation(0);

		p.update();

		newX = position.getX();
		newY = position.getY();

		// Checks if both x and y has increased by one;
		assertTrue(Math.round(newX) == Math.round(oldX + 1)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

}
