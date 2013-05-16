package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for setting up everything before starting the game.
 */
public class Game {

	private final List<Round> rounds;
	private final List<Player> players;
	private final Map map;
	private Round currentRound;
	private Player gameWinner;

	private final PropertyChangeSupport pcs;
	private final float powerUpChance;

	public Game() {
		this.powerUpChance = Settings.getInstance().getPowerUpChance();
		this.rounds = new ArrayList<>();
		this.players = new ArrayList<>();
		this.map = new Map(1500, 1500);

		this.pcs = new PropertyChangeSupport(this);

	}

	public Player createNewPlayer(String name, Color color) {
		Player player = new Player(name, color);
		this.players.add(player);
		return player;
	}

	public void update() {
		this.currentRound.update();
		sortPlayersByPoints();
	}

	private void sortPlayersByPoints() {
		Collections.sort(this.players, new SortByPointsDescending());
	}

	public void newRound() {
		this.map.setColor(Map.DEFAULT_COLOR);
		this.currentRound = new Round(this.map, this.players,
				this.powerUpChance);
		this.rounds.add(this.currentRound);
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
		if (this.currentRound != null) {
			return !this.currentRound.isRoundActive() && playerHasGoalPoints();
		} else {
			return false;
		}
	}

	private boolean playerHasGoalPoints() {
		int goalPoints = getGoalPoints();

		for (Player player : this.players) {
			if (player.getPoints() >= goalPoints) {
				this.gameWinner = player;
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

	public Player getGameWinner() {
		return this.gameWinner;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
}
