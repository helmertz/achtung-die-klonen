package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public abstract class PopupMessage implements View {

	protected final Player winner;

	public PopupMessage(Player winner) {
		this.winner = winner;
	}

	@Override
	public abstract void render(RenderService renderer, float interpolation);
}
