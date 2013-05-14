package se.chalmers.tda367.group7.achtung.model;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;
	
	@Before
	public void createGame() {
		game = new Game();
	}
	
	@Test
	public void testGetNewPlayer() {
		Player p1 = game.createNewPlayer("player1", Color.getRandomColor());
		assertTrue(p1.getName().equals("player1"));
		
		assertTrue(game.getPlayers().size() == 1);
	}
	
	@Test
	public void testNewRound() {
		game.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				assertTrue(evt.getPropertyName().equals("NewRound"));
				
			}
			
		});
		game.newRound();
	}
	
	@Test
	public void testIsOver() {
		game.createNewPlayer("player1", Color.getRandomColor());
		game.createNewPlayer("player2", Color.getRandomColor());
		game.newRound();
		for (int i = 0; i < 3000; i++) {
			game.update();
			// only creates round if none active.
			game.newRound();
		}
		
		assertTrue(game.isOver());
	}

}
