package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public abstract class AbstractPopupMessage implements View {

	protected final Player winner;

	public AbstractPopupMessage(Player winner) {
		this.winner = winner;
	}

	@Override
	public abstract void render(RenderService renderer, float interpolation);
}
