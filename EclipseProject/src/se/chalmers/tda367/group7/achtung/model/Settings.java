package se.chalmers.tda367.group7.achtung.model;

public class Settings {

	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 6;
	private static final float DEFAULT_ROTATION_SPEED = 6f;
	private static final double CHANCE_OF_HOLE = 0.015;

	private float width;
	private float speed;
	private float rotationSpeed;
	private double chanceOfHole;
	
	private Settings() {
		// TODO - Load settings from file.
		resetToDefaults();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public double getChanceOfHole() {
		return chanceOfHole;
	}

	public void setChanceOfHole(double chanceOfHole) {
		this.chanceOfHole = chanceOfHole;
	}

	public static synchronized Settings getInstance() {
		return new Settings();
	}
	
	/**
	 * Saves the currently loaded settings to a file.
	 * These will be accessible next time the game is started.
	 */
	public void saveSettings() {
		// TODO - Save current settings to file.
	}
	
	/**
	 * Reset all settings to the default values.
	 */
	public void resetToDefaults() {

		width = DEFAULT_WIDTH;
		speed = DEFAULT_SPEED;
		rotationSpeed = DEFAULT_ROTATION_SPEED;
		chanceOfHole = CHANCE_OF_HOLE;
	}
}
