package se.chalmers.tda367.group7.achtung.view;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.rendering.Image;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.RenderServiceFactory;

public class PowerUpEntityView implements View {
	private static Image powerUpBackground;

	static {
		try {
			powerUpBackground = RenderServiceFactory.getRenderService()
					.getImage("powerup-background.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private final PowerUpEntity powerUpEntity;
	private Image image;

	public PowerUpEntityView(PowerUpEntity powerUpEntity) {
		this.powerUpEntity = powerUpEntity;

		// Asks the render service for an image object
		String name = this.powerUpEntity.getPowerUpEffect().getName();
		String fileName = "powerups/" + name + ".png";
		try {
			this.image = RenderServiceFactory.getRenderService().getImage(
					fileName);
		} catch (IOException e1) {
			System.err.println(fileName + " was not found");

			// If image not found, load question mark icon instead
			try {
				this.image = RenderServiceFactory.getRenderService().getImage(
						"powerups/unknown.png");
			} catch (IOException e2) {
				e2.printStackTrace();
				System.exit(1);
			}
		}
	}

	@Override
	public void render(RenderService renderService, float interpolation) {
		Position pos = this.powerUpEntity.getPosition();
		Color color = null;
		if (this.powerUpEntity.getType() == PowerUpEntity.Type.SELF) {
			color = Color.GREEN;
		} else if (this.powerUpEntity.getType() == PowerUpEntity.Type.EVERYONE_ELSE) {
			color = Color.RED;
		} else {
			color = Color.BLUE;
		}

		powerUpBackground.drawImageCentered(pos.getX(), pos.getY(),
				this.powerUpEntity.getDiameter(),
				this.powerUpEntity.getDiameter(), color);

		if (this.image != null) {
			this.image.drawImageCentered(pos.getX(), pos.getY(),
					this.powerUpEntity.getDiameter(),
					this.powerUpEntity.getDiameter());
		}
	}
}
