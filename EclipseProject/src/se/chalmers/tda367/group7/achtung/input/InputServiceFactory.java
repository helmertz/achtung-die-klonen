package se.chalmers.tda367.group7.achtung.input;

public class InputServiceFactory {
	private InputServiceFactory() {
	}

	public static InputService getInputService() {
		return LWJGLInputService.getInstance();
	}
}
