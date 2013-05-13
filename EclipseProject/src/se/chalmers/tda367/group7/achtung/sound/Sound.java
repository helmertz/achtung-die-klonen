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

	private Audio powerUpSelf;
	private Audio powerUpEveryoneElse;
	private Audio powerUpEveryone;
	private Audio playerDied;
	private List<Audio> music;
	private Audio currentMusic;
	private float currentPosition;
	private boolean soundEnabled = true;

	public Sound() {
		initSounds();
	}

	private void initSounds() {

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
		if (this.soundEnabled) {
			if (propertyName.equals("PowerUpSELF")) {
				playSoundEffect(this.powerUpSelf);
			} else if (propertyName.equals("PowerUpEVERYONE")) {
				playSoundEffect(this.powerUpEveryone);
			} else if (propertyName.equals("PowerUpEVERYONE_ELSE")) {
				playSoundEffect(this.powerUpEveryoneElse);
			} else if (propertyName.equals("PlayerDied")) {
				playSoundEffect(this.playerDied);
			} else if (propertyName.equals("NewRound")) {
				this.currentMusic = this.music
						.get((int) (this.music.size() * Math.random()));
				this.currentMusic.playAsMusic(1.0f, 1.0f, true);
			} else if (propertyName.equals("RoundOver")) {
				if (this.currentMusic.isPlaying()) {
					this.currentMusic.stop();
				}
			}
		}

	}

	private void playSoundEffect(Audio sound) {
		sound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void pauseMusic() {
		if(currentMusic != null && currentMusic.isPlaying()) {
		currentPosition = currentMusic.getPosition();
		currentMusic.stop();
		}
	}
	
	public void playMusic() {
		if(currentMusic != null  && !currentMusic.isPlaying()) {
			currentMusic.playAsMusic(1.0f, 1.0f, true);
			currentMusic.setPosition(currentPosition);
		}
	}

	public void closeSound() {
		AL.destroy();
	}

	public void setSound(boolean sound) {
		this.soundEnabled = sound;
	}
}
