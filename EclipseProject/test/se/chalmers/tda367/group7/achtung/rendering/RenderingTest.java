package se.chalmers.tda367.group7.achtung.rendering;

import java.io.File;

import org.lwjgl.LWJGLUtil;

import se.chalmers.tda367.group7.achtung.model.Color;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.RenderServiceFactory;

/**
 * Not a unit test, but can be run and manually examined.
 */
public class RenderingTest {

	public static void main(String[] args) {
		// Sets up native bindings, location depends on platform
		String nativePath = new File(new File(System.getProperty("user.dir"),
				"native"), LWJGLUtil.getPlatformName()).getAbsolutePath();
		System.setProperty("org.lwjgl.librarypath", nativePath);

		RenderService renderService = RenderServiceFactory.getRenderService();

		renderService.setBackgroundColor(Color.BLUE);
		renderService.setViewAreaSize(800, 500);
		renderService.setScaled(true);

		while (!renderService.isCloseRequested()) {
			renderService.preDraw();

			// Draws a border at the inner part of the view area
			renderService.drawLinedRect(0, 0, renderService.getViewAreaWidth(),
					renderService.getViewAreaHeight(), 5, Color.WHITE);

			// Draws a square showing where (0,0) is
			renderService.drawFilledRect(-10, -10, 20, 20, Color.WHITE);

			// Should draw an X covering the center of the screen
			renderService.drawLine(renderService.getViewAreaWidth() / 4f,
					renderService.getViewAreaHeight() / 4f,
					3f * renderService.getViewAreaWidth() / 4f,
					3f * renderService.getViewAreaHeight() / 4f, 10, Color.RED);
			renderService.drawLine(renderService.getViewAreaWidth() / 4f,
					3f * renderService.getViewAreaHeight() / 4f,
					3f * renderService.getViewAreaWidth() / 4f,
					renderService.getViewAreaHeight() / 4f, 10, Color.GREEN);

			// Draws a black circle at the center of the viewport
			renderService
					.drawCircleCentered(renderService.getViewAreaWidth() / 2f,
							renderService.getViewAreaHeight() / 2f, 50, 20,
							Color.BLACK);

			// Should render Hello World! at the top of the screen
			renderService.drawString("Hello World!", 300, 0, 1f);

			// The same thing, but twice the size below
			renderService.drawString("Hello World!", 220, 30, 2f);

			renderService.postDraw();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
}
