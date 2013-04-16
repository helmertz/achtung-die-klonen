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
	private List<Player> activePlayers; // should only non dead players
	
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
		for (Player player : activePlayers) {
			player.update();
		}
		
		// TODO - implement collision detection and random power up placement.
	}
	
	public boolean isRoundActive() {
		// TODO - this should return true only when a round is active
		return false;
	}
	
	/**
	 * Starts a new round if there is currently no active round
	 */
	public void startRound() {
		if (!isRoundActive()) {
			activePlayers.addAll(players);
			// TODO - create new player body
		}
	}
	
	public boolean isGameOver() {
		// TODO - return true when on of the players has reached the goal points.
		return true;
	}
	
	/**
	 * Returns the number of points required to win the game.
	 */
	public int getGoalPoints() {
		return 10*(players.size() -1);
	}
}
