package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Player;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class RoundOverMessage extends AbstractWinnerPopupMessage {

	private static final String NEXT_ROUND_MESSAGE_1 = "Round over";
	private static final String NEXT_ROUND_MESSAGE_2 = "The winner is";
	private static final String NEXT_ROUND_MESSAGE_3 = "Press space to continue";
	private static final Color NEXT_ROUND_COLOR = new Color(0, 0, 0, 0.5f);

	public RoundOverMessage(Player winner) {
		super(winner);
	}

	@Override
	public void render(RenderService renderer, float interpolation) {
		// TODO perhaps not hardcode as much here. Maybe redo size handling
		// in font.
		float viewWidth = renderer.getViewAreaWidth();
		float viewHeight = renderer.getViewAreaHeight();
		float centerX = viewWidth / 2;
		float centerY = viewHeight / 2;
		float width = 600;
		float height = 240;
		String name = this.winner.getName();

		renderer.drawFilledRect(centerX - width / 2, centerY - height / 2,
				width, height, NEXT_ROUND_COLOR);
		renderer.drawStringCentered(NEXT_ROUND_MESSAGE_1, centerX,
				centerY - 55, 2.5f);
		renderer.drawStringCentered(NEXT_ROUND_MESSAGE_2, centerX,
				centerY - 15, 1.5f);
		renderer.drawStringCentered(name, centerX, centerY + 60, 3,
				this.winner.getColor());
		renderer.drawStringCentered(NEXT_ROUND_MESSAGE_3, centerX,
				centerY + 105, 1f);
	}

}
