package se.chalmers.tda367.group7.achtung;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import se.chalmers.tda367.group7.achtung.controller.GameEngine;


public class Achtung implements Runnable {

	private GameEngine engine = new GameEngine(3);

	private static final double DESIRED_FPS = 60;
	private static final double DESIRED_TICK_RATE = 60;

	private long lastFrame;
	private long lastTick;

	public void start() {
		run();
	}

	@Override
	public void run() {
		try {
			initDisplay();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		initOpenGL();

		long fpsDelay = (long) (1000000000L / DESIRED_FPS);
		long tickDelay = (long) (1000000000L / DESIRED_TICK_RATE);

		while (!Display.isCloseRequested()) {

			// Timing is definitely not perfect. Threading might be possible.

			long tickDelta = System.nanoTime() - this.lastTick;
			if (tickDelta > tickDelay) {
				this.lastTick = System.nanoTime();
				update();
			}

			long fpsDelta = System.nanoTime() - this.lastFrame;
			if (fpsDelta > fpsDelay) {
				this.lastFrame = System.nanoTime();
				render();
			}

			// No sleep is done to keep precision

		}
	}

	private void update() {
		this.engine.update();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		this.engine.render();
		Display.update();
	}

	private void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_LINE_SMOOTH);
		glEnable(GL_POLYGON_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_POINT_SMOOTH);
		glPointSize(10);
		glLineWidth(10);

		// Centers
		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0);
	}

	private void initDisplay() throws LWJGLException {
		Display.setResizable(true);
		Display.setTitle("Achtung");
		Display.create();
	}
}