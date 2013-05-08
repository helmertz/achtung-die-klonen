package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class MapView implements View {

	private final Map map;

	public MapView(Map map) {
		this.map = map;
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		// Adds 200 so that there's a usable area to the right of the world
		// TODO should not be hardcoded later, nor set here
		renderer.setViewAreaSize(this.map.getWidth() + 200,
				this.map.getHeight());
		renderer.setBackgroundColor(Color.DARK_GRAY);

		renderer.drawFilledRect(0, 0, this.map.getWidth(),
				this.map.getHeight(), this.map.getColor());

		// Draws line around the world
		renderer.drawLinedRect(0, 0, this.map.getWidth(), this.map.getHeight(),
				5, Color.GRAY);
	}
}
