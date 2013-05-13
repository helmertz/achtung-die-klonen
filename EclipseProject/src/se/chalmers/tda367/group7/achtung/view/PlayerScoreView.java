package se.chalmers.tda367.group7.achtung.view;

import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public class PlayerScoreView implements View {
	
	private final List<Player> players;
	private static final float POINTS_FONT_SIZE = 3.3f;
	private static final float NAME_FONT_SIZE = 1.4f;
	private static final int LABEL_SEPARATION = 20;
	private float height;

	public PlayerScoreView(List<Player> players, float height) {
		this.players = players;
		this.height = height;
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		// Drawing of player name and score on the side
		// TODO replace the hardcoded values
		for (Player player : this.players) {
			float sideX = renderService.getViewAreaWidth() - 175;
			float sideY = this.height * this.players.indexOf(player);

			renderService.drawString(player.getName(), sideX, sideY, NAME_FONT_SIZE,
					player.getColor());
			renderService.drawString(player.getPoints() + "", sideX,
					sideY + LABEL_SEPARATION, POINTS_FONT_SIZE, player.getColor());
		}
	}
}