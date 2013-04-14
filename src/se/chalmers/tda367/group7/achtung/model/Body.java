package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

// Class responsible for position in game and movement updates
public class Body {
	
	public static final float DEFAULT_SPEED = 1;
	public static final float DEFAULT_ROTATION = 0;
	
	private Head head;
	private List<BodySegment> bodySegments = new ArrayList<BodySegment>();
	
	// TODO: don't hardcode this in this class probably, but would be weird
	// to have a setHeadDiameter method in this class, makes no sense from an
	// interface perspective
	private float headDiameter = 10;
	
	// Have speed and direction stored like this, instead of separate vector,
	// will probably make more sense in our game. (Since we lack gravity and
	// such)
	// Would it make more sense if these were properties of the head?
	private float speed;
	private float rotation; // in degrees
	
	public Body (Vector2f position) {
		this(position, DEFAULT_SPEED, DEFAULT_ROTATION);
	}
	
	public Body (Vector2f position, float speed, float rotation) {
		head = new Head(position, headDiameter);
		this.speed = speed;
		this.rotation = rotation;
	}
	
	public Head getHead() {
		return head;
	}
	
	public List<BodySegment> getBodySegments() {
		return bodySegments;
	}
	
	public void addBodySegment(BodySegment bodySegment) {
		bodySegments.add(bodySegment);
	}

	public void setHeadPosition(Vector2f position) {
		if(this.head == null) {
			head = new Head(position, headDiameter);
		} else {
			head.setPosition(position);
		}
	}

	public void update() {
		Vector2f position = head.getPosition();
		float x = position.getX();
		float y = position.getY();
		
		// Calculates the new position of the head.
		x = (float) (x + speed * Math.cos(Math.toRadians(rotation)));
		y = (float) (y + speed * Math.sin(Math.toRadians(rotation)));
		
		position.setX(x);
		position.setY(y);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
