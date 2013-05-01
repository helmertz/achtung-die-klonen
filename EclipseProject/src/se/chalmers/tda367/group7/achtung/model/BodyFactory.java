package se.chalmers.tda367.group7.achtung.model;

public class BodyFactory {
	
	private BodyFactory(){};
	
	public static Body getBody(Map map) {
		// TODO - fix more neat code here
		int xLimiter = (int) (map.getWidth() * 0.1);
		int yLimiter = (int) (map.getHeight() * 0.1);
		
		int maxX = (int) map.getWidth() - xLimiter;
		int minX = xLimiter;
		int maxY = (int) (map.getHeight() - yLimiter);
		int minY = yLimiter;
		
		float rot = (float) (Math.random() * 360);
		Position position = Position.getRandomPosition(minX, minY, maxX, maxY);
		Body body = new Body(position, rot);
		return body;
	}
}
