package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound implements PropertyChangeListener {

	private Audio powerUpSelf;
	private Audio powerUpEveryoneElse;
	private Audio powerUpEveryone;
	private Audio playerDied;

	public Sound() {
		initSounds();
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
		}
	}
	
	private void playSound(Audio sound) {
		sound.playAsSoundEffect(1.0f, 1.0f, false);
	}
}