package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
	}

	@Test
	public void headOnCollisionTest() {
		float p1StartX = 10;
		float p2StartX = 800;
		
		float oneBeforeCollisionPoint = (Math.abs(p2StartX - p1StartX) / 2);
		
		p1.setBody(new Body(new Position(10, 50), 0));
		p1.getBody().setSpeed(1);
		p2.setBody(new Body(new Position(800, 50), 180));
		p2.getBody().setSpeed(1);

		for (int i = 0; i < oneBeforeCollisionPoint; i++) {
			round.update();
		}
		
		assertTrue(!(p1.getBody().isDead() || p2.getBody().isDead()));
		
		// Now we need only one more update for them to collide
		round.update();
		assertTrue(p1.getBody().isDead() || p2.getBody().isDead());
	}
}