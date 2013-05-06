package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.rendering.Image;

public class LWJGLImage implements Image {

	private static final Map<String, Image> LOADED = new HashMap<String, Image>();

	private final int texID;

	private LWJGLImage(String path) throws IOException {
		this.texID = Utils.loadTexture(path);
	}

	public static Image getImage(String path) throws IOException {
		Image image = LOADED.get(path);
		if (image == null) {
			image = new LWJGLImage(path);
			LOADED.put(path, image);
		}
		return image;
	}

	@Override
	public void drawImage(float x, float y, float width, float height) {
		Utils.drawTexture(this.texID, x, y, width, height);
	}

	@Override
	public void drawImage(float x, float y, float width, float height,
			Color color) {
		Utils.drawTexture(this.texID, x, y, width, height, color);
	}

	@Override
	public void drawImageCentered(float x, float y, float width, float height) {
		drawImage(x - width / 2, y - height / 2, width, height);
	}

	@Override
	public void drawImageCentered(float x, float y, float width, float height,
			Color color) {
		drawImage(x - width / 2, y - height / 2, width, height, color);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		glDeleteTextures(this.texID);
	}
}
