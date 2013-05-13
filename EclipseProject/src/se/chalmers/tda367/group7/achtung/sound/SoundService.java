package se.chalmers.tda367.group7.achtung.sound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface SoundService extends PropertyChangeListener {
	
	void propertyChange(PropertyChangeEvent evt);
	
	void pauseMusic();
	
	void playMusic();
	
	void closeSound();
	
	void setSound(boolean sound);

}
