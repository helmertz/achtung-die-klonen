package se.chalmers.tda367.group7.achtung.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.screen.Screen;
import se.chalmers.tda367.group7.achtung.input.InputEvent;
import se.chalmers.tda367.group7.achtung.input.InputListener;
import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.input.LWJGLInputService;
import se.chalmers.tda367.group7.achtung.menu.MainMenuController;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.lwjgl.LWJGLRenderService;
import se.chalmers.tda367.group7.achtung.sound.Sound;
import se.chalmers.tda367.group7.achtung.view.GameView;

/**
 * A class containing the game loop, responsible for handling the timing of game
 * logic and rendering.
 */
public class MainController implements InputListener, PropertyChangeListener {

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

	private Game game;
	private GameView gameView;
	private GameController gameController;

	private final Nifty nifty;
	private boolean atMenu = true;
	private final MainMenuController menuController;

	public MainController() {
		try {
			this.renderer = new LWJGLRenderService();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		this.inputService = new LWJGLInputService();

		this.nifty = new Nifty(new LwjglRenderDevice(), new NullSoundDevice(),
				new LwjglInputSystem(), new LWJGLTimeProvider());

		this.nifty.fromXml("menu/helloworld.xml", "start");

		// TODO perhaps do this interaction differently, without this being a
		// listener
		Screen niftyScreen = this.nifty.getScreen("start");
		this.menuController = (MainMenuController) niftyScreen
				.getScreenController();
		this.menuController.addListener(this);
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
			this.nifty.update();

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
			if (this.atMenu) {
				doLogic = false;
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
				this.fpsString = "FPS: "
						+ String.format(Locale.US, "%.2f", fps);
				System.out.println(this.fpsString);
				this.dbgFrameCounter = 0;

				// Calculate and print average game logic (tick) rate
				double logicRate = (this.dbgGameTickCounter * 1000000000l)
						/ (double) deltaDebug;
				this.tpsString = "TPS: "
						+ String.format(Locale.US, "%.2f", logicRate);
				System.out.println(this.tpsString);
				this.dbgGameTickCounter = 0;
			}
		}
		Sound.closeSound();
	}

	private void doLogic() {
		this.game.update();
	}

	private void render(float interpolation) {
		this.renderer.preDraw();

		if (!this.atMenu) {
			this.renderer.setScaled(true);

			this.gameView.render(this.renderer, interpolation);
		}

		this.renderer.setScaled(false);

		// Render debug info
		this.renderer.drawString(this.fpsString, 0, 0, 1);
		this.renderer.drawString(this.tpsString, 0, 20, 1);

		if (Display.wasResized()) {
			this.nifty.resolutionChanged();
		}

		if (this.atMenu) {
			this.nifty.render(false);
		}
		this.renderer.postDraw();
	}

	@Override
	public boolean onInputEvent(InputEvent event) {
		if (event.isPressed() && event.getKey() == Keyboard.KEY_ESCAPE) {
			toggleMenu();
			return true;
		}
		this.gameController.onInputEvent(event);
		return false;
	}

	public void toggleMenu() {
		this.atMenu = !this.atMenu;
	}

	public void startGame() {
		this.game = new Game();
		this.game.addPropertyChangeListener(Sound.getInstance());

		this.gameController = new GameController(this.game);
		this.gameController.startRound();
		this.inputService.addListener(this);

		this.gameView = new GameView(this.game);
		this.game.addPropertyChangeListener(this.gameView);
		toggleMenu();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("startPressed")) {
			startGame();
		}
	}
}
