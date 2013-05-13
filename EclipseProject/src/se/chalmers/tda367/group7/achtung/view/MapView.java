package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public class MapView implements View {

	private final Map map;
	private float scoreWidthOffset;

	public MapView(Map map, float scoreWidthOffset) {
		this.map = map;
		this.scoreWidthOffset = scoreWidthOffset;
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		// Adds 200 so that there's a usable area to the right of the world
		renderer.setViewAreaSize(this.map.getWidth() + scoreWidthOffset,
				this.map.getHeight());
		renderer.setBackgroundColor(Color.DARK_GRAY);

		renderer.drawFilledRect(0, 0, this.map.getWidth(),
				this.map.getHeight(), this.map.getColor());

		// Draws line around the world
		if (this.map.isWallsActive()) {
			renderer.drawLinedRect(0, 0, this.map.getWidth(), this.map.getHeight(),
					5, Color.WHITE);
		} else {
			renderer.drawLinedRect(0, 0, this.map.getWidth(), this.map.getHeight(),
					5, Color.GRAY);
		}
	}

	public Map getMap() {
		return this.map;
	}
}
