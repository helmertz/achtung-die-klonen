package se.chalmers.tda367.group7.achtung.model;

/**
 * A class representing a player, that is persistent over the rounds.
 */
public class Player {

	private final String name;
	private final Color color;

	private int points;
	private final int id;
	private static int numberOfPlayers = 0;

	private Body body;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;

		// id mechanism.
		this.id = numberOfPlayers;
		numberOfPlayers++;
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

	/**
	 * Adds the given number of points to the player.
	 * 
	 * @param points
	 *            - the amount of points to add
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	public int getId() {
		return this.id;
	}

	public Color getColor() {
		return this.color;
	}

	public Body getBody() {
		return this.body;
	}

	public void setBody(Body body) {
		this.body = body;
		if(this.body != null) {
			this.body.setColor(this.color);
		}
	}

	public String getName() {
		return this.name;
	}
}
