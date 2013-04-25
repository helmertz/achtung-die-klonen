package se.chalmers.tda367.group7.achtung.model;

import java.awt.Rectangle;


public class BodySegment {
	
	private Position start;
	private Position end;
	private float width;
	
	private Rectangle hitBox = new Rectangle();
	
	public BodySegment(Position start, Position end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
		
		setHitBoxBounds();
	}

	private void setHitBoxBounds() {
		float x1 = start.getX();
		float x2 = end.getX();
		float y1 = start.getY();
		float y2 = end.getY();
		
		float length = (float) Math.sqrt(Math.pow(x2 - x1, 2)
				+ Math.pow(y2 - y1, 2));
		float xadd = this.width * ((y2 - y1) / (length * 2));
		float yadd = this.width * ((x2 - x1) / (length * 2));
		
		float otherx1 = x1 + xadd;
		float othery1 = y1 - yadd;
		
		hitBox.setBounds((int)otherx1, (int)othery1, (int)this.width, (int)length);
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
	
	public Rectangle getHitBox() {
		return this.hitBox;
	}
}
