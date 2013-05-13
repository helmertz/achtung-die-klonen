package se.chalmers.tda367.group7.achtung.sound;

public class SoundServiceFactory {
	private static SoundService service;
	
	private SoundServiceFactory() {
	}
	
	public static synchronized SoundService getSoundService() {
		if(service == null) {
			service = new Sound();
		}
		return service;
	}
	
}
