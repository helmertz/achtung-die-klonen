package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class MapView implements View{

	private Map map;
	
	public MapView(Map map) {
		this.map = map;
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		// Adds 200 so that there's a usable area to the right of the world,
			// should perhaps not be hardcoded later
			renderer.setBackgroundColor(map.getColor());
			renderer.setViewAreaSize(map.getWidth() + 200, map.getHeight());
			
			renderer.drawFilledRect(0, 0, map.getWidth(), map.getHeight(), map.getColor());
				

			// Draws line around the world
			renderer.drawLinedRect(0, 0, map.getWidth(), map.getHeight(), 5, Color.GRAY);
	}

}
