package se.chalmers.tda367.group7.achtung.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.model.World;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class WorldView implements View, PropertyChangeListener {

	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private List<PowerUpEntityView> powerUpView = new ArrayList<PowerUpEntityView>();
	private World world;
	
	public WorldView(World world) {
		this.world = world;
		for(Player p : world.getPlayers()) {
			playerViews.add(new PlayerView(p));
		}

		//		updatePowerUpViews();
		
	}
	
	public void render(RenderService renderer, float interpolation) {
		
		// Adds 200 so that there's a usable area to the right of the world,
		// should perhaps not be hardcoded later
		renderer.setBackgroundColor(Color.DARK_GRAY);
		renderer.setViewAreaSize(world.getWidth() + 200, world.getHeight());
		
		renderer.drawFilledRect(0, 0, world.getWidth(), world.getHeight(), world.getColor());

		// TODO only call this when an event from world is sent
		//updatePowerUpViews();

		for (View view : playerViews) {
			view.render(renderer, interpolation);
		}
		
		for(View view : powerUpView) {
			view.render(renderer, interpolation);
		}
		
		// Draws line around the world
		renderer.drawLinedRect(0, 0, world.getWidth(), world.getHeight(), 5, Color.GRAY);
	}
	
	private void updatePowerUpViews() {

		if (world.getPowerUpEntities().size() != powerUpView.size()) {
			powerUpView.clear();
			
			for(PowerUpEntity e : world.getPowerUpEntities()) {
				powerUpView.add(new PowerUpEntityView(e));
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("PowerUp")) {
			updatePowerUpViews();
		}
	}
}
