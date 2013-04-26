package se.chalmers.tda367.group7.achtung.model;

public class Position {

	private float x;
	private float y;
	
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float distanceFrom(Position otherPosition) {
		return (float) Math.sqrt(Math.pow(this.x - otherPosition.x, 2) + Math.pow(this.y - otherPosition.y, 2));
	}

	public static Position getRandomPosition(float minX, float maxX, float minY, float maxY) {
		float randX = (float) (minX + Math.random() * (maxX - minX));
		float randY = (float) (minY + Math.random() * (maxY - minY));
		return new Position(randX, randY);
	}
	
	@Override
	public String toString() {
		return "x = " + x + "; y = " + y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
}
