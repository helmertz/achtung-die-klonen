package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for setting up everything before starting the game.
 */
public class Game {

	private final List<Round> rounds;
	private final List<Player> players;
	private final Map map;
	private Round currentRound;

	private final PropertyChangeSupport pcs;

	public Game() {

		this.rounds = new ArrayList<>();
		this.players = new ArrayList<>();
		this.map = new Map(1500, 1500);

		this.pcs = new PropertyChangeSupport(this);

		// Hardcoded in at the moment
		Player p1 = new Player("Player 1", Color.BLUE);
		addPlayer(p1);
		Player p2 = new Player("Player 2", Color.RED);
		addPlayer(p2);

		Player p3 = new Player("Player 3", Color.GREEN);
		addPlayer(p3);
	}

	public void update() {
		this.currentRound.update();
	}

	public void newRound() {

		if (this.currentRound == null || !this.currentRound.isRoundActive()) {
			this.currentRound = new Round(this.map, this.players);
			this.rounds.add(this.currentRound);
		}

		this.pcs.firePropertyChange("NewRound", false, true);
	}

	public void addPlayer(Player p) {
		this.players.add(p);
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * @return true if a player has won.
	 */
	public boolean isOver() {
		return playerHasGoalPoints();
	}

	private boolean playerHasGoalPoints() {
		int goalPoints = getGoalPoints();

		for (Player player : this.players) {
			if (player.getPoints() >= goalPoints) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the number of points required to win the game.
	 */
	public int getGoalPoints() {
		return 10 * (this.players.size() - 1);
	}

	public Round getCurrentRound() {
		return this.currentRound;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
}
