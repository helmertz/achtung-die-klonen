package se.chalmers.tda367.group7.achtung.view;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.World;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class WorldView implements View {

	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private World world;
	
	public WorldView(World world) {
		this.world = world;
		for(Player p : world.getPlayers()) {
			playerViews.add(new PlayerView(p));
		}
	}
	
	public void render(RenderService renderer, float interpolation) {
		renderer.setViewAreaSize(world.getWidth(), world.getHeight());
		renderer.drawLinedRect(0, 0, world.getWidth(), world.getHeight(), 5, Color.WHITE);
		for(PlayerView view : playerViews) {
			view.render(renderer, interpolation);
		}
	}
}
