package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class GoalPointsScoreView extends AbstractScoreView {

	private final int goalScore;

	public GoalPointsScoreView(float height, int goalScore) {
		super(height);
		this.goalScore = goalScore;
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		// TODO replace the hardcoded values
		float sideX = renderService.getViewAreaWidth() - 175;
		float sideY = 20;

		renderService.drawString("Goal", sideX + 6, sideY,
				NAME_FONT_SIZE, Color.WHITE);
		renderService.drawString(this.goalScore + "", sideX, sideY
				+ LABEL_SEPARATION, POINTS_FONT_SIZE, Color.WHITE);
	}
}