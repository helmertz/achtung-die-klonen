package se.chalmers.tda367.group7.achtung.view;

import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PlayerScoreView extends AbstractScoreView {

	private final List<Player> players;

	public PlayerScoreView(List<Player> players, float height) {
		super(height);
		this.players = players;
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		// Drawing of player name and score on the side
		// TODO replace the hardcoded values
		for (Player player : this.players) {
			float sideX = renderService.getViewAreaWidth() - 175;
			float sideY = 150 + (this.height * this.players.indexOf(player));

			renderService.drawString(player.getName(), sideX, sideY,
					NAME_FONT_SIZE, player.getColor());
			renderService.drawString(player.getPoints() + "", sideX, sideY
					+ LABEL_SEPARATION, POINTS_FONT_SIZE, player.getColor());
			if (player.getBody().isDead()) {
				drawCurrentRoundScore(renderService, player, sideX, sideY);
			}
		}
	}

	private void drawCurrentRoundScore(RenderService renderService,
			Player player, float sideX, float sideY) {
		renderService.drawString("+" + player.getRoundPoints() + "",
				sideX + 100, sideY + LABEL_SEPARATION + 30, POINTS_FONT_SIZE
						* (float) 0.5, player.getColor());
	}
}