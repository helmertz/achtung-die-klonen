package se.chalmers.tda367.group7.achtung.model;

import org.lwjgl.util.vector.Vector2f;

public class BodySegment {
	
	private Vector2f start;
	private Vector2f end;
	private float width;
	
	public BodySegment(Vector2f start, Vector2f end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
	}

	public Vector2f getStart() {
		return start;
	}

	public Vector2f getEnd() {
		return end;
	}

	public float getWidth() {
		return width;
	}
}
