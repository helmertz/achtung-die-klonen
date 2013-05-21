package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoundTest {

	private Game game;
	private Round round;
	private Player p1;
	private Player p2;

	@Before
	public void setupGame() {
		this.game = new Game();
		this.p1 = game.createNewPlayer("", null);
		this.p2 = game.createNewPlayer("", null);

		this.game.newRound();
		this.round = game.getCurrentRound();
		Settings settings = Settings.getInstance();
		settings.resetToDefaults();
		settings.setPowerUpChance(0);
		settings.setChanceOfHole(0);
	}

	@Test
	public void headOnCollisionTest() {
		setupGame();
		float p1StartX = 10;
		float p2StartX = 800;

		float oneBeforeCollisionPoint = (Math.abs(p2StartX - p1StartX) / 2);

		p1.setBody(new Body(new Position(p1StartX, 50), 0));
		p1.getBody().setSpeed(1);
		p2.setBody(new Body(new Position(p2StartX, 50), 180));
		p2.getBody().setSpeed(1);

		for (int i = 0; i < oneBeforeCollisionPoint; i++) {
			round.update();
		}

		assertFalse(p1.getBody().isDead() || p2.getBody().isDead());

		// Now we need only one more update for them to collide
		round.update();
		assertTrue(p1.getBody().isDead() || p2.getBody().isDead());
	}

	@Test
	public void mirrorXPositionThroughWallTest() {
		setupGame();
		float mapWidth = round.getMap().getWidth();
		
		// This lets players pass through walls
		this.round.setWallsActive(false);

		// These can be set to anything as long as player passes through wall
		// once
		int p1StartX = 50;
		int iterations = 300;

		float actualMirroredPosition = mapWidth - (iterations - p1StartX) + 1;

		p1.setBody(new Body(new Position(p1StartX, 5), 180));
		p1.getBody().setSpeed(1);
		p2.getBody().setSpeed(0);

		for (int i = 0; i < iterations - 1; i++) {
			round.update();
		}

		// One update before expected mirrored position, test fails
		assertFalse(p1.getBody().getPosition().getX() == actualMirroredPosition);

		// After one update, it should be correct
		round.update();
		assertTrue(p1.getBody().getPosition().getX() == actualMirroredPosition);
	}
	
	@Test
	public void mirrorYPositionThroughWallTest() {
		setupGame();
		float mapHeight = round.getMap().getHeight();
		
		// This lets players pass through walls
		this.round.setWallsActive(false);

		// These can be set to anything as long as player passes through wall
		// once
		int p1StartY = 50;
		int iterations = 300;

		float actualMirroredPosition = mapHeight - (iterations - p1StartY) + 1;

		p1.setBody(new Body(new Position(50, p1StartY), 270));
		p1.getBody().setSpeed(1);
		p2.getBody().setSpeed(0);

		for (int i = 0; i < iterations - 1; i++) {
			round.update();
		}

		// One update before expected mirrored position, test fails
		assertFalse(p1.getBody().getPosition().getY() == actualMirroredPosition);

		// After one update, it should be correct
		round.update();
		assertTrue(p1.getBody().getPosition().getY() == actualMirroredPosition);
	}
	
	@Test
	public void collisionWithWall() {
		setupGame();
		int p1StartX = 600;
		
		p1.setBody(new Body(new Position(p1StartX, 15), 180));
		p1.getBody().setSpeed(1);
		p2.getBody().setSpeed(0);
		
		float oneBeforeCollision = p1StartX - p1.getBody().getWidth() + 1;
		
		for (int i = 0; i < oneBeforeCollision; i++) {
			round.update();
		}
		
		// This is one update before colliding
		assertFalse(p1.getBody().isDead());
		
		// Only needs one more update to collide
		round.update();
		assertTrue(p1.getBody().isDead());
	}
}