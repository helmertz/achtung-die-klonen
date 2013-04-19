package se.chalmers.tda367.group7.achtung.view;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PowerUpEntityView implements View {

	private PowerUpEntity powerUpEntity;
	private static final int RENDER_QUALITY = 100;

	public PowerUpEntityView(PowerUpEntity powerUpEntity) {
		this.powerUpEntity = powerUpEntity;
	}
	
	public void render(RenderService renderService, float interpolation) {
		Position pos = powerUpEntity.getPosition();
		renderService.drawCircleCentered(pos.getX(), pos.getY(), powerUpEntity.getDiameter(), RENDER_QUALITY, new Color(255,100,255));
	}
}
