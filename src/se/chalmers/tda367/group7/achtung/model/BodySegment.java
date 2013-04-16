package se.chalmers.tda367.group7.achtung.model;


public class BodySegment {
	
	private Position start;
	private Position end;
	private float width;
	
	public BodySegment(Position start, Position end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
	}

	public Position getStart() {
		return start;
	}

	public Position getEnd() {
		return end;
	}

	public float getWidth() {
		return width;
	}
}
