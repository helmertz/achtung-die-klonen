package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.rendering.lwjgl.RenderService;

public interface View {
	public void render(RenderService renderService, float interpolation);
}
