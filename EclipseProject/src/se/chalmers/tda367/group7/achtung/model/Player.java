package se.chalmers.tda367.group7.achtung.model;


/**
 * A class representing a player, that is persistent over the rounds.
 */
public class Player {

	private String name;
	private Color color;
	
	private int points;
	private int id;
	private static int numberOfPlayers = 0;
	
	private Body body;
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		
		// id mechanism.
		id = numberOfPlayers;
		numberOfPlayers++;
	}

	/**
	 * Update the player. 
	 * The player will have to have a body before this method is called.
	 */
	public void update() {
		if(body == null) {
			throw new RuntimeException("No body is set for the player");
		}
		body.update();
	}

	public int getPoints() {
		return points;
	}
	
	/**
	 * Adds the given number of points to the player.
	 * 
	 * @param points - the amount of points to add
	 */
	public void addPoints(int points) {
		this.points += points;
	}
	
	public int getId() {
		return id;
	}

	public Color getColor() {
		return color;
	}
	
	public void setPosition(Position position) {
		body.setHeadPosition(position);
	}

	public Body getBody() {
		return this.body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}

	public String getName() {
		return name;
	}
	
	public void turnRight() {
		body.turnRight();
	}
	
	public void turnLeft() {
		body.turnLeft();
	}
}
