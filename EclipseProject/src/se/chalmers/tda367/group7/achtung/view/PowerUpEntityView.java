package se.chalmers.tda367.group7.achtung.view;

import java.io.IOException;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Position;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity;
import se.chalmers.tda367.group7.achtung.rendering.Image;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;

public class PowerUpEntityView implements View {

	private static Image powerUpBackground;

	private final PowerUpEntity powerUpEntity;
	private Image image;

	public PowerUpEntityView(PowerUpEntity powerUpEntity) {
		this.powerUpEntity = powerUpEntity;
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

		// TODO find a better way of loading these
		if (powerUpBackground == null) {
			try {
				powerUpBackground = renderService
						.getImage("powerup-background.png");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		if (this.image == null) {
			// TODO check file system if image is there
			String imgName = powerUpEntity.getPowerUpEffect().getImageName();

			if (imgName != "") {
				System.out.println("asfgsd");
				try {
					this.image = renderService.getImage("powerups/" + imgName + ".png");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
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
