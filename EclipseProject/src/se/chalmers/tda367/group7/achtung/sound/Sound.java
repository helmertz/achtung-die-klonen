package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound implements PropertyChangeListener {
	
	private static Sound instance;

	private Audio powerUpSelf;
	private Audio powerUpEveryoneElse;
	private Audio powerUpEveryone;
	private Audio playerDied;
	private Audio music;

	private Sound() {
		initSounds();
	}

	public static synchronized Sound getInstance() {
		if(instance == null) {
			instance = new Sound();
		}
		return instance;
	}
	public void initSounds() {

		try {

			powerUpSelf = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup4.wav"));

			powerUpEveryoneElse = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup2.wav"));

			powerUpEveryone = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup3.wav"));

			playerDied = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/playerdies.wav"));

			music = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("res/sounds/music.wav"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		if (propertyName.equals("PowerUpSELF")) {
			playSound(powerUpSelf);
		} else if (propertyName.equals("PowerUpEVERYONE")) {
			playSound(powerUpEveryone);
		} else if (propertyName.equals("PowerUpEVERYONE_ELSE")) {
			playSound(powerUpEveryoneElse);
		} else if (propertyName.equals("PlayerDied")) {
			playSound(playerDied);
		} else if (propertyName.equals("NewRound")) {
			music.playAsMusic(1.0f, 1.0f, false);
		} else if (propertyName.equals("RoundOver")) {
			if (music.isPlaying()) {
				music.stop();
			}
		}
	}

	private void playSound(Audio sound) {
		sound.playAsSoundEffect(1.0f, 1.0f, false);
	}
}