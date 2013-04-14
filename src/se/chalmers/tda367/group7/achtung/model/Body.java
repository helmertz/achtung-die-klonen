package se.chalmers.tda367.group7.achtung.model;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Body {
	
	private Head head;
	private List<BodySegment> bodySegments = new ArrayList<BodySegment>();
	
	// TODO: don't hardcode this in this class probably, but would be weird
	// to have a setHeadDiameter method in this class, makes no sense from an
	// interface perspective
	private float headDiameter = 10;
	
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
	
}
