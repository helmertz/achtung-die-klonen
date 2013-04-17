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
		// if (isRoundActive()) {
		updatePlayers();

		// TODO - implement collision detection and random power up placement.
	}


	private void updatePlayers() {
		for (Player player : players) {
			player.update();

			if (doesPlayerCollide(player)) {
//				player.getBody().kill();
			}
		}
	}

	private boolean doesPlayerCollide(Player player) {
		Body currentBody = player.getBody();

		// Create coordinates for the last bodysegment
		// of the player being checked
		List<BodySegment> playerSegments = currentBody.getBodySegments();
		if (playerSegments.isEmpty()) {
			return false;
		}
		BodySegment lastSeg = playerSegments.get(playerSegments.size() - 1);

		float lastx1 = lastSeg.getStart().getX();
		float lastx2 = lastSeg.getEnd().getX();
		float lasty1 = lastSeg.getStart().getY();
		float lasty2 = lastSeg.getEnd().getY();

		float lastlength = (float) Math.sqrt(Math.pow(lastx2 - lastx1, 2)
				+ Math.pow(lasty2 - lasty1, 2));
		float lastxadd = lastSeg.getWidth()
				* ((lasty2 - lasty1) / (lastlength * 2));
		float lastyadd = lastSeg.getWidth()
				* ((lastx2 - lastx1) / (lastlength * 2));

		float thisx1 = lastx1 + lastxadd;
		float thisy1 = lasty1 - lastyadd;

		float thisx2 = lastx1 - lastxadd;
		float thisy2 = lasty1 + lastyadd;

		float thisx3 = lastx2 - lastxadd;
		float thisy3 = lasty2 + lastyadd;

		float thisx4 = lastx2 + lastxadd;
		float thisy4 = lasty2 - lastyadd;

		// Loop through all other players
		for (Player otherPlayer : players) {
			List<BodySegment> otherBodySegments = otherPlayer.getBody()
					.getBodySegments();

			// Loop through all body segments of the other player
			// being checked and see if a collision has happened
			// with either of these
			for (BodySegment seg : otherBodySegments) {
				if (lastSeg == seg) {
					continue;
				}

				float x1 = seg.getStart().getX();
				float x2 = seg.getEnd().getX();
				float y1 = seg.getStart().getY();
				float y2 = seg.getEnd().getY();

				float length = (float) Math.sqrt(Math.pow(x2 - x1, 2)
						+ Math.pow(y2 - y1, 2));
				float xadd = seg.getWidth() * ((y2 - y1) / (length * 2));
				float yadd = seg.getWidth() * ((x2 - x1) / (length * 2));

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
				if(
						((thisx1 >= otherx1 && thisx1 >= otherx2
								&& thisx1 <= otherx3 && thisx1 <= otherx4) && (thisy1 >= othery1
								&& thisy1 <= othery2 && thisy1 <= othery3 && thisy1 >= othery4))

						|| ((thisx2 >= otherx1 && thisx2 >= otherx2
								&& thisx2 <= otherx3 && thisx2 <= otherx4) && (thisy2 >= othery1
								&& thisy2 <= othery2 && thisy2 <= othery3 && thisy2 >= othery4))

						|| ((thisx3 >= otherx1 && thisx3 >= otherx2
								&& thisx3 <= otherx3 && thisx3 <= otherx4) && (thisy3 >= othery1
								&& thisy3 <= othery2 && thisy3 <= othery3 && thisy3 >= othery4))

						|| ((thisx4 >= otherx1 && thisx4 >= otherx2
								&& thisx4 <= otherx3 && thisx4 <= otherx4) && (thisy4 >= othery1 
								&& thisy4 <= othery2 && thisy4 <= othery3 && thisy4 >= othery4))) {
					player.getBody().kill();
					System.out.println("collision happened");
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
