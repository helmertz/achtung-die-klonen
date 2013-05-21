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

public class GameView implements View, PropertyChangeListener {

	private final List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private final List<PowerUpEntityView> powerUpView = new ArrayList<PowerUpEntityView>();
	private AbstractScoreView scoreView;
	private AbstractScoreView goalScoreView;
	
	private MapView mapView;
	private final Game game;

	public GameView(Game game) {
		this.game = game;
		updateMapView();
		createScoreViews();
		addPlayerViews();
	}

	private void createScoreViews() {
		// because 8 is max player amount
		float scoreViewHeight = ((this.mapView.getMap().getHeight() - 150) / 8);
		this.scoreView = new PlayerScoreView(this.game.getPlayers(),
				scoreViewHeight);
		this.goalScoreView = new GoalPointsScoreView(scoreViewHeight, this.game.getGoalPoints());
	}

	private void addPlayerViews() {
		for (Player p : this.game.getPlayers()) {
			this.playerViews.add(new PlayerView(p));
		}
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		this.goalScoreView.render(renderer, interpolation);
		this.mapView.render(renderer, interpolation);

		for (View view : this.playerViews) {
			view.render(renderer, interpolation);
		}

		for (View view : this.powerUpView) {
			view.render(renderer, interpolation);
		}

		this.scoreView.render(renderer, interpolation);

		// If over draws a box displaying the winner, and instructions on how to
		// continue.
		if (this.game.isOver()) {
			Player winner = this.game.getGameWinner();
			AbstractPopupMessage message = new GameOverMessage(winner);
			message.render(renderer, interpolation);
		} else if (!this.game.getCurrentRound().isRoundActive()) {
			Player winner = this.game.getCurrentRound().getWinner();
			AbstractPopupMessage message = new RoundOverMessage(winner);
			message.render(renderer, interpolation);
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
		// TODO: this is still hardcoded, how to fix?
		this.mapView = new MapView(round.getMap(), 200);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().contains("PowerUp")) {
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("NewRound")) {
			updateMapView();
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("MapChanged")) {
			updateMapView();
		}
	}
}
