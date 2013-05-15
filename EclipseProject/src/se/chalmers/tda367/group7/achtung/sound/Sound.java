package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

class Sound implements SoundService {

	private static Sound instance;

	private Audio powerUpSelf;
	private Audio powerUpEveryoneElse;
	private Audio powerUpEveryone;
	private Audio playerDied;
	private List<Audio> music;
	private Audio currentMusic;
	private float currentPosition = 0f;
	private boolean soundEffectsEnabled = true;
	private boolean musicEnabled = true;

	private Sound() {
		initSounds();
	}

	public static synchronized Sound getInstance() {
		if (instance == null) {
			instance = new Sound();
		}
		return instance;
	}

	private void initSounds() {

		try {

			this.powerUpSelf = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("sounds/powerup4.wav"));

			this.powerUpEveryoneElse = AudioLoader.getAudio("WAV",
					ResourceLoader
							.getResourceAsStream("sounds/powerup2.wav"));

			this.powerUpEveryone = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("sounds/powerup3.wav"));

			this.playerDied = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("sounds/playerdies.wav"));

			this.music = new ArrayList<Audio>();
			this.music
					.add(AudioLoader.getAudio("WAV", ResourceLoader
							.getResourceAsStream("sounds/music.wav")));
			this.music
					.add(AudioLoader.getAudio("WAV", ResourceLoader
							.getResourceAsStream("sounds/music2.wav")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		if (this.soundEffectsEnabled) {
			if (propertyName.equals("PowerUpSELF")) {
				playSoundEffect(this.powerUpSelf);
			} else if (propertyName.equals("PowerUpEVERYONE")) {
				playSoundEffect(this.powerUpEveryone);
			} else if (propertyName.equals("PowerUpEVERYONE_ELSE")) {
				playSoundEffect(this.powerUpEveryoneElse);
			} else if (propertyName.equals("PlayerDied")) {
				playSoundEffect(this.playerDied);
			}
		}

		if (this.musicEnabled) {
			if (propertyName.equals("NewRound")) {
				this.currentMusic = getRandomMusic();
				playMusic();
			} else if (propertyName.equals("RoundOver")) {
				if (this.currentMusic.isPlaying()) {
					this.currentMusic.stop();
					this.currentMusic = null;
				}
			}
		}

	}

	private void playSoundEffect(Audio sound) {
		sound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	private Audio getRandomMusic() {
		return this.music.get((int) (this.music.size() * Math.random()));
	}

	@Override
	public void pauseMusic() {
		if (this.currentMusic != null && this.currentMusic.isPlaying()) {
			this.currentPosition = this.currentMusic.getPosition();
			this.currentMusic.stop();
		}
	}

	@Override
	public void playMusic() {
		if (!this.currentMusic.isPlaying() && this.musicEnabled) {
			if (this.currentMusic == null) {
				this.currentMusic = getRandomMusic();
			}
			this.currentMusic.playAsMusic(1.0f, 1.0f, true);
			this.currentMusic.setPosition(this.currentPosition);
			this.currentPosition = 0f;
		}
	}

	@Override
	public void closeSound() {
		AL.destroy();
	}

	@Override
	public void setSoundEffectEnabled(boolean sound) {
		this.soundEffectsEnabled = sound;
	}

	@Override
	public void setMusicEnabled(boolean music) {
		if (!music) {
			pauseMusic();
		}
		this.musicEnabled = music;
	}
}
