package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public abstract class PopupMessage implements View {

	protected static final String NEXT_ROUND_MESSAGE_1 = "Round over";
	protected static final String NEXT_ROUND_MESSAGE_2 = "The winner is";
	protected static final String NEXT_ROUND_MESSAGE_3 = "Press space to continue";
	protected static final Color NEXT_ROUND_COLOR = new Color(0, 0, 0, 0.5f);

	protected static final String WON_GAME_MESSAGE_1 = "Game over!";
	protected static final String WON_GAME_MESSAGE_2 = "The winner is";
	protected static final String WON_GAME_MESSAGE_3 = "Press esc to return to menu";
	protected static final Color WON_GAME_COLOR = new Color(0, 0, 0, 0.5f);

	protected final Player winner;

	public PopupMessage(Player winner) {
		this.winner = winner;
	}

	@Override
	public abstract void render(RenderService renderer, float interpolation);
}
