package se.chalmers.tda367.group7.achtung.view;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.rendering.Image;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PowerUpEntityView implements View {

	private static Image powerUpBackground;
	
	private PowerUpEntity powerUpEntity;
	private Image image;
	
	public PowerUpEntityView(PowerUpEntity powerUpEntity) {
		this.powerUpEntity = powerUpEntity;
	}
	
	public void render(RenderService renderService, float interpolation) {
		Position pos = powerUpEntity.getPosition();
		Color color = null;
		if (powerUpEntity.getType() == PowerUpEntity.Type.SELF) {
			color = Color.GREEN;
		} else if (powerUpEntity.getType() == PowerUpEntity.Type.EVERYONE_ELSE) {
			color = Color.RED;
		} else {
			color = Color.BLUE;
		}
		
		// TODO find a better way of loading these
		if(powerUpBackground == null) {
			try {
				powerUpBackground = renderService.getImage("powerup-background.png");				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		if(this.image == null) {
			try {
				this.image = renderService.getImage("powerups/1.png");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		powerUpBackground.drawImageCentered(pos.getX(), pos.getY(), powerUpEntity.getDiameter(), powerUpEntity.getDiameter(), color);
		image.drawImageCentered(pos.getX(), pos.getY(), powerUpEntity.getDiameter(), powerUpEntity.getDiameter());
	}
}
