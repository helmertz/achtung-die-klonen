package se.chalmers.tda367.group7.achtung.controller;

import org.lwjgl.LWJGLException;

import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.input.LWJGLInputService;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;
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
	private static final double FPS_LIMIT = 60.1; // Set to zero or below to
													// prevent limiting
	private static final long FRAME_DELAY = (long) (1000000000d / FPS_LIMIT);
	private long lastFrame;

	// Debug related
	private static final long DEBUG_PRINT_DELAY = 1000000000l;
	private long lastDebugPrint;
	private int dbgFrameCounter;
	private int dbgGameTickCounter;
	private String fpsString = "";
	private String tpsString = "";

	private RenderService renderer;
	private final InputService inputService;

	private final Game game;
	private final WorldView worldView;
	private final GameController gameController;

	public MainController() {
		try {
			this.renderer = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		this.inputService = new LWJGLInputService();
		// TODO: Hard coded here temporarily
		this.game = new Game();

		this.gameController = new GameController(this.game);
		this.gameController.startRound();
		this.inputService.addListener(this.gameController);

		this.worldView = new WorldView(this.game);
		this.game.addPropertyChangeListener(this.worldView);
	}

	private long getTickCount() {
		return System.nanoTime();
	}

	public void run() {
		// http://www.koonsolo.com/news/dewitters-gameloop/ for some different
		// types. Interpolation type renderer would probably work well, but is a
		// bit more difficult to implement.
		this.nextGameTick = getTickCount();
		while (!this.renderer.isCloseRequested()) {

			// Called as often as possible, so events gets created directly at
			// key press
			this.inputService.update();

			boolean doLogic = true;

			// Essentially pauses the game when not in focus
			if (!this.renderer.isActive()) {
				doLogic = false;

				// Allow some sleeping to minimize cpu usage
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}

				// Needs to be set to now so that no compensation is done
				this.nextGameTick = getTickCount();
			}

			this.loops = 0;
			while (doLogic && getTickCount() >= this.nextGameTick
					&& this.loops < MAX_FRAMESKIP) {
				if (this.loops > 0) {
					System.out.println("Logic loop compensating");
				}

				doLogic();

				this.nextGameTick += SKIP_TICKS;
				this.loops++;

				// For logic rate calculation
				this.dbgGameTickCounter++;
			}

			if (FPS_LIMIT <= 0
					|| getTickCount() - this.lastFrame >= FRAME_DELAY) {
				this.lastFrame = getTickCount();
				float interpolation = (getTickCount() + SKIP_TICKS - this.nextGameTick)
						/ (float) SKIP_TICKS;
				render(interpolation);

				// For fps calculation
				this.dbgFrameCounter++;
			}

			// Prints performance information to console
			long deltaDebug = getTickCount() - this.lastDebugPrint;
			if (deltaDebug >= DEBUG_PRINT_DELAY) {
				this.lastDebugPrint = getTickCount();

				// Calculate and print average fps
				double fps = (this.dbgFrameCounter * 1000000000l)
						/ (double) deltaDebug;
				this.fpsString = "FPS: " + fps;
				System.out.println(this.fpsString);
				this.dbgFrameCounter = 0;

				// Calculate and print average game logic (tick) rate
				double logicRate = (this.dbgGameTickCounter * 1000000000l)
						/ (double) deltaDebug;
				this.tpsString = "TPS: " + logicRate;
				System.out.println(this.tpsString);
				this.dbgGameTickCounter = 0;
			}
		}
	}

	private void doLogic() {
		this.game.update();
	}

	private void render(float interpolation) {
		this.renderer.preDraw();

		this.worldView.render(this.renderer, interpolation);

		// Render debug info
		this.renderer.drawString(this.fpsString, 0, 0, 1);
		this.renderer.drawString(this.tpsString, 0, 20, 1);

		this.renderer.postDraw();
	}
}
