package se.chalmers.tda367.group7.achtung.model;

import java.awt.Polygon;
import java.awt.SecondaryLoop;

public class BodySegment {
	
	private Position start;
	private Position end;
	private float width;
	
	private Polygon hitBox;
	
	public BodySegment(Position start, Position end, float width) {
		this.start = start;
		this.end = end;
		this.width = width;
		
		setHitBoxBounds();
	}

	private void setHitBoxBounds() {

		float linex1 = start.getX();
		float liney1 = start.getY();
		float linex2 = end.getX();
		float liney2 = end.getY();
		
		float length = (float) Math.sqrt(Math.pow(linex2-linex1,2) + Math.pow(liney2-liney1,2));
		float xadd = width * ((liney2-liney1) / (length*2));
		float yadd = width*(linex2-linex1)/(length*2);

		// Rounding since java.awt.Polygon.Polygon, which is used for collision, is integer based
		int x1 = Math.round(linex1 + xadd);
		int y1 = Math.round(liney1 - yadd);
		
		int x2 = Math.round(linex1 - xadd);
		int y2 = Math.round(liney1 + yadd);
		
		int x3 = Math.round(linex2 - xadd);
		int y3 = Math.round(liney2 + yadd);
		
		int x4 = Math.round(linex2 + xadd);
		int y4 = Math.round(liney2 - yadd);
		
		hitBox = new Polygon(new int[]{x1, x2, x3, x4}, new int[]{y1, y2, y3, y4}, 4);
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
	
	public Polygon getHitBox() {
		return this.hitBox;
	}
	
	public boolean collidesWith(BodySegment b) {
		return polygonsIntersect(getHitBox(), b.getHitBox());
	}
	
	// Checks for collision by checking if a polygon's corner is inside the
	// other polygon
	private static boolean polygonsIntersect(Polygon p1, Polygon p2) {
		return firstIntersectsSecond(p1, p2) || secondIntersectsFirst(p1,p2);
	}
	
	private static boolean secondIntersectsFirst(Polygon p1, Polygon p2) {
		for (int i = 0; i < p2.npoints; i++) {
			if (p1.contains(p2.xpoints[i], p2.ypoints[i])) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean firstIntersectsSecond(Polygon p1, Polygon p2) {
		for(int i = 0; i < p1.npoints; i++) {
			if(p2.contains(p1.xpoints[i], p1.ypoints[i])) {
				return true;
			}
		}
		return false;
	}
}
