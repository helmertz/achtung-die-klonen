package se.chalmers.tda367.group7.achtung.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import se.chalmers.tda367.group7.achtung.input.InputService;
import se.chalmers.tda367.group7.achtung.input.InputServiceFactory;
import se.chalmers.tda367.group7.achtung.input.KeyInputEvent;
import se.chalmers.tda367.group7.achtung.input.KeyInputListener;
import se.chalmers.tda367.group7.achtung.input.MouseInputEvent;
import se.chalmers.tda367.group7.achtung.input.MouseInputListener;
import se.chalmers.tda367.group7.achtung.menu.CustomInputSystem;
import se.chalmers.tda367.group7.achtung.menu.MainMenuController;
import se.chalmers.tda367.group7.achtung.menu.MainMenuController.PlayerInfoHolder;
import se.chalmers.tda367.group7.achtung.model.Game;
import se.chalmers.tda367.group7.achtung.model.Settings;
import se.chalmers.tda367.group7.achtung.rendering.RenderService;
import se.chalmers.tda367.group7.achtung.rendering.RenderServiceFactory;
import se.chalmers.tda367.group7.achtung.sound.SoundService;
import se.chalmers.tda367.group7.achtung.sound.SoundServiceFactory;
import se.chalmers.tda367.group7.achtung.view.GameView;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.spi.render.MouseCursor;
import de.lessvoid.nifty.spi.render.RenderDevice;

/**
 * A class containing the game loop, responsible for handling the timing of game
 * logic and rendering.
 */
public class MainController implements PropertyChangeListener,
		KeyInputListener, MouseInputListener {

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
	private SoundService sound;
	private boolean finished;

	private Game game;
	private GameView gameView;
	private GameController gameController;

	private final Nifty nifty;
	private boolean atMenu;
	private final MainMenuController menuController;
	private final CustomInputSystem inputSystem;

	public MainController() {

		this.renderer = RenderServiceFactory.getRenderService();

		// The service for supplying mouse and keyboard events
		this.inputService = InputServiceFactory.getInputService();

		this.sound = SoundServiceFactory.getSoundService();

		// Sets this as "root" handler of keyboard and mouse events. Here it's
		// decided where they are passed along to.
		this.inputService.addKeyListener(this);
		this.inputService.addMouseListener(this);

		// Limit nifty output
		java.util.logging.Logger.getLogger("de.lessvoid.nifty").setLevel(
				Level.WARNING);

		// Uses custom nifty InputSystem to control which events are forwarded
		// to nifty
		this.inputSystem = new CustomInputSystem();

		// Small modification to LwjglRenderDevice because of cursor problems.
		RenderDevice renderDevice = new LwjglRenderDevice() {
			@Override
			public MouseCursor createMouseCursor(String filename, int hotspotX,
					int hotspotY) throws IOException {
				return null;
			}
		};
		this.nifty = new Nifty(renderDevice, new NullSoundDevice(),
				this.inputSystem, new LWJGLTimeProvider());

		// Load main menu from xml
		this.nifty.fromXml("menu/main-menu.xml", "start");

		// To know when something happens, observe the nifty screen controller
		// TODO perhaps do this interaction differently, without this being a
		// listener
		Screen niftyScreen = this.nifty.getScreen("start");
		this.menuController = (MainMenuController) niftyScreen
				.getScreenController();
		this.menuController.addListener(this);

		setAtMenu(true);
	}

	private long getTickCount() {
		return System.nanoTime();
	}

	public void run() {
		this.nextGameTick = getTickCount();
		while (!this.finished && !this.renderer.isCloseRequested()) {

			// Called as often as possible, so events gets created directly at
			// key press
			this.inputService.update();

			boolean doLogic = true;

			if (this.renderer.isActive() && !this.atMenu
					&& this.game.getCurrentRound().isRoundActive()) {
				this.sound.playMusic();
			}

			// Essentially pauses the game when not in focus
			if (!this.renderer.isActive()) {
				doLogic = false;

				this.sound.pauseMusic();

				// Allow some sleeping to minimize cpu usage
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}

				// Needs to be set to now so that no compensation is done
				this.nextGameTick = getTickCount();
			}
			if (this.atMenu) {
				this.nifty.update();
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
				this.dbgFrameCounter = 0;

				// Calculate and print average game logic (tick) rate
				double logicRate = (this.dbgGameTickCounter * 1000000000l)
						/ (double) deltaDebug;
				this.tpsString = "TPS: "
						+ String.format(Locale.US, "%.2f", logicRate);
				this.dbgGameTickCounter = 0;
			}
		}
		this.sound.closeSound();
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
	public boolean onKeyInputEvent(KeyInputEvent event) {
		if (!event.isRepeat() && event.isPressed()
				&& event.getKey() == Keyboard.KEY_ESCAPE) {
			if (this.atMenu) {
				returnToGame();
			} else {
				setAtMenu(true);
				this.sound.pauseMusic();
			}
			return true;
		}
		if (this.atMenu) {
			if (this.menuController.onKeyInputEvent(event)) {
				return true;
			}
			this.inputSystem.addKeyEvent(event);
		} else if (this.gameController != null) {
			this.gameController.onKeyInputEvent(event);
		}
		return false;
	}

	@Override
	public boolean onMouseInputEvent(MouseInputEvent event) {
		if (this.atMenu) {
			this.inputSystem.addMouseEvent(event);
		}
		return false;
	}

	public void toggleMenu() {
		setAtMenu(!this.atMenu);
	}

	private void setAtMenu(boolean atMenu) {
		if (this.atMenu != atMenu) {
			this.atMenu = atMenu;

			this.menuController.setShowContinue(this.game != null
					&& !this.game.isOver());

			if (atMenu) {
				// Sends a false event to Nifty, fixes problem with nifty button
				// press
				this.inputSystem.addMouseEvent(new MouseInputEvent(0, -1, -1,
						0, false));
			}
		}
	}

	public void startGame(List<PlayerInfoHolder> pInfoList) {
		this.game = new Game();
		this.game.addPropertyChangeListener(this.sound);

		this.gameController = new GameController(this.game);

		for (PlayerInfoHolder pih : pInfoList) {
			this.gameController.addPlayer(pih.getName(), pih.getLeftKey(),
					pih.getRightKey(), pih.getColor());
		}

		this.gameController.startRound();

		this.gameView = new GameView(this.game);
		this.game.addPropertyChangeListener(this.gameView);
		setAtMenu(false);

		// Save settings
		Settings.getInstance().save();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("exit")) {
			this.finished = true;
		} else if (evt.getPropertyName().equals("startPressed")) {
			// TODO - Better check here?
			if (evt.getNewValue() instanceof List<?>) {
				startGame((List<PlayerInfoHolder>) evt.getNewValue());
			}
		} else if (evt.getPropertyName().equals("continuePressed")) {
			returnToGame();
		}
	}

	private void returnToGame() {
		if (this.game != null && !this.game.isOver()) {
			setAtMenu(false);
		}
	}
}
