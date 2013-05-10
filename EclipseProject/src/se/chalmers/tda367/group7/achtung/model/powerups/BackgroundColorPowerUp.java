package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;

public class BackgroundColorPowerUp implements BodyPowerUpEffect, RoundPowerUpEffect {
	
	private Color newColor;

	private static final String imageName = "";

	@Override
	public int getDuration() {
		return 75;
	}

	@Override
	public void applyEffect(Body body) {
		this.newColor = body.getColor();
	}

	@Override
	public void removeEffect(Body body) {
	}

	@Override
	public void applyEffect(Round round) {
		round.setMapColor(this.newColor);
	}

	@Override
	public void removeEffect(Round round) {
		if(round.getMap().getColor() == newColor) {
			round.setMapColor(Map.DEFAULT_COLOR);
		}
	}
	
	@Override
	public boolean isTypeOk(Type type) {
		return type == Type.SELF;
	}
	
	@Override
	public String getImageName() {
		return imageName;
	}


}
