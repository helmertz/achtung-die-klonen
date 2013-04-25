package se.chalmers.tda367.group7.achtung.model;


public class Head {
	
	private Position position;
	private float diameter;
	
	
	public Head(Position position, float diameter) {
		this.position = position;
		this.diameter = diameter;
	}

	public Position getPosition() {
		return position;
	}

	public float getDiameter() {
		return diameter;
	}
	
	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
