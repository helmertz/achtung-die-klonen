package se.chalmers.tda367.group7.achtung.model;

public class PowerUp {

	private PlayerPowerUpEffect effect;
	private final int durationEffectIsActiveFor;
	private int timeElapsed;
	
	
	public PowerUp(PlayerPowerUpEffect effect) {
		this.effect = effect;
		durationEffectIsActiveFor = this.effect.getDuration();
	}

	public void applyEffect(Player p) {
		effect.applyEffect(p);
	}
	
	public void removeEffect(Player p) {
		effect.removeEffect(p);
	}
	
	/**
	 * @return true if the power up is still active
	 */
	public boolean isActive() {
		return durationEffectIsActiveFor - timeElapsed > 0;
	}
	
	/**
	 * Updates the power up. This should be called every tick.
	 */
	public void update() {
		timeElapsed++;
	}
}
