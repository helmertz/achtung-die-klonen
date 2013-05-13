package se.chalmers.tda367.group7.achtung.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public class GameView implements View, PropertyChangeListener {

	private final List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private final List<PowerUpEntityView> powerUpView = new ArrayList<PowerUpEntityView>();
	private PlayerScoreView scoreView;

	private MapView mapView;
	private final Game game;

	private static final String NEXT_ROUND_MESSAGE_1 = "Round over";
	private static final String NEXT_ROUND_MESSAGE_2 = "The winner is";
	private static final String NEXT_ROUND_MESSAGE_3 = "Press space to continue";
	private static final Color NEXT_ROUND_COLOR = new Color(0, 0, 0, 0.5f);

	private static final String WON_GAME_MESSAGE_1 = "Game over!";
	private static final String WON_GAME_MESSAGE_2 = "The winner is";
	private static final String WON_GAME_MESSAGE_3 = "Press esc to return to menu";
	private static final Color WON_GAME_COLOR = new Color(0, 0, 0, 0.5f);

	public GameView(Game game) {
		this.game = game;
		updateMapView();
		createScoreView();
		addPlayerViews();
	}

	private void createScoreView() {
		// because 8 is max player amount
		float scoreViewHeight = mapView.getMap().getHeight() / 8;
		this.scoreView = new PlayerScoreView(game.getPlayers(), scoreViewHeight);
	}

	private void addPlayerViews() {
		for (Player p : this.game.getPlayers()) {
			this.playerViews.add(new PlayerView(p));
		}
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

		this.scoreView.render(renderer, interpolation);

		// If over draws a box displaying the winner, and instructions on how to
		// continue.
		if (this.game.isOver()) {
			// TODO perhaps not hardcode as much here. Maybe redo size handling
			// in font.
			float viewWidth = renderer.getViewAreaWidth();
			float viewHeight = renderer.getViewAreaHeight();
			float centerX = viewWidth / 2;
			float centerY = viewHeight / 2;
			float width = 600;
			float height = 240;
			String name = "Player1!";
			renderer.drawFilledRect(centerX - width / 2, centerY - height / 2,
					width, height, WON_GAME_COLOR);
			renderer.drawStringCentered(WON_GAME_MESSAGE_1, centerX,
					centerY - 55, 2.5f);
			renderer.drawStringCentered(WON_GAME_MESSAGE_2, centerX,
					centerY - 15, 1.5f);
			renderer.drawStringCentered(name, centerX, centerY + 60, 3,
					new Color(255, 255, 0));
			renderer.drawStringCentered(WON_GAME_MESSAGE_3, centerX,
					centerY + 105, 1f);
		} else if (!this.game.getCurrentRound().isRoundActive()) {

			// TODO perhaps not hardcode as much here. Maybe redo size handling
			// in font.
			float viewWidth = renderer.getViewAreaWidth();
			float viewHeight = renderer.getViewAreaHeight();
			float centerX = viewWidth / 2;
			float centerY = viewHeight / 2;
			float width = 600;
			float height = 240;
			Player winner = this.game.getCurrentRound().getWinner();
			String name = winner.getName();
			renderer.drawFilledRect(centerX - width / 2, centerY - height / 2,
					width, height, NEXT_ROUND_COLOR);
			renderer.drawStringCentered(NEXT_ROUND_MESSAGE_1, centerX,
					centerY - 55, 2.5f);
			renderer.drawStringCentered(NEXT_ROUND_MESSAGE_2, centerX,
					centerY - 15, 1.5f);
			renderer.drawStringCentered(name, centerX, centerY + 60, 3,
					winner.getColor());
			renderer.drawStringCentered(NEXT_ROUND_MESSAGE_3, centerX,
					centerY + 105, 1f);
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
		if (evt.getPropertyName().equals("PowerUp")) {
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("NewRound")) {
			updateMapView();
			updatePowerUpViews();
		} else if (evt.getPropertyName().equals("MapChanged")) {
			updateMapView();
		}
	}
}
