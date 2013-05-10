package se.chalmers.tda367.group7.achtung.model;

public class Map {

	public static final Color DEFAULT_COLOR = new Color(0x0a0a0a);

	private final float width;
	private final float height;

	// Color represents the background color of the world. Could potentially be
	// changed by powerups
	private Color color;

	public Map(float width, float height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public Map(float width, float height) {
		this(width, height, DEFAULT_COLOR);
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
