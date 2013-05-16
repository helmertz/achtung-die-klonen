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
	private static final boolean DEFAULT_MUSIC_ENABLED = true;
	private static final boolean DEFAULT_SOUND_EFFECTS_ENABLED = true;
	private static final String FILENAME = "achtung.conf";

	private static Settings instance;

	private Properties prop;

	private Settings() {
		load();
	}

	private void load() {
		this.prop = new Properties();

		try {
			this.prop.load(new FileInputStream(FILENAME));
		} catch (FileNotFoundException e) {
			// Use default settings if file does not exist.
			resetToDefaults();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public float getWidth() {
		return Float.parseFloat(this.prop.getProperty("width"));
	}

	public void setWidth(float width) {
		this.prop.setProperty("width", width + "");
	}

	public float getSpeed() {
		return Float.parseFloat(this.prop.getProperty("speed"));
	}

	public void setSpeed(float speed) {
		this.prop.setProperty("speed", speed + "");
	}

	public float getRotationSpeed() {
		return Float.parseFloat(this.prop.getProperty("rotation_speed"));
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.prop.setProperty("rotation_speed", rotationSpeed + "");
	}

	public double getChanceOfHole() {
		return Double.parseDouble(this.prop.getProperty("chance_of_hole"));
	}

	public void setChanceOfHole(double chanceOfHole) {
		this.prop.setProperty("chance_of_hole", chanceOfHole + "");
	}

	public float getPowerUpChance() {
		return Float.parseFloat(this.prop.getProperty("power_up_chance"));
	}

	public void setPowerUpChance(float powerUpChance) {
		this.prop.setProperty("power_up_chance", powerUpChance + "");
	}
	
	public boolean isMusicEnabled() {
		return Boolean.parseBoolean(prop.getProperty("musicEnabled"));
	}
	
	public void setMusicEnabled(boolean musicEnabled) {
		prop.setProperty("musicEnabled", musicEnabled+"");
	}
	
	public boolean isSoundEffectsEnabled() {
		return Boolean.parseBoolean(prop.getProperty("soundEffectsEnabled"));
	}

	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		prop.setProperty("soundEffectsEnabled", soundEffectsEnabled+"");
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
			this.prop.store(new FileOutputStream(FILENAME), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset all settings to the default values.
	 */
	public void resetToDefaults() {
		setWidth(DEFAULT_WIDTH);
		setSpeed(DEFAULT_SPEED);
		setRotationSpeed(DEFAULT_ROTATION_SPEED);
		setChanceOfHole(DEFAULT_CHANCE_OF_HOLE);
		setPowerUpChance(DEFAULT_POWER_UP_CHANCE);
		setMusicEnabled(DEFAULT_MUSIC_ENABLED);
		setSoundEffectsEnabled(DEFAULT_SOUND_EFFECTS_ENABLED);
		
		save();
	}
}
