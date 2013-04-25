package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound implements PropertyChangeListener{

	private Audio powerupSelf;
	private Audio powerupEveryoneElse;
	private Audio powerupEveryone;

	public Sound() {
		init();
	}
	
	public void init() {

		try {

			powerupSelf = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup4.wav"));

			powerupEveryoneElse = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup2.wav"));

			powerupEveryone = AudioLoader.getAudio("WAV", ResourceLoader
					.getResourceAsStream("res/sounds/powerup3.wav"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		if(propertyName.equals("PowerUpSELF")) {
			powerupSelf.playAsSoundEffect(1.0f, 1.0f, false);
		} else if(propertyName.equals("PowerUpEVERYONE")) {
			powerupEveryone.playAsSoundEffect(1.0f, 1.0f, false);
		} else if(propertyName.equals("PowerUpEVERYONE_ELSE"))	{
			powerupEveryoneElse.playAsSoundEffect(1.0f, 1.0f, false);
		}
	}
}