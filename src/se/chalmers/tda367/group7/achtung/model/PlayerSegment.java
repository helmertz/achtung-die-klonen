package se.chalmers.tda367.group7.achtung.model;

import org.lwjgl.util.vector.Vector2f;

public class PlayerSegment {
	private Vector2f start;
	private Vector2f end;
	private float width;

	public PlayerSegment(Vector2f start, Vector2f end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
	}

	public void setStart(Vector2f start) {
		this.start = start;
	}

	public void setEnd(Vector2f end) {
		this.end = end;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Vector2f getStart() {
		return this.start;
	}

	public Vector2f getEnd() {
		return this.end;
	}

	public float getWidth() {
		return this.width;
	}

}
