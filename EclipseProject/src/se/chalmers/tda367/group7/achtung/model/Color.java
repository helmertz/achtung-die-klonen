package se.chalmers.tda367.group7.achtung.model;

/**
 * An immutable class for representing a color with red, green, blue and alpha
 * components.
 */
public class Color {
	public static final Color WHITE = new Color(1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f);
	public static final Color RED = new Color(1f, 0f, 0f);
	public static final Color GREEN = new Color(0f, 1f, 0f);
	public static final Color BLUE = new Color(0f, 0f, 1f);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f);

	// Used in making lighter or darker versions of a color
	private static final float CHANGE_CONSTANT = 0.2f;

	private final float r;
	private final float g;
	private final float b;
	private final float a;

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

	/**
	 * Returns a string representation of the color.
	 * 
	 * @return a string representing the color
	 */
	@Override
	public String toString() {
		return "Color: red: " + this.r + " green: " + this.g + " blue: "
				+ this.b + " alpha: " + this.a + "";
	}

	/**
	 * Returns a lighter version of the color.
	 * 
	 * @return a lighter version of the color
	 */
	public Color getLighter() {
		return interpolateWith(Color.WHITE, CHANGE_CONSTANT);
	}

	/**
	 * Returns a darker version of the color.
	 * 
	 * @return a darker version of the color
	 */
	public Color getDarker() {
		return interpolateWith(Color.BLACK, CHANGE_CONSTANT);
	}

	/**
	 * Returns a new color that's linearly interpolated with the color given, by
	 * the given factor.
	 * 
	 * The factor should be given in the range (0-1), where 0 means identical to
	 * this, and 1 identical to the color passed as the argument.
	 * 
	 * @param color
	 *            the other color this is interpolated with
	 * @param factor
	 *            the factor that determines how similar the new color is this
	 * @return the interpolated color
	 */
	public Color interpolateWith(Color color, float factor) {
		if (factor < 0 || factor > 1) {
			throw new IllegalArgumentException();
		}
		float diffR = color.r - this.r;
		float diffG = color.g - this.g;
		float diffB = color.b - this.b;
		float newR = this.r + factor * diffR;
		float newG = this.g + factor * diffG;
		float newB = this.b + factor * diffB;
		return new Color(newR, newG, newB, this.a);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a)) {
			return false;
		}
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b)) {
			return false;
		}
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g)) {
			return false;
		}
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r)) {
			return false;
		}
		return true;
	}
}
