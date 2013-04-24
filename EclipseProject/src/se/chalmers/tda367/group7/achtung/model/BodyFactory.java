package se.chalmers.tda367.group7.achtung.model;

public class BodyFactory {
	
	private BodyFactory(){};
	
	public static Body getBody(float worldWidth, float worldHeight) {
		
		int xLimiter = (int) (worldWidth * 0.1);
		int yLimiter = (int) (worldHeight * 0.1);
		
		int maxX = (int) worldWidth - xLimiter;
		int minX = xLimiter;
		int maxY = (int) (worldHeight - yLimiter);
		int minY = yLimiter;
		
		float rot = (float) (Math.random() * 360);
		Position position = Position.getRandomPosition(minX, maxX, minY, maxY);
		Body body = new Body(position, rot);
		return body;
	}
}
