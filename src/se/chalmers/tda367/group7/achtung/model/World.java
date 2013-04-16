package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the data for a game currently playing.
 */
public class World {

	private final int width;
	private final int height;

	private List<Player> players;
	private List<Player> activePlayers; // should only contain non dead players.
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		
		players = new ArrayList<>();
		activePlayers = new ArrayList<>();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void update() {
		if (isRoundActive()) {
			updatePlayers();
			
			// TODO - implement collision detection and random power up placement.
		}
	}

	private void updatePlayers() {
		for (Player player : activePlayers) {
			player.update();
		}
	}
	
	/**
	 * @return true if there are players still alive
	 */
	public boolean isRoundActive() {
		return activePlayers.size() > 0;
	}
	
	/**
	 * Starts a new round if there is currently no active round
	 */
	public void startRound() {
		if (!isRoundActive()) {
			activePlayers.addAll(players);
			
			createPlayersInWorld();
		}
	}

	private void createPlayersInWorld() {
		for (Player player : activePlayers) {
			// TODO - fix so that startpoints is different, and not too close, for each snake.
			player.createNewBody(0, 0);
		}
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
		return 10*(players.size() -1);
	}
}
