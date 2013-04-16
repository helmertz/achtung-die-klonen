package se.chalmers.tda367.group7.achtung.rendering;

import org.lwjgl.LWJGLException;

import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;

public class ExampleUsage {

	public static void main(String[] args) {
		RenderService renderService = null;
		try {
			renderService = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		renderService.setBackgroundColor(Color.BLUE);
		renderService.setViewAreaSize(500, 500);
		
		while(!renderService.isCloseRequested()) {
			renderService.preDraw();
		
			// Draws a square showing where (0,0) is
			renderService.drawFilledRect(-10, -10, 20, 20, Color.WHITE);

			// Should draw an X covering the center of the screen
			renderService.drawLine(125, 100, 375, 375, 10, Color.RED);
			renderService.drawLine(125, 375, 375, 100, 10, Color.GREEN);
			
			// Should render Hello World! at the top of the screen
			renderService.drawString("Hello World!", 150, 0, 1f);
			
			// The same thing, but twice the size below
			renderService.drawString("Hello World!", 60, 30, 2f);
			
			renderService.postDraw();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
	}
}
