package se.chalmers.tda367.group7.achtung.model;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.List;

public class CollisionHelper {

	private final List<Player> players;
	private final Map map;

	public CollisionHelper(Map map, List<Player> activePlayers) {
		this.players = activePlayers;
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

		if (playerSegments.isEmpty() || currentBody.isGeneratingHole()) {
			return false;
		}
		return hasCollidedWithSegment(currentBody);
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

	private boolean hasCollidedWithSegment(Body currentBody) {
		BodySegment lastSeg = currentBody.getBodySegments().get(
				currentBody.getBodySegments().size() - 1);

		// Calculate number of segments not to test collision with.
		// When sharp turns are enabled more segments has to be ignored
		// depending on the width of the body.
		int numSegmentIgnore = (int) (lastSeg.getWidth()
				/ currentBody.getSpeed() + 0.5f);

		// Sets so that the last 4 are always ignored
		// TODO too many?
		if (numSegmentIgnore < 4) {
			numSegmentIgnore = 4;
		}

		// Enable to debug deaths that shouldn't happen
		// System.out.println(numSegmentIgnore);

		// Loop through all other players
		for (Player player : this.players) {

			Body body = player.getBody();

			int count = body.getBodySegments().size();

			// When checking with itself, allow some collisions near head
			if (currentBody == body) {
				count -= numSegmentIgnore;
			}
			for (int i = 0; i < count; i++) {
				BodySegment seg = body.getBodySegments().get(i);
				if (segmentsCollide(seg, lastSeg)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isPlayerOutOfBounds(Player player) {
		float playerWidth = player.getBody().getWidth() / 2;
		Position pos = player.getBody().getPosition();

		// Adding/subtracting by one to not be as harsh
		return (pos.getX() < playerWidth + 4
				|| pos.getX() > this.map.getWidth() - playerWidth - 4
				|| pos.getY() < playerWidth + 4 || pos.getY() > this.map
				.getHeight() - playerWidth - 4);
	}

	public static boolean segmentsCollide(BodySegment b1, BodySegment b2) {
		// Does a fast bounding box check
		if (!b1.getBounds().intersects(b2.getBounds())) {
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
