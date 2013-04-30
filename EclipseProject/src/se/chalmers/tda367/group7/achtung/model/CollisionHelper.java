package se.chalmers.tda367.group7.achtung.model;

import java.util.List;

public class CollisionHelper {

	private List<Player> activePlayers;
	private final int width;
	private final int height;

	public CollisionHelper(int width, int height, List<Player> activePlayers) {
		this.activePlayers = activePlayers;
		this.width = width;
		this.height = height;
	}

	boolean playerCollidedWithPowerUp(Player player, PowerUpEntity powerUp) {
		Head head = player.getBody().getHead();

		float headDiam = player.getBody().getWidth();
		float powerUpDiam = powerUp.getDiameter();

		return head.getPosition().distanceFrom(powerUp.getPosition()) < (powerUpDiam / 2)
				+ (headDiam / 2);
	}

	boolean playerHasCollidedWithOthers(Player player, boolean wallsAreActive) {
		Body currentBody = player.getBody();
		Position pos = currentBody.getPosition();

		if (isPositionOutOfBounds(player, pos)) {
			if(wallsAreActive) {
				return true;
			} else {
				mirrorPlayerPosition(player);
				return false;
			}
		}

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
	private void mirrorPlayerPosition(Player player) {
		Body curBody = player.getBody();
		Position curPos = curBody.getPosition();
		
		float curX = curPos.getX();
		float curY = curPos.getY();
		
		float newX = curX;
		float newY = curY;
		
		if(exitOnLeftSide(curX)) {
			newX = width;
		} else if(exitOnRightSide(curX)) {
			newX = 0;
		} else if(exitOnTop(curY)) {
			newY = 0;
		} else if(exitOnBottom(curY)) {
			newY = height;
		}
		
		curBody.setHeadPosition(new Position(newX, newY));
	}

	private boolean exitOnBottom(float curY) {
		return curY < 0;
	}

	private boolean exitOnTop(float curY) {
		return curY > height;
	}

	private boolean exitOnRightSide(float curX) {
		return curX > width;
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

	private boolean isPositionOutOfBounds(Player player, Position pos) {
		float playerWidth = player.getBody().getWidth();

		// Adding/subtracting by one to not be as harsh
		return (pos.getX() < 0 + playerWidth - 1
				|| pos.getX() > width - playerWidth + 1
				|| pos.getY() < 0 + playerWidth - 1 
				|| pos.getY() > height - playerWidth + 1);
	}
}
