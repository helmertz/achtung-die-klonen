package se.chalmers.tda367.group7.achtung.rendering;

import se.chalmers.tda367.group7.achtung.model.Color;

public interface Image {
	public void drawImage(float x, float y, float width, float height);

	public void drawImage(float x, float y, float width, float height,
			Color color);

	public void drawImageCentered(float x, float y, float width, float height);

	public void drawImageCentered(float x, float y, float width, float height,
			Color color);
}
