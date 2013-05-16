package se.chalmers.tda367.group7.achtung.sound;

public class SoundServiceFactory {

	private SoundServiceFactory() {
	}

	public static synchronized SoundService getSoundService() {
		return Sound.getInstance();
	}

}
