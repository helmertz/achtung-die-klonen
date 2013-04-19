package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import java.io.IOException;

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

}
