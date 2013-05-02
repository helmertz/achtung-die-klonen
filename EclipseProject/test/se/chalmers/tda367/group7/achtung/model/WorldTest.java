package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WorldTest {
	@Test
	public void collisionTest() {
		Round world = new Round(new Map(1000, 1000));

		Player p1 = new Player("Player 1", null);
		Body b1 = new Body(new Position(50.5f, 50.5f), 0);
		b1.setSpeed(1);
		p1.setBody(b1);
		Player p2 = new Player("Player 2", null);
		Body b2 = new Body(new Position(51, 51), 180);
		b2.setSpeed(1);
		p2.setBody(b2);

		world.addPlayer(p1);
		world.addPlayer(p2);

		assertTrue(!p1.getBody().isDead() && !p2.getBody().isDead());

		world.update();
		System.out.println(b1.getPosition().getX());
		System.out.println(b1.getPosition().getY());
		System.out.println(b2.getPosition().getX());
		System.out.println(b2.getPosition().getY());
		assertTrue(p1.getBody().isDead() || p2.getBody().isDead());


	}

}
