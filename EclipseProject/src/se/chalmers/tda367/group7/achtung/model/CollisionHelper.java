package se.chalmers.tda367.group7.achtung.model;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class CollisionHelper {

	private final List<Player> activePlayers;
	private final Map map;

	public CollisionHelper(Map map, List<Player> activePlayers) {
		this.activePlayers = activePlayers;
		this.map = map;
	}

	public boolean hasCollidedWithPowerUp(Player player, PowerUpEntity powerUp) {
		Head head = player.getBody().getHead();

		float headDiam = player.getBody().getWidth();
		float powerUpDiam = powerUp.getDiameter();

		return head.getPosition().distanceFrom(powerUp.getPosition()) < (powerUpDiam / 2)
				+ (headDiam / 2);
	}

	public boolean hasCollidedWithOthers(Player player) {
		Body currentBody = player.getBody();

		// Create coordinates for the last bodysegment
		// of the player being checked
		List<BodySegment> playerSegments = currentBody.getBodySegments();

		if (playerSegments.isEmpty()) {
			return false;
		}

		BodySegment lastSeg = playerSegments.get(playerSegments.size() - 1);

		int NumBodySegments = playerSegments.size();
		List<BodySegment> segsBeforeLast = new ArrayList<BodySegment>();
		if (NumBodySegments > 1) {
			
			// Calculate number of segments not to test collision with.
			// When sharp turns are enabled more segments has to be ignored
			// depending on the width of the body.
			int numSegmentIgnore = (int) Math.round((lastSeg.getWidth()/player.getBody().getSpeed()));

			for (int i = 0; i < numSegmentIgnore && i < NumBodySegments - 1; i++) {
				segsBeforeLast.add(playerSegments.get(NumBodySegments - (2 + i)));
			}
			
//			segBeforeLast = playerSegments.get(playerSegments.size() - 2);
		}
		
		return hasCollidedWithSegment(lastSeg, segsBeforeLast);
	}

	// TODO: this is called from the method above, which should return
	// a boolean but also mutates, not a good solution... how to fix?
	public void mirrorPlayerPosition(Player player) {
		Body curBody = player.getBody();
		Position curPos = curBody.getPosition();

		float curX = curPos.getX();
		float curY = curPos.getY();

		float newX = curX;
		float newY = curY;

		if (exitOnLeftSide(curX)) {
			newX = this.map.getWidth();
		} else if (exitOnRightSide(curX)) {
			newX = 0;
		} else if (exitOnTop(curY)) {
			newY = 0;
		} else if (exitOnBottom(curY)) {
			newY = this.map.getHeight();
		}
		Position pos = new Position(newX, newY);

		// Sets some values to make sure new segments won't be connected across
		// the world
		curBody.setHeadPosition(pos);
		curBody.setLastPosition(pos);
		curBody.setPreviousSegment(null);
	}

	private boolean exitOnBottom(float curY) {
		return curY < 0;
	}

	private boolean exitOnTop(float curY) {
		return curY > this.map.getHeight();
	}

	private boolean exitOnRightSide(float curX) {
		return curX > this.map.getWidth();
	}

	private boolean exitOnLeftSide(float curX) {
		return curX < 0;
	}

	private boolean hasCollidedWithSegment(BodySegment lastSeg,
			List<BodySegment> segsBeforeLast) {
		// Loop through all other players
		for (Player otherPlayer : this.activePlayers) {

			List<BodySegment> otherBodySegments = otherPlayer.getBody()
					.getBodySegments();

			// Loop through all body segments of the other player
			// being checked and see if a collision has happened
			// with either of these
			for (BodySegment seg : otherBodySegments) {

				// Allows collision with itself and the one before
				if (lastSeg == seg || segsBeforeLast.contains(seg)) {
					continue;
				}

				if (segmentsCollide(seg, lastSeg)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isPlayerOutOfBounds(Player player) {
		float playerWidth = player.getBody().getWidth();
		Position pos = player.getBody().getPosition();

		// Adding/subtracting by one to not be as harsh
		return (pos.getX() < 0 + playerWidth - 1
				|| pos.getX() > this.map.getWidth() - playerWidth + 1
				|| pos.getY() < 0 + playerWidth - 1 || pos.getY() > this.map
				.getHeight() - playerWidth + 1);
	}

	public static boolean segmentsCollide(BodySegment b1, BodySegment b2) {
		// Does a fast bounding box check
		if (!b1.getHitBox().getBounds().intersects(b2.getHitBox().getBounds())) {
			return false;
		}
		// Does more precise and expensive tests
		return polygonsIntersect(b1.getHitBox(), b2.getHitBox())
				|| lineOnlyIntersect(b1, b2);
	}

	// This intersection test works well, but doesn't take width into account.
	private static boolean lineOnlyIntersect(BodySegment b1, BodySegment b2) {
		return Line2D.linesIntersect(b1.getStart().getX(),
				b1.getStart().getY(), b1.getEnd().getX(), b1.getEnd().getY(),
				b2.getStart().getX(), b2.getStart().getY(), b2.getEnd().getX(),
				b2.getEnd().getY());
	}

	// Checks if a polygon's corner is inside the other polygon. Not perfect,
	// but does take width into account.
	private static boolean polygonsIntersect(Polygon p1, Polygon p2) {
		return isCornerOfFirstInSecond(p1, p2)
				|| isCornerOfFirstInSecond(p2, p1);
	}

	private static boolean isCornerOfFirstInSecond(Polygon p1, Polygon p2) {
		for (int i = 0; i < p1.npoints; i++) {
			if (p2.contains(p1.xpoints[i], p1.ypoints[i])) {
				return true;
			}
		}
		return false;
	}
}
