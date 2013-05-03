package se.chalmers.tda367.group7.achtung.model;

public class PowerUp {

	private PowerUpEffect effect;
	private int timeElapsed;
	
	
	public PowerUp(PowerUpEffect powerUpEffect) {
		this.effect = powerUpEffect;
	}

	public void applyEffect(Body p) {
		((PlayerPowerUpEffect)effect).applyEffect(p);
	}
	
	public void removeEffect(Body p) {
		((PlayerPowerUpEffect)effect).removeEffect(p);
	}
	
	public void applyEffect(Round round) {
		((WorldPowerUpEffect)effect).applyEffect(round);
	}
	
	public void removeEffect(Round round) {
		((WorldPowerUpEffect)effect).removeEffect(round);
	}
	
	/**
	 * @return true if the power up is still active
	 */
	public boolean isActive() {
		return effect.getDuration() - timeElapsed > 0;
	}
	
	/**
	 * Updates the power up. This should be called every tick.
	 */
	public void update() {
		timeElapsed++;
	}
}
