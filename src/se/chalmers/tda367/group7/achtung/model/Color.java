package se.chalmers.tda367.group7.achtung.model;

/**
 * An immutable class for representing a color with red, green, blue and alpha components.
 */
public class Color {
	public static final Color WHITE = new Color(1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f);
	public static final Color RED = new Color(1f, 0f, 0f);
	public static final Color GREEN = new Color(0f, 1f, 0f);
	public static final Color BLUE = new Color(0f, 0f, 1f);

	private float r;
	private float g;
	private float b;
	private float a;

	/**
	 * Creates a color from red, green and blue values given in the range
	 * (0-255).
	 * 
	 * @param red
	 *            the red component
	 * @param green
	 *            the green component
	 * @param blue
	 *            the blue component
	 */
	public Color(int red, int green, int blue) {
		this(red, green, blue, 0xff);
	}

	/**
	 * Creates a color from red, green, blue and alpha values given in the range
	 * (0-255).
	 * 
	 * @param red
	 *            the red component
	 * @param green
	 *            the green component
	 * @param blue
	 *            the blue component
	 * @param alpha
	 *            the alpha component
	 */
	public Color(int red, int green, int blue, int alpha) {
		this.r = (float) red / 0xff;
		this.g = (float) green / 0xff;
		this.b = (float) blue / 0xff;
		this.a = (float) alpha / 0xff;
	}

	/**
	 * Creates a color from a combined RGB value in the range 0-ffffff in
	 * hexadecimal.
	 * 
	 * @param color
	 *            the color
	 */
	public Color(int color) {
		this(color >> 16 & 0xff, color >> 8 & 0xff, color >> 0 & 0xff);
	}

	/**
	 * Creates a color from red, green and blue values given as floating point
	 * values in the range (0-1).
	 * 
	 * @param red
	 *            the red component
	 * @param green
	 *            the green component
	 * @param blue
	 *            the blue component
	 */
	public Color(float red, float green, float blue) {
		this(red, green, blue, 1f);
	}

	/**
	 * Creates a color from red, green, blue and alpha values given as floating
	 * point values in the range (0-1).
	 * 
	 * @param red
	 *            the red component
	 * @param green
	 *            the green component
	 * @param blue
	 *            the blue component
	 * @param alpha
	 *            the alpha component
	 */
	public Color(float red, float green, float blue, float alpha) {
		this.r = red;
		this.g = green;
		this.b = blue;
		this.a = alpha;
	}

	/**
	 * Returns the red component in the range (0-1).
	 * 
	 * @return the red component
	 */
	public float getRed() {
		return this.r;
	}

	/**
	 * Returns the green component in the range (0-1).
	 * 
	 * @return the green component
	 */
	public float getGreen() {
		return this.g;
	}

	/**
	 * Returns the blue component in the range (0-1).
	 * 
	 * @return the blue component
	 */
	public float getBlue() {
		return this.b;
	}

	/**
	 * Returns the alpha component in the range (0-1).
	 * 
	 * @return the alpha component
	 */
	public float getAlpha() {
		return this.a;
	}

	/**
	 * Return a random color given based on randomness from
	 * java.lang.Math.random().
	 * 
	 * @return a random color
	 */
	public static Color getRandomColor() {
		return new Color((int) (0xffffff * Math.random()));
	}

	/**
	 * Returns the opposite color to this.
	 * 
	 * @return the opposite color
	 */
	public Color getOpposite() {
		return new Color(1f - this.r, 1f - this.g, 1f - this.b, this.a);
	}
}
