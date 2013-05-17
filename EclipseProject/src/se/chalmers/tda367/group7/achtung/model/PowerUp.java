package se.chalmers.tda367.group7.achtung.model;

/**
 * A class that represents a power-up in an applied state.
 * 
 * It keeps track of how long it has lasted and when the effect should be
 * removed.
 * 
 * @param <T>
 *            the type of effect this keeps track of
 */
public class PowerUp<T extends PowerUpEffect> {

	private final T effect;
	private int timeElapsed;

	public PowerUp(T effect) {
		this.effect = effect;
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

	public T getEffect() {
		return this.effect;
	}

	public int getTimeLeft() {
		return this.effect.getDuration() - this.timeElapsed;
	}

	public void resetTimer() {
		this.timeElapsed = 0;
	}

}
