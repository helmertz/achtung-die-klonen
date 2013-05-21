package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public abstract class AbstractScoreView implements View {

	protected static final float POINTS_FONT_SIZE = 3.3f;
	protected static final int LABEL_SEPARATION = 20;
	protected static final float NAME_FONT_SIZE = 1.4f;
	protected final float height;

	public AbstractScoreView(float height) {
		this.height = height;
	}

	@Override
	public abstract void render(RenderService renderService, float interpolation);
}
