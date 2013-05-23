package se.chalmers.tda367.group7.achtung.model;

/**
 * A class representing a player, that is persistent over the rounds.
 */
public class Player {

	private final String name;
	private final Color color;

	private int points;
	private int roundPoints;

	private Body body;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	/**
	 * Update the player. The player will have to have a body before this method
	 * is called.
	 */
	public void update() {
		if (this.body == null) {
			throw new RuntimeException("No body is set for the player");
		}
		this.body.update();
	}

	public int getPoints() {
		return this.points;
	}
	
	public int getRoundPoints() {
		return this.roundPoints;
	}

	/**
	 * Adds the given number of points to the player.
	 * 
	 * @param points
	 *            - the amount of points to add
	 */
	public void addPoints(int points) {
		this.points += points;
		this.roundPoints++;
	}

	public Color getColor() {
		return this.color;
	}

	public Body getBody() {
		return this.body;
	}

	public void setBody(Body body) {
		this.body = body;
		if (this.body != null) {
			this.body.setColor(this.color);
		}
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return getName() + ", " + this.points + " points";
	}

	public void resetRoundScore() {
		this.roundPoints = 0;
	}
}
