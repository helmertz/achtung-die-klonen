package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class Player {

	private List<PowerUp> powerUps;
	private Body body = new Body();
	
	public Player(Color color, String name) {
		powerUps = new ArrayList<PowerUp>();
	}
	
	public void setPosition(Vector2f position) {
		body.setHeadPosition(position);
	}
	
	/**
	 * Update the player.
	 */
	public void update() {
		updatePowerUps();
	}
	
	/**
	 * Adds a power up to the player. When the player collides with a PowerUpEntity
	 * it will receive an effect using this method.
	 * @param effect - the effect to add to the player
	 */
	public void addPowerUp(PlayerPowerUpEffect effect) {
		PowerUp p = new PowerUp(effect);
		p.applyEffect(this);
		powerUps.add(p);
	}
	
	/**
	 * Updates all power ups, removes them if inactive.
	 */
	private void updatePowerUps() {
		for (PowerUp p : powerUps) {
			p.update();
			if (!p.isActive()) {
				p.removeEffect(this);
				powerUps.remove(p);
			}
		}
	}
}
