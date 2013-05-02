package se.chalmers.tda367.group7.achtung.model;

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
	
	public Game() {
		
		rounds = new ArrayList<>();
		map = new Map(1500, 1500);
		

		// Hardcoded in at the moment
		Player p1 = new Player("Player 1", Color.BLUE);
		addPlayer(p1);
		Player p2 = new Player("Player 2", Color.RED);
		addPlayer(p2);
		
		Player p3 = new Player("Player 3", Color.GREEN);
		addPlayer(p3);
	}
	
	public void update() {
		
	}
	
	public void newRound() {
		
		if (currentRound == null) {
			currentRound = new Round(map, players);
		} else if (!currentRound.isRoundActive()) {
			
			rounds.add(currentRound);
			currentRound = new Round(map, players);
		}
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
	public boolean isGameOver() {
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
}
