package se.chalmers.tda367.group7.achtung.model.powerups;

import se.chalmers.tda367.group7.achtung.model.Body;
import se.chalmers.tda367.group7.achtung.model.BodyPowerUpEffect;
import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.model.PowerUpEntity.Type;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.model.RoundPowerUpEffect;

public class BackgroundColorPowerUp implements BodyPowerUpEffect,
		RoundPowerUpEffect {

	private static final String NAME = "background-change";
	private static final int DURATION = 75;
	private static final boolean STACKABLE = false;
	private static final float WEIGHT = 1;

	private Color newColor;

	@Override
	public int getDuration() {
		return DURATION;
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
		if (round.getMap().getColor() == this.newColor) {
			round.setMapColor(Map.DEFAULT_COLOR);
		}
	}

	@Override
	public Type[] getAllowedTypes() {
		return new Type[] { Type.SELF };
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isStackable() {
		return STACKABLE;
	}

	@Override
	public String toString() {
		return NAME;
	}

	@Override
	public float getWeight() {
		return WEIGHT;
	}

}
