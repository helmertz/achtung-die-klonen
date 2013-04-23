package se.chalmers.tda367.group7.achtung.model;

import java.util.Random;

public class BodyFactory {
	
	private BodyFactory(){};
	
	public static Body getBody(float worldWidth, float worldHeight) {
		Random rand = new Random();
		
		int xLimiter = (int) (worldWidth * 0.1);
		int yLimiter = (int) (worldHeight * 0.1);
		
		int xMax = (int) worldWidth - xLimiter;
		int xMin = xLimiter;
		int yMax = (int) (worldHeight - yLimiter);
		int yMin = yLimiter;

		float x = rand.nextInt(xMax - xMin + 1) + xMin;
		float y = rand.nextInt(yMax - yMin + 1) + yMin;
		
		float rot = (float) (Math.random() * 360);
		Position position = new Position(x, y);
		Body body = new Body(position, rot);
		return body;
	}
}
