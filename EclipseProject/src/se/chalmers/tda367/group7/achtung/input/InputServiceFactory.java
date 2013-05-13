package se.chalmers.tda367.group7.achtung.input;

public class InputServiceFactory {
	
	private static InputService service;
	
	private InputServiceFactory() {
	}
	
	public static synchronized InputService  getInputService() {
		if(service == null) {
			service = new LWJGLInputService();
		}
		return service;
	}
}
