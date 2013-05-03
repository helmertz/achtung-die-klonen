package se.chalmers.tda367.group7.achtung.model;

public class Map {

	public static final Color DEFAULT_COLOR = new Color(0x0a0a0a);

	private final int width;
	private final int height;
	
	// Color represents the background color of the world. Could potentially be
	// changed by powerups
	private Color color;
	
	public Map(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public Map(int width, int height) {
		this(width, height, DEFAULT_COLOR);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	
}
