package se.chalmers.tda367.group7.achtung.view;

import static org.lwjgl.opengl.GL11.*;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.PlayerSegment;
import se.chalmers.tda367.group7.achtung.model.World;

public class WorldRenderer implements Renderer {

	private World world;
	private PlayerSegmentRenderer segmentRenderer;

	public WorldRenderer(World world) {
		this.world = world;
		this.segmentRenderer = new DefaultPlayerSegmentRenderer();
	}

	@Override
	public void render() {
		for (Player p : this.world.getPlayers()) {
			glColor3f(p.getColor().getRed(), p.getColor().getGreen(), p
					.getColor().getBlue());

			for (PlayerSegment segment : p.getBody()) {
				this.segmentRenderer.render(segment);
			}

			if(!p.isNextBlank()) {
				glBegin(GL_LINES);
				glVertex2f(p.getLastPosition().getX(), p.getLastPosition().getY());
				glVertex2f(p.getPosition().getX(), p.getPosition().getY());
				glEnd();
			}

			glBegin(GL_POINTS);
			glVertex2f(p.getPosition().getX(), p.getPosition().getY());
			glEnd();
		}
	}
}