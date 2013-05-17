package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeListener;

public interface SoundService extends PropertyChangeListener {

	void pauseMusic();

	void playMusic();

	void closeSound();

}
