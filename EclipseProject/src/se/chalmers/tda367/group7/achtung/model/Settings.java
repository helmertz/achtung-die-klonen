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

	public float getPowerUpChance() {
		return powerUpChance;
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
		chanceOfHole = DEFAULT_CHANCE_OF_HOLE;
		powerUpChance = DEFAULT_POWER_UP_CHANCE;
	}
}
