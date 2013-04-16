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
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		
		players = new ArrayList<>();
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
		for (Player player : players) {
			player.update();
		}
	}
	
	/**
	 * @return true if there are players still alive
	 */
	public boolean isRoundActive() {
		int deadPlayers = 0;
		for (Player player : players) {
			if (player.getBody().isDead()) {
				deadPlayers++;
			}
		}
		return deadPlayers == players.size();
	}
	
	/**
	 * Starts a new round if there is currently no active round
	 */
	public void startRound() {
		if (!isRoundActive()) {
			createPlayerBodys();
		}
	}

	private void createPlayerBodys() {
		for (Player player : players) {
			// TODO - fix so that startpoints is different, and not too close, for each snake.
			
			player.setBody(new Body(new Position(0, 0), 0));
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
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
