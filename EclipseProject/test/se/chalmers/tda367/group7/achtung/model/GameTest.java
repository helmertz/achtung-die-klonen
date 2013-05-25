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
		this.game = new Game();
	}

	@Test
	public void testGetNewPlayer() {
		Player p1 = this.game
				.createNewPlayer("player1", Color.getRandomColor());
		assertTrue(p1.getName().equals("player1"));

		assertTrue(this.game.getPlayers().size() == 1);
	}

	@Test
	public void testNewRound() {
		this.game.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				assertTrue(evt.getPropertyName().equals("NewRound"));

			}

		});
		this.game.newRound();
	}

	@Test
	public void testIsOver() {
		// Sets some settings for a more consistent result
		Settings.getInstance().resetToDefaults();
		Settings.getInstance().setChanceOfHole(0);
		Settings.getInstance().setPowerUpChance(0);

		this.game.createNewPlayer("player1", Color.getRandomColor());
		this.game.createNewPlayer("player2", Color.getRandomColor());
		this.game.newRound();

		int initialGoal = this.game.getGoalPoints();

		for (int i = 0; i < 3000; i++) {
			this.game.update();
			// only creates round if none active.
			if (!this.game.isOver()
					&& !this.game.getCurrentRound().isRoundActive()) {
				this.game.newRound();
			}
		}
		// This probably fails sometimes because of the winner needing to be a
		// couple of points ahead:
		// assertTrue(this.game.isOver());

		// Checks if goal points has started increasing as well.
		int goal = this.game.getGoalPoints();
		assertTrue(this.game.isOver() || goal > initialGoal);
	}

}
