package se.chalmers.tda367.group7.achtung.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 6;
	private static final float DEFAULT_ROTATION_SPEED = 6f;
	private static final double DEFAULT_CHANCE_OF_HOLE = 0.015;
	private static final float DEFAULT_POWER_UP_CHANCE = 0.01f;
	private static final String FILENAME = "achtung.conf";
	

	private static Settings instance;
	
	private Properties prop;

	private Settings() {
		load();
		
//		resetToDefaults();
	}
	
	private void load() {
		prop = new Properties();
		
		try {
			prop.load(new FileInputStream(FILENAME));
		} catch (FileNotFoundException e) {
			// Use default settings if file does not exist.
			resetToDefaults();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public float getWidth() {
		return Float.parseFloat(prop.getProperty("width", DEFAULT_WIDTH+""));
	}

	public void setWidth(float width) {
		prop.setProperty("width", width+"");
	}

	public float getSpeed() {
		return Float.parseFloat(prop.getProperty("speed", DEFAULT_SPEED+""));
	}

	public void setSpeed(float speed) {
		prop.setProperty("speed", speed+"");
	}

	public float getRotationSpeed() {
		return Float.parseFloat(prop.getProperty("rotation_speed", DEFAULT_ROTATION_SPEED+""));
	}

	public void setRotationSpeed(float rotationSpeed) {
		prop.setProperty("rotation_speed", rotationSpeed+"");
	}

	public double getChanceOfHole() {
		return Double.parseDouble(prop.getProperty("chance_of_hole", DEFAULT_CHANCE_OF_HOLE+""));
	}

	public void setChanceOfHole(double chanceOfHole) {
		prop.setProperty("chance_of_hole", chanceOfHole+"");
	}

	public float getPowerUpChance() {
		return Float.parseFloat(prop.getProperty("power_up_chance", DEFAULT_POWER_UP_CHANCE+""));
	}

	public void setPowerUpChance(float powerUpChance) {
		prop.setProperty("power_up_chance", powerUpChance+"");
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
	public void save() {
		
		try {
			prop.store(new FileOutputStream(FILENAME), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset all settings to the default values.
	 */
	public void resetToDefaults() {

//		this.width = DEFAULT_WIDTH;
//		this.speed = DEFAULT_SPEED;
//		this.rotationSpeed = DEFAULT_ROTATION_SPEED;
//		this.chanceOfHole = DEFAULT_CHANCE_OF_HOLE;
//		this.powerUpChance = DEFAULT_POWER_UP_CHANCE;
	}
}
