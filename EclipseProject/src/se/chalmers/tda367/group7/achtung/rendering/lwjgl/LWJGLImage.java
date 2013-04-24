package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.rendering.Image;

public class LWJGLImage implements Image {

	private int texID;

	public LWJGLImage(String path) throws IOException {
		this.texID = Utils.loadTexture(path);
	}

	@Override
	public void drawImage(float x, float y, float width, float height) {
		Utils.drawTexture(texID, x, y, width, height);
	}

	@Override
	public void drawImage(float x, float y, float width, float height,
			Color color) {
		Utils.drawTexture(texID, x, y, width, height, color);
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

}
