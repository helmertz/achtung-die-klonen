package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.LWJGLException;

import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;

/**
 * A class containing the game loop, responsible for handling the timing of game
 * logic and rendering.
 */
public class Game {

	private RenderService renderer;

	public Game() {
		try {
			this.renderer = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run() {
		// TODO implement a proper game loop. Check
		// http://www.koonsolo.com/news/dewitters-gameloop/ for some different
		// types. Interpolation type renderer would probably work well, but is a
		// bit more difficult to implement.
		while (!renderer.isCloseRequested()) {
			logic();
			render();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	private void logic() {
		// TODO game logic
	}

	private void render() {
		renderer.preDraw();
		
		// TODO SomeOtherClass.render(renderer)
		
		renderer.postDraw();
	}
}
