package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.LWJGLException;

import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.input.LWJGLInputService;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Map;
import se.chalmers.tda367.group7.achtung.model.Round;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;
import se.chalmers.tda367.group7.achtung.sound.Sound;
import se.chalmers.tda367.group7.achtung.view.WorldView;

/**
 * A class containing the game loop, responsible for handling the timing of game
 * logic and rendering.
 */
public class MainController {

	// Game time related
	private static final double TICKS_PER_SECOND = 25;
	private static final long SKIP_TICKS = (long) (1000000000d / TICKS_PER_SECOND);
	private static final int MAX_FRAMESKIP = 5;
	private long nextGameTick;
	private int loops;
	
	// Frame rate related
	private static final double FPS_LIMIT = 60.1; // Set to zero or below to prevent limiting
	private static final long FRAME_DELAY = (long) (1000000000d / FPS_LIMIT);
	private long lastFrame;
	
	// Debug related
	private static final long DEBUG_PRINT_DELAY = 1000000000l;
	private long lastDebugPrint;
	private int dbgFrameCounter;
	private int dbgGameTickCounter;
	private String fpsString = "";
	private String tpsString = "";
	// Sound enabled
	private boolean soundEnabled = false;
	
	private RenderService renderer;
	private InputService inputService;
	private Sound sound;

	private Game game;
	private WorldView worldView;
	private GameController gameController;

	public MainController() {
		try {
			this.renderer = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		this.inputService = new LWJGLInputService();
		//TODO: Hard coded here temporarily
		this.game = new Game();
		this.gameController = new GameController(game);
		gameController.startRound();
		game.addPropertyChangeListener(worldView);
		inputService.addListener(gameController);
		
		this.worldView = new WorldView(game);
		
		
	}

	private long getTickCount() {
		return System.nanoTime();
	}

	public void run() {
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
			while(doLogic && getTickCount() >= nextGameTick && loops < MAX_FRAMESKIP) {	
				if(loops > 0) {
					System.out.println("Logic loop compensating");
				}
				
				doLogic();
				
				nextGameTick += SKIP_TICKS;
				loops++;
				
				// For logic rate calculation
				dbgGameTickCounter++;	
			}
			
			if(FPS_LIMIT <= 0 || getTickCount() - lastFrame >= FRAME_DELAY) {
				lastFrame = getTickCount();
				float interpolation = (getTickCount() + SKIP_TICKS - nextGameTick) / (float)SKIP_TICKS;
				render(interpolation);
				
				// For fps calculation
				dbgFrameCounter++;
			}
			
			// Prints performance information to console
			long deltaDebug = getTickCount() - lastDebugPrint;
			if(deltaDebug >= DEBUG_PRINT_DELAY) {
				lastDebugPrint = getTickCount(); 
				
				// Calculate and print average fps
				double fps = (dbgFrameCounter * 1000000000l) / (double) deltaDebug;
				fpsString = "FPS: " + fps;
				System.out.println(fpsString);
				dbgFrameCounter = 0;

				// Calculate and print average game logic (tick) rate
				double logicRate = (dbgGameTickCounter * 1000000000l)
						/ (double) deltaDebug;
				tpsString = "TPS: " + logicRate;
				System.out.println(tpsString);
				dbgGameTickCounter = 0;
			}
		}
	}

	private void doLogic() {
		game.update();
	}

	private void render(float interpolation) {
		renderer.preDraw();
		
		worldView.render(renderer, interpolation);
		
		// Render debug info
		renderer.drawString(fpsString, 0, 0, 1);
		renderer.drawString(tpsString, 0, 20, 1);
		
		renderer.postDraw();
	}
}
