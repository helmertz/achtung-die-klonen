package se.chalmers.tda367.group7.achtung.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	public static final float MIN_WIDTH = 3;
	public static final float MAX_WIDTH = 65;
	public static final float MIN_SPEED = 1;
	public static final float MAX_SPEED = 12;
	public static final float MIN_ROTATION_SPEED = 3;
	public static final float MAX_ROTATION_SPEED = 10;
	public static final float MIN_CHANCE_OF_HOLE = 0;
	public static final float MAX_CHANCE_OF_HOLE = 0.2f;
	public static final float MIN_POWER_UP_CHANCE = 0;
	public static final float MAX_POWER_UP_CHANCE = 0.1f;
	public static final float MIN_GOAL = 1;
	public static final float MAX_GOAL = 99;

	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_SPEED = 6;
	private static final float DEFAULT_ROTATION_SPEED = 6f;
	private static final float DEFAULT_CHANCE_OF_HOLE = 0.015f;
	private static final float DEFAULT_POWER_UP_CHANCE = 0.01f;
	private static final boolean DEFAULT_MUSIC_ENABLED = true;
	private static final boolean DEFAULT_SOUND_EFFECTS_ENABLED = true;
	private static final int DEFAULT_GOAL_SCORE = 10;
	private static final boolean DEFAULT_AUTO_GOAL = true;

	private static final String FILENAME = "achtung.conf";

	private static Settings instance;

	private Properties prop;

	private Settings() {
		load();
	}

	private void load() {
		this.prop = new Properties();

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(FILENAME);
			this.prop.load(inputStream);
		} catch (FileNotFoundException e) {
			// Use default settings if file does not exist.
			resetToDefaults();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

	public float getChanceOfHole() {
		return Float.parseFloat(this.prop.getProperty("chance_of_hole"));
	}

	public void setChanceOfHole(float chanceOfHole) {
		this.prop.setProperty("chance_of_hole", chanceOfHole + "");
	}

	public float getPowerUpChance() {
		return Float.parseFloat(this.prop.getProperty("power_up_chance"));
	}

	public void setPowerUpChance(float powerUpChance) {
		this.prop.setProperty("power_up_chance", powerUpChance + "");
	}

	public boolean isMusicEnabled() {
		return Boolean.parseBoolean(this.prop.getProperty("musicEnabled"));
	}

	public void setMusicEnabled(boolean musicEnabled) {
		this.prop.setProperty("musicEnabled", musicEnabled + "");
	}

	public boolean isSoundEffectsEnabled() {
		return Boolean.parseBoolean(this.prop
				.getProperty("soundEffectsEnabled"));
	}

	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		this.prop.setProperty("soundEffectsEnabled", soundEffectsEnabled + "");
	}

	public void setGoalScore(int score) {
		this.prop.setProperty("goal_score", score + "");
	}

	public boolean isAutoGoalEnabled() {
		String loaded = this.prop.getProperty("auto_goal");
		if(loaded == null) {
			setAutoGoalEnabled(DEFAULT_AUTO_GOAL);
			return DEFAULT_AUTO_GOAL;
		}
		return Boolean.parseBoolean(this.prop.getProperty("auto_goal"));
	}

	public void setAutoGoalEnabled(boolean autoGoal) {
		this.prop.setProperty("auto_goal", autoGoal + "");
	}
	
	public int getGoalScore() {
		String loaded = this.prop.getProperty("goal_score");
		if(loaded == null) {
			setGoalScore(DEFAULT_GOAL_SCORE);
			return DEFAULT_GOAL_SCORE;
		}
		return Integer.parseInt(loaded);
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

		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(FILENAME);
			this.prop.store(outputStream, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		setGoalScore(DEFAULT_GOAL_SCORE);
		setAutoGoalEnabled(DEFAULT_AUTO_GOAL);

		save();
	}
}
