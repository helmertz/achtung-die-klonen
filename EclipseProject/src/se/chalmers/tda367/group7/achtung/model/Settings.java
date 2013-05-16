package se.chalmers.tda367.group7.achtung.model;

public class Settings {

	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 6;
	private static final float DEFAULT_ROTATION_SPEED = 6f;
	private static final double DEFAULT_CHANCE_OF_HOLE = 0.015;
	private static final float DEFAULT_POWER_UP_CHANCE = 0.01f;

	private static Settings instance;

	private float width;
	private float speed;
	private float rotationSpeed;
	private double chanceOfHole;
	private float powerUpChance;

	private Settings() {
		// TODO - Load settings from file.
		resetToDefaults();
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRotationSpeed() {
		return this.rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public double getChanceOfHole() {
		return this.chanceOfHole;
	}

	public void setChanceOfHole(double chanceOfHole) {
		this.chanceOfHole = chanceOfHole;
	}

	public float getPowerUpChance() {
		return this.powerUpChance;
	}

	public void setPowerUpChance(float powerUpChance) {
		this.powerUpChance = powerUpChance;
	}

	public static synchronized Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	/**
	 * Saves the currently loaded settings to a file. These will be accessible
	 * next time the game is started.
	 */
	public void saveSettings() {
		// TODO - Save current settings to file.
	}

	/**
	 * Reset all settings to the default values.
	 */
	public void resetToDefaults() {

		this.width = DEFAULT_WIDTH;
		this.speed = DEFAULT_SPEED;
		this.rotationSpeed = DEFAULT_ROTATION_SPEED;
		this.chanceOfHole = DEFAULT_CHANCE_OF_HOLE;
		this.powerUpChance = DEFAULT_POWER_UP_CHANCE;
	}
}
