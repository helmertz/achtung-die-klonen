package se.chalmers.tda367.group7.achtung.rendering;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

class QuadLineRenderer implements LineRenderer {

	@Override
	public void drawLine(float x1, float y1, float x2, float y2, float width) {
		float length = (float) Math.sqrt(Math.pow(x2 - x1, 2)
				+ Math.pow(y2 - y1, 2));
		float xadd = width * ((y2 - y1) / (length * 2));
		float yadd = width * (x2 - x1) / (length * 2);

		glBegin(GL_QUADS);
		glVertex2f(x1 + xadd, y1 - yadd);
		glVertex2f(x1 - xadd, y1 + yadd);
		glVertex2f(x2 - xadd, y2 + yadd);
		glVertex2f(x2 + xadd, y2 - yadd);
		glEnd();
	}

}
