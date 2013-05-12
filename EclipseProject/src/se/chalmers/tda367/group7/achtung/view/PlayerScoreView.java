package se.chalmers.tda367.group7.achtung.view;

import java.util.List;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PlayerScoreView implements View {
	private final List<Player> players;

	public PlayerScoreView(List<Player> players) {
		this.players = players;
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		// Drawing of player name and score on the side
		// TODO replace the hardcoded values
		for (Player player : this.players) {
			float sideX = renderService.getViewAreaWidth() - 150;
			float sideY = 100 * this.players.indexOf(player);

			renderService.drawString(player.getName(), sideX, sideY, 1f,
					player.getColor());
			renderService.drawString(player.getPoints() + "", sideX,
					sideY + 20, 3f, player.getColor());
		}
	}
}