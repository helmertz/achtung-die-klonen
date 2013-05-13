package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	
	private Game game;
	
	@Before
	public void setupGame() {
		this.game = new Game();
	}
	
	@Test
	public void testIdIncreasing() {
		Player p = game.getNewPlayer("", null);
		int firstId = p.getId();
		Player p1 = game.getNewPlayer("", null);
		
		assertTrue(p1.getId() == firstId+1);
	}
	
	@Test
	public void testErrorIfNoBody() {
		Player p = game.getNewPlayer("", null);
		p.setBody(null);
		try {
			p.update();
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().equals("No body is set for the player"));
		}
	}
	
	@Test
	public void testAddPoints() {
		Player p = game.getNewPlayer("", null);
		p.addPoints(5);
		
		assertTrue(p.getPoints() == 5);
	}
	
}
