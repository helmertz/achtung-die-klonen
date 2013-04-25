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
	
	boolean playerHasCollidedWithOthers(Player player) {
		Body currentBody = player.getBody();
		Position pos = currentBody.getPosition();
		
		if(isPositionOutOfBounds(pos)) {
			return true;
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
				
				if(lastSeg.collidesWith(seg)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean playerSegmentsAreEmpty(List<BodySegment> playerSegments) {
		return playerSegments.isEmpty();
	}
	
	private boolean isPositionOutOfBounds(Position pos) {
		return (pos.getX() < 0 || pos.getX() > width || pos.getY() < 0 || pos.getY() > height);
	}
	
}
