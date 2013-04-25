package se.chalmers.tda367.group7.achtung.model;

public class BodyFactory {
	
	private BodyFactory(){};
	
	public static Body getBody(float worldWidth, float worldHeight) {
		float x = (float) (Math.random()*worldWidth);
		float y = (float) (Math.random()*worldHeight);
		float rot = (float) (Math.random() * 360);
		Position position = new Position(x, y);
		Body body = new Body(position, rot);
		return body;
	}

}
