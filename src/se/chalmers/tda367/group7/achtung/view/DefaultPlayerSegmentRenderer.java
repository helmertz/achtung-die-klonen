package se.chalmers.tda367.group7.achtung.view;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;

import se.chalmers.tda367.group7.achtung.model.PlayerSegment;


public class DefaultPlayerSegmentRenderer implements PlayerSegmentRenderer {

	@Override
	public void render(PlayerSegment segment) {
		glLineWidth(segment.getWidth());
		glBegin(GL_LINES);
		Vector2f start = segment.getStart();
		Vector2f end = segment.getEnd();
		glVertex2f(start.getX(), start.getY());
		glVertex2f(end.getX(), end.getY());
		glEnd();
	}

}
