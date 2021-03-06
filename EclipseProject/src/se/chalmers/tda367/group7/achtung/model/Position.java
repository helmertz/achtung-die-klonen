package se.chalmers.tda367.group7.achtung.model;

public class Position {

	private final float x;
	private final float y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float distanceFrom(Position otherPosition) {
		return (float) Math.sqrt(Math.pow(this.x - otherPosition.x, 2)
				+ Math.pow(this.y - otherPosition.y, 2));
	}

	public static Position getRandomPosition(float minX, float minY,
			float maxX, float maxY) {
		float randX = (float) (minX + Math.random() * (maxX - minX));
		float randY = (float) (minY + Math.random() * (maxY - minY));
		return new Position(randX, randY);
	}

	public static Position getRandomPosition(float maxX, float maxY) {
		return getRandomPosition(0, 0, maxX, maxY);
	}

	@Override
	public String toString() {
		return "x = " + this.x + "; y = " + this.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.x);
		result = prime * result + Float.floatToIntBits(this.y);
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
		Position other = (Position) obj;
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}

}
