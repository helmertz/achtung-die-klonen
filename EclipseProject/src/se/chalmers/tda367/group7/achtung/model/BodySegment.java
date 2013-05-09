package se.chalmers.tda367.group7.achtung.model;

import java.awt.Polygon;

public class BodySegment {

	private final Position start;
	private final Position end;
	private final float width;

	private Polygon hitBox;

	public BodySegment(Position start, Position end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;

		setHitBoxBounds();
	}

	private void setHitBoxBounds() {

		float linex1 = this.start.getX();
		float liney1 = this.start.getY();
		float linex2 = this.end.getX();
		float liney2 = this.end.getY();

		float length = (float) Math.sqrt(Math.pow(linex2 - linex1, 2)
				+ Math.pow(liney2 - liney1, 2));
		float xadd = this.width * ((liney2 - liney1) / (length * 2));
		float yadd = this.width * (linex2 - linex1) / (length * 2);

		// Rounding since java.awt.Polygon.Polygon, which is used for collision,
		// is integer based
		int x1 = Math.round(linex1 + xadd);
		int y1 = Math.round(liney1 - yadd);

		int x2 = Math.round(linex1 - xadd);
		int y2 = Math.round(liney1 + yadd);

		int x3 = Math.round(linex2 - xadd);
		int y3 = Math.round(liney2 + yadd);

		int x4 = Math.round(linex2 + xadd);
		int y4 = Math.round(liney2 - yadd);

		this.hitBox = new Polygon(new int[] { x1, x2, x3, x4 }, new int[] { y1,
				y2, y3, y4 }, 4);
	}

	public Position getStart() {
		return this.start;
	}

	public Position getEnd() {
		return this.end;
	}

	public float getWidth() {
		return this.width;
	}

	public Polygon getHitBox() {
		return this.hitBox;
	}
}
