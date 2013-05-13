package se.chalmers.tda367.group7.achtung.rendering;

public class RenderServiceFactory {

	private RenderServiceFactory() {
	}

	public static synchronized RenderService getRenderService() {
		return LWJGLRenderService.getInstance();
	}
}
