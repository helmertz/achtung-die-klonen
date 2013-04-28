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
		startPosition = new Position(20, 50);
		b = new Body(startPosition, 0);
	}
	
	@Test
	public void testGetHead() {
		Head head = b.getHead();
		assertTrue(startPosition.equals(head.getPosition()));
		assertTrue(BodyConstants.DEFAULT_WIDTH == head.getDiameter());
	}
	
	@Test
	public void testGetBodySegments() {
		assertTrue(b.getBodySegments().size() == 0);
		int updates = 10;
		for (int i = 0; i < updates; i++) {
			b.update();
		}
		assertTrue(b.getBodySegments().size() == updates);
	}
	
	@Test
	public void testGetWidht() {
		assertTrue(b.getWidth() == BodyConstants.DEFAULT_WIDTH);
	}
	
	@Test
	public void testAddPowerUp() {
		PlayerPowerUpEffect effect = new FatPowerUp(); 
		float widthBefore = b.getWidth();
		
		b.addPowerUp(effect);
		
		assertTrue(b.getWidth() == 2*widthBefore);
		
	}
	
	@Test
	public void testSetWidth () {
		float width = 54721; 
		b.setWidth(width);
		
		assertTrue(b.getWidth() == width);
	}
	
	@Test
	public void testIsDead() {
		assertFalse(b.isDead());
	}
	
	@Test
	public void testKill() {
		b.kill();
		assertTrue(b.isDead());
	}
	
	@Test
	public void testSetImmortal() {
		b.setImmortal(true);
		assertTrue(b.isImmortal());
		b.kill();
		assertFalse(b.isDead());
		
		b.setImmortal(false);
		assertFalse(b.isImmortal());
		b.kill();
		assertTrue(b.isDead());
	}

	@Test
	public void moveTest() {
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
	
	@Test
	public void hasMovedOneYPixelAtSpeedOne() {
		b.setHeadPosition(new Position(10,10));
		b.setSpeed(1);
		b.setRotationAngleDeg(90);
		
		Position position = b.getPosition();

		float oldX = position.getX();
		float oldY = position.getY();

		// Performs one "game tick".
		b.update();

		float newX = position.getX();
		float newY = position.getY();
		
		assertTrue(newX == oldX && Math.round(newY) == Math.round(oldY + 1));
	}
	
	@Test
	public void hasMovedOneOfEachCoordinateAtSpeedOne() {
		b = BodyFactory.getBody(1000, 1000);
		b.setHeadPosition(new Position(10,10));
		b.setSpeed(1);
		b.setRotationAngleDeg(45);
		
		Position position = b.getPosition();

		float oldX = position.getX();
		float oldY = position.getY();

		// Performs one "game tick".
		b.update();
		
		float newX = position.getX();
		float newY = position.getY();

		// Checks if both x and y has increased by one;
		assertTrue(Math.round(newX) == Math.round(oldX + 1)
				&& Math.round(newY) == Math.round(oldY + 1));
	}

}
