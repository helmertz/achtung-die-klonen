package se.chalmers.tda367.group7.achtung.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class WorldView implements View, PropertyChangeListener {

	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private List<PowerUpEntityView> powerUpView = new ArrayList<PowerUpEntityView>();
	private MapView mapView;
	private Game game;
	
	public WorldView(Game game) {
		this.game = game;
		for(Player p : game.getPlayers()) {
			playerViews.add(new PlayerView(p));
		}
		updateMapView();
	}
	
	public void render(RenderService renderer, float interpolation) {
		
		mapView.render(renderer, interpolation);
		
		for (View view : playerViews) {
			view.render(renderer, interpolation);
		}
		
		for(View view : powerUpView) {
			view.render(renderer, interpolation);
		}
		
	}
	
	private void updatePowerUpViews() {

		powerUpView.clear();
		
		for(PowerUpEntity e : game.getCurrentRound().getPowerUpEntities()) {
			powerUpView.add(new PowerUpEntityView(e));
		}
	}
	
	private void updateMapView() {
		Round round = game.getCurrentRound();
		round.addPropertyChangeListener(this);
		mapView = new MapView(round.getMap());
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("PowerUp")) {
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("newRound")) {
			updateMapView();
			updatePowerUpViews();
		}
	}
}
