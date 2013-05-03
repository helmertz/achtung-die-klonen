package se.chalmers.tda367.group7.achtung.model;

import java.util.List;

public class CollisionHelper {

	private List<Player> activePlayers;
	private final Map map;

	public CollisionHelper(Map map, List<Player> activePlayers) {
		this.activePlayers = activePlayers;
		this.map = map;
	}

	boolean playerCollidedWithPowerUp(Player player, PowerUpEntity powerUp) {
		Head head = player.getBody().getHead();

		float headDiam = player.getBody().getWidth();
		float powerUpDiam = powerUp.getDiameter();

		return head.getPosition().distanceFrom(powerUp.getPosition()) < (powerUpDiam / 2)
				+ (headDiam / 2);
	}

	boolean collidesWithOthers(Player player) {
		Body currentBody = player.getBody();

		// Create coordinates for the last bodysegment
		// of the player being checked
		List<BodySegment> playerSegments = currentBody.getBodySegments();

		if (playerSegmentsAreEmpty(playerSegments)) {
			return false;
		}

		BodySegment lastSeg = playerSegments.get(playerSegments.size() - 1);

		BodySegment segBeforeLast = null;
		if (playerSegments.size() > 1) {
			segBeforeLast = playerSegments.get(playerSegments.size() - 2);
		}

		return checkCollisionWithOthersSegments(lastSeg, segBeforeLast);
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
		
		if(exitOnLeftSide(curX)) {
			newX = map.getWidth();
		} else if(exitOnRightSide(curX)) {
			newX = 0;
		} else if(exitOnTop(curY)) {
			newY = 0;
		} else if(exitOnBottom(curY)) {
			newY = map.getHeight();
		}
		Position pos = new Position(newX, newY);

		curBody.setHeadPosition(pos);
		curBody.setLastPosition(pos);
	}

	private boolean exitOnBottom(float curY) {
		return curY < 0;
	}

	private boolean exitOnTop(float curY) {
		return curY > map.getHeight();
	}

	private boolean exitOnRightSide(float curX) {
		return curX > map.getWidth();
	}

	private boolean exitOnLeftSide(float curX) {
		return curX < 0;
	}

	private boolean checkCollisionWithOthersSegments(BodySegment lastSeg,
			BodySegment segBeforeLast) {
		// Loop through all other players
		for (Player otherPlayer : activePlayers) {

			List<BodySegment> otherBodySegments = otherPlayer.getBody()
					.getBodySegments();

			// Loop through all body segments of the other player
			// being checked and see if a collision has happened
			// with either of these
			for (BodySegment seg : otherBodySegments) {

				// Allows collision with itself and the one before
				if (lastSeg == seg || segBeforeLast == seg) {
					continue;
				}

				if (lastSeg.collidesWith(seg)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean playerSegmentsAreEmpty(List<BodySegment> playerSegments) {
		return playerSegments.isEmpty();
	}

	private boolean isPlayerOutOfBounds(Player player) {
		float playerWidth = player.getBody().getWidth();
		Position pos = player.getBody().getPosition();

		// Adding/subtracting by one to not be as harsh
		return (pos.getX() < 0 + playerWidth - 1
				|| pos.getX() > map.getWidth() - playerWidth + 1
				|| pos.getY() < 0 + playerWidth - 1 
				|| pos.getY() > map.getHeight() - playerWidth + 1);
	}

	public boolean collidesWithWall(Player player) {
		return isPlayerOutOfBounds(player);
	}
}
