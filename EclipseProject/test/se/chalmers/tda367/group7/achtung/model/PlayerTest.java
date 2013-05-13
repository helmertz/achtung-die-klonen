package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	@Test
	public void testIdIncreasing() {
		Player p = new Player("", null);
		int firstId = p.getId();
		Player p1 = new Player("", null);
		
		assertTrue(p1.getId() == firstId+1);
	}
	
	@Test
	public void testErrorIfNoBody() {
		Player p = new Player("", null);
		p.setBody(null);
		try {
			p.update();
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().equals("No body is set for the player"));
		}
	}
	
	@Test
	public void testAddPoints() {
		Player p = new Player("", null);
		p.addPoints(5);
		
		assertTrue(p.getPoints() == 5);
	}
	
}
