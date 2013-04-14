package se.chalmers.tda367.group7.achtung.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<PowerUp> powerUps;
	
	public Player(Color color, String name) {
		powerUps = new ArrayList<PowerUp>();
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
