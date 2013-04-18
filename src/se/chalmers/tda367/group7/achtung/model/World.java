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

	private int deadPlayers = 0; 

	public World(int width, int height) {
		this.width = width;
		this.height = height;

		players = new ArrayList<>();

		// Hardcoded in at the moment
		Player p1 = new Player("Player 1",
				se.chalmers.tda367.group7.achtung.rendering.Color.WHITE);
		p1.setBody(BodyFactory.getBody(1000, 1000));
		addPlayer(p1);
		Player p2 = new Player("Player 2",
				se.chalmers.tda367.group7.achtung.rendering.Color.WHITE);
		p2.setBody(BodyFactory.getBody(1000, 1000));
		addPlayer(p2);
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public void update() {
		if (isRoundActive()) {
			updatePlayers();
		}
		// TODO - implement collision detection and random power up placement.
	}

	private void updatePlayers() {
		for (Player player : players) {
			player.update();
			
			if (doesPlayerCollide(player)) {
				killPlayerAndDistributePoints(player);
			}
		}
	}

	private void killPlayerAndDistributePoints(Player player) {
		player.getBody().kill();
		deadPlayers++;
		int livingPlayers = 0;
		for (Player p : players) {
			if (!p.getBody().isDead()) {
				livingPlayers++;
				p.addPoints(1);
			}
		}
		
		// Checks if one player remains, and kills him as well
		if(livingPlayers < 2) {
			for (Player p : players) {
				if (!p.getBody().isDead()) {
					p.getBody().kill();
				}
			}
		}		
	}

	private boolean doesPlayerCollide(Player player) {
		Body currentBody = player.getBody();
		
		Position pos = currentBody.getPosition();
		
		// Checks if out of bounds
		if(pos.getX() < 0 || pos.getX() > width || pos.getY() < 0 || pos.getY() > height) {
			return true;
		}

		// Create coordinates for the last bodysegment
		// of the player being checked
		List<BodySegment> playerSegments = currentBody.getBodySegments();
		if (playerSegments.isEmpty()) {
			return false;
		}
		BodySegment lastSeg = playerSegments.get(playerSegments.size() - 1);
		
		BodySegment segBefLast = null;
		if (playerSegments.size() > 1) {
			segBefLast = playerSegments.get(playerSegments.size() - 2);
		}
		// Loop through all other players
		for (Player otherPlayer : players) {			
			
			List<BodySegment> otherBodySegments = otherPlayer.getBody()
					.getBodySegments();
			
			// Loop through all body segments of the other player
			// being checked and see if a collision has happened
			// with either of these
			for (BodySegment seg : otherBodySegments) {
				
				// Allows collision with itself and the one before
				if (lastSeg == seg || segBefLast == seg) {
					continue;
				}
				
				if(lastSeg.getHitBox().intersects(seg.getHitBox())) {
					System.out.println("intersection!!!");
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * @return true if there are players still alive
	 */
	public boolean isRoundActive() {
		return deadPlayers < players.size() - 1;
	}

	/**
	 * Starts a new round if there is currently no active round
	 */
	public void startRound() {
		if (!isRoundActive()) {
			deadPlayers = 0;
			createPlayerBodys();
		}
	}

	private void createPlayerBodys() {
		for (Player player : players) {
			// TODO - fix so that startpoints is different, and not too close,
			// for each snake.
			player.setBody(BodyFactory.getBody(width, height));
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
		return 10 * (players.size() - 1);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
