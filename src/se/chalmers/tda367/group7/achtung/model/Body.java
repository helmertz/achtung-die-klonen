package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing the state of the body.
 */
public class Body {

	public static final float DEFAULT_WIDTH = 10;

	private float width;
	
	private Head head;
	private List<BodySegment> bodySegments;
	
	
	public Body (Position position) {
		width = DEFAULT_WIDTH;
		
		// Width of the body is the same as the diameter of the head.
		head = new Head(position, width);
		bodySegments = new ArrayList<BodySegment>();
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

	public void setHeadPosition(Position position) {
		if(this.head == null) {
			head = new Head(position, width);
		} else {
			head.setPosition(position);
		}
	}

	/**
	 * Moves the body by the delta values.
	 * 
	 * @param dx - the length to move in the x-axis
	 * @param dy - the length to move in the y-axis
	 */
	public void update(float dx, float dy) {
		
		// Update head with delta positions
		Position headPosition = head.getPosition();
		float x = headPosition.getX();
		float y = headPosition.getY();
		
		headPosition.setX(x + dx);
		headPosition.setY(y + dy);
		
		// Create a new body segment and add to body segment list.
		// Will use previous body segment end as start, so that there are no duplicated variables.
		// TODO - This will have to be thought through when holes is implemented.
		if (bodySegments.isEmpty()) {
			addBodySegment(new BodySegment(new Position(x, y), new Position(x + dx, y + dy), width));
		} else {
			Position start = bodySegments.get(bodySegments.size() - 1).getEnd();
			addBodySegment(new BodySegment(start, new Position(x + dx, y + dy), width));
		}
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		head.setDiameter(width);
	}
}
