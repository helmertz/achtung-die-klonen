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

	private final double TICKS_PER_SECOND = 25;
	private final long SKIP_TICKS = (long) (1000000000 / TICKS_PER_SECOND);
	private final int MAX_FRAMESKIP = 5;
	
	// Set to zero or below to prevent limiting
	private final double FPS_LIMIT = 60.1;
	private final long FRAME_DELAY = (long) (1000000000 / FPS_LIMIT);

	private long lastFrame;
	
	private RenderService renderer;
	private InputService inputService;
	private int loops;
	private long nextGameTick;
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
		nextGameTick = getTickCount();
		while (!renderer.isCloseRequested()) {
			
			// Called as often as possible, so events gets created directly at key press
			inputService.update();
			
			boolean doLogic = true; 

			// Essentially pauses the game when not in focus
			if(!renderer.isActive()) {
				doLogic = false;
				
				// Allow some sleeping to minimize cpu usage
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				
				// Needs to be set to now so that no compensation is done
				nextGameTick = getTickCount();
			}
			
			loops = 0;
			while(doLogic && getTickCount() > nextGameTick && loops < MAX_FRAMESKIP) {	
				if(loops > 0) {
					System.out.println("Logic loop compensating");
				}
				
				logic();
				
				nextGameTick += SKIP_TICKS;
				loops++;
			}
			
			if(FPS_LIMIT <= 0 || getTickCount() - lastFrame >= FRAME_DELAY) {
				lastFrame = getTickCount();
				float interpolation = (getTickCount() + SKIP_TICKS - nextGameTick) / (float)SKIP_TICKS;
				render(interpolation);
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
