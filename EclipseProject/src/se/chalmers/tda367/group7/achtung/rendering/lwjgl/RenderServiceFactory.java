package se.chalmers.tda367.group7.achtung.rendering.lwjgl;

import org.lwjgl.LWJGLException;

public class RenderServiceFactory {
	
	private static RenderService service;
	
	private RenderServiceFactory() {
	}
	
	public static synchronized RenderService getRenderService() {
		if(service == null) {
			try {
				service = new LWJGLRenderService();
			} catch (LWJGLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return service;
	}

}
