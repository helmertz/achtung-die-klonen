package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class MapView implements View {

	private static final Color NORMAL_WALL_COLOR = Color.WHITE;
	private static final Color PASSTHROUGH_WALL_COLOR = Color.GRAY;

	private final Map map;
	private final float scoreWidthOffset;

	public MapView(Map map, float scoreWidthOffset) {
		this.map = map;
		this.scoreWidthOffset = scoreWidthOffset;
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		// Adds 200 so that there's a usable area to the right of the world
		renderer.setViewAreaSize(this.map.getWidth() + this.scoreWidthOffset,
				this.map.getHeight());

		renderer.drawFilledRect(0, 0, this.map.getWidth(),
				this.map.getHeight(), this.map.getColor());

		if (this.map.isWallsActive()) {
			drawWall(renderer, NORMAL_WALL_COLOR);
		} else {
			drawWall(renderer, PASSTHROUGH_WALL_COLOR);
		}
	}

	private void drawWall(RenderService renderer, Color color) {
		renderer.drawLinedRect(0, 0, this.map.getWidth(),
				this.map.getHeight(), 5, color);
	}

	public Map getMap() {
		return this.map;
	}
}
