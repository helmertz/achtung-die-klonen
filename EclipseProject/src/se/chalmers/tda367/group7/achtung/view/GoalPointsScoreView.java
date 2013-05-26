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

		drawGoalText(renderService, sideX, sideY);
		drawGoalScore(renderService, sideX, sideY);
	}

	private void drawGoalText(RenderService renderService, float sideX,
			float sideY) {
		renderService.drawString("Goal", sideX + 6, sideY, NAME_FONT_SIZE,
				Color.WHITE);
	}

	private void drawGoalScore(RenderService renderService, float sideX,
			float sideY) {
		renderService.drawString(this.goalScore + "", sideX, sideY
				+ LABEL_SEPARATION + (float) 0.2, POINTS_FONT_SIZE
				+ (float) 1.1, Color.WHITE);
	}
}