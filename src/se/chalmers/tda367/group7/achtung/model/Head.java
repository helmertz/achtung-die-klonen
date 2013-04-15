package se.chalmers.tda367.group7.achtung.model;

import org.lwjgl.util.vector.Vector2f;

public class Head {
	
	private Vector2f position;
	private float diameter;
	
	
	public Head(Vector2f position, float diameter) {
		this.position = position;
		this.diameter = diameter;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getDiameter() {
		return diameter;
	}
	
	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
}
