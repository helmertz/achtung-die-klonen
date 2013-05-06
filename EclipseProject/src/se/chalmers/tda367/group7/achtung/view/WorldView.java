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

	private final List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private final List<PowerUpEntityView> powerUpView = new ArrayList<PowerUpEntityView>();
	private MapView mapView;
	private final Game game;

	public WorldView(Game game) {
		this.game = game;
		for (Player p : game.getPlayers()) {
			this.playerViews.add(new PlayerView(p));
		}
		updateMapView();
	}

	@Override
	public void render(RenderService renderer, float interpolation) {

		this.mapView.render(renderer, interpolation);

		for (View view : this.playerViews) {
			view.render(renderer, interpolation);
		}

		for (View view : this.powerUpView) {
			view.render(renderer, interpolation);
		}

	}

	private void updatePowerUpViews() {

		this.powerUpView.clear();

		for (PowerUpEntity e : this.game.getCurrentRound().getPowerUpEntities()) {
			this.powerUpView.add(new PowerUpEntityView(e));
		}
	}

	private void updateMapView() {
		Round round = this.game.getCurrentRound();
		round.addPropertyChangeListener(this);
		this.mapView = new MapView(round.getMap());

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("PowerUp")) {
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("NewRound")) {
			updateMapView();
			updatePowerUpViews();
		}
	}
}
