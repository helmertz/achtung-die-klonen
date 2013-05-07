package se.chalmers.tda367.group7.achtung.model;

public class PowerUp {

	private final PowerUpEffect effect;
	private int timeElapsed;

	public PowerUp(PowerUpEffect powerUpEffect) {
		this.effect = powerUpEffect;
	}

	public void applyEffect(Body p) {
		((PlayerPowerUpEffect) this.effect).applyEffect(p);
	}

	public void removeEffect(Body p) {
		((PlayerPowerUpEffect) this.effect).removeEffect(p);
	}

	public void applyEffect(Round round) {
		((WorldPowerUpEffect) this.effect).applyEffect(round);
	}

	public void removeEffect(Round round) {
		((WorldPowerUpEffect) this.effect).removeEffect(round);
	}

	/**
	 * @return true if the power up is still active
	 */
	public boolean isActive() {
		return this.effect.getDuration() - this.timeElapsed > 0;
	}

	/**
	 * Updates the power up. This should be called every tick.
	 */
	public void update() {
		this.timeElapsed++;
	}
	
	public PowerUpEffect getEffect() {
		return this.effect;
	}
	
	public int getTimeLeft() {
		return this.effect.getDuration() - this.timeElapsed;
	}
}
