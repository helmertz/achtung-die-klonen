package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the data for a game currenty playing.
 */
public class World {

	private int width;
	private int heigth;

	private List<Player> players;
	private List<Player> activePlayers; // should only contain non dead players.
	// when a player dies it is removed from acitvePlayers.
	
	public World(int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
		
		players = new ArrayList<>();
		activePlayers = new ArrayList<>();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void update() {
		if (isRoundActive()) {
			for (Player player : activePlayers) {
				player.update();
			}
			
			// TODO - implement collision detection and random power up placement.
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
			
			for (Player player : activePlayers) {
				// TODO - fix so that startpoints is different, and not too close, for each snake.
				player.createNewBody(0, 0);
			}
		}
	}
	
	/**
	 * @return true if a player has won.
	 */
	public boolean isGameOver() {

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
