package se.chalmers.tda367.group7.achtung.model;

public class Map {

	public static final Color DEFAULT_COLOR = new Color(0x0a0a0a);

	private final float width;
	private final float height;
	private boolean wallsActive = true;

	private Color backgroundColor;

	public Map(float width, float height, Color color) {
		this.width = width;
		this.height = height;
		this.backgroundColor = color;
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
		return this.backgroundColor;
	}

	public void setColor(Color color) {
		this.backgroundColor = color;
	}

	public void setWallsActive(boolean wallsActive) {
		this.wallsActive = wallsActive;
	}

	public boolean isWallsActive() {
		return this.wallsActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.backgroundColor == null) ? 0 : this.backgroundColor.hashCode());
		result = prime * result + Float.floatToIntBits(this.height);
		result = prime * result + Float.floatToIntBits(this.width);
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
		Map other = (Map) obj;
		if (this.backgroundColor == null) {
			if (other.backgroundColor != null) {
				return false;
			}
		} else if (!this.backgroundColor.equals(other.backgroundColor)) {
			return false;
		}
		if (Float.floatToIntBits(this.height) != Float.floatToIntBits(other.height)) {
			return false;
		}
		if (Float.floatToIntBits(this.width) != Float.floatToIntBits(other.width)) {
			return false;
		}
		return true;
	}

}
