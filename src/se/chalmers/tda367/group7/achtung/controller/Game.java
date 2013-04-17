package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.LWJGLException;

import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.input.LWJGLInputService;
import se.chalmers.tda367.group7.achtung.model.World;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;
import se.chalmers.tda367.group7.achtung.view.WorldView;

/**
 * A class containing the game loop, responsible for handling the timing of game
 * logic and rendering.
 */
public class Game {

	private final int TICKS_PER_SECOND = 25;
	private final int SKIP_TICKS = 1000000000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	
	private RenderService renderer;
	private InputService inputService;
	private int loops;
	private long nextGameTick = getTickCount();
	private World world;
	private WorldView worldView;
	private WorldController worldController;

	public Game() {
		try {
			this.renderer = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		this.inputService = new LWJGLInputService();
		// Hard coded here temporarily
		this.world = new World(1000,1000);
		this.worldView = new WorldView(world);
		
		this.worldController = new WorldController(world);
		
		inputService.addListener(worldController);
	}

	private long getTickCount() {
		return System.nanoTime();
	}

	public void run() {
		// TODO implement a proper game loop. Check
		// http://www.koonsolo.com/news/dewitters-gameloop/ for some different
		// types. Interpolation type renderer would probably work well, but is a
		// bit more difficult to implement.
		while (!renderer.isCloseRequested()) {
			loops = 0;

			// Called as often as possible, so events gets created directly at key press
			inputService.update();
			
			while(getTickCount() > nextGameTick && loops < MAX_FRAMESKIP) {	
				logic();
				
				nextGameTick += SKIP_TICKS;
				loops++;
			}
			
			// TODO implement being able to limit fps
			float interpolation = (getTickCount() + SKIP_TICKS - nextGameTick) / (float)SKIP_TICKS;
			render(interpolation);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	private void logic() {
		world.update();
	}

	private void render(float interpolation) {
		renderer.preDraw();
		
		worldView.render(renderer, interpolation);
		
		renderer.postDraw();
	}
}
