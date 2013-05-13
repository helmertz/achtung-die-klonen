package se.chalmers.tda367.group7.achtung.model;

public class Head {

	private Position position;
	private float diameter;

	public Head(Position position, float diameter) {
		this.position = position;
		this.diameter = diameter;
	}

	public Position getPosition() {
		return this.position;
	}

	public float getDiameter() {
		return this.diameter;
	}

	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(diameter);
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		Head other = (Head) obj;
		if (Float.floatToIntBits(diameter) != Float
				.floatToIntBits(other.diameter)) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}
}
