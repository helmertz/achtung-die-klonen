package se.chalmers.tda367.group7.achtung.model;

public class BodyFactory {

	private BodyFactory() {
	};

	public static Body getBody(Map map) {
		float xLimiter = map.getWidth() * 0.1f;
		float yLimiter = map.getHeight() * 0.1f;

		float maxX = map.getWidth() - xLimiter;
		float minX = xLimiter;
		float maxY = map.getHeight() - yLimiter;
		float minY = yLimiter;

		float rot = (float) (Math.random() * 360);
		Position position = Position.getRandomPosition(minX, minY, maxX, maxY);
		Body body = new Body(position, rot);
		return body;
	}
}
