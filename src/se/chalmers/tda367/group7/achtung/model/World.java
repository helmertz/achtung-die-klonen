package se.chalmers.tda367.group7.achtung.model;

import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the data for a game currently playing.
 */
public class World {

	private final int width;
	private final int height;

	private List<Player> players;
	
	private CollisionDetector collisionDetector = new CollisionDetector();
	
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
			detectCollision();
		}
	}
	
	private void detectCollision() {
		// Loop through all players, check if their head is
		// in the same position as another player's head or
		// any of their body segments. If so, a collision
		// has happened
		
		for(Player player : players) {
			Body currentBody = player.getBody();
			
			// Create coordinates for the last bodysegment
			// of the player being checked
			List<BodySegment> playerSegments = player.getBody().getBodySegments();
			BodySegment lastSeg = playerSegments.get(playerSegments.size() - 1);
			
			float lastx1 = lastSeg.getStart().getX();
			float lastx2 = lastSeg.getEnd().getX();
			float lasty1 = lastSeg.getStart().getY();
			float lasty2 = lastSeg.getEnd().getY();
			
			float lastlength = (float) Math.sqrt(Math.pow(lastx2-lastx1,2) + Math.pow(lasty2-lasty1,2));
			float lastxadd = width * ((lasty2-lasty1) / (lastlength*2));
			float lastyadd = width * ((lastx2-lastx1) / (lastlength*2));
			
			float thisx1 = lastx1 + lastxadd;
			float thisy1 = lasty1 - lastyadd;
			
			float thisx2 = lastx1 - lastxadd;
			float thisy2 = lasty1 + lastyadd;
			
			float thisx3 = lastx2 - lastxadd;
			float thisy3 = lasty2 + lastyadd;
			
			float thisx4 = lastx2 + lastxadd;
			float thisy4 = lasty2 - lastyadd;

			
			// Loop through all other players
			for(Player otherPlayer : players) {
				List<BodySegment> otherBodySegments = otherPlayer.getBody().getBodySegments();
				
				// Loop through all body segments of the other player
				// being checked and see if a collision has happened
				// with either of these
				for(BodySegment seg : otherBodySegments) {
					float x1 = seg.getStart().getX();
					float x2 = seg.getEnd().getX();
					float y1 = seg.getStart().getY();
					float y2 = seg.getEnd().getY();
					
					float length = (float) Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
					float xadd = seg.getWidth() * ((y2-y1) / (length*2));
					float yadd = seg.getWidth() * ((x2-x1) / (length*2));
					
					float otherx1 = x1 + xadd;
					float othery1 = y1 - yadd;
					
					float otherx2 = x1 - xadd;
					float othery2 = y1 + yadd;
					
					float otherx3 = x2 - xadd;
					float othery3 = y2 + yadd;
					
					float otherx4 = x2 + xadd;
					float othery4 = y2 - yadd;

					// Check if current player's last body
					// segment is inside the boundaries of
					// the body segment being checked
					if(thisx1 >= otherx1 && thisy1 >= othery1 || thisx2 >= otherx2 && thisy2 <= othery2 
							|| thisx3 >= otherx3 && thisy3 <= othery3 && thisx4 <= otherx4 && thisy4 >= othery4) {
						currentBody.kill();
						System.out.println("collision happened");
					}
				}
			}
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
		return deadPlayers == players.size() - 1;
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
