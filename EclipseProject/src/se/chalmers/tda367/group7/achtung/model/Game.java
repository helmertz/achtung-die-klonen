package se.chalmers.tda367.group7.achtung.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for setting up everything before starting the game.
 */
public class Game {
	
	private List<Round> rounds;
	private List<Player> players;
	private Map map;
	private Round currentRound;
	
	private PropertyChangeSupport pcs;
	
	public Game() {
		
		rounds = new ArrayList<>();
		players = new ArrayList<>();
		map = new Map(1500, 1500);
		
		pcs = new PropertyChangeSupport(this);
		

		// Hardcoded in at the moment
		Player p1 = new Player("Player 1", Color.BLUE);
		addPlayer(p1);
		Player p2 = new Player("Player 2", Color.RED);
		addPlayer(p2);
		
		Player p3 = new Player("Player 3", Color.GREEN);
		addPlayer(p3);
	}
	
	public void update() {
		currentRound.update();
	}
	
	public void newRound() {
		
		if (currentRound == null) {
			currentRound = new Round(map, players);
		} else if (!currentRound.isRoundActive()) {
			currentRound = new Round(map, players);
			
			rounds.add(currentRound);
		}

		pcs.firePropertyChange("NewRound", false, true);
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * @return true if a player has won.
	 */
	public boolean isOver() {
		return playerHasGoalPoints();
	}

	private boolean playerHasGoalPoints() {
		int goalPoints = getGoalPoints();

		for (Player player : players) {
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
		return 10 * (players.size() - 1);
	}

	public Round getCurrentRound() {
		return currentRound;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
}
