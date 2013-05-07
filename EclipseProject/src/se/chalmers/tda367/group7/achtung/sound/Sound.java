package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound implements PropertyChangeListener {

	private static Sound instance;

	private Audio powerUpSelf;
	private Audio powerUpEveryoneElse;
	private Audio powerUpEveryone;
	private Audio playerDied;
	private List<Audio> music;
	private Audio currentMusic;
	private static boolean soundEnabled;

	private Sound() {
		soundEnabled = true;
		initSounds();
	}

	public static synchronized Sound getInstance() {
		if (instance == null) {
			instance = new Sound();
		}
		return instance;
	}

	public void initSounds() {

		try {

			this.powerUpSelf = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup4.wav"));

			this.powerUpEveryoneElse = AudioLoader.getAudio("WAV",
					ResourceLoader
							.getResourceAsStream("res/sounds/powerup2.wav"));

			this.powerUpEveryone = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup3.wav"));

			this.playerDied = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/playerdies.wav"));

			this.music = new ArrayList<Audio>();
			this.music
					.add(AudioLoader.getAudio("WAV", ResourceLoader
							.getResourceAsStream("res/sounds/music.wav")));
			this.music
					.add(AudioLoader.getAudio("WAV", ResourceLoader
							.getResourceAsStream("res/sounds/music2.wav")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if (soundEnabled) {
			if (propertyName.equals("PowerUpSELF")) {
				playSound(this.powerUpSelf);
			} else if (propertyName.equals("PowerUpEVERYONE")) {
				playSound(this.powerUpEveryone);
			} else if (propertyName.equals("PowerUpEVERYONE_ELSE")) {
				playSound(this.powerUpEveryoneElse);
			} else if (propertyName.equals("PlayerDied")) {
				playSound(this.playerDied);
			} else if (propertyName.equals("NewRound")) {
				this.currentMusic = this.music.get((int) (music.size() * Math
						.random()));
				currentMusic.playAsMusic(1.0f, 1.0f, true);
			} else if (propertyName.equals("RoundOver")) {
				if (this.currentMusic.isPlaying()) {
					this.currentMusic.stop();
				}
			}
		}

	}

	private void playSound(Audio sound) {
		sound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public static void closeSound() {
		AL.destroy();
	}

	public static void setSound(boolean sound) {
		soundEnabled = sound;
	}
}