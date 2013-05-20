package se.chalmers.tda367.group7.achtung.model;

import java.awt.Polygon;
import java.awt.Rectangle;

public class BodySegment {

	private final Position start;
	private final Position end;
	private final Position[] corners;

	private final float width;

	private Polygon hitBox;
	private final Rectangle bounds;

	public BodySegment(Position start, Position end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
		this.corners = new Position[4];
		createCorners(false);
		createHitBox();
		// Stores a rectangle that covers the whole segment
		this.bounds = this.hitBox.getBounds();
	}

	public BodySegment(BodySegment previousSegment, Position end, float width) {
		this.start = previousSegment.getEnd();
		this.end = end;
		this.width = width;
		this.corners = new Position[4];
		createBackCornersFromPrevious(previousSegment);
		createCorners(true);
		createHitBox();
		this.bounds = this.hitBox.getBounds();
	}

	/**
	 * Used for connecting to a previous segment.
	 * 
	 * The two front corners of the previous segment is inherited by this.
	 * 
	 * @param previousSegment
	 *            the previous segment
	 */
	private void createBackCornersFromPrevious(BodySegment previousSegment) {
		this.corners[0] = previousSegment.corners[3];
		this.corners[1] = previousSegment.corners[2];
	}

	/**
	 * Fills the corner array.
	 * 
	 * @param setFrontOnly
	 *            is true if the two backmost corners aren't to be set
	 */
	private void createCorners(boolean setFrontOnly) {
		float linex1 = this.start.getX();
		float liney1 = this.start.getY();
		float linex2 = this.end.getX();
		float liney2 = this.end.getY();

		float length = (float) Math.sqrt(Math.pow(linex2 - linex1, 2)
				+ Math.pow(liney2 - liney1, 2));
		float xadd = this.width * ((liney2 - liney1) / (length * 2));
		float yadd = this.width * (linex2 - linex1) / (length * 2);

		// When connected to previous segment, these shouln't be set
		if (!setFrontOnly) {
			float x1 = linex1 + xadd;
			float y1 = liney1 - yadd;
			this.corners[0] = new Position(x1, y1);

			float x2 = linex1 - xadd;
			float y2 = liney1 + yadd;
			this.corners[1] = new Position(x2, y2);
		}

		float x3 = linex2 - xadd;
		float y3 = liney2 + yadd;
		this.corners[2] = new Position(x3, y3);

		float x4 = linex2 + xadd;
		float y4 = liney2 - yadd;
		this.corners[3] = new Position(x4, y4);
	}

	/**
	 * Creates a java.awt.Polygon instance that is used for collision checking.
	 * 
	 * Should be used after corners has been set.
	 */
	private void createHitBox() {
		int[] xPoints = new int[] { Math.round(this.corners[0].getX()),
				Math.round(this.corners[1].getX()),
				Math.round(this.corners[2].getX()),
				Math.round(this.corners[3].getX()) };
		int[] yPoints = new int[] { Math.round(this.corners[0].getY()),
				Math.round(this.corners[1].getY()),
				Math.round(this.corners[2].getY()),
				Math.round(this.corners[3].getY()) };
		this.hitBox = new Polygon(xPoints, yPoints, 4);
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

	public Position[] getCorners() {
		return this.corners;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}
}
